package com.smartwf.common.utils;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import com.smartwf.common.webservice.SSLClient;
import lombok.extern.log4j.Log4j2;  

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class HttpClientUtil {
	
	public static String doPost(String url,Map<String,String> map,String charset){  
	    HttpClient httpClient = null;  
	    HttpPost httpPost = null;  
	    String result = null;  
	    try{  
	      httpClient = new SSLClient();  
	      httpPost = new HttpPost(url);  
	      //设置参数  
	      List<NameValuePair> list = new ArrayList<NameValuePair>();  
	      Iterator iterator = map.entrySet().iterator();  
	      while(iterator.hasNext()){  
	        Entry<String,String> elem = (Entry<String, String>) iterator.next();  
	        list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
	      }  
	      if(list.size() > 0){  
	        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);  
	        httpPost.setEntity(entity);  
	      }  
	      HttpResponse response = httpClient.execute(httpPost);  
	      if(response != null){  
	        HttpEntity resEntity = response.getEntity();  
	        if(resEntity != null){  
	          result = EntityUtils.toString(resEntity,charset);  
	        }  
	      }  
	    }catch(Exception ex){  
	      ex.printStackTrace();  
	    }  
	    return result;  
	}  
    
    /**
     * 发送 get 请求
     * @param url 请求地址
     * @return 请求结果
     */
    public static String get(String url,Map<String, String> heads) {
        String result = null;
        CloseableHttpResponse response = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            URI uri = builder.build();
            HttpGet httpGet = new HttpGet(uri);
            //请求头
            if(heads!=null){
                Set<String> keySet=heads.keySet();
                for(String s:keySet){
                	httpGet.addHeader(s,heads.get(s));
                }
            }
            // 执行请求
            response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * http post
     * */
    public static String post(String url, String data, Map<String, String> heads){
        HttpClient httpClient= HttpClients.createDefault();
        HttpResponse httpResponse = null;
        String result="";
        HttpPost httpPost=new HttpPost(url);
        if(heads!=null){
            Set<String> keySet=heads.keySet();
            for(String s:keySet){
                httpPost.addHeader(s,heads.get(s));
            }
        }
        try{
            StringEntity s=new StringEntity(data,"utf-8");
            httpPost.setEntity(s);
            httpResponse=httpClient.execute(httpPost);
            HttpEntity httpEntity=httpResponse.getEntity();
            if(httpEntity!=null){
                result= EntityUtils.toString(httpEntity,"utf-8");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return  result;
    }
    
    /**
     * delete
     * @param url delete 的 url
     * @param heads Http Head 参数
     * */
    public static String delete(String url,Map<String,String> heads) throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpDelete httpDelete = new HttpDelete(url);
            if(heads!=null){
                Set<String> keySet=heads.keySet();
                for(String s:keySet){
                    httpDelete.addHeader(s,heads.get(s));
                }
            }
            // Create a custom response handler
            ResponseHandler< String > responseHandler = response -> {
	            int status = response.getStatusLine().getStatusCode();
	            if (status >= 200 && status < 300) {
	                HttpEntity entity = response.getEntity();
	                return entity != null ? EntityUtils.toString(entity) : null;
	            } else {
	                throw new ClientProtocolException("Unexpected response status: " + status);
	            }
            };
            String responseBody = httpclient.execute(httpDelete, responseHandler);
            return responseBody;
        }
    }
    
    /**
     * put 方法
     * @param url put 的 url
     * @param data 数据 application/json 的时候 为json格式
     * @param heads Http Head 参数
     * */
    public  static String put(String url,String data,Map<String,String> heads) throws IOException{
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPut httpPut = new HttpPut(url);
            if(heads!=null){
                Set<String> keySet=heads.keySet();
                for(String s:keySet){
                    httpPut.addHeader(s,heads.get(s));
                }
            } 
            StringEntity stringEntity = new StringEntity(data);
            httpPut.setEntity(stringEntity);
            // Create a custom response handler
            ResponseHandler < String > responseHandler = response -> {
	            int status = response.getStatusLine().getStatusCode();
	            if (status >= 200 && status < 300) {
	                HttpEntity entity = response.getEntity();
	                return entity != null ? EntityUtils.toString(entity) : null;
	            } else {
	                throw new ClientProtocolException("Unexpected response status: " + status);
	            }
            };
            String responseBody = httpclient.execute(httpPut, responseHandler);
            return  responseBody;
        }
    }
    /**
     * patch 方法
     * @param url patch 的 url
     * @param data 数据 application/json 的时候 为json格式
     * @param heads Http Head 参数
     * */
    public static String patch(String url, String data, Map<String,String> heads ) throws IOException{  
    	try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPatch httpPatch = new HttpPatch(url);
            if(heads!=null){
                Set<String> keySet=heads.keySet();
                for(String s:keySet){
                	httpPatch.addHeader(s,heads.get(s));
                }
            } 
            StringEntity stringEntity = new StringEntity(data);
            httpPatch.setEntity(stringEntity);
            // Create a custom response handler
            ResponseHandler < String > responseHandler = response -> {
	            int status = response.getStatusLine().getStatusCode();
	            if (status >= 200 && status < 300) {
	                HttpEntity entity = response.getEntity();
	                return entity != null ? EntityUtils.toString(entity) : null;
	            } else {
	                throw new ClientProtocolException("Unexpected response status: " + status);
	            }
            };
            String responseBody = httpclient.execute(httpPatch, responseHandler);
            return  responseBody;
        }
    }
   
}
