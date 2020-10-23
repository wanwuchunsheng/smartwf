package com.smartwf.sm.modules.sysconfig.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.sysconfig.pojo.EmailConfig;
import com.smartwf.sm.modules.sysconfig.vo.EmailConfigVO;

/**
 * @Description: 邮件短信配置业务层接口
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
public interface EmailConfigService {

	/**
	 * @Description: 查询邮件短信配置分页
	 * @param bean
	 * @return
	 */
	Result<?> selectEmailConfigByPage(Page<EmailConfig> page, EmailConfigVO bean);

	/**
     * @Description: 主键查询邮件短信配置
     * @param bean
     * @return
     */
	Result<?> selectEmailConfigById(EmailConfig bean);

	/**
     * @Description: 添加邮件短信配置
     * @param bean
     * @return
     */
	void saveEmailConfig(EmailConfig bean);

	/**
     * @Description： 修改邮件短信配置
     * @param bean
     * @return
     */
	void updateEmailConfig(EmailConfig bean);

	/**
     * @Description： 删除邮件短信配置
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
	void deleteEmailConfig(EmailConfigVO bean);

	
	
	

}
