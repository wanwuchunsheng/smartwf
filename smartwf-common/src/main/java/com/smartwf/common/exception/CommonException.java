package com.smartwf.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义异常
 * @author WCH
 */
@Getter
@Setter
public class CommonException extends RuntimeException {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int code;

    private String msg;

    public CommonException() {
        this.code = 500;
        this.msg = super.getMessage();
    }

    public CommonException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}