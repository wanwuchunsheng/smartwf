package com.smartwf.common.thread;

import com.smartwf.common.pojo.User;
import com.smartwf.common.utils.MD5Utils;

/**
 * @Auther: 
 * @Description: 用户本地线程
 */
public class UserThreadLocal {


    // 本地线程，存放登录用户信息
    private static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();


    /**
     * 储存用户
     * @param user
     */
    public static void setUser(User user) {
        userThreadLocal.set(user);
    }


    /**
     * 获取用户
     */
    public static User getUser() {
      // return userThreadLocal.get();
     //初始化用户，等有登录功能再注释掉
     		User user=new User();
     		user.setUserCode("T52EXF");
     		user.setUserName("张三");
     		user.setLoginCode("zhangsan");
     		user.setTenantCode("SMARTWF");
     		user.setPwd(MD5Utils.convertMd5("123456"));
     		user.setEmail("zhangsan@163.com");
     		user.setMobile("13858684039");
     		user.setSex(1);
     		user.setAddress("湖北武汉");
     		user.setRemark("湖北武汉江夏区");
     		user.setId(2);
     		user.setMgrType(1);
     		user.setTenantId(1);
     		return user;
    }

}
