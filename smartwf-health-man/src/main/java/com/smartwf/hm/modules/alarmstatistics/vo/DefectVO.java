package com.smartwf.hm.modules.alarmstatistics.vo;

import java.io.Serializable;
import java.util.Date;

import com.smartwf.hm.modules.alarmstatistics.pojo.FaultInformation;

import lombok.Data;

/**
 *@Deprecated 实体类  
 *@author WCH
 * */
public class DefectVO extends FaultInformation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 附件路径
	 * 
	 * */
	private String filePath;
	
	/**
	 * 开始时间
	 */
	private Date stime;
	/**
	 * 结束时间
	 */
	private Date etime;
	
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Date getStime() {
		return stime;
	}
	public void setStime(Date stime) {
		this.stime = stime;
	}
	public Date getEtime() {
		return etime;
	}
	public void setEtime(Date etime) {
		this.etime = etime;
	}
	
	@Override
	public String toString() {
		return "DefectVO [filePath=" + filePath + ", stime=" + stime + ", etime=" + etime + "]";
	}
	
	
}
