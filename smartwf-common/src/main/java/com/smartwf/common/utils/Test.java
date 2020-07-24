package com.smartwf.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.MapUtils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

public class Test {
	
	public static void main(String[] args) {
		Map<String,String> maps= new HashMap<>();
		maps.put("aaa", "1111");
		maps.put("bbb", "2222");
		maps.put("ccc", "3333");
		maps.put("ddd", "4444");
		
		
		String str=JSONUtil.toJsonStr(maps);
		System.out.println(str);
		Map<String, Object> map=JSONUtil.parseObj(str);//.toBean(str, Map.class);Convert.
		for(  Entry<String, Object>   m : map.entrySet()) {
			System.out.println(m.getKey()+"     "+m.getValue());
		}
	}

}
