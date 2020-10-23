package com.smartwf.sm.modules.sysconfig.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.sm.modules.sysconfig.pojo.MaintNotification;
import com.smartwf.sm.modules.sysconfig.vo.MaintNotifiVO;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 久层接口
 * @author WCH
 */
@Repository
public interface MaintNotifiDao extends BaseMapper<MaintNotification> {

	/**
	 * @Description: 查询维护通告分页
	 * @param bean
	 * @return
	 */
	List<MaintNotification> selectMaintNotifiByPage(Page<MaintNotification> page, @Param("bean") MaintNotifiVO bean);

	/**
     * @Description: 主键查询维护通告
     * @param bean
     * @return
     */
	MaintNotifiVO selectMaintNotifiById(@Param("bean") MaintNotification bean);

	/**
     * @Description： 删除维护通告
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
	void deleteMaintNotifiByIds(@Param("list") List<String> list);

	

    
}
