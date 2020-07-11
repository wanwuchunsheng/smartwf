package com.smartwf.sm.config.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.alibaba.fastjson.JSON;
import com.smartwf.common.annotation.TraceLog;
import com.smartwf.common.aop.AopAround;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.dto.LogDTO;
import com.smartwf.common.pojo.User;
import com.smartwf.common.queue.LogQueue;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.common.utils.MathUtils;
import com.smartwf.common.wso2.Wso2Config;
import com.smartwf.sm.modules.admin.pojo.ExceptionLog;
import com.smartwf.sm.modules.admin.service.ExceptionLogService;
import com.smartwf.sm.modules.admin.service.LogService;

import lombok.extern.log4j.Log4j2;


/**
 * 日志AOP
 * @author WCH
 */
@Aspect
@Component
@Log4j2
@Order(3)
public class LogAspect {


    @Autowired
    private LogService logService;
    
    @Autowired
    private ExceptionLogService exceptionLogService;
    
    @Autowired
    private Wso2Config wso2Config;


    /**
     * 切点
     *
     * @return
     */
    @Pointcut("execution(* com.smartwf.sm.*.*.controller.*.*(..)) && @annotation(com.smartwf.common.annotation.TraceLog)")
    public void webLog() {
    }


    /**
     * 后置返回通知
     *
     * @param joinPoint
     * @throws Throwable
     */
    @AfterReturning(pointcut = "webLog()", returning = "retValue")
    public void doAfterReturningAdvice(JoinPoint joinPoint, Object retValue) {
        AopAround.setLogQueue(joinPoint, retValue);
    }


    /**
     * 后置最终通知
     */
    @After("webLog()")
    public void doAfterAdvice(){
        // 判断队列是有数据。大于100，批量插入数据库
        Queue<LogDTO> queue = LogQueue.getQueue();
        if (queue.size() >= Constants.TWO) {
            int size = queue.size();
            List<LogDTO> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                list.add(queue.poll());
            }
            this.logService.saveLog(list);
        }
    }
    
    /**
     * 后置异常通知
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "webLog()", throwing = "e")
    public void throwss(JoinPoint joinPoint, Throwable e){
    	// 获取RequestAttributes
    	RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    	// 从获取RequestAttributes中获取HttpServletRequest的信息
    	HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
    	ExceptionLog excepLog = new ExceptionLog();
    	try {
    		// 从切面织入点处通过反射机制获取织入点处的方法
    		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    		// 获取切入点所在的方法
    		Method method = signature.getMethod();
    		TraceLog traceLog = method.getAnnotation(TraceLog.class);
    		// 获取请求的类名
    		//请求参数
    		excepLog.setLogRequParam( JSON.toJSONString(request.getParameterMap()) ); 
    		//模块说明
    		excepLog.setLogModul(traceLog.content());
    		//请求类型
    		excepLog.setLogType(wso2Config.smartwfSystemMan);
    		//异常信息 		
    		excepLog.setLogDesc(stackTraceToString(e.getClass().getName() , e.getMessage() , e.getStackTrace() )); 
    		//请求方法名
    		excepLog.setLogMethod(new StringBuffer().append(joinPoint.getTarget().getClass().getName()).append(".").append(method.getName()).toString()); 
    		//操作uri
    		excepLog.setLogUri(request.getRequestURI()); 
    		//ip
    		excepLog.setIpAddress(MathUtils.getIpAddress(request)); 
    		//发生异常时间
    		excepLog.setCreateTime(new Date()); 
    		User user = UserThreadLocal.getUser();
	        if (user != null) {
	        	//操作员名称
	        	excepLog.setLogUser(user.getUserName()); 
	        }
    		//log.info(JSON.toJSON(excepLog));
    		this.exceptionLogService.insert(excepLog);
		} catch (Exception e2) {
			e.printStackTrace();
		}
    }
    
    /**
     * 转换异常信息为字符串
     * 
     * @param exceptionName    异常名称
     * @param exceptionMessage 异常信息
     * @param elements         堆栈信息
     */
    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer strbuff = new StringBuffer();
        for (StackTraceElement stet : elements) {
            strbuff.append(stet + "\n");
        }
        String message = exceptionName + ":" + exceptionMessage + "\n\t" + strbuff.toString();
        return message;
    }

}