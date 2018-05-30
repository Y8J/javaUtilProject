package com.zjht.youoil.util.payquery;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**  
 * HTTP请求对象 
 */  
public class HttpRequester {   
    
    private static final Logger log = LoggerFactory.getLogger(HttpRequester.class);
    
    private static final int timeout = 45000;
    
    public String sendPost(String url,Map<String, String> params){
    	return send(url,"POST",params);
    }
    
    public String sendGet(String url,Map<String, String> params){
    	return send(url,"GET",params);
    }
    
    private String send(String url,String method,Map<String, String> params){
    	StringBuffer temp = new StringBuffer();
		HttpMethodBase httpMethod = null;
		try {
			HttpClient httpClient=new HttpClient();
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(timeout);
			if("GET".equals(method)){
				StringBuilder sb = new StringBuilder();
				if(params!=null){
					for (String varName : params.keySet()) {
						sb = sb.length() > 0 ? sb.append("&" + URLEncoder.encode(varName,"UTF-8") + "=" + URLEncoder.encode(params.get(varName).toString(),"UTF-8")) :
							sb.append(URLEncoder.encode(varName, "UTF-8") + "=" + URLEncoder.encode(params.get(varName).toString(), "UTF-8"));
					}
				}
				if(StringHelper.isNotBlank(sb.toString())){
					url=url+"?" + sb.toString();
				}
				httpMethod = new GetMethod(url);
			}else{
				UTF8PostMethod postMethod = new UTF8PostMethod(url);
				if(params!=null){
					for (String varName : params.keySet()) {
						//postMethod.setParameter(varName, params.get(varName));
						String value = StringHelper.NullToVoid(params.get(varName));
						postMethod.setParameter(varName, value);
					}
				}
				httpMethod=postMethod;
			}
			log.debug(httpMethod.getURI().toString());
			int code = httpClient.executeMethod(httpMethod);
			if (code != HttpStatus.SC_OK) {
				throw new RuntimeException("服务器错误：" + code);
			}
			InputStream in = httpMethod.getResponseBodyAsStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			temp = new StringBuffer();
			String line = bufferedReader.readLine();
			while (line != null) {
				temp.append(line).append("\r\n");
				line = bufferedReader.readLine();
			}
			bufferedReader.close();

		} 
		catch(java.net.SocketTimeoutException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			httpMethod.releaseConnection();
		}
		return temp.toString();
    }
   
}  
