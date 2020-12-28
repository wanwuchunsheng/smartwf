package com.smartwf.sm.modules.admin.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.vo.TenantVO;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 租户持久层接口
 * @author WCH
 */
@Repository
public interface TenantDao extends BaseMapper<Tenant> {

	/**
	 * 批量删除租户
	 * @param list
	 * @result:
	 */
	void deleteTenantByIds(@Param("list") List<String> list);

	/**
	 * 将所有租户默认状态设置不选中
	 */
	void updateBySel();

	/**
	 * 级联删除
	 * @param bean
	 * 
	 * */
	void deleteOrgByTenantId(@Param("bean") TenantVO bean);
	/**
	 * 用户组织架构删除
	 * @param bean
	 * 
	 * */
	void deleteUserOrgByTenantId(@Param("bean") TenantVO bean);
	/**
	 * 职务删除
	 * @param bean
	 * 
	 * */
	void deletePostByTenantId(@Param("bean") TenantVO bean);
	/**
	 * 级联删除
	 * @param bean
	 * 
	 * */
	void deleteUserPostByTenantId(@Param("bean") TenantVO bean);
	/**
	 * 级联删除
	 * @param bean
	 * 
	 * */
	void deleteRoleByTenantId(@Param("bean") TenantVO bean);
	/**
	 * 级联删除
	 * @param bean
	 * 
	 * */
	void deleteUserRoleByTenantId(@Param("bean") TenantVO bean);
	/**
	 * 级联删除
	 * @param bean
	 * 
	 * */
	void deleteRolePermissionByTenantId(@Param("bean") TenantVO bean);
	/**
	 * 级联删除
	 * @param bean
	 * 
	 * */
	void deleteUserByTenantId(@Param("bean") TenantVO bean);
	/**
	 * 级联删除
	 * @param bean
	 * 
	 * */
	void deletePermissionByTenantId(@Param("bean") TenantVO bean);
	/**
	 * 级联删除
	 * @param bean
	 * 
	 * */
	void deleteResourceByTenantId(@Param("bean") TenantVO bean);
	/**
	 * 级联删除
	 * @param bean
	 * 
	 * */
	void deleteUserActionByTenantId(@Param("bean") TenantVO bean);
	/**
	 * 级联删除
	 * @param bean
	 * 
	 * */
	void deleteDictionaryByTenantId(@Param("bean") TenantVO bean);

	/**
	 * 批量级联删除
	 * @author WCH
	 * @param list
	 * 
	 * */
	void deleteOrgByTenantIds(@Param("list") List<String> list);
	/**
	 * 批量级联删除
	 * @author WCH
	 * @param list
	 * 
	 * */
	void deleteUserOrgByTenantIds(@Param("list") List<String> list);
	/**
	 * 批量级联删除
	 * @author WCH
	 * @param list
	 * 
	 * */
	void deletePostByTenantIds(@Param("list") List<String> list);
	/**
	 * 批量级联删除
	 * @author WCH
	 * @param list
	 * 
	 * */
	void deleteUserPostByTenantIds(@Param("list") List<String> list);
	/**
	 * 批量级联删除
	 * @author WCH
	 * @param list
	 * 
	 * */
	void deleteRoleByTenantIds(@Param("list") List<String> list);
	/**
	 * 批量级联删除
	 * @author WCH
	 * @param list
	 * 
	 * */
	void deleteUserRoleByTenantIds(@Param("list") List<String> list);
	/**
	 * 批量级联删除
	 * @author WCH
	 * @param list
	 * 
	 * */
	void deleteRolePermissionByTenantIds(@Param("list") List<String> list);
	/**
	 * 批量级联删除
	 * @author WCH
	 * @param list
	 * 
	 * */
	void deleteUserByTenantIds(@Param("list") List<String> list);
	/**
	 * 批量级联删除
	 * @author WCH
	 * @param list
	 * 
	 * */
	void deletePermissionByTenantIds(@Param("list") List<String> list);
	/**
	 * 批量级联删除
	 * @author WCH
	 * @param list
	 * 
	 * */
	void deleteResourceByTenantIds(@Param("list") List<String> list);
	/**
	 * 批量级联删除
	 * @author WCH
	 * @param list
	 * 
	 * */
	void deleteUserActionByTenantIds(@Param("list") List<String> list);
	/**
	 * 批量级联删除
	 * @author WCH
	 * @param list
	 * 
	 * */
	void deleteDictionaryByTenantIds(@Param("list") List<String> list);

	/**
	 * 查询租户
	 * @return
	 * @author WCH
	 * @param bean
	 * */
	Tenant selectTenantById(@Param("bean") UserInfo bean);
	/**
     * 风场租户域映射关系
     * @return
     */
	List<Map<String, String>> initWeatherTenant();

	
    
}
