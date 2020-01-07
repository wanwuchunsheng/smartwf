package com.smartwf.sm.modules.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.service.RedisService;
import com.smartwf.common.utils.JsonUtil;
import com.smartwf.common.utils.StrUtils;
import com.smartwf.sm.modules.admin.pojo.Dictionary;
import com.smartwf.sm.modules.admin.pojo.GlobalData;
import com.smartwf.sm.modules.admin.pojo.Post;
import com.smartwf.sm.modules.admin.pojo.Role;
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.service.DictionaryService;
import com.smartwf.sm.modules.admin.service.GlobalDataService;
import com.smartwf.sm.modules.admin.service.OrganizationService;
import com.smartwf.sm.modules.admin.service.PostService;
import com.smartwf.sm.modules.admin.service.RoleService;
import com.smartwf.sm.modules.admin.service.TenantService;
import com.smartwf.sm.modules.admin.vo.OrganizationVO;

import lombok.extern.log4j.Log4j2;
/**
 * @Description: 全局数据业务层接口实现
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
@Log4j2
public class GlobalDataServiceImpl implements GlobalDataService{
	
	@Autowired
    private RedisService redisService;
	
	@Autowired
	private TenantService tenantService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
    private PostService postService;
	
	@Autowired
    private RoleService roleService;
	
	@Autowired
    private DictionaryService dictionaryService;

	 /**
     * @Description 租户列表
     * @return
     */
	@Override
	public Result<?> tenantAll() {
		List<Tenant> list=JsonUtil.jsonToList(redisService.get("initTenant"), Tenant.class);
		if(list!=null && list.size()>0) {
			return Result.data(list);
		}
		return null;
	}

	/**
     * @Description 组织架构列表
     * @param tenantId
     * @return
     */
	@Override
	public Result<?> organizationAll(OrganizationVO bean) {
    	//1）判断数据类型是否为空
    	if(null !=bean.getOrgType()) {
    		//2）获取所有租户下的组织架构数据
			Map<Integer, List<OrganizationVO>> map = (Map<Integer, List<OrganizationVO>>) JSONObject.parseObject(redisService.get("initOrganization"), new TypeReference<Map<Integer, List<OrganizationVO>>>(){} );
			if(map!=null && map.size()>0) {
				List<OrganizationVO> orglist=map.get(bean.getTenantId());
        		//3）判断当前租户下是否有组织架构数据
        		if(orglist!=null && orglist.size()>0 ) {
        			//4）判断返回的数据类型
            		if(Constants.ONE==bean.getOrgType()) {
            			//5）转换成树形
            			List<OrganizationVO> orglist2=buildByRecursive(orglist);
            			if(orglist2!=null && orglist2.size()>0 ) {
            				//6）返回
                    		return Result.data(orglist2);
            			}
            		}
        		}
			}
    	}
    	return null;
	}
	
	/**
     * @Description 职务列表
     * @param tenantId
     * @param organizationId
     * @return
     */
	@Override
	public Result<?> PostAll(Post bean) {
		Map<Integer, List<Post>> map = (Map<Integer, List<Post>>) JSONObject.parseObject(redisService.get("initPost"), new TypeReference<Map<Integer, List<Post>>>() {} );
		if(map!=null && map.size()> 0 ) {
			List<Post> orglist=map.get(bean.getTenantId());
    		if(orglist!=null && orglist.size()>0 ) {
                Integer tenantId=bean.getTenantId();
                Integer orgId=bean.getOrganizationId();
                List<Post> postlist=new ArrayList<>();
                if(orgId!=null) {
                	//过滤租户&&组织机构
                	for(Post p : orglist) {
	    				if(tenantId.equals(p.getTenantId()) && orgId.equals(p.getOrganizationId())) {
	    					postlist.add(p);
	    				}
	    			}
                }else {
                	//过滤租户
                	for(Post p : orglist) {
	    				if(tenantId==p.getTenantId()) {
	    					postlist.add(p);
	    				}
	    			}
                }
                if(postlist!=null && postlist.size()>0 ) {
                	return Result.data(postlist);
                }
    		}
		}
		return null;
	}
	
	/**
     * @Description 角色列表
     * @param 
     * @return
     */
	@Override
	public Result<?> RoleAll(Role bean) {
    	Map<Integer, List<Role>> map = (Map<Integer, List<Role>>) JSONObject.parseObject(redisService.get("initRole"), new TypeReference<Map<Integer, List<Role>>>() {} );
		if(map!=null && map.size()> 0 ) {
			List<Role> orglist=map.get(bean.getTenantId());
    		if(orglist!=null && orglist.size()>0 ) {
                Integer tenantId=bean.getTenantId();
                List<Role> postlist=new ArrayList<>();
            	//过滤租户
            	for(Role p : orglist) {
    				if(tenantId==p.getTenantId()) {
    					postlist.add(p);
    				}
    			}
            	if( postlist!=null && postlist.size()>0 ) {
            		return Result.data(postlist);
            	}
    		}
		}
		return null;
	}
	
	 /**
     * @Description 获取数据字典基础数据列表
     * @param 
     * @return
     */
	@Override
	public Result<?> dictAll(Dictionary bean) {
		Map<Integer, List<Dictionary>> map = (Map<Integer, List<Dictionary>>) JSONObject.parseObject(redisService.get("initDictionary"), new TypeReference<Map<Integer, List<Dictionary>>>() {} );
		if(map!=null && map.size()> 0 ) {
			List<Dictionary> orglist=map.get(bean.getTenantId());
    		if(orglist!=null && orglist.size()>0 ) {
                Integer tenantId=bean.getTenantId();
                String dictCode=bean.getDictCode();
                List<Dictionary> postlist=new ArrayList<>();
            	//过滤租户
            	for(Dictionary p : orglist) {
    				if(tenantId==p.getTenantId() && dictCode.equals(p.getDictCode())) {
    					postlist.add(p);
    				}
    			}
            	if(postlist!=null && postlist.size()>0) {
            		return Result.data(postlist);
            	}
    		}
		}
		return null;
	}
	
	/**
     * @Description 刷新缓存
     * @param flushType 0全部 1租户 2组织机构 3职务  4数据字典 5角色
     * @return
     */
	@Override
	public void flushCache(GlobalData bean) {
		//租户
		List<Tenant> tenantList=this.tenantService.InitTenantDatas();
    	if(StringUtils.isNotBlank(bean.getFlushType())) {
	    	String flushTypes=StrUtils.regex(bean.getFlushType());
	    	if(StringUtils.isNotBlank(flushTypes)) {
	    		for(String val:flushTypes.split(",")) {
	    			switch (val) {
		    			case "0":
		    				//组织机构,职务,角色,数据字典
		    				this.redisService.set("initTenant", JSON.toJSONString(tenantList));
		                	this.redisService.set("initOrganization",JSON.toJSONString(this.organizationService.initOrganizationDatas(tenantList),SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty));
		                	this.redisService.set("initPost", JSON.toJSONString(this.postService.initPostDatas(tenantList),SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty));
		                	this.redisService.set("initRole", JSON.toJSONString(this.roleService.initRoleDatas(tenantList),SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty));
		                	this.redisService.set("initDictionary", JSON.toJSONString(this.dictionaryService.InitDictionaryDatas(tenantList),SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty));
		                	log.info("租户数据{}",redisService.get("initTenant"));
		                	log.info("组织机构数据{}",redisService.get("initOrganization"));
		                	log.info("职务基础数据{}",redisService.get("initPost"));
		                	log.info("角色基础数据{}",redisService.get("initRole"));
		                	log.info("数据字典数据{}",redisService.get("initDictionary"));
		                	break;
						case "1":
							//租户
							this.redisService.set("initTenant", JSON.toJSONString(tenantList));
							log.info("租户数据{}",redisService.get("initTenant"));
							break;
						case "2":
							//组织机构
							this.redisService.set("initOrganization",JSON.toJSONString(this.organizationService.initOrganizationDatas(tenantList),SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty));
							log.info("组织机构数据{}",redisService.get("initOrganization"));
							break;	
						case "3":
							//职务
							this.redisService.set("initPost", JSON.toJSONString(this.postService.initPostDatas(tenantList),SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty));
                            log.info("职务基础数据{}",redisService.get("initPost"));
							break;
						case "4":
							//数据字典
		                	this.redisService.set("initDictionary", JSON.toJSONString(this.dictionaryService.InitDictionaryDatas(tenantList),SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty));
                            log.info("角色基础数据{}",redisService.get("initRole"));
		                	break;
						case "5":
							//角色
							this.redisService.set("initRole", JSON.toJSONString(this.roleService.initRoleDatas(tenantList),SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty));
							log.info("数据字典数据{}",redisService.get("initDictionary"));
							break;
						default:
							break;
					}
	    		}
	    	}
	    }
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


}
