package com.smartwf.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * 启动类
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.smartwf.sso.dao"})
@ComponentScan(basePackages = {"com.smartwf"})
public class SsoApplication {

   public static void main(String[] args) {
      SpringApplication.run(SsoApplication.class, args);
   }
}