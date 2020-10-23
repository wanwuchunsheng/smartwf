package com.smartwf.sm.modules.sysconfig.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.sysconfig.pojo.AssetClassification;
import com.smartwf.sm.modules.sysconfig.vo.AssetClassifVO;

/**
 * @Description: 资产分类业务层接口
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
public interface AssetClassifService {

	/**
	 * @Description: 查询资产分类分页
	 * @param bean
	 * @return
	 */
	Result<?> selectAssetClassifByPage(Page<AssetClassification> page, AssetClassifVO bean);

	/**
     * @Description: 主键查询资产分类
     * @param bean
     * @return
     */
	Result<?> selectAssetClassifById(AssetClassification bean);

	/**
     * @Description: 添加资产分类
     * @param bean
     * @return
     */
	void saveAssetClassif(AssetClassification bean);

	/**
     * @Description： 修改资产分类
     * @param bean
     * @return
     */
	void updateAssetClassif(AssetClassification bean);

	/**
     * @Description： 删除资产分类
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
	void deleteAssetClassif(AssetClassifVO bean);

	
	
	

}
