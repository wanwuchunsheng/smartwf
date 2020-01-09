package com.smartwf.hm.modules.alarmstatistics.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.common.service.RedisService;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.common.utils.JsonUtil;
import com.smartwf.hm.modules.alarmstatistics.dao.AlarmInboxDao;
import com.smartwf.hm.modules.alarmstatistics.dao.FaultOperationRecordDao;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultInformation;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultOperationRecord;
import com.smartwf.hm.modules.alarmstatistics.service.AlarmInboxService;
import com.smartwf.hm.modules.alarmstatistics.vo.FaultInformationVO;


/**
 * @Date: 2019-11-27 11:25:24
 * @Description: 报警收件箱业务层实现
 */
@Service
public class AlarmInboxServiceImpl implements AlarmInboxService {
	
	@Autowired
	private AlarmInboxDao alarmInboxDao;
	
	@Autowired
    private RedisService redisService;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private FaultOperationRecordDao faultOperationRecordDao;
	
	
	/**
	 * @Description: 分页查询故障报警信息 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public Result<?> selectAlarmInforByPage( Page<FaultInformation> page,FaultInformationVO bean) {
		List<FaultInformationVO> list=this.alarmInboxDao.selectAlarmInforByPage(page,bean);
		return Result.data(page.getTotal(), list);
	}
	
	/**
	 * @Description: 查询所有故障报警信息 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public Result<?> selectAlarmInforByAll(FaultInformationVO bean) {
		List<FaultInformationVO> list=this.alarmInboxDao.selectAlarmInforByAll(bean);
		return Result.data(list);
	}
	
	/**
	 * @Description: 实时故障报警总数查询
	 * @return
	 */
	@Override
	public Integer selectAlarmsCountByAll() {
		//-Map<String, Object> maps=JsonUtil.jsonToMap(this.stringRedisTemplate.opsForValue().get("faultCount"));
		Map<String, Object> maps=JsonUtil.jsonToMap(this.redisService.get("faultCount"));
		if(maps!=null && maps.size()>0) {
			return maps.size();
		}
		return Constants.ZERO;
	}
	
	/**
	 * @Description: 故障报警修改
	 *    0）获取当前登录人信息
	 *    1）更新最后修改时间及相关状态
	 *    2）向操作记录表插入操作记录
	 *    3)刷新redis数据，保存未处理数据与mysql一致
	 * @param id
	 * @param alarmStatus
	 *    0未处理
		  1已转工单
		  2处理中
		  3已处理
		  4已关闭
	 */
	@Transactional
	@Override
	public void updateAlarmInforById(FaultInformationVO bean) {
		//1)获取当前登录人信息
		User user=UserThreadLocal.getUser();
		//2)更新修改状态
		bean.setUpdateTime(new Date());
		this.alarmInboxDao.updateById(bean);
		//3）插入修改记录
		FaultOperationRecord fr=new FaultOperationRecord();
		fr.setFaultInfoId(bean.getId());//故障表主键
		fr.setCreateUserName(user.getUserName()); //操作人
		fr.setCreateTime(bean.getUpdateTime()); //时间
		fr.setRemark(bean.getRemark()); //备注
		fr.setClosureReason(bean.getClosureReason()); //关闭原因
		switch (bean.getAlarmStatus()) {
			case 1:
				fr.setClosureReason("已转工单");
				rmFaultInformationByRedis(bean.getId());
				break;
			case 2:
				fr.setClosureReason("处理中");
				//rmFaultInformationByRedis(bean.getId());
				break;
			case 3:
				fr.setClosureReason("已处理");
				//rmFaultInformationByRedis(bean.getId());
				break;
			case 4:
				fr.setClosureReason("已关闭");
				rmFaultInformationByRedis(bean.getId());
				break;
			default:
				break;
		}
		this.faultOperationRecordDao.insert(fr);
	}
	
	/**
	 * @Description: 主键查询
	 * @param id
	 */
	@Override
	public Result<?> selectAlarmInforById(FaultInformationVO bean) {
		FaultInformation list= this.alarmInboxDao.selectById(bean);
		return Result.data(list);
	}

	/**
	 * @Description: 初始化未结束的故障
	 * @return
	 */
	@Override
	public void selectFaultInformationByAll() {
		//-RestConfig.setIndex(stringRedisTemplate, 1);
		//-this.stringRedisTemplate.opsForValue().set("faultCount",JSON.toJSONString(list,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty));
		//查询mysql所有未处理的redis数据
		Map<String,FaultInformation> list = this.alarmInboxDao.selectFaultInformationByAll();
		//保存redis数据
		this.redisService.set("faultCount",JSON.toJSONString(list,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty));
	}

	/**
   	 * @Description: 查询所有故障报警记录信息 
   	 *   故障操作记录
   	 * @param faultInfoId
   	 * @param tenantCode
   	 * @return
   	 */
	@Override
	public Result<?> selectFaultRecordByAll(FaultOperationRecord bean) {
		QueryWrapper<FaultOperationRecord> queryWrapper = new QueryWrapper<>();
		//故障报警表ID
		if(StringUtils.isNotBlank(bean.getFaultInfoId())) {
			queryWrapper.eq("fault_info_id", bean.getFaultInfoId());
		}
		List<FaultOperationRecord> list = this.faultOperationRecordDao.selectList(queryWrapper);
		return Result.data(list);
	}

	/**
	 * @Deprecated 删除redis对应的数据
	 * 
	 * */
	public void rmFaultInformationByRedis(String id) {
		Map<String, Object> maps=JsonUtil.jsonToMap(this.redisService.get("faultCount"));
		if(maps!=null && maps.size()>0) {
			maps.remove(id);
			//将新数据保存redis
			this.redisService.set("faultCount",JSON.toJSONString(maps,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty));
		}
	}
	
}
