package com.smartwf.sm.modules.wso2.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.exception.CommonException;
import com.smartwf.common.pojo.Wso2Group;
import com.smartwf.common.pojo.Wso2User;
import com.smartwf.common.utils.HttpClientUtil;
import com.smartwf.common.wso2.Wso2Config;
import com.smartwf.sm.modules.admin.dao.TenantDao;
import com.smartwf.sm.modules.admin.pojo.Role;
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.vo.UserInfoVO;
import com.smartwf.sm.modules.wso2.service.Wso2RoleService;
import com.smartwf.sm.modules.wso2.service.Wso2UserService;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import lombok.extern.log4j.Log4j2;

/**
 * 功能说明：wso2角色管理服务层
 * @author WCH
 * 
 * */
@Service
@Log4j2
public class Wso2RoleServiceImpl implements Wso2RoleService {
	
	@Autowired
	private Wso2Config wso2Config;
	
	@Autowired
	private TenantDao tenantDao;
	
	@Autowired
	private Wso2UserService wso2UserService;
	
	/**
	 * 说明：添加角色
	 * 
	 * */
	@Override
	public Map<String, Object> addRole(Role bean) {
		//通过租户ID查询租户信息
		Tenant resInfo=this.tenantDao.selectById(bean.getTenantId());
		if(null!=resInfo) {
			StringBuffer sb=new StringBuffer();
			//封装http请求头
			Map<String,String> headers=new HashMap<>(16);
			headers.put("content-type", "application/json");
			sb.append(resInfo.getTenantCode()).append("@").append(resInfo.getTenantDomain()).append(":").append(resInfo.getTenantPw());
			headers.put("Authorization","Basic " + Base64.encodeBase64String(sb.toString().getBytes()));
	        //封装数据
	        Map<String,String> data=new HashMap<>(16);
	        data.put("displayName", bean.getEngName());
	        //拼接uri
	        sb=new StringBuffer();
	        sb.append(wso2Config.userServerUri).append("/t/").append(resInfo.getTenantDomain()).append("/scim2/Groups");
	        //发送请求
	        String str=HttpClientUtil.post(String.valueOf(sb), JSONUtil.toJsonStr(data),headers);
	        Map<String,Object> map=JSONUtil.parseObj(str);
	        //返回
			return map;
		}
		return null;
	}

	/**
	 * 说明：删除角色
	 * 
	 * */
	@Override
	public void deleteRole(Role rl) {
		//通过租户ID查询租户信息
		Tenant resInfo=this.tenantDao.selectById(rl.getTenantId());
		if(null!=resInfo) {
			StringBuffer sb=new StringBuffer();
			//封装http请求头
			Map<String,String> headers=new HashMap<>(16);
			headers.put("content-type", "application/json");
			sb.append(resInfo.getTenantCode()).append("@").append(resInfo.getTenantDomain()).append(":").append(resInfo.getTenantPw());
			headers.put("Authorization","Basic " + Base64.encodeBase64String(sb.toString().getBytes()));
			//拼接uri
	        sb=new StringBuffer();
	        sb.append(wso2Config.userServerUri).append("/t/").append(resInfo.getTenantDomain()).append("/scim2/Groups/").append(rl.getRoleCode());
	        //发送请求
			try {
				HttpClientUtil.delete(String.valueOf(sb), headers);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 说明：修改角色
	 *  1） 修改角色前，先查询角色绑定的用户
	 *  2）将绑定的用户当参数进行修改，避免wso2之前绑定的用户会丢失
	 * @author WCH
	 * @datetime 2020年6月18日17:45:34
	 * 
	 * */
	@Override
	public Map<String, Object> updateRole(Role bean) {
		//通过租户ID查询租户信息
		Tenant resInfo=this.tenantDao.selectById(bean.getTenantId());
		if(null!=resInfo) {
			StringBuffer sb=new StringBuffer();
			//封装http请求头
			Map<String,String> headers=new HashMap<>(16);
			headers.put("content-type", "application/json");
			sb.append(resInfo.getTenantCode()).append("@").append(resInfo.getTenantDomain()).append(":").append(resInfo.getTenantPw());
			headers.put("Authorization","Basic " + Base64.encodeBase64String(sb.toString().getBytes()));
			//封装数据
	        Map<String,Object> data=new HashMap<>(16);
	        data.put("displayName", bean.getEngName());
			//拼接uri
	        sb=new StringBuffer();
	        sb.append(wso2Config.userServerUri).append("/t/").append(resInfo.getTenantDomain()).append("/scim2/Groups/").append(bean.getRoleCode());
	        //发送请求
			try {
				//查询角色已绑定的用户
				String res=HttpClientUtil.get(String.valueOf(sb), headers);
				Map<String,Object> map=JSONUtil.parseObj(res);
				for( Entry<String, Object> m: map.entrySet()) {
					log.info(m.getKey()+"   "+m.getValue());
				}
				if(null!=map && map.containsKey(Constants.MEMBERS)) {
					data.put("members", map.get("members"));
				}
				String str=HttpClientUtil.put(String.valueOf(sb), JSONUtil.toJsonStr(data),headers);
				log.info("返回的数据："+str);
				return JSONUtil.parseObj(str);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
     * @Description：模拟wso2用户角色绑定
     * 1）Wso2给不同的角色绑定新用户
     * a.当前角色已绑定用户，查询出来后，追加新用户保存
     * b.当前角色没有绑定角色，直接角色和用户绑定保存
     * @author WCH
     * @return
     */
	@Override
	public Map<String, Object> addRoleOrUser(Role ur, UserInfoVO bean) {
		//1）通过租户ID查询租户信息
		Tenant resInfo=this.tenantDao.selectById(bean.getTenantId());
		if(null!=resInfo) {
			//2）角色查询带出已绑定用户
			StringBuffer sb=new StringBuffer();
			//封装http请求头
			//Map<String,String> headers=new HashMap<>(16);
			//headers.put("content-type", "application/json");
			String authorization="Basic " + Base64.encodeBase64String(sb.append(resInfo.getTenantCode()).append("@").append(resInfo.getTenantDomain()).append(":").append(resInfo.getTenantPw()).toString().getBytes());
			//封装数据
	        Wso2Group wg=new Wso2Group();
	        wg.setDisplayName(ur.getEngName());
			//拼接url
	        sb=new StringBuffer();
	        String url=sb.append(wso2Config.userServerUri).append("/t/").append(resInfo.getTenantDomain()).append("/scim2/Groups/").append(ur.getRoleCode()).toString();
			
			//查询角色已绑定的用户
			String res=HttpRequest.get(url).header("Authorization",authorization).timeout(60000).execute().body();
			Map<String, Object> map=JSONUtil.parseObj(res);
			List<Map<String,Object>> listmap=null;
			Map<String,Object> lmap=null;
			//3）判断当前角色是否已绑定用户
			if(null!=map && map.containsKey(Constants.MEMBERS)) {
				//已绑定
				Wso2Group wgf=JSONUtil.toBean(res, Wso2Group.class);
				//获得已绑定所有用户
				listmap=wgf.getMembers();
				//封装新用户，在已绑定用户集合追加新用户
				lmap=new HashMap<>(16);
				lmap.put("display", bean.getLoginCode());
				lmap.put("value", bean.getUserCode());
				listmap.add(lmap);
				wg.setMembers(listmap);
			}else {
				//未绑定
				listmap=new ArrayList<>();
				lmap=new HashMap<>(16);
				lmap.put("display", bean.getLoginCode());
				lmap.put("value", bean.getUserCode());
				listmap.add(lmap);
				wg.setMembers(listmap);
			}
			//4）角色用户绑定保存
			String str=HttpRequest.put(url).header("Authorization",authorization).body(JSONUtil.toJsonStr(wg)).timeout(60000).execute().body();
			log.info("返回的数据："+str);
			return JSONUtil.parseObj(str);
			
		}
		return null;
	}

	/**
     * @Description：模拟wso2用户角色解绑
     *  1）解绑之前用户角色绑定
     *    {只解绑角色下当前用户，其他用户保留}
     *  2）添加新的用户角色绑定
     * @param 
     * @return
     */
	@Override
	public Map<String, Object> updateRoleOrUser(List<Role> listRole, UserInfoVO bean) {
		Tenant resInfo=this.tenantDao.selectById(bean.getTenantId());
		if(null!=resInfo) {
			//1）通过当前用户，查询已绑定的角色
			Map<String,Object> roleOrUser=this.wso2UserService.selectUserById(bean,resInfo);
			log.info(roleOrUser.containsKey("groups"));
			if(null!=roleOrUser && roleOrUser.containsKey(Constants.GROUPS)) {
				Wso2User wg=JSONUtil.toBean( JSONUtil.toJsonStr(roleOrUser), Wso2User.class);
				List<Map<String,Object>> list=wg.getGroups();
				if( null !=list && list.size()>0 ) {
					//2）判断已绑定角色是否存在
					for(Map<String,Object> m:list) {
						if(m.containsKey("display")) {
							//3)查询角色下所有用户，过滤当前用户
							StringBuffer sb=new StringBuffer();
							//4）封装http请求头
							Map<String,String> headers=new HashMap<>(16);
							headers.put("content-type", "application/json");
							sb.append(resInfo.getTenantCode()).append("@").append(resInfo.getTenantDomain()).append(":").append(resInfo.getTenantPw());
							headers.put("Authorization","Basic " + Base64.encodeBase64String(sb.toString().getBytes()));
							//5）拼接uri
					        sb=new StringBuffer();
					        sb.append(wso2Config.userServerUri).append("/t/").append(resInfo.getTenantDomain()).append("/scim2/Groups/").append(String.valueOf(m.get("value")));
							//6）查询角色已绑定的用户，过滤当前用户
							String res=HttpClientUtil.get(String.valueOf(sb), headers);
							Wso2Group wgs=JSONUtil.toBean(res, Wso2Group.class);
							List<Map<String,Object>> umap=wgs.getMembers();
							List<Map<String,Object>> nmap=new ArrayList<>();
							if(null !=wgs && null!=umap && umap.size()>0) {
								for(Map<String,Object> u:umap) {
									if(!bean.getUserCode().equals(u.get("value"))) {
										nmap.add(u);
									}
								}
							}
							//7）封装参数
							String dt=null;
							if(!nmap.isEmpty()) {
						        Wso2Group wgdata=new Wso2Group();
						        wgdata.setDisplayName(String.valueOf(m.get("display")));
								wgdata.setMembers(nmap);
								dt=JSONUtil.toJsonStr(wgdata);
							}else {
						        Map<String,Object> data=new HashMap<>(16);
						        data.put("displayName", String.valueOf(m.get("display")));
						        dt=JSONUtil.toJsonStr(data);
							}
							//8）发送请求
							try {
								String str = HttpClientUtil.put(String.valueOf(sb),dt,headers);
								log.info("返回的数据："+str);
							} catch (IOException e) {
								e.printStackTrace();
							}
					 }
				  }
			    }
			}
			//9）添加角色用户绑定
			for(Role r:listRole) {
				Map<String,Object> resmap=this.addRoleOrUser(r,bean);
				if(null==resmap || !resmap.containsKey(Constants.ID)) {
	        		throw new CommonException(Constants.INTERNAL_SERVER_ERROR, "Wso2用户角色绑定异常！");
				}
			}
		}
		return null;
	}
	
	
	
}
