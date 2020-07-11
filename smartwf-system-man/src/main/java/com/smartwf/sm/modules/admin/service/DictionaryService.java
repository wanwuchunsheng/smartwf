package com.smartwf.sm.modules.admin.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.Dictionary;
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.vo.DictionaryVO;

/**
 * @Description: 数据字典业务层接口
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
public interface DictionaryService {

	/**
	 * 查询数据字典分页
	 * @param bean
	 * @param page
	 * @return 
	 */
	Result<?> selectDictionaryByPage(Page<Dictionary> page, DictionaryVO bean);

	/**
     * 主键查询数据字典
     * @author WCH
     * @param bean
     * @return
     */
	Result<?> selectDictionaryById(Dictionary bean);
	
	/**
     *  
	 * 主键查询数据字典子节点集合
	 * @param bean
     * @return
     */
	Result<?> selectDictionaryByUid(Dictionary bean);

	/**
     *  添加数据字典
     * @param bean
     */
	void saveDictionary(Dictionary bean);
	
	/**
     *  修改数据字典
     * @param bean
     */
	void updateDictionary(Dictionary bean);

	/**
     *  删除数据字典
     *  @param bean
     */
	void deleteDictionary(DictionaryVO bean);

	/**
	 * 初始化数据字典信息
     * @param list 
     * @return
     */
	Map<Integer,List<Dictionary>> initDictionaryDatas(List<Tenant> list);
	
	
	
	

}
