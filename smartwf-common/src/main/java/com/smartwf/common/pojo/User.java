package com.smartwf.common.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 用户
 */
@Data
public class User extends BasePojo implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	 * 审批状态 0-审批通过 1-审批中 2-待审批
	 */
	private String status;
	/**
	 * 管理员类型 0非管理员 1系统管理员 
	 */
	private Integer mgrType;
	/**
	 * 保密问题
	 */
	private String pwdQuestion;
	/**
	 * 保密问题答案
	 */
	private String pwdQuestionAnswer;
	/**
	 * 保密问题2
	 */
	private String pwdQuestion2;
	/**
	 * 保密问题答案2
	 */
	private String pwdQuestionAnswer2;
	/**
	 * 保密问题3
	 */
	private String pwdQuestion3;
	/**
	 * 保密问题答案3
	 */
	private String pwdQuestionAnswer3;
	/**
	 * 是否有效 0有效  1无效 
	 */
	private Integer enable;
	

}
