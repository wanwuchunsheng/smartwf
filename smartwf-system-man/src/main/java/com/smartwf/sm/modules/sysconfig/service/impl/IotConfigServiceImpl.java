package com.smartwf.sm.modules.sysconfig.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.utils.CkUtils;
import com.smartwf.sm.config.redis.StreamProducer;
import com.smartwf.sm.modules.sysconfig.dao.IotConfigDao;
import com.smartwf.sm.modules.sysconfig.pojo.IotConfig;
import com.smartwf.sm.modules.sysconfig.service.IotConfigService;
import com.smartwf.sm.modules.sysconfig.vo.IotConfigVO;

import cn.hutool.json.JSONUtil;
/**
 * @Description: 设备物联配置业务实现层
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
public class IotConfigServiceImpl implements IotConfigService{
	
	@Autowired
	private IotConfigDao iotConfigDao;
	
	@Autowired
	private StreamProducer streamProducer;

	/**
	 * @Description: 查询设备物联配置分页
	 * @param bean
	 * @return
	 */
	@Override
	public Result<?> selectIotConfigByPage(Page<IotConfig> page, IotConfigVO bean) {
		List<IotConfig> list=this.iotConfigDao.selectIotConfigByPage(page,bean);
		return Result.data(Constants.EQU_SUCCESS, list);
	}

	/**
     * @Description: 主键查询设备物联配置
     * @param bean
     * @return
     */
	@Override
	public Result<?> selectIotConfigById(IotConfig bean) {
		IotConfigVO icfg=this.iotConfigDao.selectIotConfigById(bean);
		return Result.data(Constants.EQU_SUCCESS, icfg);
	}

	/**
     * @Description: 添加设备物联配置
     * @param bean
     * @return
     */
	@Override
	public void saveIotConfig(IotConfig bean) {
		bean.setCreateTime(new Date());
		this.iotConfigDao.insert(bean);
		//添加成功，发送消息提醒Iot有配置有变更
		Map<String, Object> msgMap = new HashMap<>();
		msgMap.put("status", "1");
		msgMap.put("msg", "配置有新增！");
		Map<String, String> map = new HashMap<>();
		map.put("iot", JSONUtil.toJsonStr(msgMap));
		this.streamProducer.sendMsg(Constants.REDIS_TOPIC_MONITOR, map);
	}

	/**
     * @Description： 修改设备物联配置
     * @param bean
     * @return
     */
	@Override
	public void updateIotConfig(IotConfig bean) {
		this.iotConfigDao.updateById(bean);
		//添加成功，发送消息提醒Iot有配置有变更
		Map<String, Object> msgMap = new HashMap<>();
		msgMap.put("status", "1");
		msgMap.put("msg", "配置有修改！");
		Map<String, String> map = new HashMap<>();
		map.put("iot", JSONUtil.toJsonStr(map));
		this.streamProducer.sendMsg(Constants.REDIS_TOPIC_MONITOR, map);
	}

	/**
     * @Description： 删除设备物联配置
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteIotConfig(IotConfigVO bean) {
		if( null!=bean.getId()) {
			//删除多租户配置表
			this.iotConfigDao.deleteById(bean);
		}else {
			String ids=CkUtils.regex(bean.getIds());
			//批量删除
			if(StringUtils.isNotBlank(ids)) {
				List<String> list=new ArrayList<>();
				for(String val:ids.split(Constants.CHAR)) {
					list.add(val);
				}
				//多租户配置表
				this.iotConfigDao.deleteIotConfigByIds(list);
			}
		}
	}

	/**
	 * @Description: 查询全部设备物联配置
	 * @param bean
	 * @return
	 */
	@Override
	public Result<?> selectIotConfigByAll(IotConfigVO bean) {
		List<IotConfig> list=this.iotConfigDao.selectIotConfigByAll(bean);
		return Result.data(Constants.EQU_SUCCESS, list);
	}
	

}
