package com.smartwf.sm.modules.admin.service.impl;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.handler.UserProfile;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.common.utils.Md5Utils;
import com.smartwf.common.utils.Wso2ClientUtils;
import com.smartwf.common.wso2.Wso2Config;
import com.smartwf.sm.modules.admin.dao.TenantDao;
import com.smartwf.sm.modules.admin.dao.UserInfoDao;
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.service.PersonalCenterService;
import com.smartwf.sm.modules.admin.vo.UserInfoVO;


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
	

	/**
     * @Description： 修改用户密码
     *   1）验证旧密码正确性
     *   2）通过修改本地密码，wso2密码
     * @return
     */
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
	        	}else {
	        		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.BAD_REQUEST,"wso2修改异常"+res));
	        	}
			}else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.BAD_REQUEST,"旧密码不正确！"));
			}
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.msg(Constants.BAD_REQUEST,"改用户不存在，请确定UID是否正确！"));
		}
		return ResponseEntity.status(HttpStatus.OK).body(Result.msg(Constants.EQU_SUCCESS,"密码修改成功！"));
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
