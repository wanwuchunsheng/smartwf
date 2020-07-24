package com.smartwf.sm.modules.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.sm.modules.admin.dao.PermissionDao;
import com.smartwf.sm.modules.admin.dao.ResourceDao;
import com.smartwf.sm.modules.admin.dao.UserActionDao;
import com.smartwf.sm.modules.admin.pojo.Permission;
import com.smartwf.sm.modules.admin.pojo.Resource;
import com.smartwf.sm.modules.admin.pojo.UserAction;
import com.smartwf.sm.modules.admin.service.PermissionService;
import com.smartwf.sm.modules.admin.vo.PermissionVO;
import com.smartwf.sm.modules.admin.vo.ResourceVO;
import com.smartwf.sm.modules.admin.vo.UserActionVO;

import cn.hutool.json.JSONUtil;
import lombok.extern.log4j.Log4j;
/**
 * @Description: 权限业务层接口实现
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
@Log4j
public class PermissionServiceImpl implements PermissionService{
	
	@Autowired
	private PermissionDao permissionDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private UserActionDao userActionDao;
	
	/**
	 * @Description: 查询资源子系统
	 * @return
	 */
	@Override
	public Result<?> selectResourceByPid(ResourceVO bean) {
		List<Resource> list=this.resourceDao.selectResourceByPid(bean);
		return Result.data(list);
	}

	/**
	 * @Description:查询权限分页
	 * @result:
	 */
	@Override
	public Result<?> selectPermissionByPage( PermissionVO bean) {
		//全部资源
		List<ResourceVO> resList=this.permissionDao.selectResourceByAll(bean);
		//返回集合
		List<ResourceVO> list=buildByRecursive(resList);
		return Result.data(list);
	}
	
	 /**
     * 使用递归方法建树
     * @param treeNodes
     * @return
     */
    public static List<ResourceVO> buildByRecursive(List<ResourceVO> treeNodes) {
        List<ResourceVO> trees = new ArrayList<ResourceVO>();
        for (ResourceVO treeNode : treeNodes) {
            if (treeNode.getUid()==0) {
                trees.add(findChildren(treeNode,treeNodes));
            }
        }
        return trees;
    }
 
    /**
     * 递归查找子节点
     * @param treeNodes
     * @return
     */
    public static ResourceVO findChildren(ResourceVO treeNode,List<ResourceVO> treeNodes) {
        for (ResourceVO it : treeNodes) {
            if(treeNode.getId().equals(it.getUid())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<ResourceVO>());
                }
                treeNode.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return treeNode;
    }

	
	/**
     * @Description: 添加权限
     * @return
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void savePermission(PermissionVO bean) {
		//添加创建人基本信息
		User user=UserThreadLocal.getUser();
		bean.setCreateTime(new Date());
		bean.setCreateUserId(user.getId());
		bean.setCreateUserName(user.getUserName());
		bean.setUpdateTime(bean.getCreateTime());
		bean.setUpdateUserId(bean.getCreateUserId());
		bean.setUpdateUserName(bean.getCreateUserName());
		//将json字符串装换成实体对象
		List<Permission> list=JSONUtil.toList(JSONUtil.parseArray(bean.getIds()), Permission.class); //------- JsonUtil.jsonToList(bean.getIds(),Permission.class);
		if(list!=null && list.size()>0) {
			for(Permission per:list) {
				//批量插入
				per.setTenantId(bean.getTenantId());
				per.setCreateTime(bean.getCreateTime());
				per.setCreateUserId(bean.getId());
				per.setCreateUserName(bean.getCreateUserName());
				per.setUpdateTime(bean.getCreateTime());
				per.setUpdateUserId(bean.getCreateUserId());
				per.setUpdateUserName(bean.getCreateUserName());
				//保存
				this.permissionDao.insert(per);
			}
		}
	}

	/**
     * @Description： 删除权限
     * @return
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deletePermission(Permission bean) {
		this.permissionDao.deleteById(bean);
	}

	/**
	 * @Description: 资源与用户操作查询
	 * @DateTime 2019-12-13 11:00:43
	 * @return
	 */
	@Override
	public Result<?> selectResourceUserActByPage(PermissionVO bean) {
		List<ResourceVO> list=this.resourceDao.selectResourceUserActByPage(bean);
		return Result.data(list);
	}

	/**
	 * @Description: 用户操作
	 * @DateTime 2019-12-13 11:00:43
	 * @return
	 */
	@Override
	public Result<?> selectUserActAll(UserActionVO bean) {
		QueryWrapper<UserAction> queryWrapper=new QueryWrapper<>();
		//状态0-启用  1禁用
		queryWrapper.eq("enable", 0);
		//租户,不为超级管理员，过滤租户
  		if (null != bean.getTenantId()) {
  			queryWrapper.eq("tenant_id", bean.getTenantId());
  		}
		List<UserAction> list = this.userActionDao.selectList(queryWrapper);
		return Result.data(list);
	}
	

}
