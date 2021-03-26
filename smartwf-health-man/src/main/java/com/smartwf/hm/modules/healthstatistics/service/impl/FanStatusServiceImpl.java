package com.smartwf.hm.modules.healthstatistics.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartwf.common.pojo.Result;
import com.smartwf.hm.modules.healthstatistics.dao.FanStatusDao;
import com.smartwf.hm.modules.healthstatistics.pojo.FanStatus;
import com.smartwf.hm.modules.healthstatistics.service.FanStatusService;
import com.smartwf.hm.modules.healthstatistics.vo.FanStatusVO;

@Service
public class FanStatusServiceImpl implements FanStatusService{

	@Autowired
	private FanStatusDao fanStatusDao;
	
	/**
	 * @Description: 统计风机运行状态
	 *    统计结果信息
	 * @param tenantDomain
	 * @param windFarm
	 * @author WCH
	 * @Datetime 2021-3-26 10:26:07
	 * @return
	 */
	@Override
	public Result<?> selectFanRunStatusByCount(FanStatus bean) {
		//资产状态甬道图统计
		List<Map<String,Object>> assetModelMaps = this.fanStatusDao.selectAssetModelByCount(bean);
		//资产状态二级状态统计
		List<FanStatusVO> runStatusList = this.fanStatusDao.selectFanRunStatusByCount(bean);
		//封装资产状态  (资产状态: 0正常  1故障 2缺陷  3警告)
		Map<Object,Object> assetModel = new HashMap<Object, Object>();
		assetModel.put("0", 0);
		assetModel.put("1", 0);
		//assetModel.put("2", 0);
		assetModel.put("3", 0);
		for(Map<String,Object> map : assetModelMaps) {
			assetModel.put(map.get("runStatus"), map.get("count"));
		}
		//封装返回值
		Map<Object,Object> runStatusMap = new HashMap<>();
		runStatusMap.put("assetModel", assetModel);
		runStatusMap.put("fanStatus", runStatusList);
		return Result.data(HttpStatus.SC_OK, runStatusMap);
	}

}
