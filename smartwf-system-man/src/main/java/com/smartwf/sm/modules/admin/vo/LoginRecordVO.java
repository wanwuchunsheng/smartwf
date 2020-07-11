package com.smartwf.sm.modules.admin.vo;

import java.util.Date;

import com.smartwf.sm.modules.admin.pojo.LoginRecord;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * 
 * @author WCH
 * 
 * */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class LoginRecordVO extends LoginRecord{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ids; 
	
	private Date startTime;
	
	private Date endTime;

}
