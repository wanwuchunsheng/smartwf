package com.smartwf.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.smartwf.common.constant.Constants;
import com.smartwf.common.pojo.User;
import com.smartwf.common.thread.UserThreadLocal;
import com.smartwf.common.webservice.SslClient;

import cn.hutool.http.useragent.Browser;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import lombok.extern.log4j.Log4j2;
/**
 * @author WCH
 * 
 * */
@Log4j2
public class HttpClientUtil {
	
	public static String doPost(String url,Map<String,String> map,String charset){  
	    HttpClient httpClient = null;  
	    HttpPost httpPost = null;  
	    String result = null;  
	    try{  
	      httpClient = new SslClient();  
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
            if (response.getStatusLine().getStatusCode() == Constants.EQU_SUCCESS) {
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
	            if (status >= Constants.EQU_SUCCESS && status < Constants.MULTIPLE_CHOICES) {
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
	            if (status >= Constants.EQU_SUCCESS && status < Constants.MULTIPLE_CHOICES) {
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
	            if (status >= Constants.EQU_SUCCESS && status < Constants.MULTIPLE_CHOICES) {
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
    
    
	/**
	 * 使用SOAP {修正}
	 *   请求wso2发送消息
	 * @param postUrl
	 * @param soapXml
	 * @param soapAction
	 * @return
	 */
	public static String doPostSoap(String postUrl, String soapXml,String soapAction) {
		User user=UserThreadLocal.getUser();
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		HttpPost httpPost = new HttpPost(postUrl);
        //  设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(Constants.SOCKET_TIME_OUT).setConnectTimeout(Constants.CONNECT_TIME_OUT).build();
		httpPost.setConfig(requestConfig);
		try {
			httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");
			httpPost.setHeader("SOAPAction", soapAction);
			StringBuffer sb=new StringBuffer();
			sb.append(user.getTenantCode()).append("@").append(user.getTenantDomain()).append(":").append(user.getTenantPw());
			httpPost.setHeader(new BasicHeader("Authorization","Basic " + Base64.encodeBase64String(sb.toString().getBytes())));
			StringEntity data = new StringEntity(soapXml, Charset.forName("UTF-8"));
			httpPost.setEntity(data);
			CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null) {
				// 打印响应内容
				String retStr = EntityUtils.toString(httpEntity, "UTF-8");
				log.info("doPostSoap callback："+retStr);
				return retStr;
			}
		} catch (Exception e) {
			log.error("exception in doPostSoap", e);
		}finally {
			// 释放资源
			try {
				closeableHttpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	/**
	 * 说明 api鉴权
	 *  
	 * @param postUrl
	 * @param soapXml
	 * @param soapAction
	 * @return
	 */
	public static String doPostApiEntitlement(String postUrl, String soapXml,User user) {
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		HttpPost httpPost = new HttpPost(postUrl);
        // 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(Constants.SOCKET_TIME_OUT).setConnectTimeout(Constants.CONNECT_TIME_OUT).build();
		httpPost.setConfig(requestConfig);
		try {
			httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");
			httpPost.setHeader("SOAPAction", null);
			StringBuffer sb=new StringBuffer();
			sb.append(user.getTenantCode()).append("@").append(user.getTenantDomain()).append(":").append(user.getTenantPw());
			httpPost.setHeader(new BasicHeader("Authorization","Basic " + Base64.encodeBase64String(sb.toString().getBytes())));
			StringEntity data = new StringEntity(soapXml, Charset.forName("UTF-8"));
			httpPost.setEntity(data);
			CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null) {
				// 打印响应内容
				String retStr = EntityUtils.toString(httpEntity, "UTF-8");
				log.info("response:" + retStr);
				return retStr;
			}
		} catch (Exception e) {
			log.error("exception in doPostApiEntitlement", e);
		}finally {
			// 释放资源
			try {
				closeableHttpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	/**
	 * 说明：wso2 accessToken 过期验证
	 * @throws Exception 
	 * 
	 * */
	public static String expVerification(String postUrl,String basic) {
		InputStream input = null;
		BufferedReader br =null;
		try {
			HttpClient httpclient = new SslClient();
	        HttpPost httpget = new HttpPost(postUrl);
	        httpget.setHeader("Authorization", basic);
	        HttpResponse response = httpclient.execute(httpget);
	        StatusLine statusLine = response.getStatusLine();
	        int responseCode = statusLine.getStatusCode();
	        if (responseCode == Constants.EQU_SUCCESS) {
	            HttpEntity entity = response.getEntity();
	            input = entity.getContent();
	            br = new BufferedReader(new InputStreamReader(input));
	            String str = br.readLine();
	            //String result = new String(str1.getBytes("gbk"), "utf-8");
	            log.info("AccessToken验证返回："+str);
	            return str;
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//安全关流
			if(input!=null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(br !=null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
        return null;
	}

	
	/**
	 * 获取浏览器信息
	 * @param request HttpServletRequest
	 * @return  String
	 * @throws Exception
	 */
	public static String getBrowserInfo(HttpServletRequest request) throws Exception {
		String au=request.getHeader("User-Agent");
		if(au.equalsIgnoreCase("Android") || au.equalsIgnoreCase("Ios") || au.equalsIgnoreCase("iPad")) {
			return "app";
		}
		UserAgent userAgent = UserAgentUtil.parse(au);
		return userAgent.getBrowser().getName();
	}
	
	/**
	 * 获取浏览器信息
	 * @param request HttpServletRequest
	 * @return  String
	 * @throws Exception
	 */
	public static String getDeviceName(HttpServletRequest request) throws Exception{
		String au=request.getHeader("User-Agent");
		if(au.equalsIgnoreCase("Android")) {
			return "Android";
		}
		if(au.equalsIgnoreCase("Ios")) {
			return "iPhone";
		}
		if(au.equalsIgnoreCase("iPad")) {
			return "iPad";
		}
		UserAgent userAgent = UserAgentUtil.parse(au);
		return userAgent.getPlatform().getName();
	}
}
