package com.smartwf.hm.modules.knowledgebase.vo;

import java.util.Date;

import com.smartwf.hm.modules.knowledgebase.pojo.FaultCodeBase;

import lombok.Data;
@Data
public class FaultCodeBaseVO extends FaultCodeBase{

	/**
	 * 开始时间
	 */
	private Date stime;
	/**
	 * 结束时间
	 */
	private Date etime;
}
