package com.smartwf.sm.modules.sysconfig.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.sysconfig.pojo.DocumentType;
import com.smartwf.sm.modules.sysconfig.vo.DocumentTypeVO;

/**
 * @Description: 文档文件配置业务层接口
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
public interface DocumentFileService {

	/**
	 * @Description: 查询文档文件配置分页
	 * @param bean
	 * @return
	 */
	Result<?> selectDocumentTypeByPage(Page<DocumentType> page, DocumentTypeVO bean);

	/**
     * @Description: 主键查询文档文件配置
     * @param bean
     * @return
     */
	Result<?> selectDocumentTypeById(DocumentType bean);

	/**
     * @Description: 添加文档文件配置
     * @param bean
     * @return
     */
	void saveDocumentType(DocumentType bean);

	/**
     * @Description： 修改文档文件配置
     * @param bean
     * @return
     */
	void updateDocumentType(DocumentType bean);

	/**
     * @Description： 删除文档文件配置
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
	void deleteDocumentType(DocumentTypeVO bean);

	
	
	

}
