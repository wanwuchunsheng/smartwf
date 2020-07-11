package com.smartwf.sm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * 启动类
 * @author WCH
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.smartwf.sm.*.*.dao"})
@ComponentScan(basePackages = {"com.smartwf"})
public class SystemManApplication {
	public static void main(String[] args) {
		SpringApplication.run(SystemManApplication.class, args);
	}
}
