package com.smartwf.hm.modules.alarmstatistics.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.common.service.RedisService;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.hm.modules.alarmstatistics.dao.FileUploadRecordDao;
import com.smartwf.hm.modules.alarmstatistics.dao.SecurityIncidentsDao;
import com.smartwf.hm.modules.alarmstatistics.pojo.FileUploadRecord;
import com.smartwf.hm.modules.alarmstatistics.pojo.SecurityIncidents;
import com.smartwf.hm.modules.alarmstatistics.service.SecurityIncidentsService;
import com.smartwf.hm.modules.alarmstatistics.vo.SecurityIncidentsVO;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.log4j.Log4j2;
/**
 * 安全事故实现类
 * @author WCH
 * @Date: 2021年2月2日11:35:53
 */
@Service
@Log4j2
public class SecurityIncidentsServiceImpl implements SecurityIncidentsService{
	
	@Autowired
	private SecurityIncidentsDao securityIncidentsDao;
	
	@Autowired
	private FileUploadRecordDao fileUploadRecordDao;
	
	@Autowired
	private RedisService redisService;
	
	/**
	 * @Description: 安全事故-分页查询
	 *    列表信息
	 * @param startTime,endTime
	 * @param  incidentCode,locality
	 * @return
	 */
	@Override
	public Result<?> selectAlarmInforByPage(Page<SecurityIncidents> page, SecurityIncidentsVO bean) {
		QueryWrapper<SecurityIncidents> queryWrapper = new QueryWrapper<>();
		//租户域
		if (StrUtil.isNotEmpty(bean.getTenantDomain())) {
			queryWrapper.eq("tenant_domain", bean.getTenantDomain());
		}
		//风场
		if (StrUtil.isNotEmpty(bean.getWindFarm())) {
			queryWrapper.eq("wind_farm", bean.getWindFarm());
		}
		//发生地质
		if (StrUtil.isNotEmpty(bean.getLocality())) {
			 queryWrapper.like("locality", Constants.PER_CENT + bean.getLocality() + Constants.PER_CENT);
		}
		//事故编码
		if (StrUtil.isNotEmpty(bean.getIncidentCode())) {
	        queryWrapper.like("incident_code", Constants.PER_CENT + bean.getIncidentCode() + Constants.PER_CENT);
	    }
		//发生时间范围
		if (bean.getStartTime() != null && bean.getEndTime() != null) {
			queryWrapper.between("occurrence_time", bean.getStartTime(), bean.getEndTime());
		}
		//发生时间降序排列
		queryWrapper.orderByDesc("occurrence_time");
		IPage<SecurityIncidents> datas=this.securityIncidentsDao.selectPage(page, queryWrapper);
		return Result.data(Constants.EQU_SUCCESS, datas.getTotal(),datas.getRecords());
	}

	/**
	 * @Description: 安全事故-主键查询
	 * @param id
	 * @return
	 */
	@Override
	public Result<?> selectSecurityIncidentsById(SecurityIncidents bean) {
		QueryWrapper<SecurityIncidents> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id", bean.getId());
		List<SecurityIncidents> datas= this.securityIncidentsDao.selectList(queryWrapper);
		return Result.data(Constants.EQU_SUCCESS, datas);
	}

	/**
	 * @Description: 安全事故-附件查询
	 * @param id
	 * @return
	 */
	@Override
	public Result<?> selectSecurityIncidentsByFiles(SecurityIncidents bean) {
		QueryWrapper<FileUploadRecord> queryWrapper=new QueryWrapper<>();
		//附件ID
		queryWrapper.eq("pid", bean.getId());
		List<FileUploadRecord> datas = fileUploadRecordDao.selectList(queryWrapper);
		return Result.data(Constants.EQU_SUCCESS, datas);
	}

	/**
	 * @Description: 安全事故-添加
	 * a.先保存事故记录，返回ID
	 * b.再插入图片附件
	 * @param id
	 * @return
	 */
	@Override
	public Result<?> saveSecurityIncidents(SecurityIncidentsVO bean) {
		User user= UserThreadLocal.getUser();
		bean.setCreateTime(new Date());
		bean.setCreateUserId(user.getId());
		bean.setCreateUserName(user.getUserName());
		bean.setUpdateTime(bean.getCreateTime());
		bean.setUpdateUserId(bean.getCreateUserId());
		bean.setUpdateUserName(bean.getCreateUserName());
		bean.setIncidentStatus(Constants.ZERO);
		//事故编码 随机生成
		bean.setIncidentCode(IdUtil.createSnowflake(1, 1).nextIdStr());
		this.securityIncidentsDao.insert(bean);
		//添加附件
		if(StrUtil.isNotBlank(bean.getFilePath()) ) {
			String[] str=bean.getFilePath().split("&&");
			if(str != null && str.length>0) {
				FileUploadRecord fuRecord=null;
				for(String s:str) {
					String[] cont=s.split(",,");
					fuRecord= new FileUploadRecord();
					fuRecord.setPid(bean.getId());
					fuRecord.setFilePath(Convert.toStr(cont[0]));
					fuRecord.setTenantDomain(bean.getTenantDomain());
					fuRecord.setRemark(Convert.toStr(cont[1]));
					fuRecord.setCreateTime(new Date());
					fuRecord.setCreateUserId(user.getId());
					fuRecord.setCreateUserName(user.getUserName());
					this.fileUploadRecordDao.insert(fuRecord);
				}
			}
		}
		return Result.msg(Constants.EQU_SUCCESS, "保存成功！");
	}

	/**
	 * @Description: 安全事故-修改
	 * @param id
	 * @return
	 */
	@Override
	public Result<?> updateSecurityIncidents(SecurityIncidentsVO bean) {
		User user= UserThreadLocal.getUser();
		bean.setUpdateTime(new Date());
		bean.setUpdateUserId(user.getId());
		bean.setUpdateUserName(user.getUserName());
		this.securityIncidentsDao.updateById(bean);
		//保存附件，1删除原有附件  2重新添加附件
		QueryWrapper<FileUploadRecord> queryWrapper = new QueryWrapper<>();
		//主键
		queryWrapper.eq("pid", bean.getId());
		//删除
		this.fileUploadRecordDao.delete(queryWrapper);
		//重新添加附件
		if(StrUtil.isNotBlank(bean.getFilePath()) ) {
			String[] str=bean.getFilePath().split("&&");
			if(str != null && str.length>0) {
				FileUploadRecord fuRecord=null;
				for(String s:str) {
					String[] cont=s.split(",,");
					fuRecord= new FileUploadRecord();
					fuRecord.setPid(bean.getId());
					fuRecord.setFilePath(Convert.toStr(cont[0]));
					fuRecord.setTenantDomain(bean.getTenantDomain());
					fuRecord.setRemark(Convert.toStr(cont[1]));
					fuRecord.setCreateTime(new Date());
					fuRecord.setCreateUserId(user.getId());
					fuRecord.setCreateUserName(user.getUserName());
					this.fileUploadRecordDao.insert(fuRecord);
				}
			}
		}
		return Result.msg(Constants.EQU_SUCCESS,"修改成功！");
	}

	/**
 	 *  功能说明：安全生产多少天
 	 *    根据事故记录分析系统运行天数
 	 * @author WCH
 	 * @return
 	 */
	@Override
	public Result<?> selectSafetyProductionTime(SecurityIncidentsVO bean) {
		Map<String,Object> map = JSONUtil.parseObj(redisService.get("safetyProductionTime"));
		return Result.data(Constants.EQU_SUCCESS, map);
	}

	/**
	 * @Description: 安全事故-删除附件
	 * @param id
	 * @return
	 */
	@Override
	public Result<?> deleteSecurityIncidentsByFiles(SecurityIncidentsVO bean) {
		QueryWrapper<FileUploadRecord> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("pid", bean.getFilePath());
		queryWrapper.eq("file_path", bean.getFilePath());
		this.fileUploadRecordDao.delete(queryWrapper);
		return Result.msg(Constants.EQU_SUCCESS, "删除成功");
	}

	/**
	 * 功能说明：通过主键,路径查询附件信息
	 * @author WCH
	 * @Date 2021年2月4日13:59:17
	 * 
	 * */
	@Override
	public FileUploadRecord selectFileUploadRecord(SecurityIncidentsVO bean) {
		QueryWrapper<FileUploadRecord> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("pid", bean.getFilePath());
		queryWrapper.eq("file_path", bean.getFilePath());
		return this.fileUploadRecordDao.selectOne(queryWrapper);
	}

}
