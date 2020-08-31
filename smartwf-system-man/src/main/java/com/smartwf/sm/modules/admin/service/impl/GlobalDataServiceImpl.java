package com.smartwf.sm.modules.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.service.RedisService;
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
import com.smartwf.sm.modules.wso2.pojo.IdentityConfig;
import com.smartwf.sm.modules.wso2.service.IdentityConfigService;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONUtil;
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
	
	@Autowired
	private IdentityConfigService identityConfigService;

	 /**
     * @Description 租户列表
     * @return
     */
	@Override
	public Result<?> tenantAll() {
		List<Tenant> list=JSONUtil.toList(JSONUtil.parseArray(redisService.get("initTenant")), Tenant.class);//-----JsonUtil.jsonToList(redisService.get("initTenant"), Tenant.class);
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
			//获取数据字典全局数据
			Map<String, Object> map = JSONUtil.parseObj(redisService.get("initOrganization"));
			//判断是否为空
			if(map!=null && map.size()> 0 ) {
				//通过编码，获取对象集合
				List<OrganizationVO> orglist= JSONUtil.toList( JSONUtil.parseArray( map.get(Convert.toStr(bean.getTenantId()))), OrganizationVO.class) ;	
				//3）判断当前租户下是否有组织架构数据
        		if(orglist!=null && orglist.size()>0 ) {
        			//4）判断返回的数据类型
            		if(Constants.ONE == bean.getOrgType() ) {
            			//5）转换成树形
            			List<OrganizationVO> orglist2=buildByRecursive(orglist);
            			if(orglist2!=null && orglist2.size()>0 ) {
            				//6）返回树形数据
                    		return Result.data(orglist2);
            			}
            		}
        			//6）返回列表数据
            		return Result.data(orglist);
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
	public Result<?> postAll(Post bean) {
		//获取数据字典全局数据
		Map<String, Object> map = JSONUtil.parseObj(redisService.get("initPost"));
		//判断是否为空
		if(map!=null && map.size()> 0 ) {
			//通过编码，获取对象集合
			List<Post> orglist= JSONUtil.toList( JSONUtil.parseArray( map.get( Convert.toStr(bean.getTenantId()))), Post.class) ;
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
	public Result<?> roleAll(Role bean) {
		//获取数据字典全局数据
		Map<String, Object> map = JSONUtil.parseObj(redisService.get("initRole"));
		//判断是否为空
		if(map!=null && map.size()> 0 ) {
			//通过编码，获取对象集合
			List<Role> orglist= JSONUtil.toList( JSONUtil.parseArray( map.get( Convert.toStr(bean.getTenantId()))), Role.class) ;
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
		//获取数据字典全局数据
		Map<String, Object> map = JSONUtil.parseObj(redisService.get("initDictionary"));
		//判断是否为空
		if(map!=null && map.size()> 0 ) {
			//通过编码，获取对象集合
			List<Dictionary> orglist= JSONUtil.toList( JSONUtil.parseArray( map.get( Convert.toStr(bean.getTenantId()))), Dictionary.class) ;
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
     * @param flushType 0全部 1租户 2组织机构 3职务  4数据字典 5角色 6wso2配置
     * @return
     */
	@Override
	public void flushCache(GlobalData bean) {
		//租户
		List<Tenant> tenantList=this.tenantService.initTenantDatas();
    	if(StringUtils.isNotBlank(bean.getFlushType())) {
	    	String flushTypes=StrUtils.regex(bean.getFlushType());
	    	if(StringUtils.isNotBlank(flushTypes)) {
	    		for(String val:flushTypes.split(Constants.CHAR)) {
	    			switch (val) {
		    			case "0":
		    				//组织机构,职务,角色,数据字典
		    				this.redisService.set("initTenant", JSONUtil.toJsonStr(tenantList));
		                	this.redisService.set("initOrganization",JSONUtil.toJsonStr(this.organizationService.initOrganizationDatas(tenantList)));
		                	this.redisService.set("initPost", JSONUtil.toJsonStr(this.postService.initPostDatas(tenantList)));
		                	this.redisService.set("initRole", JSONUtil.toJsonStr(this.roleService.initRoleDatas(tenantList)));
		                	this.redisService.set("initDictionary", JSONUtil.toJsonStr(this.dictionaryService.initDictionaryDatas(tenantList)));
		                	log.info("租户数据{}",redisService.get("initTenant"));
		                	log.info("组织机构数据{}",redisService.get("initOrganization"));
		                	log.info("职务基础数据{}",redisService.get("initPost"));
		                	log.info("角色基础数据{}",redisService.get("initRole"));
		                	log.info("数据字典数据{}",redisService.get("initDictionary"));
		                	//wos2配置初始化数据
		            		List<IdentityConfig> idtconfig=this.identityConfigService.initIdentityConfig();
		            		if(idtconfig!=null && idtconfig.size()>0) {
		            			for(IdentityConfig idt: idtconfig) {
		                    		this.redisService.set(idt.getClientKey(), JSONUtil.toJsonStr(idt));
		                    		log.info("wso2配置信息{}",idt.getClientKey(), JSONUtil.toJsonStr(idt));
		            			}
		            		}
		                	break;
						case "1":
							//租户
							this.redisService.set("initTenant", JSONUtil.toJsonStr(tenantList));
							log.info("租户数据{}",redisService.get("initTenant"));
							break;
						case "2":
							//组织机构
							this.redisService.set("initOrganization",JSONUtil.toJsonStr(this.organizationService.initOrganizationDatas(tenantList)));
							log.info("组织机构数据{}",redisService.get("initOrganization"));
							break;	
						case "3":
							//职务
							this.redisService.set("initPost", JSONUtil.toJsonStr(this.postService.initPostDatas(tenantList)));
                            log.info("职务基础数据{}",redisService.get("initPost"));
							break;
						case "4":
							//数据字典
		                	this.redisService.set("initDictionary", JSONUtil.toJsonStr(this.dictionaryService.initDictionaryDatas(tenantList)));
                            log.info("角色基础数据{}",redisService.get("initRole"));
		                	break;
						case "5":
							//角色
							this.redisService.set("initRole", JSONUtil.toJsonStr(this.roleService.initRoleDatas(tenantList)));
							log.info("数据字典数据{}",redisService.get("initDictionary"));
							break;
						case "6":
							//wos2配置初始化数据
							List<IdentityConfig> idtconfig2=this.identityConfigService.initIdentityConfig();
							if(idtconfig2!=null && idtconfig2.size()>0) {
								for(IdentityConfig idt: idtconfig2) {
					        		this.redisService.set(idt.getClientKey(), JSONUtil.toJsonStr(idt));
					        		log.info("wso2配置信息{}",idt.getClientKey(), JSONUtil.toJsonStr(idt));
								}
							}
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
