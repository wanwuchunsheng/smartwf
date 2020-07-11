package com.smartwf.hm.modules.alarmstatistics.vo;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.smartwf.common.annotation.ParamValidated.Query;
import com.smartwf.hm.modules.alarmstatistics.pojo.FaultInformation;

import lombok.Data;
/**
 * @Description: 故障报警表
 * @author WCH
 */
public class FaultInformationVO extends FaultInformation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 名称
	 */
	private String fname;
	/**
	 * 值
	 */
	private String fvalue;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 关闭原因
	 */
	private String closureReason;
	/**
	 * 开始时间
	 */
	private Date stime;
	/**
	 * 结束时间
	 */
	private Date etime;
	
	/**
	 * 未处理
	 * 
	 * */
	private Integer untreated;
	/**
	 * 故障类型-天
	 * 
	 * */
	private Integer faultTypeDay;
	/**
	 * 故障类型-月
	 * 
	 * */
	private Integer faultTypeMouth;
	/**
	 * 故障类型-所有
	 * 
	 * */
	private Integer faultTypeAll;
	/**
	 * 最近时间
	 * 
	 * */
	private String recentTime;
	
	
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getFvalue() {
		return fvalue;
	}
	public void setFvalue(String fvalue) {
		this.fvalue = fvalue;
	}
	@Override
	public String getRemark() {
		return remark;
	}
	@Override
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getClosureReason() {
		return closureReason;
	}
	public void setClosureReason(String closureReason) {
		this.closureReason = closureReason;
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
	public Integer getUntreated() {
		return untreated;
	}
	public void setUntreated(Integer untreated) {
		this.untreated = untreated;
	}
	public Integer getFaultTypeDay() {
		return faultTypeDay;
	}
	public void setFaultTypeDay(Integer faultTypeDay) {
		this.faultTypeDay = faultTypeDay;
	}
	public Integer getFaultTypeMouth() {
		return faultTypeMouth;
	}
	public void setFaultTypeMouth(Integer faultTypeMouth) {
		this.faultTypeMouth = faultTypeMouth;
	}
	public Integer getFaultTypeAll() {
		return faultTypeAll;
	}
	public void setFaultTypeAll(Integer faultTypeAll) {
		this.faultTypeAll = faultTypeAll;
	}
	public String getRecentTime() {
		return recentTime;
	}
	public void setRecentTime(String recentTime) {
		this.recentTime = recentTime;
	}
	@Override
	public String toString() {
		return "FaultInformationVO [fname=" + fname + ", fvalue=" + fvalue + ", remark=" + remark + ", closureReason="
				+ closureReason + ", stime=" + stime + ", etime=" + etime + ", untreated=" + untreated
				+ ", faultTypeDay=" + faultTypeDay + ", faultTypeMouth=" + faultTypeMouth + ", faultTypeAll="
				+ faultTypeAll + ", recentTime=" + recentTime + "]";
	}
	
}
