package com.smartwf.sm.modules.admin.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.sm.modules.admin.pojo.Post;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.vo.PostVO;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 职务持久层接口
 */
@Repository
public interface PostDao extends BaseMapper<Post> {

	/**
	 * @Description: 批量删除职务
	 * @result:
	 */
	void deletePostByIds(@Param("list") List<String> list);

	/**
	 * @Description: 初始化职务
	 * @result:
	 */
	List<Post> queryPostAll();

	/**
	 * @Deprecated 删除用户职务
	 * 
	 * */
	void deleteUserPostById(@Param("bean") PostVO bean);
	/**
	 * @Deprecated 分页查询职务所有数据=删除用户职务
	 * 
	 * */
	List<PostVO> selectPostByPage(@Param("bean") PostVO bean, Page<Post> page);


    
}
