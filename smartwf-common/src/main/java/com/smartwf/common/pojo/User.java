package com.smartwf.common.pojo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @author WCH
 * @Description: 用户
 */
@Data
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 授权临时票据 code 参数
	 */
	private String code;
	/**
	 * wso2子系统注册 key
	 * 
	 * */
	private String clientKey;
	
	/**
	 * wso2子系统注册 secret
	 * 
	 * */
	private String clientSecret;
	/**
	 * wso2子系统注册 redirect_Uri
	 * 
	 * */
	private String redirectUri;
	
    /**
	 * 前端系统token
	 */
    private String smartwfToken;
    /**
	 * wso2 accesstoken
	 */
    private String accessToken;
    
    /**
     * 刷新token凭证
     * */
    private String refreshToken;
    /**
     * wso2 id_token凭证
     * */
    private String idToken;
    /**
     * wso2 注销凭证
     * */
    private String sessionState;
    /**
     * 过期时间
     * */
    private Long dateTime;
    
	/**
     * id
     */
    private Integer id;

    /**
	 * 用户编码
	 */
	private String userCode;
	/**
	 * 登录账号
	 */
	private String loginCode;
	/**
	 * 用户昵称
	 */
	private String userName;
	/**
	 * 登录密码
	 */
	private String pwd;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 办公电话
	 */
	private String phone;
	/**
	 * 性别 0男 1女
	 */
	private Integer sex;
	/**
	 * 头像路径
	 */
	private String avatar;
	/**
	 * 个性签名
	 */
	private String sign;
	/**
	 * 微信号
	 */
	private String wxOpenid;
	/**
	 * qq号
	 */
	private String qqOpenid;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 租户id
	 */
	private Integer tenantId;
	/**
	 * 租户编码
	 */
	private String tenantCode;
	/**
	 * 租户名称
	 */
	private String tenantName;
	/**
	 * 租户域
	 */
	private String tenantDomain;
	/**
	 * 租户密码
	 */
	private String tenantPw;
	
	/**
	 * 审批状态 0-审批通过 1-审批中 2-待审批
	 */
	private String status;
	/**
	 * 管理员类型 0非管理员 1系统管理员 
	 */
	private Integer mgrType;
	/**
	 * 职务
	 */
	private List<TreePost> postList;
	/**
	 * 角色
	 */
	private List<TreeRole> roleList;
	
	/**
	 * 资源菜单
	 */
	//private List<TreeResource> resouceList;
	/**
	 * 组织架构 
	 */
	//private List<TreeOrganization> organizationList;
}
