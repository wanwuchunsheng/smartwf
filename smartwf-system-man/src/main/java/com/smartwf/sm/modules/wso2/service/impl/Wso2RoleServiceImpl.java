package com.smartwf.sm.modules.wso2.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.utils.HttpClientUtil;
import com.smartwf.common.utils.JsonUtil;
import com.smartwf.common.wso2.Wso2Config;
import com.smartwf.sm.modules.admin.dao.TenantDao;
import com.smartwf.sm.modules.admin.pojo.Role;
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.wso2.service.Wso2RoleService;

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
	
	/**
	 * 说明：添加角色
	 * 
	 * */
	@Override
	public Map<String, Object> addRole(Role bean) {
		//通过租户ID查询租户信息
		Tenant tinfo=new Tenant();
		tinfo.setId(bean.getTenantId());
		Tenant resInfo=this.tenantDao.selectById(tinfo);
		if(null!=resInfo) {
			StringBuffer sb=new StringBuffer();
			//封装http请求头
			Map<String,String> headers=new HashMap<>();
			headers.put("content-type", "application/json");
			sb.append(resInfo.getTenantCode()).append("@").append(resInfo.getTenantCode()).append(".com:").append(Constants.WSO2_PASSWORD);
			headers.put("Authorization","Basic " + Base64.encodeBase64String(sb.toString().getBytes()));
	        //封装数据
	        Map<String,String> data=new HashMap<>();
	        data.put("displayName", bean.getEngName());
	        //拼接uri
	        sb=new StringBuffer();
	        sb.append(wso2Config.userServerUri).append("/t/").append(resInfo.getTenantCode()).append(".com").append("/scim2/Groups");
	        //发送请求
	        String str=HttpClientUtil.post(String.valueOf(sb), JsonUtil.objectToJson(data),headers);
	        Map<String,Object> map=JsonUtil.jsonToMap(str);
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
		Tenant tinfo=new Tenant();
		tinfo.setId(rl.getTenantId());
		Tenant resInfo=this.tenantDao.selectById(tinfo);
		if(null!=resInfo) {
			StringBuffer sb=new StringBuffer();
			//封装http请求头
			Map<String,String> headers=new HashMap<>();
			headers.put("content-type", "application/json");
			sb.append(resInfo.getTenantCode()).append("@").append(resInfo.getTenantCode()).append(".com:").append(Constants.WSO2_PASSWORD);
			headers.put("Authorization","Basic " + Base64.encodeBase64String(sb.toString().getBytes()));
			//拼接uri
	        sb=new StringBuffer();
	        sb.append(wso2Config.userServerUri).append("/t/").append(resInfo.getTenantCode()).append(".com").append("/scim2/Groups/").append(rl.getRoleCode());
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
		Tenant tinfo=new Tenant();
		tinfo.setId(bean.getTenantId());
		Tenant resInfo=this.tenantDao.selectById(tinfo);
		if(null!=resInfo) {
			StringBuffer sb=new StringBuffer();
			//封装http请求头
			Map<String,String> headers=new HashMap<>();
			headers.put("content-type", "application/json");
			sb.append(resInfo.getTenantCode()).append("@").append(resInfo.getTenantCode()).append(".com:").append(Constants.WSO2_PASSWORD);
			headers.put("Authorization","Basic " + Base64.encodeBase64String(sb.toString().getBytes()));
			//封装数据
	        Map<String,Object> data=new HashMap<>();
	        data.put("displayName", bean.getEngName());
			//拼接uri
	        sb=new StringBuffer();
	        sb.append(wso2Config.userServerUri).append("/t/").append(resInfo.getTenantCode()).append(".com").append("/scim2/Groups/").append(bean.getRoleCode());
	        //发送请求
			try {
				//1）查询
				String res=HttpClientUtil.get(String.valueOf(sb), headers);
				Map<String,Object> map=JsonUtil.jsonToMap(res);
				for( Entry<String, Object> m: map.entrySet()) {
					log.info(m.getKey()+"   "+m.getValue());
				}
				if(!map.isEmpty() && map.containsKey("members")) {
					data.put("members", map.get("members"));
					String str=HttpClientUtil.put(String.valueOf(sb), JsonUtil.objectToJson(data),headers);
					log.info("返回的数据："+str);
					return JsonUtil.jsonToMap(str);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
}
