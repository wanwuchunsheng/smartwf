package com.smartwf.proxy.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonException extends RuntimeException {


    /**
	 * 
	 */
	private static final long serialVersionUID = -5866113234261773821L;

	private int code;

    private String msg;

    public CommonException() {
        this.code = 500;
        this.msg = super.getMessage();
    }

    public CommonException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
