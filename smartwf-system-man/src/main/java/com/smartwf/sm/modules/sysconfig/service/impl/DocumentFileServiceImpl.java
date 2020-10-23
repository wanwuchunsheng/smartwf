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
import com.smartwf.sm.modules.sysconfig.dao.DocumentFileDao;
import com.smartwf.sm.modules.sysconfig.pojo.DocumentType;
import com.smartwf.sm.modules.sysconfig.service.DocumentFileService;
import com.smartwf.sm.modules.sysconfig.vo.DocumentTypeVO;
import com.smartwf.sm.modules.sysconfig.vo.TenantConfigVO;
/**
 * @Description: 文档文件配置业务实现层
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@Service
public class DocumentFileServiceImpl implements DocumentFileService{
	
	@Autowired
	private DocumentFileDao documentFileDao;

	/**
	 * @Description: 查询文档文件配置分页
	 * @param bean
	 * @return
	 */
	@Override
	public Result<?> selectDocumentTypeByPage(Page<DocumentType> page, DocumentTypeVO bean) {
		List<DocumentType> list=this.documentFileDao.selectDocumentTypeByPage(page,bean);
		return Result.data(Constants.EQU_SUCCESS, list);
	}

	/**
     * @Description: 主键查询文档文件配置
     * @param bean
     * @return
     */
	@Override
	public Result<?> selectDocumentTypeById(DocumentType bean) {
		DocumentType icfg=this.documentFileDao.selectDocumentTypeById(bean);
		return Result.data(Constants.EQU_SUCCESS, icfg);
	}

	/**
     * @Description: 添加文档文件配置
     * @param bean
     * @return
     */
	@Override
	public void saveDocumentType(DocumentType bean) {
		this.documentFileDao.insert(bean);
	}

	/**
     * @Description： 修改文档文件配置
     * @param bean
     * @return
     */
	@Override
	public void updateDocumentType(DocumentType bean) {
		this.documentFileDao.updateById(bean);
	}

	/**
     * @Description： 删除文档文件配置
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteDocumentType(DocumentTypeVO bean) {
		if( null!=bean.getId()) {
			//删除多租户配置表
			this.documentFileDao.deleteById(bean);
		}else {
			String ids=CkUtils.regex(bean.getIds());
			//批量删除
			if(StringUtils.isNotBlank(ids)) {
				List<String> list=new ArrayList<>();
				for(String val:ids.split(Constants.CHAR)) {
					list.add(val);
				}
				//多租户配置表
				this.documentFileDao.deleteDocumentTypeByIds(list);
			}
		}
	}
	

}
