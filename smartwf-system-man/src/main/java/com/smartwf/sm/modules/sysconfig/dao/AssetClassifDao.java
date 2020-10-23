package com.smartwf.sm.modules.sysconfig.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.sm.modules.sysconfig.pojo.AssetClassification;
import com.smartwf.sm.modules.sysconfig.vo.AssetClassifVO;
/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 久层接口
 * @author WCH
 */
@Repository
public interface AssetClassifDao extends BaseMapper<AssetClassification> {

	/**
	 * @Description: 查询资产分类分页
	 * @param bean
	 * @return
	 */
	List<AssetClassification> selectAssetClassifByPage(Page<AssetClassification> page, @Param("bean") AssetClassifVO bean);

	/**
     * @Description: 主键查询资产分类
     * @param bean
     * @return
     */
	AssetClassifVO selectAssetClassifById(@Param("bean") AssetClassification bean);

	/**
     * @Description： 删除资产分类
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
	void deleteAssetClassifByIds(@Param("list") List<String> list);

	
}
