package com.smartwf.sm.modules.admin.service;

import com.smartwf.common.pojo.Result;

/**
 * @Date: 2018/12/18 15:43
 * @Description: 知识中心业务层接口
 * @author WCH
 */
public interface WikiService {


	/**
     * @Description：知识中心-用户ID返回名称
     * @param tenantId
     * @param userId
     * @return
     */
	Result<?> selectUserInfoByIds(String tenantId, String userId,String orgId);

	/**
     * @Description：知识中心-组织ID查询组织下的所有用户（
     *    不包括当前组织人员）
     * @param tenantId
     * @param orgId
     * @return
     */
	Result<?> selectUserOrganizationByOrgId(String tenantId, String orgId);
}
