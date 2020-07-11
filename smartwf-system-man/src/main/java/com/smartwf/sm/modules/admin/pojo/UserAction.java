package com.smartwf.sm.modules.admin.pojo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.smartwf.common.pojo.BasePojo;

import lombok.Data;

/**
 * @Description: 用户操作表
 * @author WCH
 */
@Data
@TableName("sys_user_action")
public class UserAction extends BasePojo implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	 
	/**
     * 主键id
     */
	@TableId(type = IdType.AUTO)
	@NotNull(message = "id不能为空", groups = Query.class)
	@NotNull(message = "id不能为空", groups = Update.class)
	private Integer id;
   
	/**
	 * 编码
	 */
	@NotNull(message = "编码不能为空", groups = Add.class)
	private String actCode;
	/**
	 * 名称
	 */
	@NotNull(message = "名称不能为空", groups = Add.class)
	private String actName;
	
	/**
	** 是否有效  0有效    1无效
     *       
	 */
	@NotNull(message = "是否启用不能为空", groups = Add.class)
	private Integer enable;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 租户id
	 */
	@NotNull(message = "租户id不能为空", groups = Add.class)
	@NotNull(message = "租户id不能为空", groups = QueryParam.class)
	private Integer tenantId;
	
}
