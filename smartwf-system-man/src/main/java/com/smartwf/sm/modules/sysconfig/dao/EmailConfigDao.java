package com.smartwf.sm.modules.sysconfig.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.sm.modules.sysconfig.pojo.EmailConfig;
import com.smartwf.sm.modules.sysconfig.pojo.EmailConfig;
import com.smartwf.sm.modules.sysconfig.vo.EmailConfigVO;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 久层接口
 * @author WCH
 */
@Repository
public interface EmailConfigDao extends BaseMapper<EmailConfig> {

	/**
	 * @Description: 查询邮件短信配置分页
	 * @param bean
	 * @return
	 */
	List<EmailConfig> selectEmailConfigByPage(Page<EmailConfig> page, @Param("bean") EmailConfigVO bean);

	/**
     * @Description: 主键查询邮件短信配置
     * @param bean
     * @return
     */
	EmailConfig selectEmailConfigById(@Param("bean") EmailConfig bean);

	/**
     * @Description： 删除邮件短信配置
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
	void deleteEmailConfigByIds(@Param("list") List<String> list);

	

    
}
