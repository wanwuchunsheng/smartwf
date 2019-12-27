package com.smartwf.sm.modules.admin.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.sm.modules.admin.pojo.Dictionary;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.vo.DictionaryVO;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 字典数据持久层接口
 */
@Repository
public interface DictionaryDao extends BaseMapper<Dictionary> {

	/**
     * @Description：分页查询数据字典数据
     * @return
     */
	List<UserInfo> selectDictionaryByPage(@Param("bean") DictionaryVO bean, Page<Dictionary> page);

	/**
     * @Description：批量 删除数据字典
     * @return
     */
	void deleteDictionaryByIds(@Param("list") List<String> list);
    
}
