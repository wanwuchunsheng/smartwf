package com.smartwf.sm.modules.admin.service.impl;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.exception.CommonException;
import com.smartwf.common.handler.UserProfile;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.common.service.RedisService;
import com.smartwf.common.utils.Md5Utils;
import com.smartwf.common.utils.Wso2ClientUtils;
import com.smartwf.common.wso2.Wso2Config;
import com.smartwf.sm.modules.admin.dao.TenantDao;
import com.smartwf.sm.modules.admin.dao.UserInfoDao;
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.service.PersonalCenterService;
import com.smartwf.sm.modules.admin.vo.UserInfoVO;

import cn.hutool.json.JSONUtil;


/**
 * @Date: 2019-11-27 11:25:24
 * @Description: 个人中心业务层实现
 * @author WCH
 */
@Service
public class PersonalCenterServiceImpl implements PersonalCenterService {
	
	@Autowired
	private UserInfoDao userInfoDao;
	
	@Autowired
	private TenantDao tenantDao;
	
	@Autowired
	private Wso2Config wso2Config;
	
	@Autowired
	private RedisService redisService;
	

	/**
     * @Description： 修改用户密码
     *   1）验证旧密码正确性
     *   2）通过修改本地密码，wso2密码
     *   3）修改本地密码，是否指向
     * @return
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public ResponseEntity<Result<?>> updateUserPwd(HttpServletRequest request,Integer id, String oldPwd, String newPwd) {
		User user=UserProfile.getUser(request);
		UserInfo userInfo=userInfoDao.selectById(id);
  		if(userInfo!=null) {
			if(oldPwd.equals(userInfo.getPwd())) {
				//修改wso2密码
				//补全用户租户信息
				Tenant tenant=tenantDao.selectById(user.getTenantId());
				user.setTenantPw(tenant.getTenantPw());
				String res=Wso2ClientUtils.updateUserPwd(wso2Config, user, newPwd, oldPwd);
				//res为空，修改成功，反正返回失败提醒
				if(StringUtils.isEmpty(res)) {
					UserInfo uinfo=new UserInfo();
					uinfo.setId(user.getId());
					uinfo.setPwd(newPwd);
					//修改本地密码
					this.userInfoDao.updateById(uinfo);
					//判断当前用户是否为租户管理员，通过租户ID，租户登录名修改租户表密码
					QueryWrapper<Tenant> qw=new QueryWrapper<>();
					qw.eq("id", user.getTenantId());
					qw.eq("tenant_code", user.getLoginCode());
					Tenant tbean=this.tenantDao.selectOne(qw);
					if(null!=tbean) {
						Tenant tt=new Tenant();
						tt.setId(user.getTenantId());
						tt.setTenantPw(newPwd);
						this.tenantDao.updateById(tt);
						//刷新redis存储数据
						String ruser=this.redisService.get(user.getSessionId());
				        if (StringUtils.isNotBlank(ruser)) {
					        User userObj= JSONUtil.toBean(ruser, User.class);
					        userObj.setTenantPw(newPwd);
					        this.redisService.set(user.getSessionId(),JSONUtil.toJsonStr(userObj) ,wso2Config.tokenRefreshTime);
				        }
					}
	        	}else {
	        		return ResponseEntity.ok(Result.msg(Constants.INTERNAL_SERVER_ERROR,"wso2修改异常"));
	        	}
			}else {
				return ResponseEntity.ok(Result.msg(Constants.INTERNAL_SERVER_ERROR,"旧密码不正确！"));
			}
		}else {
			return ResponseEntity.ok(Result.msg(Constants.INTERNAL_SERVER_ERROR,"改用户不存在，请确定UID是否正确！"));
		}
		return ResponseEntity.ok(Result.msg(Constants.EQU_SUCCESS,"密码修改成功！"));
	}

	/**
     * @Description： 修改用户资料
     * @return
     */
	@Override
	public void updateUserInfo(UserInfoVO bean) {
		this.userInfoDao.updateById(bean);
	}
	/**
     * @Description: 主键查询用户资料
     * @return
     */
	@Override
	public Result<?> selectUserInfoById(UserInfo bean) {
		UserInfo userInfo= this.userInfoDao.selectUserInfoById(bean);
		//屏蔽关键信息
		userInfo.setPwd(null);
		return Result.data(userInfo);
	}
   
}
