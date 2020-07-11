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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.common.utils.StrUtils;
import com.smartwf.sm.modules.admin.dao.PostDao;
import com.smartwf.sm.modules.admin.pojo.Organization;
import com.smartwf.sm.modules.admin.pojo.Post;
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.service.PostService;
import com.smartwf.sm.modules.admin.vo.PostVO;

import lombok.extern.log4j.Log4j;
/**
 * @Description: 职务业务层接口实现
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
@Log4j
public class PostServiceImpl implements PostService{
	
	@Autowired
	private PostDao postDao;

	/**
	 * @Description:查询职务分页
	 * @result:
	 */
	@Override
	public Result<?> selectPostByPage(Page<Post> page, PostVO bean) {
		//查询
		List<PostVO> userInfoList = this.postDao.selectPostByPage(bean,page);
		return Result.data(page.getTotal(), userInfoList);
	}

	/**
     * @Description: 主键查询职务
     * @return
     */
	@Override
	public Result<?> selectPostById(Post bean) {
		Post post= this.postDao.selectById(bean);
		return Result.data(post);
	}
	
	/**
     * @Description: 添加职务
     * @return
     */
	@Override
	public void savePost(Post bean) {
		//添加创建人基本信息
		User user=UserThreadLocal.getUser();
		bean.setCreateTime(new Date());
		bean.setCreateUserId(user.getId());
		bean.setCreateUserName(user.getUserName());
		bean.setUpdateTime(bean.getCreateTime());
		bean.setUpdateUserId(bean.getCreateUserId());
		bean.setUpdateUserName(bean.getCreateUserName());
		//保存
		this.postDao.insert(bean);
	}

	/**
     * @Description： 修改职务
     * @return
     */
	@Override
	public void updatePost(Post bean) {
		//添加修改人信息
		User user=UserThreadLocal.getUser();
		bean.setUpdateTime(new Date());
		bean.setUpdateUserId(user.getId());
		bean.setUpdateUserName(user.getUserName());
		//修改
		this.postDao.updateById(bean);
	}

	/**
     * @Description： 删除职务
     * @return
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deletePost(PostVO bean) {
		if( null!=bean.getId()) {
			//删除职务
			this.postDao.deleteById(bean);
			//删除用户职务
			this.postDao.deleteUserPostById(bean);
		}else {
			String ids=StrUtils.regex(bean.getIds());
			if(StringUtils.isNotBlank(ids)) {
				List<String> list=new ArrayList<>();
				for(String val:ids.split(Constants.CHAR)) {
					list.add(val);
					bean=new PostVO();
					bean.setId(Integer.valueOf(val));
					//删除用户职务
					this.postDao.deleteUserPostById(bean);
				}
				//批量删除职务
				this.postDao.deletePostByIds(list);
			}
		}
	}
	
	/**
	 * @Description: 初始化组织机构
	 * @return
	 */
	@Override
	public Map<Integer,List<Post>> initPostDatas(List<Tenant> list) {
		Map<Integer,List<Post>> map =new HashMap<>(16);
		QueryWrapper<Post> queryWrapper =null;
		for(Tenant t:list) {
			queryWrapper = new QueryWrapper<>();
			//降序
			queryWrapper.orderByDesc("update_time"); 
			//0启用  1禁用
			queryWrapper.eq("enable", 0); 
			//租户
			queryWrapper.eq("tenant_id", t.getId());
			map.put(t.getId(), this.postDao.selectList(queryWrapper));
		}
		return map;
	}

}
