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
import com.smartwf.hm.modules.alarmstatistics.dao.KeyPositionDao;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultInformation;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultOperationRecord;
import com.smartwf.hm.modules.alarmstatistics.pojo.KeyPosition;
import com.smartwf.hm.modules.alarmstatistics.service.AlarmInboxService;
import com.smartwf.hm.modules.alarmstatistics.vo.FaultInformationVO;

import lombok.extern.log4j.Log4j2;


/**
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 * @Description: 报警收件箱业务层实现
 */
@Service
@Log4j2
public class AlarmInboxServiceImpl implements AlarmInboxService {
	
	@Autowired
	private AlarmInboxDao alarmInboxDao;
	
	@Autowired
    private RedisService redisService;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private FaultOperationRecordDao faultOperationRecordDao;
	
	@Autowired
	private KeyPositionDao keyPositionDao;
	
	
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
		//故障表主键
		fr.setFaultInfoId(bean.getId());
		//操作人
		fr.setCreateUserName(user.getUserName()); 
		//时间
		fr.setCreateTime(bean.getUpdateTime()); 
		//备注
		fr.setRemark(bean.getRemark()); 
		//关闭原因
		fr.setClosureReason(bean.getClosureReason()); 
		//5待审核  6驳回  0未处理  1已转工单  2处理中  3已处理  4已关闭  7回收站  8未解决
		switch (bean.getAlarmStatus()) { 
			case 1:
				fr.setClosureReason("已转工单");
				//删除redis对应数据
				rmFaultInformationByRedis(bean.getId()); 
				//向生产中心发送相关数据 1.id查询对象， 2封装对象调用生产中心api接口
				break;
			case 2:
				fr.setClosureReason("处理中");
				break;
			case 3:
				fr.setClosureReason("已处理");
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
	 *  1.查询mysql未处理记录
	 *  2.保存redis最新未处理记录
	 * @return
	 */
	@Override
	public void selectFaultInformationByAll() {
		Map<String,FaultInformation> list = this.alarmInboxDao.selectFaultInformationByAll();
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
			log.info("redis未处理故障总数：{}",maps.size());
			//将新数据保存redis
			this.redisService.set("faultCount",JSON.toJSONString(maps,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty));
		}
	}

	/**
 	 * @Description: 重点机位添加
 	 *  批量添加，格式: 设备编号,设备名称,资产编码@设备编号,设备名称,资产编码
 	 * @author wch
 	 * @date 2020-04-07
 	 * @return
 	 */
	@Override
	public void addKeyPosition(KeyPosition bean) {
		//1)将字符串转换成字符串数组对象
		bean.setCreateTime(new Date());
		//2）保存前判断是否存在
		KeyPosition kp=this.keyPositionDao.selectByDeviceCode(bean);
		if(kp==null) {
			//3）保存
			this.keyPositionDao.insert(bean);
		}
	}

	/**
 	 * @Description: 重点机位删除
 	 *  通过重点机位表主键ID删除
 	 * @author wch
 	 * @date 2020-04-07
 	 * @return
 	 */
	@Override
	public void deleteKeyPosition(KeyPosition bean) {
		//1)封装参数
		QueryWrapper<KeyPosition> queryWrapper = new QueryWrapper<>();
  		queryWrapper.eq("device_code", bean.getDeviceCode()); 
  		queryWrapper.eq("tenant_code", bean.getTenantCode()); 
		//2）设备编码删除
		this.keyPositionDao.delete(queryWrapper);
	}

	/**
 	 * @Description: 重点机位统计数据-图表
 	 *   重点风机的报警统计
 	 * @author wch
 	 * @date 2020-04-07
 	 * @return
 	 */
	@Override
	public Result<?> selectKeyPositionByCount(KeyPosition bean) {
		List<FaultInformationVO> list=this.alarmInboxDao.selectKeyPositionByCount(bean);
		return Result.data(list);
	}

	/**
 	 * @Description: 重点机位统计数据-列表
 	 *   重点风机的报警统计
 	 * @author wch
 	 * @date 2020-04-07
 	 * @return
 	 */
	@Override
	public Result<?> selectKeyPositionByList(KeyPosition bean) {
		List<FaultInformationVO> list=this.alarmInboxDao.selectKeyPositionByList(bean);
		return Result.data(list);
	}
	
	/**
 	 * @Description: 重点机位已添加机位数据查询接口
 	 * @author wch
 	 * @date 2020-04-07
 	 * @return
 	 */
	@Override
	public Result<?> selectKeyPosition(KeyPosition bean) {
		List<KeyPosition> list=this.keyPositionDao.selectKeyPosition(bean);
		return Result.data(list);
	}

	/**
 	 * @Description: 单个重点机位所有故障报警数据
 	 * @author wch
 	 * @date 2020-04-07
 	 * @return
 	 */
	@Override
	public Result<?> selectKeyPositionByDeviceCode(KeyPosition bean) {
		List<FaultInformationVO> list=this.alarmInboxDao.selectKeyPositionByList(bean);
		return Result.data(list);
	}
	
}
