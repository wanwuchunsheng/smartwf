package com.smartwf.hm.modules.knowledgebase.vo;

import java.util.Date;

import com.smartwf.hm.modules.knowledgebase.pojo.FaultCodeBase;

import lombok.Data;

/**
 * @author WCH
 * 
 * */

@Data
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
}
