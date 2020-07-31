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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.exception.CommonException;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.TreeResource;
import com.smartwf.common.pojo.User;
import com.smartwf.common.service.RedisService;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.common.utils.Md5Utils;
import com.smartwf.common.utils.StrUtils;
import com.smartwf.sm.modules.admin.dao.OrganizationDao;
import com.smartwf.sm.modules.admin.dao.PostDao;
import com.smartwf.sm.modules.admin.dao.ResourceDao;
import com.smartwf.sm.modules.admin.dao.RoleDao;
import com.smartwf.sm.modules.admin.dao.TenantDao;
import com.smartwf.sm.modules.admin.dao.UserInfoDao;
import com.smartwf.sm.modules.admin.dao.UserOrganizationDao;
import com.smartwf.sm.modules.admin.dao.UserPostDao;
import com.smartwf.sm.modules.admin.dao.UserRoleDao;
import com.smartwf.sm.modules.admin.pojo.Role;
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.pojo.UserOrganization;
import com.smartwf.sm.modules.admin.pojo.UserPost;
import com.smartwf.sm.modules.admin.pojo.UserRole;
import com.smartwf.sm.modules.admin.service.UserInfoService;
import com.smartwf.sm.modules.admin.vo.UserInfoVO;
import com.smartwf.sm.modules.wso2.service.Wso2RoleService;
import com.smartwf.sm.modules.wso2.service.Wso2UserService;

import cn.hutool.core.convert.Convert;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.json.JSONUtil;
import lombok.extern.log4j.Log4j;
/**
 * @Description: 用户资料业务层接口实现
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
@Log4j
public class UserInfoServiceImpl implements UserInfoService{
	
	@Autowired
	private UserInfoDao userInfoDao;
	
	@Autowired
	private UserOrganizationDao userOrganizationDao;
	
	@Autowired
	private UserPostDao userPostDao;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	@Autowired
	private OrganizationDao organizationDao;
	
	@Autowired
	private PostDao postDao;
	
	@Autowired
	private RoleDao roleDao;

	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private Wso2UserService wso2UserService;

	@Autowired
	private Wso2RoleService wso2RoleService;
	
	@Autowired
	private RedisService redisService;
	
	/**
	 * @Description:查询用户资料分页
	 * @ MgrType：2超级管理员  1管理员  0普通
	 * 
	 *   1）a.普通用户只能看到所有普通用户信息  b.过滤租户
	 *   2）a.管理员用户可以看到所有管理员用户及所有普通用户信息  b.过滤租户
	 *   3）a.超级管理员可以看到所有超级用户信息，所有管理员信息，所有普通用户信息 b.非过滤租户
	 * @result:
	 */
	@Override
	public Result<?> selectUserInfoByPage(Page<UserInfo> page, UserInfo bean) {
		//查询
		List<UserInfo> userInfoList = this.userInfoDao.selectUserInfoByPage(bean,page);
		return Result.data(page.getTotal(), userInfoList);
	}

	/**
     * @Description: 主键查询用户资料
     * @return
     */
	@Override
	public Result<?> selectUserInfoById(UserInfo bean) {
		UserInfo userInfo= this.userInfoDao.selectUserInfoById(bean);
		return Result.data(userInfo);
	}
	
	/**
     * @Description: 添加用户资料
     * @author WCH
     *  步骤：
     *   1.向wso2添加用户
     *   2.成功后，返回wso2唯一编码
     *   3.保存系统中心用户，将唯一编码和用户信息绑定
     *   4.绑定租户，组织机构，职务，角色信息
     * @return
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public Result<?> saveUserInfo(UserInfoVO bean) {
		//添加创建人基本信息
		User user=UserThreadLocal.getUser();
		bean.setCreateTime(new Date());
		bean.setCreateUserId(user.getId());
		bean.setCreateUserName(user.getUserName());
		bean.setUpdateTime(bean.getCreateTime());
		bean.setUpdateUserId(bean.getCreateUserId());
		bean.setUpdateUserName(bean.getCreateUserName());
		//保存wso2用户
		Map<String,Object> map=this.wso2UserService.addUser(bean);
		//验证wso2插入是否成功，成功返回ID
		if(map==null || !map.containsKey(Constants.ID)) {
			return Result.data(Constants.BAD_REQUEST,"失败，wso2用户保存失败！"+JSONUtil.toJsonStr(map));
		}
		bean.setUserCode(String.valueOf(map.get("id")) );
		//md5加密
		if(StringUtils.isNotBlank(bean.getPwd())) {
			bean.setPwd(Md5Utils.convertMd5(bean.getPwd()));
		}
		//保存用户资料
		this.userInfoDao.insert(bean);
		//批量添加与用户相关联表
		//添加用户组织机构
		if(StringUtils.isNotBlank(bean.getOrganizationIds())) {
			String orgIds=StrUtils.regex(bean.getOrganizationIds());
			if(StringUtils.isNotBlank(orgIds)) {
				UserOrganization uo=null;
				for(String id:orgIds.split(Constants.CHAR)) {
					uo=new UserOrganization();
					uo.setUserId(bean.getId());
					uo.setTenantId(bean.getTenantId());
					uo.setOrganizationId(Integer.valueOf(id));
					this.userOrganizationDao.insert(uo);
				}
			}
		}
		
		//添加用户职务
		if(StringUtils.isNotBlank(bean.getPostIds())) {
			String postIds=StrUtils.regex(bean.getPostIds());
			if(StringUtils.isNotBlank(postIds)) {
				UserPost up = null;
				for(String id:postIds.split(Constants.CHAR)) {
					up=new UserPost();
					up.setUserId(bean.getId());
					up.setTenantId(bean.getTenantId());
					up.setPostId(Integer.valueOf(id));
					this.userPostDao.insert(up);
				}
			}
		}
		//添加用户角色
		if(StringUtils.isNotBlank(bean.getRoleIds())) {
			String roleIds=StrUtils.regex(bean.getRoleIds());
			if(StringUtils.isNotBlank(roleIds)) {
				UserRole ur = null;
				for(String id:roleIds.split(Constants.CHAR)) {
					ur=new UserRole();
					ur.setUserId(bean.getId());
					ur.setTenantId(bean.getTenantId());
					ur.setRoleId(Integer.valueOf(id));
					this.userRoleDao.insert(ur);
					//wso2角色和用户绑定
					Map<String,Object> resmap=this.wso2RoleService.addRoleOrUser(this.roleDao.selectById(id),bean);
					if(null==resmap) {
		        		throw new CommonException(Constants.UNAUTHORIZED, "Wso2用户角色绑定异常！");
					}
				}
			}
		}
		return Result.data(Constants.EQU_SUCCESS,"成功!");
	}

	/**
     * @Description： 修改用户资料
     *    1.修改Wso2用户资料，
     *    2.修改系统中心用户资料
     *    3.删除已绑定的组织结构
     *    4.生成修改提交时的组织结构
     * @return
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void updateUserInfo(UserInfoVO bean) {
		//添加修改人信息
		User user=UserThreadLocal.getUser();
		bean.setUpdateTime(new Date());
		bean.setUpdateUserId(user.getId());
		bean.setUpdateUserName(user.getUserName());
		//系统管理中心用户ID主键查询loginCode
		UserInfo uf=this.userInfoDao.selectById(bean);
		bean.setLoginCode(uf.getLoginCode());
		bean.setUserName(uf.getUserName());
		bean.setUserCode(uf.getUserCode());
		bean.setTenantId(uf.getTenantId());
		
		//md5加密
		if(StringUtils.isNotBlank(bean.getPwd())) {
			bean.setPwd(Md5Utils.convertMd5(bean.getPwd()));
		}
		//1)修改用户资料
		this.userInfoDao.updateById(bean);
		//2）删除原始用户对应的关系表（清空）
		//删除用户组织架构表
		this.userInfoDao.deleteUserOrgById(bean);
		//删除用户职务表
		this.userInfoDao.deleteUserPostById(bean);
		//删除用户角色表
		this.userInfoDao.deleteUserRoleById(bean);
		//3）重新添加新的关联关系
		//添加用户组织机构
		if(StringUtils.isNotBlank(bean.getOrganizationIds())){
			String orgIds=StrUtils.regex(bean.getOrganizationIds());
			if(StringUtils.isNotBlank(orgIds)) {
				UserOrganization uo=null;
				for(String id:orgIds.split(Constants.CHAR)) {
					uo=new UserOrganization();
					uo.setUserId(bean.getId());
					uo.setTenantId(bean.getTenantId());
					uo.setOrganizationId(Integer.valueOf(id));
					this.userOrganizationDao.insert(uo);
				}
			}
		}
		//添加用户职务
		if(StringUtils.isNotBlank(bean.getPostIds())){
			String postIds=StrUtils.regex(bean.getPostIds());
			if(StringUtils.isNotBlank(postIds)) {
				UserPost up = null;
				for(String id:postIds.split(Constants.CHAR)) {
					up=new UserPost();
					up.setUserId(bean.getId());
					up.setTenantId(bean.getTenantId());
					up.setPostId(Integer.valueOf(id));
					this.userPostDao.insert(up);
				}
			}
		}
		
		//添加用户角色
		if(StringUtils.isNotBlank(bean.getRoleIds())){
			String roleIds=StrUtils.regex(bean.getRoleIds());
			if(StringUtils.isNotBlank(roleIds)) {
				List<String> list=new ArrayList<>();
				UserRole ur = null;
				for(String id:roleIds.split(Constants.CHAR)) {
					list.add(id);
					ur=new UserRole();
					ur.setUserId(bean.getId());
					ur.setTenantId(bean.getTenantId());
					ur.setRoleId(Integer.valueOf(id));
					this.userRoleDao.insert(ur);
				}
				//Wso2实时更新
				if(!list.isEmpty()) {
					//查询所有角色集合对象
					List<Role> listRole=this.roleDao.selectRoleByIds(list);
					//wso2角色和用户绑定
					Map<String,Object> resmap=this.wso2RoleService.updateRoleOrUser(listRole,bean);
					if(null==resmap) {
		        		throw new CommonException(Constants.UNAUTHORIZED, "Wso2用户角色绑定异常！");
					}
				}
			}
		}
	}

	/**
     * @Description： 删除用户资料
     * @return
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public Result<?> deleteUserInfo(UserInfoVO bean) {
		if( null!=bean.getId()) {
			//接入wso2,删除系统中心用户前，先删除wso2
			UserInfo uben= this.userInfoDao.selectById(bean);
			String res=this.wso2UserService.deleteUserByUserCode(uben);
			log.info("wso2用户删除成功，返回信息内容{}"+res);
			//单个对象删除
			this.userInfoDao.deleteUserInfoById(bean);
			//删除用户组织架构表
			this.userInfoDao.deleteUserOrgById(bean);
			//删除用户职务表
			this.userInfoDao.deleteUserPostById(bean);
			//删除用户角色表
			this.userInfoDao.deleteUserRoleById(bean);
		}else {
			//批量删除
			String ids=StrUtils.regex(bean.getIds());
			if(StringUtils.isNotBlank(ids)) {
				List<String> list=new ArrayList<>();
				UserInfo userinfo=null;
				for(String val:ids.split(Constants.CHAR)) {
					//批量删除wso2用户
					userinfo=new UserInfo();
					userinfo.setId(Integer.valueOf(val));
					UserInfo uben= this.userInfoDao.selectById(userinfo);
					String res=this.wso2UserService.deleteUserByUserCode(uben);
					log.info("wso2用户删除成功，返回信息内容{}"+res);
					//拼接用户ID
					list.add(val);
					bean = new UserInfoVO();
					bean.setId(Integer.valueOf(val));
					//删除用户组织架构表
					this.userInfoDao.deleteUserOrgById(bean);
					//删除用户职务表
					this.userInfoDao.deleteUserPostById(bean);
					//删除用户角色表
					this.userInfoDao.deleteUserRoleById(bean);
				}
				//批量删除用户
				this.userInfoDao.deleteUserInfoByIds(list);
			}
		}
		return Result.data(Constants.EQU_SUCCESS,"成功!");
	}

	/**
     * @Description：获取用户基础信息
     *   角色，权限，组织架构
     * @return
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public User selectUserInfoByUserCode(User user) {
		//获取用户信息
		User userInfo= this.userInfoDao.selectUserInfoByUserCode(user);
		if(userInfo !=null) {
			userInfo.setCode(user.getCode());
			userInfo.setSmartwfToken(user.getSmartwfToken());
			userInfo.setRefreshToken(user.getRefreshToken());
			userInfo.setAccessToken(user.getAccessToken());
			userInfo.setIdToken(user.getIdToken());
			userInfo.setClientKey(user.getClientKey());
			userInfo.setClientSecret(user.getClientSecret());
			userInfo.setRedirectUri(user.getRedirectUri());
			userInfo.setDateTime(user.getDateTime());
			userInfo.setFlag(user.getFlag());
			//职务
			userInfo.setPostList(this.postDao.selectPostByUserId(userInfo));
			//角色
			userInfo.setRoleList(this.roleDao.selectRoleByUserId(userInfo));
			/**
			//获取组织架构
			userInfo.setOrganizationList(this.organizationDao.selectOrganizationByUserId(userInfo));
			//资源权限
			//List<TreeResource> reslist=buildByRecursive(this.resourceDao.selectResourceByUserId(userInfo) );
			userInfo.setResouceList(reslist);
			 * 
			 */
		}
		return userInfo;
	}

	
	 /**
     * 使用递归方法建树
	* @param treeNodes
	* @return
	*/
	public static List<TreeResource> buildByRecursive(List<TreeResource> treeNodes) {
		List<TreeResource> trees = new ArrayList<TreeResource>();
		for (TreeResource treeNode : treeNodes) {
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
	public static TreeResource findChildren(TreeResource treeNode,List<TreeResource> treeNodes) {
		for (TreeResource it : treeNodes) {
			 if(treeNode.getId().equals(it.getUid())) {
			     if (treeNode.getChildren() == null ) {
			         treeNode.setChildren(new ArrayList<TreeResource>());
			     }
			     treeNode.getChildren().add(findChildren(it,treeNodes));
			 }
		}
		return treeNode;
	}

	/**
     * @Description: wso2租户默认用户保存
     * @return
     */
	@Override
	public void saveWso2UserInfo(UserInfoVO tv,Tenant bean) {
		//查询wso2 租户默认用户id
		Map<String,Object> maplist=wso2UserService.selectUserByName(tv, bean);
		if(maplist.containsKey(Constants.RESOURCES)) {
			List<Map> list=JSONUtil.toList(JSONUtil.parseArray( maplist.get("Resources")), Map.class); 
			if(list!=null && list.size()>0) {
				Map<String,Object> map=JSONUtil.parseObj( JSONUtil.toJsonStr(list.get(0))); 
				if(map.containsKey(Constants.ID)) {
					//给用户添加关联的wso2 userId
					tv.setUserCode(String.valueOf(map.get("id")));
				}
			}
		}
		//保存
		this.userInfoDao.insert(tv);
	}

	/**
     * @Description: 查询用户头像路径
     * @author WCH
     * @param ids
     * @return
     */
	@Override
	public Result<?> selectUserInfoByCreateId(String ids) {
		List<String> list=new ArrayList<>();
		Map<Integer,String> map =new HashMap<>(16);
		String[] str=ids.split(",");
		for(String s: str ) {
			list.add(s);
		}
		List<UserInfo> userInfoList=this.userInfoDao.selectUserInfoByCreateId(list);
		for(UserInfo uf:userInfoList) {
			map.put(uf.getId(), uf.getAvatar());
		}
		return Result.data(Constants.EQU_SUCCESS,map);
	}

	/**
   	 *  排班用户查询
   	 *   查询所有
   	 * @author WCH
   	 * @param bean
   	 * @return
   	 */
	@Override
	public Result<?> selectUserInfoByRoleParam(String tenantDomain){
		List<UserInfo> userInfoList=this.userInfoDao.selectUserInfoByRoleParam(tenantDomain,Constants.SHIFT_GROUP);
		return Result.data(Constants.EQU_SUCCESS,userInfoList);
	}

	/**
   	 *  排班人员信息
   	 *    根据租户和角色名称 分组查询
   	 * @author WCH
   	 */
	@Override
	public void selectUserInfoByShift() {
		//查询租户ID，角色ID
		List<Role> roleList= this.roleDao.selectTenantByGroup(Constants.SHIFT_GROUP);
		if( roleList!=null && roleList.size()>0 ) {
			for( Role t:roleList) {
				//通过租户ID，角色英文名称 查询 用户
				List<UserInfo> userInfoList= this.userInfoDao.selectUserInfoByShift(t);
				//推送消息队列
				this.redisService.set(SecureUtil.md5(Convert.toStr(t.getTenantId())), JSONUtil.toJsonStr(userInfoList));
			}
		}
		
	}
}
