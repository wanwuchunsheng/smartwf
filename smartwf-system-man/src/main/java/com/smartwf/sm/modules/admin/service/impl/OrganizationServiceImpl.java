package com.smartwf.sm.modules.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.common.utils.StrUtils;
import com.smartwf.sm.modules.admin.dao.OrganizationDao;
import com.smartwf.sm.modules.admin.pojo.Organization;
import com.smartwf.sm.modules.admin.service.OrganizationService;
import com.smartwf.sm.modules.admin.vo.OrganizationVO;

import lombok.extern.log4j.Log4j;
/**
 * @Description: 组织架构业务层接口实现
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
@Log4j
public class OrganizationServiceImpl implements OrganizationService{
	
	@Autowired
	private OrganizationDao organizationDao;

	/**
	 * @Description:查询组织架构分页
	 * @result:
	 */
	@Override
	public Result<?> selectOrganizationByPage(Page<Organization> page, OrganizationVO bean) {
		QueryWrapper<Organization> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByDesc("update_time"); //降序
        //过滤租户（登录人为超级管理员，无需过滤，查询所有租户）
  		if (null!=bean.getTenantId() && Constants.ADMIN!=bean.getMgrType()) { 
  			queryWrapper.eq("tenant_id", bean.getTenantId()); 
  		} 
        //组织架构编码
        if (!StringUtils.isEmpty(bean.getOrgCode())) {
        	queryWrapper.like("org_code", Constants.PER_CENT + bean.getOrgCode() + Constants.PER_CENT);
        }
        //组织架构名称
        if (!StringUtils.isEmpty(bean.getOrgName())) {
        	queryWrapper.like("org_name", Constants.PER_CENT + bean.getOrgName() + Constants.PER_CENT);
        }
        //状态
		if (null!=bean.getEnable()) { 
			queryWrapper.eq("enable", bean.getEnable()); 
		}
        //时间
        if (bean.getStartTime() != null && bean.getEndTime() != null) {
        	queryWrapper.between("create_time", bean.getStartTime(), bean.getEndTime());
        }
        //备注
        if (!StringUtils.isEmpty(bean.getRemark())) {
        	queryWrapper.like("remark", Constants.PER_CENT + bean.getRemark() + Constants.PER_CENT);
        }
		IPage<Organization> list=this.organizationDao.selectPage(page, queryWrapper);
		return Result.data(list.getTotal(), list.getRecords());
	}

	/**
     * @Description: 主键查询组织架构
     * @return
     */
	@Override
	public Result<?> selectOrganizationById(Organization bean) {
		Organization Organization= this.organizationDao.selectById(bean.getId());
		return Result.data(Organization);
	}
	
	/**
     * @Description: 添加组织架构
     * @return
     */
	@Override
	public void saveOrganization(Organization bean) {
		//添加创建人基本信息
		User user=UserThreadLocal.getUser();
		bean.setCreateTime(new Date());
		bean.setCreateUserId(user.getId());
		bean.setCreateUserName(user.getUserName());
		bean.setUpdateTime(bean.getCreateTime());
		bean.setUpdateUserId(bean.getCreateUserId());
		bean.setUpdateUserName(bean.getCreateUserName());
		//保存
		this.organizationDao.insert(bean);
	}

	/**
     * @Description： 修改组织架构
     * @return
     */
	@Override
	public void updateOrganization(Organization bean) {
		//添加修改人信息
		User user=UserThreadLocal.getUser();
		bean.setUpdateTime(new Date());
		bean.setUpdateUserId(user.getId());
		bean.setUpdateUserName(user.getUserName());
		//修改
		this.organizationDao.updateById(bean);
	}

	/**
     * @Description： 删除组织架构
     * @return
     */
	@Transactional
	@Override
	public void deleteOrganization(OrganizationVO bean) {
		if( null!=bean.getId()) {
			//删除组织机构
			this.organizationDao.deleteById(bean.getId());
			//删除用户组织结构
			this.organizationDao.deleteUserOrgById(bean);
		}else {
			String ids=StrUtils.regex(bean.getIds());
			if(StringUtils.isNotBlank(ids)) {
				List<String> list=new ArrayList<>();
				for(String val:ids.split(",")) {
					list.add(val);
					bean=new OrganizationVO();
					bean.setId(Integer.valueOf(val));
					//删除用户组织结构
					this.organizationDao.deleteUserOrgById(bean);
				}
				//批量删除组织机构
				this.organizationDao.deleteOrganizationByIds(list);
			}
		}
	}
	

	/**
     * @Description： 初始化组织架构
     * @return
     */
	@Override
	public List<Organization> queryOrganizationAll() {
		return this.organizationDao.queryOrganizationAll();
	}


}
