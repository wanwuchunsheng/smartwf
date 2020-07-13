package com.smartwf.common.pojo;

import lombok.Data;

/**
 * @author WCH
 * @Description: 分页
 */
@Data
public class PageVO {
	
	 /**
     * 第几页
     */
    private Integer page;

    /**
     * 每页查询数量
     */
    private Integer limit;
    
	/**
	 * 构造默认值
	 * 
	 * */
	public PageVO() {
		this.page=1;
		this.limit=10;
	}

   
}
