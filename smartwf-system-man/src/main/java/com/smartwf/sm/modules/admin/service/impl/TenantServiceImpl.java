package com.smartwf.sm.modules.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.sql.ast.statement.SQLWithSubqueryClause.Entry;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.common.utils.GenerateUtils;
import com.smartwf.common.utils.MD5Utils;
import com.smartwf.common.utils.StrUtils;
import com.smartwf.sm.modules.admin.dao.DictionaryDao;
import com.smartwf.sm.modules.admin.dao.TenantDao;
import com.smartwf.sm.modules.admin.pojo.Dictionary;
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.service.TenantService;
import com.smartwf.sm.modules.admin.service.UserInfoService;
import com.smartwf.sm.modules.admin.vo.TenantVO;
import com.smartwf.sm.modules.admin.vo.UserInfoVO;
import com.smartwf.sm.modules.wso2.pojo.Wso2Tenant;
import com.smartwf.sm.modules.wso2.service.Wso2TenantService;

import lombok.extern.log4j.Log4j;
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
	
	@Autowired
	private DictionaryDao dictionaryDao;
	
	@Autowired
	Wso2TenantService wso2TenantService;
	
	@Autowired
	UserInfoService userInfoService;

	/**
	 * @Description:查询租户分页
	 * @result:
	 */
	@Override
	public Result<?> selectTenantByPage(Page<Tenant> page, TenantVO bean) {
		QueryWrapper<Tenant> queryWrapper = new QueryWrapper<>();
		//降序
		queryWrapper.orderByDesc("update_time"); 
        //租户编码
        if (!StringUtils.isEmpty(bean.getTenantCode())) {
        	queryWrapper.like("tenant_code", Constants.PER_CENT + bean.getTenantCode() + Constants.PER_CENT);
        }
        //租户名称
        if (!StringUtils.isEmpty(bean.getTenantName())) {
        	queryWrapper.like("tenant_name", Constants.PER_CENT + bean.getTenantName() + Constants.PER_CENT);
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
		IPage<Tenant> list=this.tenantDao.selectPage(page, queryWrapper);
		return Result.data(list.getTotal(), list.getRecords());
	}

	/**
     * @Description: 主键查询租户
     * @return
     */
	@Override
	public Result<?> selectTenantById(Tenant bean) {
		Tenant tenant= this.tenantDao.selectById(bean);
		return Result.data(tenant);
	}
	
	/**
     * @Description: 添加租户
     * @return
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void saveTenant(Tenant bean) {
		//1）查询默认租户
		QueryWrapper<Tenant> queryWrapper = new QueryWrapper<>();
		//默认租户：0-否  1-是
		queryWrapper.eq("sel", 1);
		Tenant dict=this.tenantDao.selectOne(queryWrapper);
		//2）添加新租户
		User user=UserThreadLocal.getUser();
		bean.setCreateTime(new Date());
		bean.setCreateUserId(user.getId());
		bean.setCreateUserName(user.getUserName());
		bean.setUpdateTime(bean.getCreateTime());
		bean.setUpdateUserId(bean.getCreateUserId());
		bean.setUpdateUserName(bean.getCreateUserName());
		if(bean.getSel().equals(Constants.ISSEL)) {
			this.tenantDao.updateBySel();//置空默认租户
		}
		this.tenantDao.insert(bean);
		//wso2租户添加，模拟默认用户
		Wso2Tenant wt=new Wso2Tenant();
		wt.setActive(true);
		wt.setAdmin(bean.getTenantCode());
		wt.setAdminPassword(bean.getTenantPw());
		wt.setEmail("email@windmagics.com");
		wt.setFirstname("firstname");
		wt.setLastname("lastname");
		wt.setTenantDomain(bean.getTenantDomain());
		this.wso2TenantService.addTenant(wt);
		//系统中心用户表添加用户信息
		UserInfoVO tv=new UserInfoVO();
		//登录名
		tv.setLoginCode(bean.getTenantCode());
		//用户名
		tv.setUserName(bean.getTenantCode());
		//密码
		tv.setPwd(bean.getTenantPw());
		//租户ID
		tv.setTenantId(bean.getId()); 
		this.userInfoService.saveWso2UserInfo(tv,bean);
		//3）批量添加新租户数据字典（从默认租户拷贝的数据字典）
		if( null !=dict) {
			QueryWrapper<Dictionary> queryWrapper2 = new QueryWrapper<>();
			queryWrapper2.eq("tenant_id", dict.getId());
			List<Dictionary> list=this.dictionaryDao.selectList(queryWrapper2);
			if(list!=null && list.size()>0){
				int tenantId=bean.getId();
				for(Dictionary dt: list) {
					if(Constants.ZERO==dt.getUid()) {
						dt.setTenantId(tenantId);
						dictionaryDao.insert(dt);
						for(Dictionary udt: list) {
							if(dt.getDictCode().equals(udt.getDictCode()) && 0!=udt.getUid()) {
								udt.setTenantId(tenantId);
								udt.setUid(dt.getId());
								dictionaryDao.insert(udt);
							}
						}
					}
				}
			}
		}
	}

	/**
     * @Description： 修改租户
     * @return
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void updateTenant(Tenant bean) {
		//添加修改人信息
		User user=UserThreadLocal.getUser();
		bean.setUpdateTime(new Date());
		bean.setUpdateUserId(user.getId());
		bean.setUpdateUserName(user.getUserName());
		//默认租户 0-否 1-是
		if( null!=bean.getSel() && bean.getSel().equals(Constants.ISSEL)) {
			//置空默认租户
			this.tenantDao.updateBySel();
		}
		//修改
		this.tenantDao.updateById(bean);
		//wso2租户启用、禁用判断
		if(null !=bean.getEnable()) {
			Wso2Tenant wt= new Wso2Tenant();
			Tenant tinfo=this.tenantDao.selectById(bean);
			wt.setTenantDomain(String.valueOf(new StringBuffer().append(tinfo.getTenantCode()).append(".com")));
	        if(Constants.ONE==bean.getEnable()) {
	        	wt.setActive(false);
	        }else {
	        	wt.setActive(true);
	        }
	        this.wso2TenantService.deactivateTenant(wt);
		}
	}

	/**
     * @Description： 删除租户
     * @return
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteTenant(TenantVO bean) {
		if( null!=bean.getId()) {
			//删除租户表
			this.tenantDao.deleteById(bean);
			//删除组织架构表
			this.tenantDao.deleteOrgByTenantId(bean);
			//删除用户组织架构表
			this.tenantDao.deleteUserOrgByTenantId(bean);
			//删除职务表
			this.tenantDao.deletePostByTenantId(bean);
			//删除用户职务表
			this.tenantDao.deleteUserPostByTenantId(bean);
			//删除角色表
			this.tenantDao.deleteRoleByTenantId(bean);
			//删除用户角色表
			this.tenantDao.deleteUserRoleByTenantId(bean);
			//删除角色权限表
			this.tenantDao.deleteRolePermissionByTenantId(bean);
			//删除用户表
			this.tenantDao.deleteUserByTenantId(bean);
			//删除权限表
			this.tenantDao.deletePermissionByTenantId(bean);
			//删除资源表
			this.tenantDao.deleteResourceByTenantId(bean);
			//删除用户操作表
			this.tenantDao.deleteUserActionByTenantId(bean);
			//删除数据字典
			this.tenantDao.deleteDictionaryByTenantId(bean);
		}else {
			String ids=StrUtils.regex(bean.getIds());
			//批量删除
			if(StringUtils.isNotBlank(ids)) {
				List<String> list=new ArrayList<>();
				for(String val:ids.split(Constants.CHAR)) {
					list.add(val);
				}
				//租户表
				this.tenantDao.deleteTenantByIds(list);
				//删除组织架构表
				this.tenantDao.deleteOrgByTenantIds(list);
				//删除用户组织架构表
				this.tenantDao.deleteUserOrgByTenantIds(list);
				//删除职务表
				this.tenantDao.deletePostByTenantIds(list);
				//删除用户职务表
				this.tenantDao.deleteUserPostByTenantIds(list);
				//删除角色表
				this.tenantDao.deleteRoleByTenantIds(list);
				//删除用户角色表
				this.tenantDao.deleteUserRoleByTenantIds(list);
				//删除角色权限表
				this.tenantDao.deleteRolePermissionByTenantIds(list);
				//删除用户表
				this.tenantDao.deleteUserByTenantIds(list);
				//删除权限表
				this.tenantDao.deletePermissionByTenantIds(list);
				//删除资源表
				this.tenantDao.deleteResourceByTenantIds(list);
				//删除用户操作表
				this.tenantDao.deleteUserActionByTenantIds(list);
				//删除数据字典
				this.tenantDao.deleteDictionaryByTenantIds(list);
			}
		}
	}
	
	/**
     * @Description：初始化租户信息
     * @return
     */
	@Override
	public List<Tenant> initTenantDatas() {
		QueryWrapper<Tenant> queryWrapper = new QueryWrapper<>();
		//降序
		queryWrapper.orderByDesc("update_time");
		 //0启用  1禁用
		queryWrapper.eq("enable", 0);
		return this.tenantDao.selectList(queryWrapper);
	}

	


}
