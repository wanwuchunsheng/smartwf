package com.smartwf.common.pojo;

import lombok.Data;

/**
 * @author WCH
 * @Description: webservice返回实体类
 */
@Data
public class WsResult {

    public WsResult(int code, boolean data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    private Integer code;

    private Boolean data;

    private String msg;

}
