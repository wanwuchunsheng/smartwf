package com.smartwf.sm.modules.admin.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.Organization;
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
	 * @Description: 查询职务分页
	 * @result: 
	 */
	Result<?> selectPostByPage(Page<Post> page, PostVO bean);

	/**
     * @Description: 主键查询职务
     * @return
     */
	Result<?> selectPostById(Post bean);

	/**
     * @Description： 添加职务
     * @return
     */
	void savePost(Post bean);
	
	/**
     * @Description： 修改职务
     * @return
     */
	void updatePost(Post bean);

	/**
     * @Description： 删除职务
     * @return
     */
	void deletePost(PostVO bean);
	
	/**
	 * @Description: 初始化职务
	 * @return
	 */
	Map<Integer,List<Post>> initPostDatas(List<Tenant> list);
	
	
	

}
