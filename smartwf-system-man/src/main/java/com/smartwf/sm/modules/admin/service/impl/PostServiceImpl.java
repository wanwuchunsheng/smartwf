package com.smartwf.sm.modules.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.smartwf.common.utils.StrUtils;
import com.smartwf.sm.modules.admin.dao.PostDao;
import com.smartwf.sm.modules.admin.pojo.Post;
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
		QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByDesc("update_time"); //降序
        //过滤租户（登录人为超级管理员，无需过滤，查询所有租户）
  		if (null!=bean.getTenantId() && Constants.ADMIN!=bean.getMgrType()) { 
  			queryWrapper.eq("tenant_id", bean.getTenantId()); 
  		} 
  	    //组织机构
        if (null != bean.getOrganizationId()) {
        	queryWrapper.eq("organization_id", bean.getOrganizationId());
        }
        //职务编码
        if (!StringUtils.isEmpty(bean.getPostCode())) {
        	queryWrapper.like("post_code", Constants.PER_CENT + bean.getPostCode() + Constants.PER_CENT);
        }
        //职务名称
        if (!StringUtils.isEmpty(bean.getPostName())) {
        	queryWrapper.like("post_name", Constants.PER_CENT + bean.getPostName() + Constants.PER_CENT);
        }
        //职务类型
        if (null != bean.getPostType()) {
        	queryWrapper.eq("post_name", bean.getPostType());
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
		IPage<Post> list=this.postDao.selectPage(page, queryWrapper);
		return Result.data(list.getTotal(), list);
	}

	/**
     * @Description: 主键查询职务
     * @return
     */
	@Override
	public Result<?> selectPostById(Post bean) {
		Post Post= this.postDao.selectById(bean);
		return Result.data(Post);
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
	@Transactional
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
				for(String val:ids.split(",")) {
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
     * @Description： 初始化职务
     * @return
     */
	@Override
	public List<Post> queryPostAll() {
		return this.postDao.queryPostAll();
	}


}
