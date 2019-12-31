package com.smartwf.hm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * 启动类
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.smartwf.hm.*.*.dao"})
@ComponentScan(basePackages = {"com.smartwf"})
public class HealthManApplication {
	public static void main(String[] args) {
		SpringApplication.run(HealthManApplication.class, args);
	}
}
