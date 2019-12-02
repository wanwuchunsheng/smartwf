package com.smartwf.sm.modules.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
import tk.mybatis.mapper.entity.Example;
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
	public Result<?> selectPostByPage(Page<Object> page, PostVO bean) {
		Page<Object> objectPage = PageHelper.startPage(page.getPageNum(), page.getPageSize());
		Example example = new Example(Post.class);
        example.setOrderByClause("create_time desc");
        Example.Criteria criteria = example.createCriteria();
        //过滤租户（登录人为超级管理员，无需过滤，查询所有租户）
  		if (null!=bean.getTenantId() && Constants.ADMIN!=bean.getMgrType()) { 
  			criteria.andEqualTo("tenantId", bean.getTenantId()); 
  		} 
  	    //组织机构
        if (null != bean.getOrganizationId()) {
            criteria.andEqualTo("organizationId", bean.getOrganizationId());
        }
        //职务编码
        if (!StringUtils.isEmpty(bean.getPostCode())) {
            criteria.andLike("postCode", Constants.PER_CENT + bean.getPostCode() + Constants.PER_CENT);
        }
        //职务名称
        if (!StringUtils.isEmpty(bean.getPostName())) {
            criteria.andLike("postName", Constants.PER_CENT + bean.getPostName() + Constants.PER_CENT);
        }
        //职务类型
        if (null != bean.getPostType()) {
            criteria.andEqualTo("postName", bean.getPostType());
        }
        //状态
		if (null!=bean.getEnable()) { 
			criteria.andEqualTo("enable", bean.getEnable()); 
		}
        //时间
        if (bean.getStartTime() != null && bean.getEndTime() != null) {
            criteria.orBetween("createTime", bean.getStartTime(), bean.getEndTime());
        }
        //备注
        if (!StringUtils.isEmpty(bean.getRemark())) {
            criteria.andLike("remark", Constants.PER_CENT + bean.getRemark() + Constants.PER_CENT);
        }
		List<Post> list=this.postDao.selectByExample(example);
		return Result.data(objectPage.getTotal(), list);
	}

	/**
     * @Description: 主键查询职务
     * @return
     */
	@Override
	public Result<?> selectPostById(Post bean) {
		Post Post= this.postDao.selectByPrimaryKey(bean);
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
		this.postDao.insertSelective(bean);
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
		this.postDao.updateByPrimaryKeySelective(bean);
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
			this.postDao.deleteByPrimaryKey(bean);
			//删除用户组织结构
			this.postDao.deleteUserOrgById(bean);
		}else {
			String ids=StrUtils.regex(bean.getIds());
			if(StringUtils.isNotBlank(ids)) {
				List<String> list=new ArrayList<>();
				for(String val:ids.split(",")) {
					list.add(val);
					bean=new PostVO();
					bean.setId(Integer.valueOf(val));
					//删除用户组织结构
					this.postDao.deleteUserOrgById(bean);
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
