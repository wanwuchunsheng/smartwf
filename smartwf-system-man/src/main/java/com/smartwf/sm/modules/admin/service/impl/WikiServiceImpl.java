package com.smartwf.sm.modules.admin.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.utils.CkUtils;
import com.smartwf.sm.modules.admin.dao.OrganizationDao;
import com.smartwf.sm.modules.admin.dao.UserInfoDao;
import com.smartwf.sm.modules.admin.service.WikiService;


/**
 * @Date: 2019-11-27 11:25:24
 * @Description: 知识中心业务层实现
 * @author WCH
 */
@Service
public class WikiServiceImpl implements WikiService {

    @Autowired
    private UserInfoDao userInfoDao;
    
    @Autowired
    private OrganizationDao organizationDao;

    /**
     * @Description：知识中心-用户ID返回名称
     * @param tenantId
     * @param userId
     * @return
     */
	@Override
	public Result<?> selectUserInfoByIds(String tenantId, String userId,String orgId) {
		//查询用户
		String uid=CkUtils.regex(userId);
		String[] uids=uid.split(Constants.CHAR);
		List<String> list = List.of(uids);
		List<Map<String,Object>> userData= this.userInfoDao.selectUserInfoByIds(tenantId,list);
		//查询组织机构
		String oid=CkUtils.regex(orgId);
		String[] oids=oid.split(Constants.CHAR);
		List<String> oidList = List.of(oids);
		List<Map<String,Object>> orgData= this.organizationDao.selectOrganizationByIds(tenantId,oidList);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("user", userData);
		map.put("org", orgData);
		return Result.data(Constants.EQU_SUCCESS, map);
	}

	/**
     * @Description：知识中心-组织ID查询组织下的所有用户（
     *    不包括当前组织人员）
     * @param tenantId
     * @param orgId
     * @return
     */
	@Override
	public Result<?> selectUserOrganizationByOrgId(String tenantId, String orgId) {
		//查询用户
		String oid=CkUtils.regex(orgId);
		String[] oids=oid.split(Constants.CHAR);
		Map<String,Object> map=new HashMap<>();
		for(String orgid:oids) {
			//查询
			List<Map<String,Object>> list=this.userInfoDao.selectUserOrganizationByOrgId(tenantId,orgid);
			map.put(orgid, list);
		}
		return Result.data(Constants.EQU_SUCCESS, map);
	}


   
}
