package com.smartwf.sm.modules.admin.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.Organization;
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.vo.OrganizationVO;

/**
 * @Description: 组织架构业务层接口
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
public interface OrganizationService {

	/**
	 * @Description: 查询组织架构分页
	 * @result: 
	 */
	Result<?> selectOrganizationByPage(Page<Organization> page, OrganizationVO bean);

	/**
     * @Description: 主键查询组织架构
     * @return
     */
	Result<?> selectOrganizationById(Organization bean);

	/**
     * @Description： 添加组织架构
     * @return
     */
	void saveOrganization(Organization bean);
	
	/**
     * @Description： 修改组织架构
     * @return
     */
	void updateOrganization(Organization bean);

	/**
     * @Description： 删除组织架构
     * @return
     */
	void deleteOrganization(OrganizationVO bean);
	
	/**
	 * @Description: 查询所有组织架构（树形结构）
	 * @return
	 */
	Result<?> selectOrganizationByAll(OrganizationVO bean);
	
	/**
	 * @Description: 初始化组织机构
	 * @return
	 */
	Map<Integer,List<OrganizationVO>> initOrganizationDatas(List<Tenant> list);
	
	

}
