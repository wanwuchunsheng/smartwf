package com.smartwf.sm.modules.admin.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.TreePost;
import com.smartwf.common.pojo.User;
import com.smartwf.sm.modules.admin.pojo.Post;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.vo.PostVO;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 职务持久层接口
 * @author WCH
 */
@Repository
public interface PostDao extends BaseMapper<Post> {

	/**
	 * 批量删除职务
	 * @param list
	 * @result:
	 */
	void deletePostByIds(@Param("list") List<String> list);

	/**
	 * 删除用户职务
	 * @param bean
	 * 
	 * */
	void deleteUserPostById(@Param("bean") PostVO bean);
	/**
	 * 分页查询职务所有数据=删除用户职务
	 * @param page 
	 * @param bean
	 * @return
	 * 
	 * */
	List<PostVO> selectPostByPage(@Param("bean") PostVO bean, Page<Post> page);
	/**
	 * 通过用户ID查询职务
	 * @param userInfo
	 * @return
	 * */
	List<TreePost> selectPostByUserId(@Param("bean") User userInfo);


    
}
