package com.smartwf.sm.modules.admin.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.Dictionary;
import com.smartwf.sm.modules.admin.pojo.Role;
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.vo.DictionaryVO;

/**
 * @Description: 数据字典业务层接口
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
public interface DictionaryService {

	/**
	 * @Description: 查询数据字典分页
	 * @result: 
	 */
	Result<?> selectDictionaryByPage(Page<Dictionary> page, DictionaryVO bean);

	/**
     * @Description: 主键查询数据字典
     * @return
     */
	Result<?> selectDictionaryById(Dictionary bean);

	/**
     * @Description： 添加数据字典
     * @return
     */
	void saveDictionary(Dictionary bean);
	
	/**
     * @Description： 修改数据字典
     * @return
     */
	void updateDictionary(Dictionary bean);

	/**
     * @Description： 删除数据字典
     * @return
     */
	void deleteDictionary(DictionaryVO bean);

	/**
     * @param tenantList 
	 * @Description：初始化数据字典信息
     * @return
     */
	Map<Integer,List<Dictionary>> InitDictionaryDatas(List<Tenant> list);
	
	
	

}
