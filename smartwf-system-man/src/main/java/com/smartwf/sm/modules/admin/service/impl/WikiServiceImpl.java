package com.smartwf.sm.modules.admin.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.utils.CkUtils;
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

    /**
     * @Description：知识中心-用户ID返回名称
     * @param tenantId
     * @param userId
     * @return
     */
	@Override
	public Result<?> selectUserInfoByIds(String tenantId, String userId) {
		//去除前后逗号
		String ids=CkUtils.regex(userId);
		String[] str=ids.split(Constants.CHAR);
		List<String> list = List.of(str);
		List<Map<String,Object>> data= this.userInfoDao.selectUserInfoByIds(tenantId,list);
		return Result.data(Constants.EQU_SUCCESS, data);
	}


   
}
