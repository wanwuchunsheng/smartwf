package com.smartwf.hm.config.mybatis;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
/**
 * @Deprecated mybatis插件配置
 * @author WCH
 * 
 * 
 * */

@SpringBootConfiguration
@EnableTransactionManagement
public class MybatisPlusConfig {
	
	/**
	 * @author WCH
     *   mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }


}
