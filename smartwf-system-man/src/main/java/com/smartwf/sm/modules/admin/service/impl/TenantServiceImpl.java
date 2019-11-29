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
import com.smartwf.sm.modules.admin.dao.TenantDao;
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.service.TenantService;
import com.smartwf.sm.modules.admin.vo.TenantVO;

import lombok.extern.log4j.Log4j;
import tk.mybatis.mapper.entity.Example;
/**
 * @Description: 租户业务层接口实现
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
@Log4j
public class TenantServiceImpl implements TenantService{
	
	@Autowired
	private TenantDao tenantDao;

	/**
	 * @Description:查询租户分页
	 * @result:
	 */
	@Override
	public Result<?> selectTenantByPage(Page<Object> page, TenantVO bean) {
		Page<Object> objectPage = PageHelper.startPage(page.getPageNum(), page.getPageSize());
		Example example = new Example(Tenant.class);
        example.setOrderByClause("create_time desc");
        Example.Criteria criteria = example.createCriteria();
        //租户编码
        if (!StringUtils.isEmpty(bean.getTenantCode())) {
            criteria.andLike("tenantCode", Constants.PER_CENT + bean.getTenantCode() + Constants.PER_CENT);
        }
        //租户名称
        if (!StringUtils.isEmpty(bean.getTenantName())) {
            criteria.andLike("tenantName", Constants.PER_CENT + bean.getTenantName() + Constants.PER_CENT);
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
		List<Tenant> list=this.tenantDao.selectByExample(example);
		return Result.data(objectPage.getTotal(), list);
	}

	/**
     * @Description: 主键查询租户
     * @return
     */
	@Override
	public Result<?> selectTenantById(Tenant bean) {
		Tenant tenant= this.tenantDao.selectByPrimaryKey(bean);
		return Result.data(tenant);
	}
	
	/**
     * @Description: 添加租户
     * @return
     */
	@Override
	public void saveTenant(Tenant bean) {
		//添加创建人基本信息
		User user=UserThreadLocal.getUser();
		bean.setCreateTime(new Date());
		bean.setCreateUserId(user.getId());
		bean.setCreateUserName(user.getUserName());
		bean.setUpdateTime(bean.getCreateTime());
		bean.setUpdateUserId(bean.getCreateUserId());
		bean.setUpdateUserName(bean.getCreateUserName());
		//保存
		this.tenantDao.insertSelective(bean);
	}

	/**
     * @Description： 修改租户
     * @return
     */
	@Override
	public void updateTenant(Tenant bean) {
		//添加修改人信息
		User user=UserThreadLocal.getUser();
		bean.setUpdateTime(new Date());
		bean.setUpdateUserId(user.getId());
		bean.setUpdateUserName(user.getUserName());
		//修改
		this.tenantDao.updateByPrimaryKeySelective(bean);
	}

	/**
     * @Description： 删除租户
     * @return
     */
	@Transactional
	@Override
	public void deleteTenant(TenantVO bean) {
		if( null!=bean.getId()) {
			//单个对象删除
			this.tenantDao.deleteByPrimaryKey(bean);
		}else {
			//批量删除
			if(StringUtils.isNotBlank(bean.getIds())) {
				List<String> list=new ArrayList<>();
				for(String val:bean.getIds().split(",")) {
					list.add(val);
				}
				this.tenantDao.deleteTenantByIds(list);
			}
		}
	}

	/**
     * @Description： 初始化租户
     * @return
     */
	@Override
	public List<Tenant> queryTenantAll() {
		return this.tenantDao.queryTenantAll();
	}


}
