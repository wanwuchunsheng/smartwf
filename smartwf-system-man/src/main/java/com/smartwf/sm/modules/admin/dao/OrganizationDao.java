package com.smartwf.sm.modules.admin.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartwf.common.pojo.TreeOrganization;
import com.smartwf.common.pojo.User;
import com.smartwf.sm.modules.admin.pojo.Organization;
import com.smartwf.sm.modules.admin.pojo.UserOrganization;
import com.smartwf.sm.modules.admin.pojo.UserPost;
import com.smartwf.sm.modules.admin.vo.OrganizationVO;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 组织架构持久层接口
 * @author WCH
 */
@Repository
public interface OrganizationDao extends BaseMapper<Organization> {

	/**
	 * 查询所有组织架构
	 * @param bean
	 * @return
	 */
	List<OrganizationVO> selectOrganizationByAll(@Param("bean") OrganizationVO bean);

	/**
	 *  删除用户组织结构
	 * @param bean
	 * 
	 * */
	void deleteUserOrgById(@Param("bean") OrganizationVO bean);
	/**
	 * 删除职务表
	 * @param bean
	 */
	void deletePostByOrgId(@Param("bean") OrganizationVO bean);
	/**
	 *  批量删除用户职务表
	 *  @param list
	 */
	void deleteUserPostByOrgIds(@Param("list") List<Integer> list);
	/**
	 * 通过组织架构id，查询用户组织架构集合
	 * @param bean
	 * @return
	 * 
	 */
	List<UserPost> queryUserPostByOrgId(@Param("bean") OrganizationVO bean);
	/**
	 * 通过用户id，查询用户组织架构集合
	 * @param userInfo
	 * @return
	 * 
	 */
	List<TreeOrganization> selectOrganizationByUserId(@Param("bean") User userInfo);

	/**
     * @Description 通过租户、用户查询   - 所有风场
     * @return
     */
	List<OrganizationVO> selectOrganizationByOrgUserId(@Param("bean") UserOrganization uobean);
	
	/**
     * @Description：知识中心-获取用户风场信息
     * @param sessionId
     * @return
     */
	List<Map<String, Object>> selectUserInfoByWindFarm(@Param("bean") UserOrganization bean);

	/**
     * @Description 通过租户查询   - 所有风场
     * @return
     */
	List<OrganizationVO> selectOrganizationByTenantId(@Param("bean") UserOrganization uobean);

	/**
	 * @Description: 查询组织机构人员信息（知识中心提供）
	 *      组织机构信息
	 * @return
	 */
	List<Map<String, Object>> selectUserOrganizationByUid(@Param("bean") OrganizationVO bean);
	/**
	 * @Description: 查询组织机构人员信息（知识中心提供）
	 *     组织机构人员信息
	 * @return
	 */
	List<Map<String, Object>> selectUserOrganizationByOrgId(@Param("bean") OrganizationVO bean);
	/**
     * @Description：知识中心-获取用户风场信息 -平台管理员
     * @param sessionId
     * @return
     */
	List<Map<String, Object>> selectUserInfoByWindFarmByAdminUser(@Param("bean") UserOrganization bean);

	/**
     * @Description：查询组织机构和用户列表
     * @param sessionId
     * @return
     */
	List<OrganizationVO> selectUserOrganizationByAll(@Param("bean") OrganizationVO bean);

	/**
     * @Description：知识中心-用户ID返回名称
     * @param tenantId
     * @param userId
     * @return
     */
	List<Map<String, Object>> selectOrganizationByIds(@Param("tenantId") String tenantId, @Param("list") List<String> list);

	/**
     * @Description：知识中心-查询所有组织机构
     * @param tenantId
     * @param orgId
     * @return
     */
	List<Map<String, String>> selectWikiOrganizationByAll(@Param("tenantId") Integer tenantId);

	
	
	

    
}
