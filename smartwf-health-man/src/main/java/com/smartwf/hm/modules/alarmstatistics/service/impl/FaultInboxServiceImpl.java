package com.smartwf.hm.modules.alarmstatistics.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.common.service.RedisService;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.hm.modules.alarmstatistics.dao.AlarmInboxDao;
import com.smartwf.hm.modules.alarmstatistics.dao.DefectDao;
import com.smartwf.hm.modules.alarmstatistics.dao.FaultDataDao;
import com.smartwf.hm.modules.alarmstatistics.dao.FaultInboxDao;
import com.smartwf.hm.modules.alarmstatistics.dao.FaultOperationRecordDao;
import com.smartwf.hm.modules.alarmstatistics.dao.KeyPositionDao;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultInformation;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultOperationRecord;
import com.smartwf.hm.modules.alarmstatistics.pojo.KeyPosition;
import com.smartwf.hm.modules.alarmstatistics.service.AlarmInboxService;
import com.smartwf.hm.modules.alarmstatistics.service.FaultInboxService;
import com.smartwf.hm.modules.alarmstatistics.service.PmsSendDataService;
import com.smartwf.hm.modules.alarmstatistics.vo.FaultInformationVO;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.log4j.Log4j2;


/**
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 * @Description: 故障收件箱业务层实现
 */
@Service
@Log4j2
public class FaultInboxServiceImpl implements FaultInboxService {
	
	@Autowired
	private FaultInboxDao faultInboxDao;
	
	@Autowired
    private RedisService redisService;

	@Autowired
	private FaultOperationRecordDao faultOperationRecordDao;
	
	@Autowired
	private FaultDataDao faultDataDao;
	
	@Autowired
	private KeyPositionDao keyPositionDao;
	
	@Autowired
	private PmsSendDataService pmsSendDataService;
	
	
	/**
	 * @Description: 分页查询故障信息 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public Result<?> selectFaultInforByPage( Page<FaultInformation> page,FaultInformationVO bean) {
		List<FaultInformationVO> list=this.faultInboxDao.selectFaultInforByPage(page,bean);
		return Result.data(page.getTotal(), list);
	}
	
	/**
	 * @Description: 查询所有故障信息 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public Result<?> selectFaultInforByAll(FaultInformationVO bean) {
		List<FaultInformationVO> list=this.faultInboxDao.selectFaultInforByAll(bean);
		return Result.data(list);
	}
	
	/**
	 * @Description: 实时故障总数查询
	 * @return
	 */
	@Override
	public Integer selectFaultCountByAll(String tenantDomain,String windFarm) {
		QueryWrapper<FaultInformation> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("tenant_domain", tenantDomain);
		queryWrapper.eq("incident_type", Constants.ONE);
		queryWrapper.eq("alarm_status", Constants.ZERO);
		//支持批量拼接
		if(StringUtils.isNotBlank(windFarm)) {
			List<String> list=new ArrayList<>();
			String[] str=windFarm.split(",");
			for(String s:str) {
				list.add(s);
			}
			queryWrapper.in("wind_farm", list);
		}
		Integer count= this.faultInboxDao.selectCount(queryWrapper);
		return count;
	}
	
	/**
	 * 故障修改
	 *    0）获取当前登录人信息
	 *    1）更新最后修改时间及相关状态
	 *    2）向操作记录表插入操作记录
	 *    3)刷新redis数据，保存未处理数据与mysql一致
	 * @param id
	 * @param bean
	 * @param alarmStatus
	 *    0未处理
		  1已转工单
		  2处理中
		  3已处理
		  4已关闭
	 */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void updateFaultInforById(FaultInformationVO bean) {
		//1)获取当前登录人信息
		User user=UserThreadLocal.getUser();
		//2)更新修改状态
		bean.setUpdateTime(new Date());
		this.faultInboxDao.updateById(bean);
		//过滤重点关注修改，避免重复提交
		if(null != bean.getAlarmStatus() && null==bean.getOperatingStatus()) {
			//3）插入修改记录
			FaultOperationRecord fr=new FaultOperationRecord();
			//故障表主键
			fr.setFaultInfoId(bean.getId());
			//操作人姓名
			fr.setCreateUserName(user.getUserName()); 
			//操作人ID
			fr.setCreateUserId(String.valueOf(user.getId()));
			//时间
			fr.setCreateTime(bean.getUpdateTime()); 
			//备注
			fr.setRemark(bean.getRemark()); 
			//租户域
			fr.setTenantDomain(bean.getTenantDomain());
			//对生产中心转工单状态修改
			int alarmStatus=bean.getAlarmStatus();
			if(alarmStatus==0 || alarmStatus==6 || alarmStatus==7 || alarmStatus==8) {
				bean.setAlarmStatus(4);
			}
			if(alarmStatus==5){
				bean.setAlarmStatus(3);
			}
			//0未处理  1已转工单  2处理中  3已处理  4已关闭
			switch (bean.getAlarmStatus()) {
				case 1:
					//已转工单{状态已废弃}
					fr.setClosureStatus(1);
					//删除redis对应数据
					this.rmFaultInformationByRedis(bean.getId()); 
					//转工单
					this.pmsSendDataService.faultWordOrder(bean);
					break;
				case 2:
					//处理中
					fr.setClosureStatus(2);
					fr.setClosureReason("已转工单，在处理中");
					//删除redis对应数据
					this.rmFaultInformationByRedis(bean.getId()); 
					//转工单
					this.pmsSendDataService.faultWordOrder(bean);
					break;
				case 3:
					//已处理
					fr.setClosureStatus(3);
					break;
				case 4:
					//已关闭
					fr.setClosureStatus(4);
					//关闭原因
					fr.setClosureReason(bean.getClosureReason()); 
					//删除redis对应数据
					this.rmFaultInformationByRedis(bean.getId()); 
					break;
				default:
					break;
			}
			//1处理记录   2处理意见
			fr.setClosureType(1); 
			//插入处理记录
			this.faultOperationRecordDao.insert(fr);
		}
	}
	
	/**
	 * @Description: 主键查询
	 * @param id
	 */
	@Override
	public Result<?> selectFaultInforById(FaultInformationVO bean) {
		FaultInformation list= this.faultInboxDao.selectById(bean);
		return Result.data(Constants.EQU_SUCCESS,list);
	}

	/**
	 * @Description: 初始化未结束的故障
	 *  1.查询mysql未处理记录
	 *  2.保存redis最新未处理记录
	 * @return
	 */
	@Override
	public void selectFaultInformationByAll() {
		Map<String,FaultInformation> list = this.faultInboxDao.selectFaultInformationByAll();
		this.redisService.set("faultCount",JSONUtil.toJsonStr(list));
	}

	/**
   	 * @Description: 查询所有故障记录信息 
   	 *   故障操作记录
   	 * @param faultInfoId
   	 * @param tenantDomain
   	 * @return
   	 */
	@Override
	public Result<?> selectFaultRecordByAll(FaultOperationRecord bean) {
		QueryWrapper<FaultOperationRecord> queryWrapper = new QueryWrapper<>();
		//故障表ID
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
		Map<String, Object> maps=JSONUtil.parseObj(this.redisService.get("faultCount"));
		if(maps!=null && maps.size()>0) {
			maps.remove(id);
			log.info("redis未处理故障总数：{}",maps.size());
			//将新数据保存redis
			this.redisService.set("faultCount",JSONUtil.toJsonStr(maps));
		}
	}

	/**
 	 * @Description: 重点机位添加
 	 * @author wch
 	 * @date 2020-04-07
 	 * @return
 	 */
	@Override
	public void addKeyPosition(KeyPosition bean) {
		bean.setCreateTime(new Date());
		KeyPosition kp=this.keyPositionDao.selectByDeviceCode(bean);
		if(kp==null) {
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
  		queryWrapper.eq("asset_number", bean.getAssetNumber()); 
  		queryWrapper.eq("tenant_domain", bean.getTenantDomain());
  	    //支持批量拼接
		if(StringUtils.isNotBlank(bean.getWindFarm())) {
			List<String> list=new ArrayList<>();
			String[] str=bean.getWindFarm().split(",");
			for(String s:str) {
				list.add(s);
			}
			queryWrapper.in("wind_farm", list);
		}
		//2）设备编码删除
		this.keyPositionDao.delete(queryWrapper);
	}

	/**
 	 * @Description: 重点机位统计数据-图表
 	 *   重点风机的统计
 	 * @author wch
 	 * @date 2020-04-07
 	 * @return
 	 */
	@Override
	public Result<?> selectKeyPositionByCount(KeyPosition bean) {
		List<FaultInformationVO> list=this.faultInboxDao.selectKeyPositionByCount(bean);
		return Result.data(list);
	}

	/**
 	 * @Description: 重点机位统计数据-列表
 	 *   重点风机的统计
 	 * @author wch
 	 * @date 2020-04-07
 	 * @return
 	 */
	@Override
	public Result<?> selectKeyPositionByList(KeyPosition bean) {
		List<FaultInformationVO> list=this.faultInboxDao.selectKeyPositionByList(bean);
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
 	 * @Description: 单个重点机位所有故障数据
 	 * @author wch
 	 * @date 2020-04-07
 	 * @return
 	 */
	@Override
	public Result<?> selectKeyPositionByDeviceCode(KeyPosition bean) {
		List<FaultInformationVO> list=this.faultInboxDao.selectKeyPositionByList(bean);
		return Result.data(list);
	}

	/**
	 * @Description: 故障处理意见
	 *    添加
	 * @author WCH
	 * @dateTime 2020-7-20 17:55:35
	 * @param bean
	 */
	@Override
	public void addFaultOperationRecord(FaultOperationRecord bean) {
		User user=UserThreadLocal.getUser();
		bean.setCreateTime(new Date());
		bean.setCreateUserId(String.valueOf(user.getId()));
		bean.setCreateUserName(user.getUserName());
		this.faultOperationRecordDao.insert(bean);
	}

	/**
   	 *  故障、缺陷{转工单}
   	 *      生产中心状态修改
   	 *      1）修改工单状态
   	 *      2）记录表插入修改记录
   	 * @author WCH
   	 * @param bean
   	 */
	@Override
	public void updateFaultInByParam(FaultInformationVO bean) {
		User user=UserThreadLocal.getUser();
		QueryWrapper<FaultInformation> queryWrapper = new QueryWrapper<>();
		//租户域
  		queryWrapper.eq("tenant_domain", bean.getTenantDomain()); 
  		//资产编码
  		queryWrapper.eq("id", bean.getFaultId()); 
  		//查询对象
  		FaultInformation ft=this.faultDataDao.selectOne(queryWrapper);
  		if( null != ft ) {
  		    //状态
  	  		ft.setAlarmStatus(bean.getAlarmStatus()); 
  	  		//工单号
  	  	    ft.setOrderNumber(bean.getOrderNumber());
  	  	    this.faultDataDao.updateById(ft);
  	  	    //3）插入修改记录
			FaultOperationRecord fr=new FaultOperationRecord();
			//故障表主键
			fr.setFaultInfoId(ft.getId());
			//操作人姓名
			fr.setCreateUserName(user.getUserName()); 
			//操作人ID
			fr.setCreateUserId(String.valueOf(user.getId()));
			//时间
			fr.setCreateTime(new Date()); 
			//租户域
			fr.setTenantDomain(ft.getTenantDomain());
			fr.setRemark(bean.getRemark());
			//对生产中心转工单状态修改
			int alarmStatus=bean.getAlarmStatus();
			if(alarmStatus==0 || alarmStatus==6 || alarmStatus==7 || alarmStatus==8) {
				bean.setAlarmStatus(4);
			}
			if(alarmStatus==5){
				bean.setAlarmStatus(3);
			}
			//0未处理  1已转工单  2处理中  3已处理  4已关闭 
			switch (bean.getAlarmStatus()) {
				case 1:
					//已转工单{状态已废弃}
					fr.setClosureStatus(1);
					fr.setClosureReason("已转工单"); 
					break;
				case 2:
					//处理中
					fr.setClosureStatus(2);
					fr.setClosureReason("处理中"); 
					break;
				case 3:
					//已处理
					fr.setClosureStatus(3);
					fr.setClosureReason("已处理"); 
					break;
				case 4:
					//已关闭
					fr.setClosureStatus(4);
					fr.setClosureReason("关闭");
					break;
				default:
					break;
			}
			//1处理记录   2处理意见
			fr.setClosureType(Constants.ONE); 
			//插入处理记录
			this.faultOperationRecordDao.insert(fr);
  		}
  	    
	}

	/**
	 * 今日新增总数查询接口
	 * @author WCH
	 * @return
	 */
	@Override
	public Integer selectFaultCountByToday(String tenantDomain,String windFarm) {
		QueryWrapper<FaultInformation> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("tenant_domain", tenantDomain);
		queryWrapper.ge("create_time", DateUtil.today());
		//1故障  2缺陷 3警告
		queryWrapper.eq("incident_type", Constants.ONE);
		//支持批量拼接
		if(StringUtils.isNotBlank(windFarm)) {
			List<String> list=new ArrayList<>();
			String[] str=windFarm.split(",");
			for(String s:str) {
				list.add(s);
			}
			queryWrapper.in("wind_farm", list);
		}
		Integer count= this.faultInboxDao.selectCount(queryWrapper);
		return count;
	}
	
}
