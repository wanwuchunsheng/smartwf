package com.smartwf.hm.modules.admin.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.hm.modules.admin.service.GlobalService;
import com.smartwf.hm.modules.alarmstatistics.dao.FaultDataDao;
import com.smartwf.hm.modules.alarmstatistics.dao.FaultOperationRecordDao;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultInformation;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultOperationRecord;
import com.smartwf.hm.modules.alarmstatistics.service.AlarmInboxService;
import com.smartwf.hm.modules.alarmstatistics.service.DefectService;
import com.smartwf.hm.modules.alarmstatistics.service.FaultInboxService;
import com.smartwf.hm.modules.alarmstatistics.vo.DefectVO;
import com.smartwf.hm.modules.alarmstatistics.vo.FaultInformationVO;

@Service
public class GlobalServiceImple implements GlobalService{
	
	@Autowired
	private AlarmInboxService alarmInboxService;
	
	@Autowired
	private DefectService defectService;
	
	@Autowired
	private FaultInboxService faultInboxService;
	
	@Autowired
	private FaultDataDao faultDataDao;
	
	@Autowired
	private FaultOperationRecordDao faultOperationRecordDao;
	

	/**
   	 *  故障、警告、缺陷 主键查询{生产中心提供接口}
   	 *     1）根据id查询对象
   	 *     2）根据对象类型判断调用哪个接口返回
   	 * @author WCH
   	 * @return
   	 */
	@Override
	public Result<?> selectfaultInformationById(String id) {
		FaultInformation bean= this.faultDataDao.selectById(id);
		if(bean!=null) {
			Integer incidentType=bean.getIncidentType();
			FaultInformationVO fv=null;
			//1-故障类型
			if(Constants.ONE==incidentType) {
				//封装对象
				fv=new FaultInformationVO();
				fv.setId(id);
				//调用故障接口
				return this.faultInboxService.selectFaultInforById(fv);
			}
			// 2-缺陷类型
			if(Constants.TWO ==incidentType) {
				//封装对象
				DefectVO dv= new DefectVO();
				dv.setId(id);
				//调用故障接口
				return this.defectService.selectDefectById(dv);
			}
			// 3告警类型
			if(Constants.ORDERSOURCES ==incidentType) {
				//封装对象
				fv=new FaultInformationVO();
				fv.setId(id);
				//调用故障接口
				return this.alarmInboxService.selectAlarmInforById(fv);
			}
		}
		return Result.msg(Constants.ERRCODE502012,"失败");
	}

	/**
	 * @Description: 故障、警告、缺陷处理意见 {生产中心提供接口}
	 * @author WCH
	 * @dateTime 2020-7-20 17:55:35
	 * @param bean
	 * @return
	 */
	@Override
	public Result<?> selectFaultRecordByAll(FaultOperationRecord bean) {
		QueryWrapper<FaultOperationRecord> queryWrapper = new QueryWrapper<>();
		//警告表ID
		if(StringUtils.isNotBlank(bean.getFaultInfoId())) {
			queryWrapper.eq("fault_info_id", bean.getFaultInfoId());
		}
		List<FaultOperationRecord> list = this.faultOperationRecordDao.selectList(queryWrapper);
		return Result.data(list);
	}

	
}
