package com.smartwf.hm.config.mvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.smartwf.hm.interceptor.LoginInterceptor;

import lombok.extern.slf4j.Slf4j;

/**
 * @author WCH
 * @Description: springMVC配置
 */
@SpringBootConfiguration
@Slf4j
public class MySpringMvcConfig implements WebMvcConfigurer {


    @Autowired
    private LoginInterceptor loginInterceptor;


    /**
     * 添加自定义拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                
                .excludePathPatterns("/login")
                .excludePathPatterns("/app/login")
                .excludePathPatterns("/logout")
                .excludePathPatterns("/druid/**")
                .excludePathPatterns("/user/selectByUsername")
                // swagger2页面
                .excludePathPatterns("/swagger-resources/**","/images/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/error/")
                // rpc注册中心
                .excludePathPatterns("/rpc/**")
                .addPathPatterns("/**");
    }


    /**
     * 请求参数时间格式化
     *
     * @return
     */
    @Bean
    public Converter<String, Date> addNewConvert() {

        return new Converter<String, Date>() {

            @Override
            public Date convert(String source) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = null;
                try {
                    if (StringUtils.isNotBlank(source)) {
                        date = sdf.parse(source);
                    }
                } catch (ParseException e) {
                    log.error("字符串转时间格式化错误：{}", e.getMessage(), e);
                }
                return date;
            }
        };
    }

}
