package com.smartwf.hm.modules.knowledgebase.service.impl;

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
import com.smartwf.common.utils.JsonUtil;
import com.smartwf.hm.modules.knowledgebase.dao.FaultCodeBaseDao;
import com.smartwf.hm.modules.knowledgebase.pojo.FaultCodeBase;
import com.smartwf.hm.modules.knowledgebase.service.FaultCodeBaseService;
import com.smartwf.hm.modules.knowledgebase.vo.FaultCodeBaseVO;

import lombok.extern.log4j.Log4j2;
/**
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 * @Description: 故障代码业务层实现
 */
@Service
@Log4j2
public class FaultCodeBaseServiceImpl implements FaultCodeBaseService{
	
	@Autowired
	private FaultCodeBaseDao faultCodeBaseDao;
	
	/**
	 * @Description: 故障代码批量录入
	 * @author WCH
	 * @datetime 2020-5-14 11:27:13
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void saveFaultCodeBase(FaultCodeBase bean) {
		//将json字符串强制装换成对象
		List<FaultCodeBase> fcblist=JsonUtil.jsonToList(bean.getRemark(), FaultCodeBase.class);
		if(!fcblist.isEmpty()) {
			for(FaultCodeBase fb :fcblist) {
				fb.setTenantDomain(bean.getTenantDomain());
				//0默认  1审核通过
				fb.setStatus(Constants.ONE); 
				this.faultCodeBaseDao.insert(fb);
			}
		}
	}

	/**
     * @Description： 删除故障代码库
     *   id删除 级联删除
     *   1）查询要删除的故障代码
     *   2）删除故障代码库
     *   3）通过故障代码，删除故障解决方案
     * @author WCH
     * @return
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteFaultCodeBase(FaultCodeBase bean) {
		//1)查询删除对应的代码库
		List<FaultCodeBase> fcblist=this.faultCodeBaseDao.selectFaultCodeBase(bean);
		//2)删除代码库
		this.faultCodeBaseDao.deleteFaultCodeBase(bean);
		//3)通过代码库级联删除故障解决方案
		if(!fcblist.isEmpty()) {
			for(FaultCodeBase fcb : fcblist) {
				this.faultCodeBaseDao.deleteFaultSolutionInfo(fcb);
			}
		}
	}

	/**
     * @Description： 故障代码库主键删除
     *   主键删除 、级联删除
     *   //1）查询对象
     *   //2） 删除故障代码
     *   //3）根据对象faultCode，删除故障解决方案  
     * @return
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteFaultCodeBaseById(FaultCodeBase bean) {
		//1）查询对象
		FaultCodeBase obj=this.faultCodeBaseDao.selectById(bean);
		//2) 删除故障代码
		this.faultCodeBaseDao.deleteById(obj);
		//3） 删除故障解决方案
		this.faultCodeBaseDao.deleteFaultSolutionInfo(obj);
		
	}

	/**
     * @Description： 故障代码库(主键)查询
     *   主键查询
     * @return
     */
	@Override
	public Result<?> selectFaultCodeBaseById(FaultCodeBase bean) {
		return Result.data(Constants.EQU_SUCCESS,this.faultCodeBaseDao.selectById(bean));
	}

	/**
     * @Description： 故障代码库分页查询
     * @author WCH
     * @datetime 2020-5-14 15:55:37
     * @return
     */
	@Override
	public Result<?> selectFaultCodeBaseByPage(Page<FaultCodeBase> page, FaultCodeBaseVO bean) {
		QueryWrapper<FaultCodeBase> queryWrapper = new QueryWrapper<>();
		//降序
		queryWrapper.orderByDesc("create_time"); 
  		//租户
  		if (StringUtils.isNotBlank(bean.getTenantDomain()) ) { 
  			queryWrapper.eq("tenant_domain", bean.getTenantDomain()); 
  		}
        //风电机组型号
        if (StringUtils.isNotBlank(bean.getModel())) {
        	queryWrapper.like("model", Constants.PER_CENT + bean.getModel() + Constants.PER_CENT);
        }
        //协议号
        if (StringUtils.isNotBlank(bean.getProtocolNo())) {
        	queryWrapper.like("protocol_no", Constants.PER_CENT + bean.getProtocolNo() + Constants.PER_CENT);
        }
        //协议号
        if (StringUtils.isNotBlank(bean.getIecPath())) {
        	queryWrapper.like("iec_path", Constants.PER_CENT + bean.getIecPath() + Constants.PER_CENT);
        }
        //故障代码
        if (StringUtils.isNotBlank(bean.getFaultCode())) {
        	queryWrapper.like("fault_code", Constants.PER_CENT + bean.getFaultCode() + Constants.PER_CENT);
        }
        //故障名称
        if (StringUtils.isNotBlank(bean.getFaultName())) {
        	queryWrapper.like("fault_name", Constants.PER_CENT + bean.getFaultName() + Constants.PER_CENT);
        }
        //英文名称
        if (StringUtils.isNotBlank(bean.getEngName())) {
        	queryWrapper.like("eng_name", Constants.PER_CENT + bean.getEngName() + Constants.PER_CENT);
        }
        //部件名称
        if (StringUtils.isNotBlank(bean.getComponentName())) {
        	queryWrapper.like("component_name", Constants.PER_CENT + bean.getComponentName() + Constants.PER_CENT);
        }
        //资产型号ID
  		if (StringUtils.isNotBlank(bean.getPmsAmId())) { 
  			queryWrapper.eq("pms_am_id", bean.getPmsAmId()); 
  		}
  		//部件ID
  		if (StringUtils.isNotBlank(bean.getPmsAiId())) { 
  			queryWrapper.eq("pms_ai_id", bean.getPmsAiId()); 
  		}
        //审核状态 0默认待审核  1审核通过
  		if (null!=bean.getStatus()) { 
  			queryWrapper.eq("status", bean.getStatus()); 
  		}
        //是否启用 0启用  1禁用
  		if (null!=bean.getEnable()) { 
  			queryWrapper.eq("enable", bean.getEnable()); 
  		}
  		//备注
        if (StringUtils.isNotBlank(bean.getRemark())) {
        	queryWrapper.like("remark", Constants.PER_CENT + bean.getRemark() + Constants.PER_CENT);
        }
        //时间
        if (bean.getStime() != null && bean.getEtime() != null) {
        	queryWrapper.between("create_time", bean.getStime(), bean.getEtime());
        }
		IPage<FaultCodeBase> list=this.faultCodeBaseDao.selectPage(page, queryWrapper);
		return Result.data(list.getTotal(), list,Constants.EQU_SUCCESS,null);
	}

	
	
}
