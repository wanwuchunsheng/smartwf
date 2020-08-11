package com.smartwf.common.thread;

import com.smartwf.common.pojo.User;

import cn.hutool.json.JSONUtil;
import lombok.extern.log4j.Log4j2;

/**
 * @author WCH
 * @Description: 用户本地线程
 */
@Log4j2
public class UserThreadLocal {


    /**
     * 本地线程，存放登录用户信息
     * */ 
    private static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();


    /**
     * 储存用户
     * @param user
     */
    public static void setUser(User user) {
    	/**
    	 * 先清理，再添加
    	 * */
    	userThreadLocal.remove();
        userThreadLocal.set(user);
    }


    /**
     * 获取用户
     */
    public static User getUser() {
    	/**
        //初始化用户，等有登录功能再注释掉
  		User user=new User();
  		user.setCode("7172ac6b-e847-3cf5-8f31-ec06cb889fa1");
  		user.setClientKey("nqenKoLbjFswa_PQY3CcQacsiqka");
  		user.setClientSecret("qMS_RXTSfS5NU8JkLuX0NG4opHAa");
  		user.setRedirectUri("http://localhost:8500");
  		user.setId(1);
  		user.setUserCode("07c91050-270f-4e5a-889b-258a1dd6ae9e");
  	
  		user.setUserName("平台管理员");
  		user.setLoginCode("admin");
  		user.setTenantCode("admin");
  		user.setPwd("admin");
  		user.setTenantDomain("carbon.super");
  		user.setEmail("zhangsan@163.com");
  		user.setMobile("13858684039");
  		user.setSex(1);
  		user.setAddress("湖北武汉");
  		user.setRemark("湖北武汉江夏区");
  		user.setId(2);
  		user.setMgrType(1);
  		user.setTenantId(1);
  		return user;
  		*/
      //log.info( JSONUtil.toJsonStr(userThreadLocal.get()));
      return userThreadLocal.get();
     
      
    }
   
}
