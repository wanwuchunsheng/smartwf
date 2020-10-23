package com.smartwf.sm.modules.sysconfig.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.utils.CkUtils;
import com.smartwf.sm.modules.sysconfig.dao.EmailConfigDao;
import com.smartwf.sm.modules.sysconfig.pojo.EmailConfig;
import com.smartwf.sm.modules.sysconfig.service.EmailConfigService;
import com.smartwf.sm.modules.sysconfig.vo.EmailConfigVO;
/**
 * @Description: 邮件短信配置业务实现层
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
public class EmailConfigServiceImpl implements EmailConfigService{
	
	@Autowired
	private EmailConfigDao emailConfigDao;

	/**
	 * @Description: 查询邮件短信配置分页
	 * @param bean
	 * @return
	 */
	@Override
	public Result<?> selectEmailConfigByPage(Page<EmailConfig> page, EmailConfigVO bean) {
		List<EmailConfig> list=this.emailConfigDao.selectEmailConfigByPage(page,bean);
		return Result.data(Constants.EQU_SUCCESS, list);
	}

	/**
     * @Description: 主键查询邮件短信配置
     * @param bean
     * @return
     */
	@Override
	public Result<?> selectEmailConfigById(EmailConfig bean) {
		EmailConfig icfg=this.emailConfigDao.selectEmailConfigById(bean);
		return Result.data(Constants.EQU_SUCCESS, icfg);
	}

	/**
     * @Description: 添加邮件短信配置
     * @param bean
     * @return
     */
	@Override
	public void saveEmailConfig(EmailConfig bean) {
		this.emailConfigDao.insert(bean);
	}

	/**
     * @Description： 修改邮件短信配置
     * @param bean
     * @return
     */
	@Override
	public void updateEmailConfig(EmailConfig bean) {
		this.emailConfigDao.updateById(bean);
	}

	/**
     * @Description： 删除邮件短信配置
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteEmailConfig(EmailConfigVO bean) {
		if( null!=bean.getId()) {
			//删除多租户配置表
			this.emailConfigDao.deleteById(bean);
		}else {
			String ids=CkUtils.regex(bean.getIds());
			//批量删除
			if(StringUtils.isNotBlank(ids)) {
				List<String> list=new ArrayList<>();
				for(String val:ids.split(Constants.CHAR)) {
					list.add(val);
				}
				//多租户配置表
				this.emailConfigDao.deleteEmailConfigByIds(list);
			}
		}
	}
	

}
