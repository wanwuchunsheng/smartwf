package com.smartwf.sm.modules.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.common.utils.StrUtils;
import com.smartwf.sm.modules.admin.dao.RoleDao;
import com.smartwf.sm.modules.admin.pojo.Role;
import com.smartwf.sm.modules.admin.service.RoleService;
import com.smartwf.sm.modules.admin.vo.RoleVO;

import lombok.extern.log4j.Log4j;
import tk.mybatis.mapper.entity.Example;
/**
 * @Description: 角色业务层接口实现
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
@Log4j
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleDao roleDao;

	/**
	 * @Description:查询角色分页
	 * @result:
	 */
	@Override
	public Result<?> selectRoleByPage(Page<Object> page, RoleVO bean) {
		Page<Object> objectPage = PageHelper.startPage(page.getPageNum(), page.getPageSize());
		Example example = new Example(Role.class);
        example.setOrderByClause("create_time desc");
        Example.Criteria criteria = example.createCriteria();
        //过滤租户（登录人为超级管理员，无需过滤，查询所有租户）
  		if (null!=bean.getTenantId() && Constants.ADMIN!=bean.getMgrType()) { 
  			criteria.andEqualTo("tenantId", bean.getTenantId()); 
  		} 
        //角色编码
        if (!StringUtils.isEmpty(bean.getRoleCode())) {
            criteria.andLike("roleCode", Constants.PER_CENT + bean.getRoleCode() + Constants.PER_CENT);
        }
        //角色名称
        if (!StringUtils.isEmpty(bean.getRoleName())) {
            criteria.andLike("RoleName", Constants.PER_CENT + bean.getRoleName() + Constants.PER_CENT);
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
		List<Role> list=this.roleDao.selectByExample(example);
		return Result.data(objectPage.getTotal(), list);
	}

	/**
     * @Description: 主键查询角色
     * @return
     */
	@Override
	public Result<?> selectRoleById(Role bean) {
		Role Role= this.roleDao.selectByPrimaryKey(bean);
		return Result.data(Role);
	}
	
	/**
     * @Description: 添加角色
     * @return
     */
	@Override
	public void saveRole(Role bean) {
		//添加创建人基本信息
		User user=UserThreadLocal.getUser();
		bean.setCreateTime(new Date());
		bean.setCreateUserId(user.getId());
		bean.setCreateUserName(user.getUserName());
		bean.setUpdateTime(bean.getCreateTime());
		bean.setUpdateUserId(bean.getCreateUserId());
		bean.setUpdateUserName(bean.getCreateUserName());
		//保存
		this.roleDao.insertSelective(bean);
	}

	/**
     * @Description： 修改角色
     * @return
     */
	@Override
	public void updateRole(Role bean) {
		//添加修改人信息
		User user=UserThreadLocal.getUser();
		bean.setUpdateTime(new Date());
		bean.setUpdateUserId(user.getId());
		bean.setUpdateUserName(user.getUserName());
		//修改
		this.roleDao.updateByPrimaryKeySelective(bean);
	}

	/**
     * @Description： 删除角色
     * @return
     */
	@Transactional
	@Override
	public void deleteRole(RoleVO bean) {
		if( null!=bean.getId()) {
			//删除角色
			this.roleDao.deleteByPrimaryKey(bean);
			//删除用户角色
			this.roleDao.deleteUserRoleById(bean);
			//删除角色权限
			this.roleDao.deleteRolePermissionById(bean);
		}else {
			String ids=StrUtils.regex(bean.getIds());
			if(StringUtils.isNotBlank(ids)) {
				List<String> list=new ArrayList<>();
				for(String val:ids.split(",")) {
					list.add(val);
					bean=new RoleVO();
					bean.setId(Integer.valueOf(val));
					//删除用户角色
					this.roleDao.deleteUserRoleById(bean);
					//删除角色权限
					this.roleDao.deleteRolePermissionById(bean);
				}
				//批量删除角色
				this.roleDao.deleteRoleByIds(list);
			}
		}
	}
	

	/**
     * @Description： 初始化角色
     * @return
     */
	@Override
	public List<Role> queryRoleAll() {
		return this.roleDao.queryRoleAll();
	}


}
