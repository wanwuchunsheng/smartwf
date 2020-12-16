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
import com.smartwf.sm.config.redis.StreamProducer;
import com.smartwf.sm.modules.admin.dao.OrganizationDao;
import com.smartwf.sm.modules.admin.pojo.GlobalData;
import com.smartwf.sm.modules.admin.pojo.Organization;
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.pojo.UserOrganization;
import com.smartwf.sm.modules.admin.service.GlobalDataService;
import com.smartwf.sm.modules.admin.service.OrganizationService;
import com.smartwf.sm.modules.admin.vo.OrganizationVO;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONUtil;
import lombok.extern.log4j.Log4j2;
/**
 * @Description: 组织架构业务层接口实现
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
@Log4j2
public class OrganizationServiceImpl implements OrganizationService{
	
	@Autowired
	private OrganizationDao organizationDao;
	
	@Autowired
    private GlobalDataService globalDataService;
	
	@Autowired
    private StreamProducer streamProducer;

	/**
	 * @Description:查询组织架构分页
	 * @result:
	 */
	@Override
	public Result<?> selectOrganizationByPage(Page<Organization> page, OrganizationVO bean) {
		QueryWrapper<Organization> queryWrapper = new QueryWrapper<>();
		//降序
		queryWrapper.orderByDesc("update_time"); 
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
		Organization organization= this.organizationDao.selectById(bean.getId());
		return Result.data(organization);
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
		try {
			/**刷新缓存 */
			//flushType 0全部 1租户 2组织机构 3职务  4数据字典 5角色 6wso2配置
			GlobalData gd=new GlobalData();
			gd.setFlushType(Convert.toStr(Constants.ZERO));
			this.globalDataService.flushCache(gd);
			/** 向知识中心推送新增 */
			Map<String,String> map=new HashMap<>();
			bean.setRemark("add");
			map.put("org", JSONUtil.toJsonStr(bean));
			this.streamProducer.sendMsg("topic:smartwf_wiki", map);
			log.info("向消息中心推送-组织机构新增{}",JSONUtil.toJsonStr(map));
		} catch (Exception e) {
			log.error("组织机构添加异常！",e,e.getMessage());
		}
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
		try {
			/**刷新缓存 */
			//flushType 0全部 1租户 2组织机构 3职务  4数据字典 5角色 6wso2配置
			GlobalData gd=new GlobalData();
			gd.setFlushType(Convert.toStr(Constants.ZERO));
			this.globalDataService.flushCache(gd);
			/** 向知识中心推送新增 */
			Map<String,String> map=new HashMap<>();
			bean.setRemark("update");
			map.put("org", JSONUtil.toJsonStr(bean));
			this.streamProducer.sendMsg("topic:smartwf_wiki", map);
			log.info("向消息中心推送-组织机构修改{}",JSONUtil.toJsonStr(map));
		} catch (Exception e) {
			log.error("组织机构添加异常！",e,e.getMessage());
		}
	}

	/**
     * @Description： 删除组织架构
     * @return
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteOrganization(OrganizationVO bean) {
		//删除组织机构
		this.organizationDao.deleteById(bean.getId());
		//删除用户组织结构
		this.organizationDao.deleteUserOrgById(bean);
		/**
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
		*/
		/**刷新缓存 */
		//flushType 0全部 1租户 2组织机构 3职务  4数据字典 5角色 6wso2配置
		GlobalData gd=new GlobalData();
		gd.setFlushType(Convert.toStr(Constants.ZERO));
		this.globalDataService.flushCache(gd);
	}
	
	/**
	 * @Description: 初始化组织机构
	 * @return
	 */
	@Override
	public Map<Integer,List<OrganizationVO>> initOrganizationDatas(List<Tenant> list) {
		Map<Integer,List<OrganizationVO>> map =new HashMap<>(16);
		OrganizationVO bean=null;
		for(Tenant t:list) {
			bean = new OrganizationVO();
			//租户
			bean.setTenantId(t.getId());
			//0启用  1禁用
			bean.setEnable(0);
			map.put(t.getId(), this.organizationDao.selectOrganizationByAll(bean));
		}
		return map;
	}

	/**
     * @Description 通过租户、用户查询   - 所有风场
     * @return
     */
	@Override
	public List<OrganizationVO> selectOrganizationByUserId(UserOrganization uobean) {
		return this.organizationDao.selectOrganizationByOrgUserId(uobean);
	}

	/**
     * @Description 通过租户查询   - 所有风场
     * @return
     */
	@Override
	public List<OrganizationVO> selectOrganizationByTenantId(UserOrganization uobean) {
		return this.organizationDao.selectOrganizationByTenantId(uobean);
	}

	/**
	 * @Description: 查询组织机构人员信息（知识中心提供）
	 * @return
	 */
	@Override
	public Result<?> selectUserOrganizationByParam(OrganizationVO bean) {
		try {
			//是否返回人员信息 0-否  1-是
			if(bean.getOrgType()==0) {
				List<Map<String,Object>> orgMaps=this.organizationDao.selectUserOrganizationByUid(bean);
				return Result.data(Constants.EQU_SUCCESS, orgMaps);
			}
            if(bean.getOrgType()==1){
            	Map<String,Object> map=new HashMap<>();
				//查询人员信息
            	List<Map<String,Object>> orgMaps=this.organizationDao.selectUserOrganizationByUid(bean);
				List<Map<String,Object>> orgUserMaps=this.organizationDao.selectUserOrganizationByOrgId(bean);
				map.put("organization", orgMaps);
				map.put("users", orgUserMaps);
				return Result.data(Constants.EQU_SUCCESS, map);
			}
		} catch (Exception e) {
			log.error("查询异常{}",e);
		}
		return Result.data(Constants.INTERNAL_SERVER_ERROR, "查询异常！");
	}

	/**
	 * @Description: 查询组织机构人员信息-全局返回（知识中心提供）
	 *   提供：1）租户下组织机构人员信息 2）树形结构返回
	 * @return
	 */
	@Override
	public Result<?> selectUserOrganizationByAll(OrganizationVO bean) {
		//查询组织机构和用户
		List<OrganizationVO> userOrg=this.organizationDao.selectUserOrganizationByAll(bean);
		//查询组织机构
		List<OrganizationVO> org=this.organizationDao.selectOrganizationByAll(bean);
		List<Map<String,Object>> listmap=null;
		Map<String,Object> umap=null;
		for(OrganizationVO ov : org) {
			listmap=new ArrayList<>();
			for(OrganizationVO uov: userOrg) {
				if(ov.getId()==uov.getId()) {
					umap=new HashMap<>();
					umap.put("id", uov.getUserId());
					umap.put("orgName", uov.getOrgName());
					listmap.add(umap);
				}
			}
			ov.setLmap(listmap);
		}
		List<OrganizationVO> list=buildByRecursive(org);
		return Result.data(list);
	}
	

}
