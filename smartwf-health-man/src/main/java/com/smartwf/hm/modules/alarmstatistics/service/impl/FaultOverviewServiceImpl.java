package com.smartwf.hm.modules.alarmstatistics.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartwf.common.pojo.Result;
import com.smartwf.hm.modules.alarmstatistics.dao.FaultOverviewDao;
import com.smartwf.hm.modules.alarmstatistics.service.FaultOverviewService;
import com.smartwf.hm.modules.alarmstatistics.vo.FaultInformationVO;


/**
 * @Date: 2019-11-27 11:25:24
 * @Description: 故障总览业务层实现
 */
@Service
public class FaultOverviewServiceImpl implements FaultOverviewService {
	
	@Autowired
	FaultOverviewDao faultOverviewDao;

	/**
	 * @Description:  故障类型统计 1:30天   2:90天   3：一年   4 自定义
	 * @param faultType  0-系统故障    1-预警信息   2-人工提交
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public Result<?> selectFaultTypeByDate(FaultInformationVO bean) {
		List<Map<String,Object>> list=new ArrayList<>();
		Map<String,Object> fr=null;
		String[][] master=null;
		//0统计故障
		List<FaultInformationVO> fault= this.faultOverviewDao.selectFaultTypeByFault(bean);
		//1统计报警
		List<FaultInformationVO> alarm= this.faultOverviewDao.selectFaultTypeByAlarm(bean);
		//2统计缺陷
		List<FaultInformationVO> defect= this.faultOverviewDao.selectFaultTypeByDefect(bean);
		//封装故障
		if(fault!=null && fault.size()>0) {
			master =new String[fault.size()][2];
			int i = 0; 
			for(FaultInformationVO fivo:fault ) {
				master[i][0]=fivo.getFname();
				master[i][1]=fivo.getFvalue();
				i++;
			}
			fr = new HashMap<String,Object>();
			fr.put("id", 0);
			fr.put("name", "故障");
			fr.put("data",master);
			list.add(fr);
		}
		
		//封装报警
		if(alarm!=null && alarm.size()>0) {
			master =new String[alarm.size()][2];
			int i = 0; 
			for(FaultInformationVO fivo:alarm ) {
				master[i][0]=fivo.getFname();
				master[i][1]=fivo.getFvalue();
				i++;
			}
			fr = new HashMap<String,Object>();
			fr.put("id", 1);
			fr.put("name", "报警");
			fr.put("data",master);
			list.add(fr);
		}
		//封装缺陷
		if(defect!=null && defect.size()>0) {
			master =new String[defect.size()][2];
			int i = 0; 
			for(FaultInformationVO fivo:defect ) {
				master[i][0]=fivo.getFname();
				master[i][1]=fivo.getFvalue();
				i++;
			}
			fr = new HashMap<String,Object>();
			fr.put("id", 2);
			fr.put("name", "缺陷");
			fr.put("data",master);
			list.add(fr);
		}
		//返回结果
		return Result.data(list);
	}

	
	/**
	 * @Description: 故障等级分布统计 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public Result<?> selectFaultDistrByDate(FaultInformationVO bean) {
		List<Map<String,Object>> list=new ArrayList<>();
		Map<String,Object> fr=null;
		String[][] master=null;
		//1查询故障分布统计数据
		List<FaultInformationVO> fault= this.faultOverviewDao.selectFaultDistrByDate(bean);
		//2故障分布统计数据封装
		if(fault!=null && fault.size()>0) {
			master =new String[fault.size()][2];
			int i = 0; 
			for(FaultInformationVO fivo:fault ) {
				master[i][0]=fivo.getFname();
				master[i][1]=fivo.getFvalue();
				i++;
			}
			fr = new HashMap<String,Object>();
			fr.put("id", 1);
			fr.put("name", "故障分布统计");
			fr.put("data",master);
			list.add(fr);
		}
		//返回结果
		return Result.data(list);
	}


}
