package com.smartwf.sm.modules.admin.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.Result;
import com.smartwf.common.pojo.User;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.common.utils.CkUtils;
import com.smartwf.sm.modules.admin.dao.SubsystemDao;
import com.smartwf.sm.modules.admin.pojo.Resource;
import com.smartwf.sm.modules.admin.service.SubsystemService;
import com.smartwf.sm.modules.admin.vo.ResourceVO;
import com.smartwf.sm.modules.admin.vo.SysConfigVO;

import lombok.extern.log4j.Log4j;
/**
 * @Description: 资源业务层接口实现
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
@Log4j
public class SubsystemServiceImpl implements SubsystemService{
	
	@Autowired
	private SubsystemDao subsystemDao;
	
	/**
	 * @Description:查询资源分页
	 * @result:
	 */
	@Override
	public Result<?> selectSubsystemByPage(Page<Resource> page, ResourceVO bean) {
		QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
		//降序
		queryWrapper.orderByDesc("update_time"); 
		//默认查询所有子系统
		queryWrapper.eq("pid",Constants.ZERO);
        //租户
  		if (null!=bean.getTenantId()) {
  			queryWrapper.eq("tenant_id", bean.getTenantId());
  		}
        //资源编码
        if (!StringUtils.isEmpty(bean.getResCode())) {
        	queryWrapper.like("res_code", Constants.PER_CENT + bean.getResCode() + Constants.PER_CENT);
        }
        //资源名称
        if (!StringUtils.isEmpty(bean.getResName())) {
        	queryWrapper.like("res_name", Constants.PER_CENT + bean.getResName() + Constants.PER_CENT);
        }
        //状态
		if (null!=bean.getEnable()) {
			queryWrapper.eq("enable", bean.getEnable());
		}
        //备注
        if (!StringUtils.isEmpty(bean.getRemark())) {
        	queryWrapper.like("remark", Constants.PER_CENT + bean.getRemark() + Constants.PER_CENT);
        }
		IPage<Resource> list=this.subsystemDao.selectPage(page, queryWrapper);
		return Result.data(list.getTotal(), list.getRecords());
	}

	/**
     * @Description: 主键查询资源
     * @return
     */
	@Override
	public Result<?> selectSubsystemById(Resource bean) {
		Resource resource= this.subsystemDao.selectById(bean);
		return Result.data(resource);
	}
	
	/**
     * @Description: 添加资源
     * @return
     */
	@Override
	public void saveSubsystem(Resource bean) {
		//添加创建人基本信息
		User user=UserThreadLocal.getUser();
		bean.setCreateTime(new Date());
		bean.setCreateUserId(user.getId());
		bean.setCreateUserName(user.getUserName());
		bean.setUpdateTime(bean.getCreateTime());
		bean.setUpdateUserId(bean.getCreateUserId());
		bean.setUpdateUserName(bean.getCreateUserName());
		bean.setUid(0);
		bean.setPid(0);
		//排序
		bean.setSort(0);
		bean.setLevel(1);
		//1 系统  2模块 3资源
		bean.setResType(1);
		//保存
		this.subsystemDao.insert(bean);
	}

	/**
     * @Description： 修改资源
     * @return
     */
	@Override
	public void updateSubsystem(Resource bean) {
		//添加修改人信息
		User user=UserThreadLocal.getUser();
		bean.setUpdateTime(new Date());
		bean.setUpdateUserId(user.getId());
		bean.setUpdateUserName(user.getUserName());
		//修改
		this.subsystemDao.updateById(bean);
	}

	/**
     * @Description： 删除资源
     * @return
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteSubsystem(ResourceVO bean) {
		if( null!=bean.getId()) {
			//删除资源
			this.subsystemDao.deleteById(bean);
		}else {
			String ids=CkUtils.regex(bean.getIds());
			if(StringUtils.isNotBlank(ids)) {
				SysConfigVO scv=null;
				for(String val:ids.split(Constants.CHAR)) {
					scv = new SysConfigVO();
					scv.setId(Integer.valueOf(val));
					//删除
					this.subsystemDao.deleteById(scv);
				}
		    }
		}
		
	}


}
