package com.smartwf.hm.modules.knowledgebase.vo;

import java.util.Date;

import com.smartwf.hm.modules.knowledgebase.pojo.FaultCodeBase;

import lombok.Data;

/**
 * @author WCH
 * 
 * */
public class FaultCodeBaseVO extends FaultCodeBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 开始时间
	 */
	private Date stime;
	/**
	 * 结束时间
	 */
	private Date etime;
	
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
		return "FaultCodeBaseVO [stime=" + stime + ", etime=" + etime + "]";
	}
	
	
}
