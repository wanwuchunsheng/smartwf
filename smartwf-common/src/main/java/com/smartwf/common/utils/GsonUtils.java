package com.smartwf.common.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
/**
 * @author WCH
 * 
 * */
public class GsonUtils { 

	/**
	 * 定义gson对象
	 * 
	 * */ 
    private static final Gson GSON = new Gson();

    /**
     * 将对象转换成json字符串。
     * <p>Title: pojoToJson</p>
     * <p>Description: </p>
     * @param data
     * @return
     */
    public static String objectToJson(Object data) {
        try {
        	String gsonStr = GSON.toJson(data);
        	return gsonStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }
    
    /**
     * 将json字符串转换成对象
     * 
     * @param jsonData json数据
     * @param clazz 对象中的object类型
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
    		T t = GSON.fromJson(jsonData, beanType);
            return t;
        } catch (Exception e) {
        	 e.printStackTrace();
        }
        return null;
    }
    
    
    /**
     * 将json(字符串）转化为map
     * @param jsonStr json字符串
     */
    public static Map<String, Object> jsonToMap(String jsonStr){
    	try {
        	Map<String, Object> map = GSON.fromJson(jsonStr, new TypeToken<Map<String, Object>>() {}.getType());
        	return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
        
    }

    /**
     * json字符串转成list
     * 
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> jsonToList(String gsonString, Class<T> cls) {
    	try {
    		//根据泛型返回解析指定的类型,TypeToken<List<T>>{}.getType()获取返回类型
            List<T> list = GSON.fromJson(gsonString, new TypeToken<List<T>>() {}.getType());
            return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null; 
    }
    
    /**
     * 方法二：
     *    将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T> List<T> jsonToList2(String jsonData, Class<T> beanType) {
        try {
        	List<T> list = new ArrayList<T>();
            JsonArray array = new JsonParser().parse(jsonData).getAsJsonArray();
            for(final JsonElement elem : array){
                list.add(GSON.fromJson(elem, beanType));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json字符串转成list中有map的
     * 
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> jsonToListMaps(String gsonString) {
    	try {
    		List<Map<String, T>> list = GSON.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {}.getType());
            return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }
    
    

}
