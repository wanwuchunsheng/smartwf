package com.smartwf.sm.modules.admin.pojo;



import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.smartwf.common.dto.LogDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @Date: 2019-10-25 15:35:18
 * @Description: 日志
 * @author WCH
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@TableName( "sys_log")
public class Log extends LogDTO {


    /**
     * id
     */
	@TableId(type = IdType.AUTO)
    private Integer id;

}
