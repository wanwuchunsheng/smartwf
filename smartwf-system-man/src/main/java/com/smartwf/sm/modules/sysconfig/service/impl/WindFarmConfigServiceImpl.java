package com.smartwf.sm.modules.sysconfig.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.utils.CkUtils;
import com.smartwf.sm.modules.sysconfig.dao.WindFarmConfigDao;
import com.smartwf.sm.modules.sysconfig.pojo.WindfarmConfig;
import com.smartwf.sm.modules.sysconfig.service.WindFarmConfigService;
import com.smartwf.sm.modules.sysconfig.vo.WindfarmConfigVO;

import lombok.extern.log4j.Log4j;
/**
 * @Description: 风场配置业务层接口实现
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
@Log4j
public class WindFarmConfigServiceImpl implements WindFarmConfigService{
	
	@Autowired
	private WindFarmConfigDao windFarmConfigDao;

	/**
	 * @Description:查询风场配置配置分页
	 * @result
	 */
	@Override
	public Result<?> selectWindFarmConfigByPage(Page<WindfarmConfig> page, WindfarmConfigVO bean) {
		List<WindfarmConfigVO> list=this.windFarmConfigDao.selectWindFarmConfigByPage(page, bean);
		return Result.data(page.getTotal(), list);
	}

	/**
     * @Description: 主键查询风场配置
     * @return
     */
	@Override
	public Result<?> selectWindFarmConfigById(WindfarmConfig bean) {
		QueryWrapper<WindfarmConfig> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("tenant_id", bean.getTenantId());
		queryWrapper.eq("wind_farm", bean.getWindFarm());
		WindfarmConfig windfarmConfig= this.windFarmConfigDao.selectOne(queryWrapper);
		return Result.data(windfarmConfig);
	}
	
	/**
     * @Description: 添加风场配置配置
     * @return
     */
	@Override
	public Result<?> saveWindFarmConfig(WindfarmConfig bean) {
		QueryWrapper<WindfarmConfig> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("tenant_id", bean.getTenantId());
		queryWrapper.eq("wind_farm", bean.getWindFarm());
		WindfarmConfig windfarmConfig= this.windFarmConfigDao.selectOne(queryWrapper);
		//判断是否重复
		if(null !=windfarmConfig) {
			return Result.data(Constants.EQU_SUCCESS,"添加失败，数据重复添加");
		}
		bean.setCreateTime(new Date());
		this.windFarmConfigDao.insert(bean);
		return Result.data(Constants.EQU_SUCCESS,"添加成功");
		
		
	}

	/**
     * @Description： 修改风场配置配置
     * @return
     */
	@Override
	public void updateWindFarmConfig(WindfarmConfig bean) {
		this.windFarmConfigDao.updateById(bean);
	}

	/**
     * @Description： 删除风场配置配置
     * @return
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteWindFarmConfig(WindfarmConfigVO bean) {
		if( null!=bean.getId()) {
			//删除风场配置表
			this.windFarmConfigDao.deleteById(bean);
		}else {
			String ids=CkUtils.regex(bean.getIds());
			//批量删除
			if(StringUtils.isNotBlank(ids)) {
				List<String> list=new ArrayList<>();
				for(String val:ids.split(Constants.CHAR)) {
					list.add(val);
				}
				//风场配置表
				this.windFarmConfigDao.deleteWindFarmConfigByIds(list);
			}
		}
	}
	
	


}
