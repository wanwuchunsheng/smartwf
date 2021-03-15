package com.smartwf.sm.config.exception;


import java.util.HashMap;
import java.util.Map;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 说明：校验统一 参数校验处理异常
 *        如果校验不通过，就在异常中处理，统一输出格式
 * @author WCH
 * @datetime 2021-3-13 18:14:22
 **/
@RestControllerAdvice
public class RequestValidateExceptionHandle {
	
	//json格式
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Map<String, Object> errorHandler(MethodArgumentNotValidException ex) {
        StringBuilder errorMsg = new StringBuilder();
        BindingResult re = ex.getBindingResult();
        for (ObjectError error : re.getAllErrors()) {
            errorMsg.append(error.getDefaultMessage()).append(",");
        }
        errorMsg.delete(errorMsg.length() - 1, errorMsg.length());
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("code", 400);
        map.put("msg", errorMsg.toString());
        return map;
    }

	//表单格式
    @ExceptionHandler(value = BindException.class)
    public Map<String, Object> errorHandler(BindException ex) {
        BindingResult result = ex.getBindingResult();
        StringBuilder errorMsg = new StringBuilder();
        for (ObjectError error : result.getAllErrors()) {
            errorMsg.append(error.getDefaultMessage()).append(",");
        }
        errorMsg.delete(errorMsg.length() - 1, errorMsg.length());
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("code", 400);
        map.put("msg", errorMsg.toString());
        return map;
    }

    /**
     * 说明：统一参数验证异常
     *   参数不合法马上返回，无需全局检验完成再返回
    * */
    @Bean
    public javax.validation.Validator validator(){
        ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
                .configure()
                .addProperty( "hibernate.validator.fail_fast", "true" )
                .buildValidatorFactory();
        return validatorFactory.getValidator();
 
    }

}
