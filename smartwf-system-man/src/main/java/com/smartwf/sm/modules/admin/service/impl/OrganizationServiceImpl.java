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
import com.smartwf.common.utils.StrUtils;
import com.smartwf.sm.modules.admin.dao.OrganizationDao;
import com.smartwf.sm.modules.admin.pojo.Organization;
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.pojo.UserPost;
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
  		//租户
  		if (null!=bean.getTenantId() ) { 
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
	 * @Description: 查询所有组织架构（树形结构）
	 * @return
	 */
	@Override
	public Result<?> selectOrganizationByAll(OrganizationVO bean) {
		List<OrganizationVO> list=buildByRecursive(this.organizationDao.selectOrganizationByAll(bean));
		return Result.data(list);
	}
	 /**
     * 使用递归方法建树
	* @param treeNodes
	* @return
	*/
	public static List<OrganizationVO> buildByRecursive(List<OrganizationVO> treeNodes) {
		List<OrganizationVO> trees = new ArrayList<OrganizationVO>();
		for (OrganizationVO treeNode : treeNodes) {
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
	public static OrganizationVO findChildren(OrganizationVO treeNode,List<OrganizationVO> treeNodes) {
		for (OrganizationVO it : treeNodes) {
			 if(treeNode.getId().equals(it.getUid())) {
			     if (treeNode.getChildren() == null) {
			         treeNode.setChildren(new ArrayList<OrganizationVO>());
			     }
			     treeNode.getChildren().add(findChildren(it,treeNodes));
			 }
		}
		return treeNode;
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
		//删除组织机构
		this.organizationDao.deleteById(bean.getId());
		//删除用户组织结构
		this.organizationDao.deleteUserOrgById(bean);
		//1 通过组织架构id查询用户职务数据
		List<UserPost> uplist = this.organizationDao.queryUserPostByOrgId(bean);
		//2 批量删除用户职务
		if(uplist!=null && uplist.size()>0 ) {
			List<Integer> list=new ArrayList<>();
			for(UserPost up : uplist) {
				list.add( up.getId() );
			}
			this.organizationDao.deleteUserPostByOrgIds(list);
		}
		//3 删除职务
		this.organizationDao.deletePostByOrgId(bean);
	}
	
	/**
	 * @Description: 初始化组织机构
	 * @return
	 */
	@Override
	public Map<Integer,List<OrganizationVO>> initOrganizationDatas(List<Tenant> list) {
		Map<Integer,List<OrganizationVO>> map =new HashMap<>();
		OrganizationVO bean=null;
		for(Tenant t:list) {
			bean = new OrganizationVO();
			bean.setTenantId(t.getId());//租户
			bean.setEnable(0);//0启用  1禁用
			map.put(t.getId(), this.organizationDao.selectOrganizationByAll(bean));
		}
		return map;
	}
	


	


}
