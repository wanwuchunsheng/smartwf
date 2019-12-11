package com.smartwf.sm.modules.admin.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.sm.modules.admin.dao.ResouceDao;
import com.smartwf.sm.modules.admin.pojo.Resouce;
import com.smartwf.sm.modules.admin.service.ResouceService;
import com.smartwf.sm.modules.admin.vo.ResouceVO;

import lombok.extern.log4j.Log4j;
import tk.mybatis.mapper.entity.Example;
/**
 * @Description: 资源业务层接口实现
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
@Log4j
public class ResouceServiceImpl implements ResouceService{
	
	@Autowired
	private ResouceDao resouceDao;

	/**
	 * @Description:查询资源分页
	 * @result:
	 */
	@Override
	public Result<?> selectResouceByPage(Page<Object> page, ResouceVO bean) {
		Page<Object> objectPage = PageHelper.startPage(page.getPageNum(), page.getPageSize());
		Example example = new Example(Resouce.class);
        example.setOrderByClause("create_time desc");
        Example.Criteria criteria = example.createCriteria();
        //过滤租户（登录人为超级管理员，无需过滤，查询所有租户）
  		if (null!=bean.getTenantId() && Constants.ADMIN!=bean.getMgrType()) { 
  			criteria.andEqualTo("tenantId", bean.getTenantId()); 
  		}
        //资源编码
        if (!StringUtils.isEmpty(bean.getResCode())) {
            criteria.andLike("ResouceCode", Constants.PER_CENT + bean.getResCode() + Constants.PER_CENT);
        }
        //资源名称
        if (!StringUtils.isEmpty(bean.getResName())) {
            criteria.andLike("ResouceName", Constants.PER_CENT + bean.getResName() + Constants.PER_CENT);
        }
        //状态
		if (null!=bean.getEnable()) { 
			criteria.andEqualTo("enable", bean.getEnable()); 
		}
        //备注
        if (!StringUtils.isEmpty(bean.getRemark())) {
            criteria.andLike("remark", Constants.PER_CENT + bean.getRemark() + Constants.PER_CENT);
        }
		List<Resouce> list=this.resouceDao.selectByExample(example);
		return Result.data(objectPage.getTotal(), list);
	}

	/**
     * @Description: 主键查询资源
     * @return
     */
	@Override
	public Result<?> selectResouceById(Resouce bean) {
		Resouce resouce= this.resouceDao.selectByPrimaryKey(bean);
		return Result.data(resouce);
	}
	
	/**
     * @Description: 添加资源
     * @return
     */
	@Override
	public void saveResouce(Resouce bean) {
		//添加创建人基本信息
		User user=UserThreadLocal.getUser();
		bean.setCreateTime(new Date());
		bean.setCreateUserId(user.getId());
		bean.setCreateUserName(user.getUserName());
		bean.setUpdateTime(bean.getCreateTime());
		bean.setUpdateUserId(bean.getCreateUserId());
		bean.setUpdateUserName(bean.getCreateUserName());
		//保存
		this.resouceDao.insertSelective(bean);
	}

	/**
     * @Description： 修改资源
     * @return
     */
	@Override
	public void updateResouce(Resouce bean) {
		//添加修改人信息
		User user=UserThreadLocal.getUser();
		bean.setUpdateTime(new Date());
		bean.setUpdateUserId(user.getId());
		bean.setUpdateUserName(user.getUserName());
		//修改
		this.resouceDao.updateByPrimaryKeySelective(bean);
	}

	/**
     * @Description： 删除资源
     * @return
     */
	@Transactional
	@Override
	public void deleteResouce(ResouceVO bean) {
		if( null!=bean.getId()) {
			//判断删除的数据类型
			Resouce res=this.resouceDao.selectOne(bean);
			//1系统  2模块  3资源
			switch (res.getResType()) {
				case 1:
					//a删除子系统
					this.resouceDao.deleteByPrimaryKey(res);
					//b删除子系统下所有资源
					this.resouceDao.deleteResouceByfid(res);
					break;
	            case 2:
					//a删除模块
	            	this.resouceDao.deleteByPrimaryKey(res);
	            	//b查询模块下所有资源
	            	this.resouceDao.selectResouceByfidLevel(res);
					break;
	            case 3:
	            	//删除资源
					this.resouceDao.deleteByPrimaryKey(res);
					break;
				default:
					break;
			}
		}
	}

	/**
     * @Description： 初始化资源
     * @return
     */
	@Override
	public List<Resouce> queryResouceAll() {
		return this.resouceDao.queryResouceAll();
	}


}
