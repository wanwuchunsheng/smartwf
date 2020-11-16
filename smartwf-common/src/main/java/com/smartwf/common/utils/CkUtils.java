package com.smartwf.common.utils;

import java.util.List;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.TreeRole;
import com.smartwf.common.pojo.User;

/**
 * @author WCH
 * 
 * */
public class CkUtils {
	
	
    /**
     * @ 去除前后逗号
     * @param clazz
     * @param xmlStr
     * @return
     */
    public static String regex(String str) {
    	String regex = "^,*|,*$";
    	return str.replaceAll(regex, "");
    }
   
    /**
     * 验证是否平台管理员
     * @param platform_administration: 平台管理员wso2登录名
     * @author WCH
     * @Data 2020-11-12 14:04:56
     * @return
     * */
    public static boolean verifyAdminUser(User user){
    	List<TreeRole> rolelist=user.getRoleList();
    	for(TreeRole tr:rolelist) {
    		if(Constants.SUPER_ADMIN.equalsIgnoreCase(tr.getEngName())) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * 验证是否租户管理员
     * @param user
     * @author WCH
     * @Data 2020-11-12 14:04:56
     * @return
     * */
    public static boolean verifyTenantUser(User user){
    	List<TreeRole> rolelist=user.getRoleList();
    	for(TreeRole tr:rolelist) {
    		if(Constants.SUPER_TENANT.equalsIgnoreCase(tr.getEngName())) {
    			return true;
    		}
    	}
    	return false;
    }
    /**
     * 验证是否风场管理员
     * @param user
     * @author WCH
     * @Data 2020-11-12 14:04:56
     * @return
     * */
    public static boolean verifyWindFarmUser(User user){
    	List<TreeRole> rolelist=user.getRoleList();
    	for(TreeRole tr:rolelist) {
    		if(Constants.SUPER_WINDFARM.equalsIgnoreCase(tr.getEngName())) {
    			return true;
    		}
    	}
    	return false;
    }
}
