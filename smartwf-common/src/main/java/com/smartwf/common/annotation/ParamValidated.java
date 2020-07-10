package com.smartwf.common.annotation;

import javax.validation.groups.Default;
/**
 * 说明：参数统一校验
 * @author WCH
 * 
 */
public class ParamValidated {
	/**
	 * 继承Default类，可以在不指定@Validated的group时，使用所有默认校验方式。
	 */
    public interface Delete extends Default {
    }
    public interface Update extends Default {
    }
    public interface Add extends Default {
    }
    public interface Query extends Default {
    }
    public interface QueryParam extends Default {
    }
}

