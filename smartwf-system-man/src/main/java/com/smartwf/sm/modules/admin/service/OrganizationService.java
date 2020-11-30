package com.smartwf.sm.modules.admin.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.Organization;
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.pojo.UserOrganization;
import com.smartwf.sm.modules.admin.vo.OrganizationVO;

/**
 * @Description: 组织架构业务层接口
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
public interface OrganizationService {

	/**
	 * 查询组织架构分页
	 * @param bean
	 * @param page
	 * @return 
	 */
	Result<?> selectOrganizationByPage(Page<Organization> page, OrganizationVO bean);

	/**
     * 主键查询组织架构
     * @param bean
     * @return
     */
	Result<?> selectOrganizationById(Organization bean);

	/**
     * 添加组织架构
     * @param bean
     * @return
     */
	void saveOrganization(Organization bean);
	
	/**
     *  修改组织架构
     *  @param bean
     * @return
     */
	void updateOrganization(Organization bean);

	/**
     *  删除组织架构
     *  @param bean
     * @return
     */
	void deleteOrganization(OrganizationVO bean);
	
	/**
	 * 查询所有组织架构（树形结构）
	 * @param bean
	 * @return
	 */
	Result<?> selectOrganizationByAll(OrganizationVO bean);
	
	/**
	 * 初始化组织机构
	 * @param list
	 * @return
	 */
	Map<Integer,List<OrganizationVO>> initOrganizationDatas(List<Tenant> list);
	/**
     * @Description 通过租户、用户查询   - 所有风场
     * @return
     */
	List<OrganizationVO> selectOrganizationByUserId(UserOrganization uobean);
	/**
     * @Description 通过租户查询   - 所有风场
     * @return
     */
	List<OrganizationVO> selectOrganizationByTenantId(UserOrganization uobean);

	/**
	 * @Description: 查询组织机构人员信息（知识中心提供）
	 * @return
	 */
	Result<?> selectUserOrganizationByParam(OrganizationVO bean);
	
	
	

}
