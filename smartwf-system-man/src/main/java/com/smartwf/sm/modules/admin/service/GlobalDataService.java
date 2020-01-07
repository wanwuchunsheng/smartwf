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

	Result<?> tenantAll();

	Result<?> organizationAll(OrganizationVO bean);

	Result<?> PostAll(Post bean);

	Result<?> RoleAll(Role bean);

	Result<?> dictAll(Dictionary bean);

	void flushCache(GlobalData bean);

	
}
