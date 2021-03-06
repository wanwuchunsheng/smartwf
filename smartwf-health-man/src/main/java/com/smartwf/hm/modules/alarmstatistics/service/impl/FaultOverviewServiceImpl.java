package com.smartwf.hm.modules.alarmstatistics.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.hm.modules.alarmstatistics.dao.FaultOverviewDao;
import com.smartwf.hm.modules.alarmstatistics.dao.SecurityIncidentsDao;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultInformation;
import com.smartwf.hm.modules.alarmstatistics.service.FaultOverviewService;
import com.smartwf.hm.modules.alarmstatistics.vo.FaultInformationVO;
import com.smartwf.hm.modules.alarmstatistics.vo.SecurityIncidentsVO;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.log4j.Log4j2;


/**
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 * @Description: 故障总览业务层实现
 */
@Service
@Log4j2
public class FaultOverviewServiceImpl implements FaultOverviewService {
	
	@Autowired
	private FaultOverviewDao faultOverviewDao;
	
	@Autowired
	private SecurityIncidentsDao securityIncidentsDao;
	
	

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
		//3安全事故
		List<FaultInformationVO> securityInc= this.securityIncidentsDao.selectSecurityIncidents(bean);
		//故障，警告，缺陷全局未处理数统计
		List<Map<String,Integer>> stList=this.faultOverviewDao.selectFaultStatusByAll(bean);
		//封装故障
		if(fault!=null && fault.size()>0) {
			master =new String[fault.size()][2];
			int i = 0; 
			for(FaultInformationVO fivo:fault ) {
				master[i][0]=fivo.getFname();
				master[i][1]=fivo.getFvalue();
				i++;
			}
			fr = new HashMap<String,Object>(4);
			fr.put("id", 0);
			fr.put("title", "故障");
			fr.put("content",master);
			//故障未处理数
			int val=0;
			for( Map<String,Integer> m:stList) {
				if("fault".equals(Convert.toStr(m.get("name")))) {
					val=Convert.toInt( m.get("value"));
					break;
				}
			}
			fr.put("fault", val);
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
			fr = new HashMap<String,Object>(4);
			fr.put("id", 1);
			fr.put("title", "报警");
			fr.put("content",master);
			//警告未处理数
			int val=0;
			for( Map<String,Integer> m:stList) {
				if("alert".equals(Convert.toStr(m.get("name")))) {
					val=Convert.toInt( m.get("value"));
					break;
				}
			}
			fr.put("alert", val);
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
			fr = new HashMap<String,Object>(4);
			fr.put("id", 2);
			fr.put("title", "缺陷");
			fr.put("content",master);
			//缺陷未处理数
			int val=0;
			for( Map<String,Integer> m:stList) {
				if("defect".equals(Convert.toStr(m.get("name")))) {
					val=Convert.toInt( m.get("value"));
					break;
				}
			}
			fr.put("defect", val);
			list.add(fr);
		}
		//事故
		if(securityInc!=null && securityInc.size()>0) {
			master =new String[securityInc.size()][2];
			int i = 0; 
			for(FaultInformationVO fivo:securityInc ) {
				master[i][0]=fivo.getFname();
				master[i][1]=fivo.getFvalue();
				i++;
			}
			fr = new HashMap<String,Object>(4);
			fr.put("id", 3);
			fr.put("title", "事故");
			fr.put("content",master);
			//缺陷未处理数
			int val=0;
			for( Map<String,Integer> m:stList) {
				if("securityInc".equals(Convert.toStr(m.get("name")))) {
					val=Convert.toInt( m.get("value"));
					break;
				}
			}
			fr.put("securityInc", val);
			list.add(fr);
		}
		
		//返回结果
		return Result.data(list);
	}

	
	/**
	 * @Description: 故障等级分布统计
	 *    1 全局故障等级循环遍历（该处不灵活，后期可优化，借助远程调用系统管理中心）
	 *    2 分别遍历等级查询故障数据
	 *    3  封装故障数据返回前端
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public Result<?> selectFaultLevelByDate(FaultInformationVO bean) {
		List<Map<String,Object>> list=new ArrayList<>();
		Map<String,Object> fr=null;
		String[][] master=null;
		//1·获得两个日期间的所有日期集合
		List<DateTime> listDates= DateUtil.rangeToList( bean.getStime() ,bean.getEtime(), DateField.DAY_OF_YEAR );
		for(int t=0;t<Constants.ALARMLEVEL;t++) {
			bean.setAlarmLevel(t);
			//2.查询故障分布统计数据
			List<FaultInformationVO> fault= this.faultOverviewDao.selectFaultLevelByDate(bean);
			if(listDates!=null && listDates.size()>0) {
				master =new String[listDates.size()][2];
				int i = 0;
				//3.遍历日期集合
				for(DateTime str:listDates) {
					String fmtDate=DateUtil.format(str, "yyyy-MM-dd");
					master[i][0]=fmtDate;
					//4.查询统计分布时间和日期集合对比
					if(fault!=null && fault.size()>0) {
						for(FaultInformationVO fivo:fault ) {
							if(fmtDate.equals(fivo.getFname())) {
								//5.存在赋值跳出循环，不存在默认给0
								master[i][1]=fivo.getFvalue();
								break;
							}
							//当前日期没有值，赋默认值0
							master[i][1]="0"; 
						}
					}
					i++;
				}
				//封装参数对象
				fr = new HashMap<String,Object>(4);
				fr.put("id", t);
				fr.put("content",master);
				list.add(fr);
			}
		}
		//返回结果
		return Result.data(list);
	}
	
	/**
     * 说明：门户故障/缺陷/警告 统计
     *   1）查询所有信息状态
     *   2）查询已处理的信息列表
     * @param bean
     * @return
     * */
	@Override
	public Result<?> selectFaultByAlarmStatus(Page<FaultInformation> page,FaultInformationVO bean) {
		//故障状态统计
		List<Map<String,String>> listAlarmStatus= this.faultOverviewDao.selectFaultByAlarmStatus(bean);
		//已处理故障分页查询
		List<Map<String,String>> listFaultInfomation=this.faultOverviewDao.selectFaultInformationByPage(page,bean);
		Map<Integer,List<Map<String,String>>> map =new HashMap<>();
		map.put(0, listAlarmStatus);
		map.put(1, listFaultInfomation);
		return Result.data(Constants.EQU_SUCCESS, map);
	}

	/**
	 * @Description: 故障处理状态
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public Result<?> selectFaultStatusByDate(FaultInformationVO bean) {
		String[][] master=null;
		//故障状态，数量统计
		List<FaultInformationVO> alarmStatus= this.faultOverviewDao.selectFaultStatusByDate(bean);
		//故障状态
		if(alarmStatus!=null && alarmStatus.size()>0) {
			master =new String[alarmStatus.size()][2];
			int i = 0; 
			for(FaultInformationVO fivo: alarmStatus ) {
				master[i][0]=String.valueOf(fivo.getAlarmStatus());
				master[i][1]=fivo.getFvalue();
				i++;
			}
		}
		return Result.data(Constants.EQU_SUCCESS,master);
	}

	

	/**
	 * @Description: 处理效率统计
	 * @param alarmStatus 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public Result<?> selectProcessingEfficByDate(FaultInformationVO bean) {
		String[][] master=null;
		//故障部位，数量统计
		List<FaultInformationVO> alarmLocation= this.faultOverviewDao.selectFaultLocationByDate(bean);
		//故障部位
		if(alarmLocation!=null && alarmLocation.size()>0) {
			master =new String[alarmLocation.size()][2];
			int i = 0; 
			for(FaultInformationVO fivo: alarmLocation ) {
				master[i][0]=String.valueOf(fivo.getFname());
				master[i][1]=fivo.getFvalue();
				i++;
			}
		}
		return Result.data(Constants.EQU_SUCCESS,master);
	}


	/**
   	 * @Description: (故障、缺陷、警告)未处理记录统计
   	 * @author WCH
   	 * @dateTime 2020-7-20 17:55:35
   	 * @param bean
   	 * @return
   	 */
	@Override
	public Result<?> selectFaultRecordByIncidentType(FaultInformationVO bean) {
		//故障部位，数量统计
		List<Map<String,Object>> alarmLocation= this.faultOverviewDao.selectFaultRecordByIncidentType(bean);
		Map<String,Object> map=new HashMap<>();
		for(Map<String,Object> m: alarmLocation) {
			map.put(JSONUtil.toJsonStr(m.get("fname")), m.get("fvalue"));
		}
		//验证是否存在
		if(!map.containsKey("fault")) {
			map.put("fault", 0);
		}
		if(!map.containsKey("defect")) {
			map.put("defect", 0);
		}
		if(!map.containsKey("alert")) {
			map.put("alert", 0);
		}
		return Result.data(map);
	}
	
	
}
