package com.smartwf.sm.modules.admin.pojo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.smartwf.common.annotation.ParamValidated.Update;
import com.smartwf.common.pojo.BasePojo;

import lombok.Data;

/**
 * @Description: 角色表
 */
@Data
@TableName("sys_role")
public class Role extends BasePojo implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	 
	/**
     * 主键id
     */
	@TableId(type = IdType.AUTO)
	@NotNull(message = "id不能为空", groups = Query.class)
	@NotNull(message = "id不能为空", groups = Update.class)
	private Integer id;
   
    /**
	 * 角色编号
	 */
	private String roleCode;
	
	/**
	 * 角色英文名称
	 */
	@NotNull(message = "角色英文名称不能为空", groups = Add.class)
	private String engName;
	/**
	 * 角色名称
	 */
	@NotNull(message = "角色名称不能为空", groups = Add.class)
	private String roleName;
	/**
	 * 租户id
	 */
	@NotNull(message = "租户id不能为空", groups = QueryParam.class)
	@NotNull(message = "租户id不能为空", groups = Add.class)
	private Integer tenantId;
	/**
	 * 是否有效
        0有效  
        1无效 
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
	
}
