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
import com.smartwf.sm.modules.admin.dao.PermissionDao;
import com.smartwf.sm.modules.admin.dao.ResouceDao;
import com.smartwf.sm.modules.admin.pojo.Resouce;
import com.smartwf.sm.modules.admin.service.ResouceService;
import com.smartwf.sm.modules.admin.vo.ResouceVO;

import lombok.extern.log4j.Log4j;
/**
 * @Description: 资源业务层接口实现
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
@Log4j
public class ResouceServiceImpl implements ResouceService{
	
	@Autowired
	private ResouceDao resouceDao;
	
	@Autowired
	private PermissionDao permissionDao;

	/**
	 * @Description:查询资源分页
	 * @result:
	 */
	@Override
	public Result<?> selectResouceByPage(Page<Resouce> page, ResouceVO bean) {
		QueryWrapper<Resouce> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByDesc("update_time"); //降序
		//子系统
  		if (null!=bean.getId()) { 
  			queryWrapper.eq("id", bean.getId()).or().eq("pid", bean.getId()); 
  		}
        //租户
  		if (null!=bean.getTenantId()) { 
  			queryWrapper.eq("tenant_id", bean.getTenantId()); 
  		}
        //资源编码
        if (!StringUtils.isEmpty(bean.getResCode())) {
        	queryWrapper.like("res_code", Constants.PER_CENT + bean.getResCode() + Constants.PER_CENT);
        }
        //资源名称
        if (!StringUtils.isEmpty(bean.getResName())) {
        	queryWrapper.like("res_name", Constants.PER_CENT + bean.getResName() + Constants.PER_CENT);
        }
        //状态
		if (null!=bean.getEnable()) { 
			queryWrapper.eq("enable", bean.getEnable()); 
		}
        //备注
        if (!StringUtils.isEmpty(bean.getRemark())) {
        	queryWrapper.like("remark", Constants.PER_CENT + bean.getRemark() + Constants.PER_CENT);
        }
		IPage<Resouce> list=this.resouceDao.selectPage(page, queryWrapper);
		return Result.data(list.getTotal(), list.getRecords());
	}
	
	/**
	 * @Description: 查询所有资源，树形结构
	 * @return
	 */
	@Override
	public Result<?> selectResouceByAll(ResouceVO bean) {
		List<ResouceVO> list=this.resouceDao.selectResouceByAll(bean);
		return Result.data( buildByRecursive(list));
	}
	
	/**
     * 使用递归方法建树
	* @param treeNodes
	* @return
	*/
	public static List<ResouceVO> buildByRecursive(List<ResouceVO> treeNodes) {
		List<ResouceVO> trees = new ArrayList<ResouceVO>();
		for (ResouceVO treeNode : treeNodes) {
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
	public static ResouceVO findChildren(ResouceVO treeNode,List<ResouceVO> treeNodes) {
		for (ResouceVO it : treeNodes) {
			 if(treeNode.getId().equals(it.getUid())) {
			     if (treeNode.getChildren() == null) {
			         treeNode.setChildren(new ArrayList<ResouceVO>());
			     }
			     treeNode.getChildren().add(findChildren(it,treeNodes));
			 }
		}
		return treeNode;
	}

	/**
     * @Description: 主键查询资源
     * @return
     */
	@Override
	public Result<?> selectResouceById(Resouce bean) {
		ResouceVO resouce= this.resouceDao.selecResoucetById(bean);
		return Result.data(resouce);
	}
	
	/**
     * @Description: 添加资源
     * @return
     */
	@Override
	public void saveResouce(Resouce bean) {
		//添加创建人基本信息
		User user=UserThreadLocal.getUser();
		bean.setCreateTime(new Date());
		bean.setCreateUserId(user.getId());
		bean.setCreateUserName(user.getUserName());
		bean.setUpdateTime(bean.getCreateTime());
		bean.setUpdateUserId(bean.getCreateUserId());
		bean.setUpdateUserName(bean.getCreateUserName());
		//保存
		this.resouceDao.insert(bean);
	}

	/**
     * @Description： 修改资源
     * @return
     */
	@Override
	public void updateResouce(Resouce bean) {
		//添加修改人信息
		User user=UserThreadLocal.getUser();
		bean.setUpdateTime(new Date());
		bean.setUpdateUserId(user.getId());
		bean.setUpdateUserName(user.getUserName());
		//修改
		this.resouceDao.updateById(bean);
	}

	/**
     * @Description： 删除资源
     * @return
     */
	@Transactional
	@Override
	public void deleteResouce(ResouceVO bean) {
		//id判断正负值(正：删除资源 负：删除权限表)
		if(bean.getId()>0){
			//删除资源
			this.resouceDao.deleteById(bean);
		}else {
			//删除权限
			this.permissionDao.deleteById(bean);
		}
	}

	/**
     * @Description： 初始化资源
     * @return
     */
	@Override
	public List<Resouce> queryResouceAll() {
		return this.resouceDao.queryResouceAll();
	}
	
	/**
	 * @Description: 查询资源子系统
	 * @return
	 */
	@Override
	public Result<?> selectResouceByPid(ResouceVO bean) {
		List<Resouce> list=this.resouceDao.selectResouceByPid(bean);
		return Result.data(list);
	}
	


}
