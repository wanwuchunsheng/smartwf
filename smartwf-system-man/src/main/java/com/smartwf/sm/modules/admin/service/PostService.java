package com.smartwf.sm.modules.admin.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.Post;
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.vo.PostVO;

/**
 * @Description: 职务业务层接口
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
public interface PostService {

	/**
	 * 查询职务分页
	 * @param page
	 * @param bean
	 * @return 
	 */
	Result<?> selectPostByPage(Page<Post> page, PostVO bean);

	/**
     * 主键查询职务
     * @param bean
     * @return
     */
	Result<?> selectPostById(Post bean);

	/**
     * 添加职务
     * @param bean
     */
	void savePost(Post bean);
	
	/**
     *  修改职务
     *  @param bean
     */
	void updatePost(Post bean);

	/**
     *  删除职务
     *  @param bean
     */
	void deletePost(PostVO bean);
	
	/**
	 * 初始化职务
	 * @param list
	 * @return
	 */
	Map<Integer,List<Post>> initPostDatas(List<Tenant> list);
	
	
	

}
