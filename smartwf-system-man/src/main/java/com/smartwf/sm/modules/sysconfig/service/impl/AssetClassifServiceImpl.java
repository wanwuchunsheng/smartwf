package com.smartwf.sm.modules.sysconfig.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.utils.CkUtils;
import com.smartwf.sm.modules.sysconfig.dao.AssetClassifDao;
import com.smartwf.sm.modules.sysconfig.pojo.AssetClassification;
import com.smartwf.sm.modules.sysconfig.service.AssetClassifService;
import com.smartwf.sm.modules.sysconfig.vo.AssetClassifVO;
/**
 * @Description: 资产分类业务实现层
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
public class AssetClassifServiceImpl implements AssetClassifService{
	@Autowired
	private AssetClassifDao AssetClassifDao;

	/**
	 * @Description: 查询资产分类分页
	 * @param bean
	 * @return
	 */
	@Override
	public Result<?> selectAssetClassifByPage(Page<AssetClassification> page, AssetClassifVO bean) {
		List<AssetClassification> list=this.AssetClassifDao.selectAssetClassifByPage(page,bean);
		return Result.data(Constants.EQU_SUCCESS, list);
	}

	/**
     * @Description: 主键查询资产分类
     * @param bean
     * @return
     */
	@Override
	public Result<?> selectAssetClassifById(AssetClassification bean) {
		AssetClassifVO icfg=this.AssetClassifDao.selectAssetClassifById(bean);
		return Result.data(Constants.EQU_SUCCESS, icfg);
	}

	/**
     * @Description: 添加资产分类
     * @param bean
     * @return
     */
	@Override
	public void saveAssetClassif(AssetClassification bean) {
		this.AssetClassifDao.insert(bean);
	}

	/**
     * @Description： 修改资产分类
     * @param bean
     * @return
     */
	@Override
	public void updateAssetClassif(AssetClassification bean) {
		this.AssetClassifDao.updateById(bean);
	}

	/**
     * @Description： 删除资产分类
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteAssetClassif(AssetClassifVO bean) {
		if( null!=bean.getId()) {
			//删除多租户配置表
			this.AssetClassifDao.deleteById(bean);
		}else {
			String ids=CkUtils.regex(bean.getIds());
			//批量删除
			if(StringUtils.isNotBlank(ids)) {
				List<String> list=new ArrayList<>();
				for(String val:ids.split(Constants.CHAR)) {
					list.add(val);
				}
				//多租户配置表
				this.AssetClassifDao.deleteAssetClassifByIds(list);
			}
		}
	}
	
}
