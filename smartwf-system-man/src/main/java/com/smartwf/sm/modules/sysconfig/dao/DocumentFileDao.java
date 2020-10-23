package com.smartwf.sm.modules.sysconfig.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.sm.modules.sysconfig.pojo.DocumentType;
import com.smartwf.sm.modules.sysconfig.vo.DocumentTypeVO;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 久层接口
 * @author WCH
 */
@Repository
public interface DocumentFileDao extends BaseMapper<DocumentType> {

	/**
	 * @Description: 查询文档文件配置分页
	 * @param bean
	 * @return
	 */
	List<DocumentType> selectDocumentTypeByPage(Page<DocumentType> page, @Param("bean") DocumentTypeVO bean);

	/**
     * @Description: 主键查询文档文件配置
     * @param bean
     * @return
     */
	DocumentType selectDocumentTypeById(@Param("bean") DocumentType bean);

	/**
     * @Description： 删除文档文件配置
     * @param id 单个删除
     * @param ids 批量删除，逗号拼接
     * @return
     */
	void deleteDocumentTypeByIds(@Param("list") List<String> list);

	

    
}
