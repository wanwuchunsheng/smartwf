package com.smartwf.hm.modules.admin.pojo;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.smartwf.common.dto.LogDTO;

import lombok.Data;

/**
 * @author WCH
 * @Date: 2019-10-25 15:35:18
 * @Description: 日志
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@TableName( "sys_log")
public class Log extends LogDTO {


    /**
     * id
     */
	@TableId(type = IdType.AUTO)
    private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
