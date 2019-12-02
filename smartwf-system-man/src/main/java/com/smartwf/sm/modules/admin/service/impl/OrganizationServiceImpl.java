package com.smartwf.sm.modules.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.sm.modules.admin.dao.OrganizationDao;
import com.smartwf.sm.modules.admin.pojo.Organization;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.service.OrganizationService;
import com.smartwf.sm.modules.admin.vo.OrganizationVO;

import lombok.extern.log4j.Log4j;
import tk.mybatis.mapper.entity.Example;
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
	public Result<?> selectOrganizationByPage(Page<Object> page, OrganizationVO bean) {
		Page<Object> objectPage = PageHelper.startPage(page.getPageNum(), page.getPageSize());
		Example example = new Example(Organization.class);
        example.setOrderByClause("create_time desc");
        Example.Criteria criteria = example.createCriteria();
        //过滤租户（登录人为超级管理员，无需过滤，查询所有租户）
  		if (null!=bean.getTenantId() && Constants.ADMIN!=bean.getMgrType()) { 
  			criteria.andEqualTo("tenantId", bean.getTenantId()); 
  		} 
        //组织架构编码
        if (!StringUtils.isEmpty(bean.getOrgCode())) {
            criteria.andLike("orgCode", Constants.PER_CENT + bean.getOrgCode() + Constants.PER_CENT);
        }
        //组织架构名称
        if (!StringUtils.isEmpty(bean.getOrgName())) {
            criteria.andLike("orgName", Constants.PER_CENT + bean.getOrgName() + Constants.PER_CENT);
        }
        //状态
		if (null!=bean.getEnable()) { 
			criteria.andEqualTo("enable", bean.getEnable()); 
		}
        //时间
        if (bean.getStartTime() != null && bean.getEndTime() != null) {
            criteria.orBetween("createTime", bean.getStartTime(), bean.getEndTime());
        }
        //备注
        if (!StringUtils.isEmpty(bean.getRemark())) {
            criteria.andLike("remark", Constants.PER_CENT + bean.getRemark() + Constants.PER_CENT);
        }
		List<Organization> list=this.organizationDao.selectByExample(example);
		return Result.data(objectPage.getTotal(), list);
	}

	/**
     * @Description: 主键查询组织架构
     * @return
     */
	@Override
	public Result<?> selectOrganizationById(Organization bean) {
		Organization Organization= this.organizationDao.selectByPrimaryKey(bean);
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
		this.organizationDao.insertSelective(bean);
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
		this.organizationDao.updateByPrimaryKeySelective(bean);
	}

	/**
     * @Description： 删除组织架构
     * @return
     */
	@Transactional
	@Override
	public void deleteOrganization(OrganizationVO bean) {
		if( null!=bean.getId()) {
			//单个对象删除
			this.organizationDao.deleteByPrimaryKey(bean);
			
		}else {
			//批量删除
			if(StringUtils.isNotBlank(bean.getIds())) {
				List<String> list=new ArrayList<>();
				for(String val:bean.getIds().split(",")) {
					list.add(val);
				}
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
