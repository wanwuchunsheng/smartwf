package com.smartwf.sm.modules.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.common.utils.StrUtils;
import com.smartwf.sm.modules.admin.dao.DictionaryDao;
import com.smartwf.sm.modules.admin.pojo.Dictionary;
import com.smartwf.sm.modules.admin.pojo.GlobalData;
import com.smartwf.sm.modules.admin.pojo.Tenant;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.service.DictionaryService;
import com.smartwf.sm.modules.admin.service.GlobalDataService;
import com.smartwf.sm.modules.admin.vo.DictionaryVO;

import cn.hutool.core.convert.Convert;
import lombok.extern.log4j.Log4j;
/**
 * @Description: 数据字典业务层接口实现
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
@Log4j
public class DictionaryServiceImpl implements DictionaryService{
	
	@Autowired
	private DictionaryDao dictionaryDao;
	
	@Autowired
    private GlobalDataService globalDataService;

	/**
	 * @Description:查询数据字典分页
	 * @result:
	 */
	@Override
	public Result<?> selectDictionaryByPage(Page<Dictionary> page, DictionaryVO bean) {
		//查询
		List<UserInfo> userInfoList = this.dictionaryDao.selectDictionaryByPage(bean,page);
		return Result.data(page.getTotal(), userInfoList);
	}

	/**
     * @Description: 主键查询数据字典
     * @return
     */
	@Override
	public Result<?> selectDictionaryById(Dictionary bean) {
		Dictionary dictionary= this.dictionaryDao.selectById(bean);
		return Result.data(dictionary);
	}
	
	/**
     * @Description:主键查询数据字典子节点集合
     * @return
     */
	@Override
	public Result<?> selectDictionaryByUid(Dictionary bean) {
		QueryWrapper<Dictionary> queryWrapper =new QueryWrapper<>();
		queryWrapper.eq("uid", bean.getId());
		List<Dictionary> list=this.dictionaryDao.selectList(queryWrapper);
		return Result.data(list);
	}
	
	/**
     * @Description: 添加数据字典
     * @return
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void saveDictionary(Dictionary bean) {
		//添加创建人基本信息
		User user=UserThreadLocal.getUser();
		bean.setCreateTime(new Date());
		bean.setCreateUserId(user.getId());
		bean.setCreateUserName(user.getUserName());
		bean.setUpdateTime(bean.getCreateTime());
		bean.setUpdateUserId(bean.getCreateUserId());
		bean.setUpdateUserName(bean.getCreateUserName());
		//保存
		this.dictionaryDao.insert(bean);
		/**刷新缓存 */
		//flushType 0全部 1租户 2组织机构 3职务  4数据字典 5角色 6wso2配置
		GlobalData gd=new GlobalData();
		gd.setFlushType(Convert.toStr(Constants.ZERO));
		this.globalDataService.flushCache(gd);
	}

	/**
     * @Description： 修改数据字典
     * @return
     */
	@Override
	public void updateDictionary(Dictionary bean) {
		//添加修改人信息
		User user=UserThreadLocal.getUser();
		bean.setUpdateTime(new Date());
		bean.setUpdateUserId(user.getId());
		bean.setUpdateUserName(user.getUserName());
		//修改
		this.dictionaryDao.updateById(bean);
		/**刷新缓存 */
		//flushType 0全部 1租户 2组织机构 3职务  4数据字典 5角色 6wso2配置
		GlobalData gd=new GlobalData();
		gd.setFlushType(Convert.toStr(Constants.ZERO));
		this.globalDataService.flushCache(gd);
	}

	/**
     * @Description： 删除数据字典
     * @return
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteDictionary(DictionaryVO bean) {
		if( null!=bean.getId()) {
			//删除数据字典表
			this.dictionaryDao.deleteById(bean);
		}else {
			String ids=StrUtils.regex(bean.getIds());
			//批量删除
			if(StringUtils.isNotBlank(ids)) {
				List<String> list=new ArrayList<>();
				for(String val:ids.split(Constants.CHAR)) {
					list.add(val);
				}
				//数据字典表
				this.dictionaryDao.deleteDictionaryByIds(list);
			}
		}
		/**刷新缓存 */
		//flushType 0全部 1租户 2组织机构 3职务  4数据字典 5角色 6wso2配置
		GlobalData gd=new GlobalData();
		gd.setFlushType(Convert.toStr(Constants.ZERO));
		this.globalDataService.flushCache(gd);
	}
	
	/**
     * @Description：初始化数据字典信息
     * @return
     */
	@Override
	public Map<Integer,List<Dictionary>> initDictionaryDatas(List<Tenant> list) {
		Map<Integer,List<Dictionary>> map =new HashMap<>(16);
		QueryWrapper<Dictionary> queryWrapper =null;
		for(Tenant t:list) {
			queryWrapper = new QueryWrapper<>();
			//降序
			queryWrapper.orderByDesc("update_time"); 
			//0启用  1禁用
			queryWrapper.eq("enable", 0); 
			//租户
			queryWrapper.eq("tenant_id", t.getId());
			//租户
			queryWrapper.ne("uid", 0);
			map.put(t.getId(), this.dictionaryDao.selectList(queryWrapper));
		}
		return map;
	}

	

}
