package com.smartwf.sm.modules.wso2.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.exception.CommonException;
import com.smartwf.common.pojo.User;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.common.utils.HttpClientUtil;
import com.smartwf.common.wso2.Wso2Config;
import com.smartwf.sm.modules.admin.dao.TenantDao;
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.vo.UserInfoVO;
import com.smartwf.sm.modules.wso2.service.Wso2UserService;

import cn.hutool.http.HttpConnection;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import lombok.extern.log4j.Log4j2;

/**
 * 功能说明：wso2用户管理服务层
 * @author WCH
 * 
 * */
@Service
@Log4j2
public class Wso2UserServiceImpl implements Wso2UserService {
	
	@Autowired
	private Wso2Config wso2Config;
	
	@Autowired
	private TenantDao tenantDao;

	/**
     * @Description：模拟wso2用户创建
     * @param userName,password
     * @return maps
     */
	@Override
	public Map<String,Object> addUser(UserInfoVO bean) {
		//通过租户ID查询租户信息
		Tenant tinfo=new Tenant();
		tinfo.setId(bean.getTenantId());
		Tenant resInfo=this.tenantDao.selectById(tinfo);
		if(null!=resInfo) {
			StringBuffer sb=new StringBuffer();
			//封装http请求头
			Map<String,String> headers=new HashMap<>(16);
			headers.put("content-type", "application/json");
			sb.append(resInfo.getTenantCode()).append("@").append(resInfo.getTenantDomain()).append(":").append(resInfo.getTenantPw());
			headers.put("Authorization","Basic " + Base64.encodeBase64String(sb.toString().getBytes()));
	        //封装数据
	        Map<String,String> data=new HashMap<>(16);
	        data.put("userName", bean.getLoginCode());
	        data.put("password", bean.getPwd());
	        //拼接uri
	        sb=new StringBuffer();
	        sb.append(wso2Config.userServerUri).append("/t/").append(resInfo.getTenantDomain()).append("/scim2/Users");
	        //发送请求
	        String str=HttpClientUtil.post(String.valueOf(sb), JSONUtil.toJsonStr(data),headers);
	        Map<String,Object> map=JSONUtil.parseObj(str);
	        //返回
			return map;
		}
		return null;
	}

	/**
     * @Description：模拟wso2删除用户
     *    wso2用户主键 对应 userCode
     * @author WCH
     * @param  userCode   
     * @return
	 * @throws IOException 
     */
	@Override
	public String deleteUserByUserCode(UserInfo bean)  {
		Tenant tinfo=new Tenant();
		tinfo.setId(bean.getTenantId());
		//通过用户关联租户
		Tenant resInfo=tenantDao.selectById(tinfo);
		if( null != resInfo) {
			StringBuffer sb=new StringBuffer();
			//删除租户下的用户
			Map<String,String> handlers=new HashMap<>(16);
			sb.append(resInfo.getTenantCode()).append("@").append(resInfo.getTenantDomain()).append(":").append(resInfo.getTenantPw());
			handlers.put("Authorization","Basic " + Base64.encodeBase64String(sb.toString().getBytes()));
			try {
				sb=new StringBuffer();
				String url=sb.append(this.wso2Config.userServerUri).append("/t/").append(resInfo.getTenantDomain()).append("/scim2/Users/").append(bean.getUserCode()).toString();
				return HttpClientUtil.delete(url,  handlers);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
     * @Description：模拟wso2用户修改
     * @param code,session_state和state
     * @return
     */
	@Override
	public Map<String, Object> updateByUserCode(UserInfoVO bean) {
		Tenant tinfo=this.tenantDao.selectById(bean.getTenantId());
		String authorization="Basic " + Base64.encodeBase64String(new StringBuffer().append(tinfo.getTenantCode()).append("@").append(tinfo.getTenantDomain()).append(":").append(tinfo.getTenantPw()).toString().getBytes());
		Map<String,Object> bodymap = new HashMap<>(16);
		bodymap.put("userName", bean.getLoginCode());
		String url=new StringBuffer().append(this.wso2Config.userServerUri).append("/t/").append(tinfo.getTenantDomain()).append("/scim2/Users/").append(bean.getUserCode()).toString();
		String res=HttpRequest.put(url).header("Authorization",  authorization).body(JSONUtil.toJsonStr(bodymap)).timeout(60000).execute().body();
		Map<String,Object> map=JSONUtil.parseObj(res);
		if(!map.containsKey(Constants.ID)) {
			throw new CommonException(Constants.INTERNAL_SERVER_ERROR, "修改wso2用户名称失败！");
		}
		return null;
	}

	
	
	/**
     * @Description：模拟wso2用户查询
     * @param userName,password
     * @return maps
     */
	@Override
	public Map<String,Object> selectUserById(UserInfoVO bean, Tenant resInfo){
		try {
			StringBuffer sb=new StringBuffer();
			//封装http请求头
			Map<String,String> headers=new HashMap<>(16);
			headers.put("content-type", "application/json");
			sb.append(resInfo.getTenantCode()).append("@").append(resInfo.getTenantDomain()).append(":").append(resInfo.getTenantPw());
			headers.put("Authorization","Basic " + Base64.encodeBase64String(sb.toString().getBytes()));
	        //拼接uri
	        sb=new StringBuffer();
	        sb.append(wso2Config.userServerUri).append("/t/").append(resInfo.getTenantDomain()).append("/scim2/Users/").append(bean.getUserCode());
	        //发送请求
	        String str=HttpClientUtil.get(String.valueOf(sb), headers);
	        Map<String,Object> map=JSONUtil.parseObj(str);
	        //返回
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
     * @Description：模拟wso2用户查询
     * @param userName,password
     * @return maps
     */
	@Override
	public Map<String,Object> selectUserByName(UserInfoVO bean, Tenant resInfo){
		try {
			StringBuffer sb=new StringBuffer();
			//封装http请求头
			Map<String,String> headers=new HashMap<>(16);
			headers.put("content-type", "application/json");
			sb.append(resInfo.getTenantCode()).append("@").append(resInfo.getTenantDomain()).append(":").append(resInfo.getTenantPw());
			headers.put("Authorization","Basic " + Base64.encodeBase64String(sb.toString().getBytes()));
	        //拼接uri
	        sb=new StringBuffer();
	        sb.append(wso2Config.userServerUri).append("/t/").append(resInfo.getTenantDomain()).append("/scim2/Users?userName=").append(bean.getLoginCode());
	        //发送请求
	        String str=HttpClientUtil.get(String.valueOf(sb), headers);
	        Map<String,Object> map=JSONUtil.parseObj(str);
	        //返回
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
