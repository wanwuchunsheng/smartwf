package com.smartwf.sm.modules.admin.pojo;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.smartwf.common.dto.LogDTO;

import lombok.Getter;
import lombok.Setter;

/**
 * @Date: 2019-10-25 15:35:18
 * @Description: 日志
 * @author WCH
 */
@Setter
@Getter
@TableName( "sys_log")
public class Log extends LogDTO {


    /**
     * id
     */
	@TableId(type = IdType.AUTO)
    private Integer id;

	
}
