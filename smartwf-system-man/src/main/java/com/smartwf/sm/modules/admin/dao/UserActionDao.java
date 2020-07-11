package com.smartwf.sm.modules.admin.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartwf.sm.modules.admin.pojo.UserAction;

/**
 * @author WCH
 * @Date: 2019-11-27 11:29:02
 * 用户操作持久层接口
 */
@Repository
public interface UserActionDao extends BaseMapper<UserAction> {

	/**
	 * 批量删除用户操作
	 * @author WCH
	 * @param list
	 */
	void deleteUserActionByIds(@Param("list") List<String> list);
	/**
	 * 初始化用户操作
	 * @return
	 */
	List<UserAction> queryUserActionAll();

}
