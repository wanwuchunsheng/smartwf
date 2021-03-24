package com.smartwf.hm.config.exception;


import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.apache.http.HttpStatus;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.smartwf.common.pojo.Result;

/**
 * 说明：校验统一 参数校验处理异常
 *        如果校验不通过，就在异常中处理，统一输出格式
 * @author WCH
 * @datetime 2021-3-13 18:14:22
 **/
@RestControllerAdvice
public class RequestValidateExceptionHandle {
	
	
    /**
     * 处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<?>> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return ResponseEntity.status(HttpStatus.SC_OK).body(Result.data(HttpStatus.SC_BAD_REQUEST,message));
    }
 
    /**
     * 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseEntity<Result<?>> BindExceptionHandler(BindException e) {
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return ResponseEntity.status(HttpStatus.SC_OK).body(Result.data(HttpStatus.SC_BAD_REQUEST,message));
    }
 
    /**
     * 处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Result<?>> ConstraintViolationExceptionHandler(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        return ResponseEntity.status(HttpStatus.SC_OK).body(Result.data(HttpStatus.SC_BAD_REQUEST,message));
    }

    /**
     * 说明：统一参数验证异常
     *   参数不合法马上返回，无需全局检验完成再返回
   
    @Bean
    public javax.validation.Validator validator(){
        ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
                .configure()
                .addProperty( "hibernate.validator.fail_fast", "true" )
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }
     * */
    
}
