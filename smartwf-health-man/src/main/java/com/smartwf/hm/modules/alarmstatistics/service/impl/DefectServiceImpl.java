package com.smartwf.hm.modules.alarmstatistics.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.common.service.RedisService;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.hm.modules.alarmstatistics.dao.DefectDao;
import com.smartwf.hm.modules.alarmstatistics.dao.FaultOperationRecordDao;
import com.smartwf.hm.modules.alarmstatistics.dao.FileUploadRecordDao;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultInformation;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultOperationRecord;
import com.smartwf.hm.modules.alarmstatistics.pojo.FileUploadRecord;
import com.smartwf.hm.modules.alarmstatistics.service.DefectService;
import com.smartwf.hm.modules.alarmstatistics.service.PmsSendDataService;
import com.smartwf.hm.modules.alarmstatistics.vo.DefectVO;

import cn.hutool.json.JSONUtil;
import lombok.extern.log4j.Log4j2;


/**
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 * @Description: 缺陷业务层实现
 */
@Service
@Log4j2
public class DefectServiceImpl implements DefectService {
	
	@Autowired
	private DefectDao defectDao;
	
	@Autowired
    private RedisService redisService;
	
	@Autowired
	private DefectService defectService;
	
	@Autowired
    private FaultOperationRecordDao faultOperationRecordDao;
	
	@Autowired
    private FileUploadRecordDao fileUploadRecordDao;
	
	@Autowired
	private PmsSendDataService pmsSendDataService;
	
	
	/**
	 * @Description: 初始化缺陷数据
	 *  1.查询未处理缺陷信息
	 *  2.保存redis
	 * @return
	 */
	@Override
	public void initDefectAll() {
		Map<String,FaultInformation> list = this.defectDao.initDefectAll();
		this.redisService.set("defectCount",JSONUtil.toJsonStr(list));
	}

	
	/**
	 * @Description: 实时缺陷数据
	 * 1.人工手动创建缺陷工单
	 * 2.记录缺陷工单处理状态
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void saveDefect(DefectVO bean) {
		//添加工单
		bean.setCreateTime(new Date());
		bean.setUpdateTime(bean.getCreateTime());
		//0未处理 1已转工单 2处理中 3已处理 4已关闭
		bean.setAlarmStatus(Constants.ZERO);
		this.defectDao.insert(bean);
		//添加工单处理记录
		FaultOperationRecord fr=new FaultOperationRecord();
		fr.setFaultInfoId(bean.getId());
		//待处理
		fr.setClosureStatus(Constants.ZERO);
		if(Constants.ONE==bean.getIncidentType()) {
			fr.setClosureReason("故障工单录入");
		}else {
			fr.setClosureReason("缺陷工单录入");
		}
		//1处理记录  2处理意见
		fr.setClosureType(Constants.ONE);
		fr.setCreateTime(new Date());
		fr.setTenantDomain(bean.getTenantDomain());
		this.faultOperationRecordDao.insert(fr);
		//添加附件
		if(StringUtils.isNotBlank(bean.getFilePath()) ) {
			String[] str=bean.getFilePath().split(",");
			if(str != null && str.length>0) {
				FileUploadRecord fuRecord=null;
				for(String s:str) {
					fuRecord= new FileUploadRecord();
					fuRecord.setPid(bean.getId());
					fuRecord.setFilePath(s);
					fuRecord.setTenantDomain(bean.getTenantDomain());
					fuRecord.setCreateTime(new Date());
					this.fileUploadRecordDao.insert(fuRecord);
				}
			}
		}
	}

	/**
	 * @Description: 缺陷工单处理
	 *   审核工单/修改缺陷工单状态
	 * @param id
	 * @param alarmStatus  
     *
	 */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void updateDefectById(DefectVO bean) {
		//1)获取当前登录人信息
		User user=UserThreadLocal.getUser();
		//2)更新修改状态
		bean.setUpdateTime(new Date());
		this.defectDao.updateById(bean);
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
			//0未处理  1已转工单  2处理中  3已处理  4已关闭
			switch (bean.getAlarmStatus()) {
				case 1:
					//已转工单{状态已废弃}
					fr.setClosureStatus(1);
					//删除redis对应数据
					this.rmDefectByRedis(bean.getId()); 
					//转工单
					this.pmsSendDataService.faultWordOrder(bean);
					break;
				case 2:
					//处理中
					fr.setClosureStatus(2);
					fr.setClosureReason("已转工单，在处理中");
					//删除redis对应数据
					this.rmDefectByRedis(bean.getId()); 
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
					this.rmDefectByRedis(bean.getId()); 
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
	 * @Deprecated 删除redis对应的数据
	 * 
	 * */
	public void rmDefectByRedis(String id) {
		Map<String, Object> maps=JSONUtil.parseObj(this.redisService.get("defectCount"));
		if(maps!=null && maps.size()>0) {
			maps.remove(id);
			log.info("redis未处理故障总数：{}",maps.size());
			this.redisService.set("defectCount",JSONUtil.toJsonStr(maps));
		}
	}

	/**
	 * @Description: 缺陷信息主键查询
	 *    缺陷详细
	 * @param id
	 */
	@Override
	public Result<?> selectDefectById(DefectVO bean) {
		FaultInformation faultInformation= this.defectDao.selectDefectById(bean);
		return Result.data(faultInformation);
	}

	/**
	 * @Description: 分页查询缺陷信息 
	 *    列表信息
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public Result<?> selectDefectByPage(Page<DefectVO> page, DefectVO bean) {
		List<DefectVO> list=this.defectDao.selectDefectByPage(page,bean);
		return Result.data(page.getTotal(), list);
	}

	/**
	 * @Description: 实时缺陷总数查询
	 * @return
	 */
	@Override
	public Integer selectDefectCountByAll(String tenantDomain , String windFarm) {
		QueryWrapper<FaultInformation> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("tenant_domain", tenantDomain);
		//1故障  2缺陷
		queryWrapper.eq("incident_type", Constants.TWO);
		//0未处理
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
		Integer count= this.defectDao.selectCount(queryWrapper);
		return count;
	}

	/**
   	 * @Description: 查询所有缺陷记录信息 
   	 * @param faultInfoId
   	 * @param tenantDomain
   	 * @return
   	 */
	@Override
	public Result<?> selectDefectByAll(FaultOperationRecord bean) {
		QueryWrapper<FaultOperationRecord> queryWrapper = new QueryWrapper<>();
		//升序
		queryWrapper.orderByAsc("create_time"); 
		//故障报警表ID
		if(StringUtils.isNotBlank(bean.getFaultInfoId())) {
			queryWrapper.eq("fault_info_id", bean.getFaultInfoId());
		}
		//租户code
		if(StringUtils.isNotBlank(bean.getTenantDomain())) {
			queryWrapper.eq("tenant_domain", bean.getTenantDomain());
		}
		List<FaultOperationRecord> list = this.faultOperationRecordDao.selectList(queryWrapper);
		return Result.data(list);
	}
	
	
}
