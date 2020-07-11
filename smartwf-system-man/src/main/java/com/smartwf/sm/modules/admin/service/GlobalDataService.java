package com.smartwf.sm.modules.admin.service;

import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.Dictionary;
import com.smartwf.sm.modules.admin.pojo.GlobalData;
import com.smartwf.sm.modules.admin.pojo.Post;
import com.smartwf.sm.modules.admin.pojo.Role;
import com.smartwf.sm.modules.admin.vo.OrganizationVO;

/**
 * @Description: 全局数据业务层接口
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
public interface GlobalDataService {

	/**
	 * 查询全部租户
	 * @return
	 * 
	 * */
	Result<?> tenantAll();
	/**
	 * 查询组织架构
	 * @param bean
	 * @return
	 * 
	 * */
	Result<?> organizationAll(OrganizationVO bean);
	/**
	 * 查询职务
	 * @param bean
	 * @return
	 * 
	 * */
	Result<?> postAll(Post bean);
	/**
	 * 查询全部角色
	 * @param bean
	 * @return
	 * 
	 * */
	Result<?> roleAll(Role bean);
	/**
	 * 查询数据字典
	 * @param bean
	 * @return
	 * 
	 * */
	Result<?> dictAll(Dictionary bean);
	/**
	 * 刷新缓存
	 * @param bean
	 * 
	 * */
	void flushCache(GlobalData bean);

	
}
