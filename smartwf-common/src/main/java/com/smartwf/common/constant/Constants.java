package com.smartwf.common.constant;

/**
 * @author WCH
 * @Description: 常量
 */
public class Constants {

    public static final String SESSION_ID = "sessionId";
    
    public static final String ACCESS_TOKEN = "accessToken";

    public static final String SUCCESS = "成功";

    public static final String FAIL = "失败";

    public static final String ERROR = "错误";

    public static final String PER_CENT = "%";

    public static final String EMPTY = "empty";

    public static final String BLANK = "";
    
    public static final String IMAGE = "image";
    
    public static final String ACTIVE = "active";
    
    public static final String USERID = "user_id";
    /** 排班角色 */
    public static final String SHIFT_GROUP = "shift_group_role";
    /** 平台管理员角色 */
    public static final String SUPER_ADMIN="platform_administration";
    /** 租户管理员角色 */
    public static final String SUPER_TENANT="tenants_super_administrator";
    /** 风场管理员角色 */
    public static final String SUPER_WINDFARM="station_manager";
    
    public static final String REDIS_TOPIC = "topic:smartwf_monitor";
    
    public static final String CHAR = ",";
    
    public static final String ID = "id";
    
    public static final String RESOURCES = "Resources";
    
    public static final String MEMBERS = "members";
    
    public static final String GROUPS = "groups";
    
    public static final String ACCESSTOKEN = "access_token";
    
    public static final String ERRORCODE = "error";
    
    public static final String UNKNOWN = "unknown";
    
    public static final int ORDERTYPE = 6;
    
    public static final int ORDERSOURCES = 3;

    public static final int ONE = 1;

    public static final int ZERO = 0;
    
    public static final int TWO = 2;
    
    public static final int ADMIN = 2;
    
    /** 0故障 1严重  2一般  3普通 */
    public static final int ALARMLEVEL = 4; 
    
    /**
     * 默认租户状态
     */ 
    public static final Integer ISSEL = 1;
    
    
    
    /**
     * 自定义状态码
     */
    public static final int INTERNAL_SERVER_ERROR = 500;

    public static final int GONE = 410;

    public static final int CONFLICT = 409;

    public static final int FORBIDDEN = 403;

    public static final int UNAUTHORIZED = 401;

    public static final int BAD_REQUEST = 400;
    
    public static final int EQU_SUCCESS = 200;
    
    public static final int MULTIPLE_CHOICES = 300;

    
    /**
     * wso2 soap请求响应时间
     * */
    public static final int SOCKET_TIME_OUT = 90000;
    
    public static final int CONNECT_TIME_OUT = 90000;
    
    public static final int TOKEN_TIMEOUT = 500;
    
    public static final int APP_TIMEOUT = 43200;
    
    /** 
     * 统一状态码 
     * 参考微信：
     *   https://developers.weixin.qq.com/miniprogram/dev/wxcloud/reference/errcode.html
     * 
     * */
    public static final int ERRCODE502012 = 502012;
    
}
