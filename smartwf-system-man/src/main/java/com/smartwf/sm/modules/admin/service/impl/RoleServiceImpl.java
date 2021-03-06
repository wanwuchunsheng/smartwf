package com.smartwf.sm.modules.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.smartwf.common.utils.CkUtils;
import com.smartwf.sm.modules.admin.dao.RoleDao;
import com.smartwf.sm.modules.admin.pojo.GlobalData;
import com.smartwf.sm.modules.admin.pojo.Role;
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.service.GlobalDataService;
import com.smartwf.sm.modules.admin.service.RoleService;
import com.smartwf.sm.modules.admin.vo.RoleVO;
import com.smartwf.sm.modules.wso2.service.Wso2RoleService;

import cn.hutool.core.convert.Convert;
import lombok.extern.log4j.Log4j;
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
	
	@Autowired
	private Wso2RoleService wso2RoleService;
	
	@Autowired
    private GlobalDataService globalDataService;

	/**
	 * @Description:查询角色分页
	 * @result:
	 */
	@Override
	public Result<?> selectRoleByPage(Page<Role> page, RoleVO bean) {
		QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
		//降序
		queryWrapper.orderByDesc("update_time"); 
        //过滤租户（登录人为超级管理员，无需过滤，查询所有租户）
  		if (null!=bean.getTenantId()) { 
  			queryWrapper.eq("tenant_id", bean.getTenantId()); 
  		} 
        //角色编码
        if (!StringUtils.isEmpty(bean.getRoleCode())) {
        	queryWrapper.like("role_code", Constants.PER_CENT + bean.getRoleCode() + Constants.PER_CENT);
        }
        //角色名称
        if (!StringUtils.isEmpty(bean.getRoleName())) {
        	queryWrapper.like("role_name", Constants.PER_CENT + bean.getRoleName() + Constants.PER_CENT);
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
		IPage<Role> list=this.roleDao.selectPage(page, queryWrapper);
		return Result.data(list.getTotal(), list.getRecords());
	}

	/**
     * @Description: 主键查询角色
     * @return
     */
	@Override
	public Result<?> selectRoleById(Role bean) {
		Role role= this.roleDao.selectById(bean);
		return Result.data(role);
	}
	
	/**
     * @Description: 添加角色
     * @return
     */
	@Override
	public Result<?> saveRole(Role bean) {
		/**添加wso2角色*/
		Map<String,Object> map=this.wso2RoleService.addRole(bean);
		if(null!=map && map.size()>0 && map.containsKey(Constants.ID)) {
			bean.setRoleCode(String.valueOf(map.get("id")));
			//添加创建人基本信息
			User user=UserThreadLocal.getUser();
			bean.setCreateTime(new Date());
			bean.setCreateUserId(user.getId());
			bean.setCreateUserName(user.getUserName());
			bean.setUpdateTime(bean.getCreateTime());
			bean.setUpdateUserId(bean.getCreateUserId());
			bean.setUpdateUserName(bean.getCreateUserName());
			//保存
			this.roleDao.insert(bean);
			
			/**刷新缓存 */
			//flushType 0全部 1租户 2组织机构 3职务  4数据字典 5角色 6wso2配置
			GlobalData gd=new GlobalData();
			gd.setFlushType(Convert.toStr(Constants.ZERO));
			this.globalDataService.flushCache(gd);
			
			return Result.data(Constants.EQU_SUCCESS,"添加成功！");
		}
		return Result.data(Constants.BAD_REQUEST,"失败，wso2角色添加失败！");
	}

	/**
     * @Description： 修改角色
     * @return
     */
	@Override
	public Result<?> updateRole(Role bean) {
		//判断是否为空
		if(StringUtils.isNotBlank(bean.getEngName())) {
			Role rl=this.roleDao.selectById(bean);
			//验证是否修改RoleCode,平台、租户、风场角色RoleCode不能修改，只能修改名称
			if(!bean.getEngName().equals(rl.getEngName())) {
				//平台管理员角色不能修改，不能删除
				if(Constants.SUPER_ADMIN.equalsIgnoreCase(rl.getEngName())) {
					return Result.msg(Constants.BAD_REQUEST, "平台管理员角色不能修改！");
				}
				if(Constants.SUPER_TENANT.equalsIgnoreCase(rl.getEngName())) {
					return Result.msg(Constants.BAD_REQUEST, "租户管理员角色不能修改！");
				}
				if(Constants.SUPER_WINDFARM.equalsIgnoreCase(rl.getEngName())) {
					return Result.msg(Constants.BAD_REQUEST, "风场管理员角色不能修改！");
				}
			}
			//非平台管理员可修改
			rl.setEngName(bean.getEngName());
			Map<String ,Object> map=this.wso2RoleService.updateRole(rl);
			if(null!=map) {
				//添加修改人信息
				User user=UserThreadLocal.getUser();
				bean.setUpdateTime(new Date());
				bean.setUpdateUserId(user.getId());
				bean.setUpdateUserName(user.getUserName());
				//修改
				this.roleDao.updateById(bean);
				/**刷新缓存 */
				//flushType 0全部 1租户 2组织机构 3职务  4数据字典 5角色 6wso2配置
				GlobalData gd=new GlobalData();
				gd.setFlushType(Convert.toStr(Constants.ZERO));
				this.globalDataService.flushCache(gd);
				return Result.data(Constants.EQU_SUCCESS,"修改成功！");
			}
		}
		return Result.data(Constants.BAD_REQUEST,"失败，wso2角色修改失败！");
	}

	/**
     * @Description： 删除角色
     * @return
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public Result<?> deleteRole(RoleVO bean) {
		Role rl=this.roleDao.selectById(bean);
		if(Constants.SUPER_ADMIN.equalsIgnoreCase(rl.getEngName())) {
			return Result.msg(Constants.BAD_REQUEST, "平台管理员角色不能删除！");
		}
		if(Constants.SUPER_TENANT.equalsIgnoreCase(rl.getEngName())) {
			return Result.msg(Constants.BAD_REQUEST, "租户管理员角色不能删除！");
		}
		if(Constants.SUPER_WINDFARM.equalsIgnoreCase(rl.getEngName())) {
			return Result.msg(Constants.BAD_REQUEST, "风场管理员角色不能删除！");
		}
		// 删除wso2角色
		this.wso2RoleService.deleteRole(rl);
		//删除角色
		this.roleDao.deleteById(bean);
		//删除用户角色
		this.roleDao.deleteUserRoleById(bean);
		//删除角色权限
		this.roleDao.deleteRolePermissionById(bean);
		/**刷新缓存 */
		//flushType 0全部 1租户 2组织机构 3职务  4数据字典 5角色 6wso2配置
		GlobalData gd=new GlobalData();
		gd.setFlushType(Convert.toStr(Constants.ZERO));
		this.globalDataService.flushCache(gd);
		return Result.msg(Constants.EQU_SUCCESS, "成功");
	}

	/**
     * @Description：初始化角色信息
     * @return
     */
	@Override
	public Map<Integer,List<Role>> initRoleDatas(List<Tenant> list) {
		Map<Integer,List<Role>> map =new HashMap<>(16);
		QueryWrapper<Role> queryWrapper =null;
		for(Tenant t:list) {
			queryWrapper = new QueryWrapper<>();
			//降序
			queryWrapper.orderByDesc("update_time"); 
			//0启用  1禁用
			queryWrapper.eq("enable", 0); 
			//租户
			queryWrapper.eq("tenant_id", t.getId());
			map.put(t.getId(), this.roleDao.selectList(queryWrapper));
		}
		return map;
	}

	/**
	 * @Description: 查询角色列表
	 *   根据当前用户的角色，过滤列表
	 *   平台管理员显示全部角色
	 *   租户管理员不能显示平台管理员角色
	 *   风场管理员不能显示平台管理员、租户管理员角色
	 * @return
	 */
	@Override
	public Result<?> selectRoleByUserId(Role bean) {
		User user=UserThreadLocal.getUser();
		QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
		//平台管理员角色，查询所有
		if(!CkUtils.verifyAdminUser(user)) {
			//queryWrapper.notIn("eng_name", Constants.SUPER_ADMIN,Constants.SUPER_TENANT,Constants.SUPER_ADMIN);
			//是否风场管理员角色
			//if(CkUtils.verifyWindFarmUser(user)) {
				//queryWrapper.notIn("eng_name", Constants.SUPER_ADMIN,Constants.SUPER_TENANT);
			//}
			//租户管理员角色,过滤平台管理员
			if(CkUtils.verifyTenantUser(user)) {
				queryWrapper.notIn("eng_name", Constants.SUPER_ADMIN);
			}else {
				queryWrapper.notIn("eng_name", Constants.SUPER_ADMIN,Constants.SUPER_TENANT);
			}
		}
		//降序
		queryWrapper.orderByDesc("update_time"); 
        //租户
  		if (null!=bean.getTenantId()) { 
  			queryWrapper.eq("tenant_id", bean.getTenantId()); 
  		} 
  		List<Role> list=this.roleDao.selectList(queryWrapper);
		return Result.data(list.size(), list);
	}
	


}
