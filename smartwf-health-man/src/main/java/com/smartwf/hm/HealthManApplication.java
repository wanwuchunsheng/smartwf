package com.smartwf.hm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import tk.mybatis.spring.annotation.MapperScan;

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
