package com.zjht.youoil.util.payquery;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("deprecation")
public class HttpsRequester {
	private static final Logger log = LoggerFactory.getLogger(HttpsRequester.class);
	public String doPost(String url, Map<String, String> map, String charset) {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			// 请求超时
			httpPost.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 45000);
			// 读取超时
			httpPost.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 45000);
			// 设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> elem = (Entry<String, String>) iterator.next();
				list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
			}
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
				httpPost.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			if (httpPost != null) {
				httpPost.releaseConnection();
			}			
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
		return result;
	}

	public String doGet(String url, Map<String, String> map, String charset) {
		HttpClient httpClient = null;
		HttpGet httpGet = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			StringBuilder sb = new StringBuilder();
			if (map != null) {
				for (String varName : map.keySet()) {
					sb = sb.length() > 0 ? sb.append("&" + URLEncoder.encode(varName, charset) + "="
							+ URLEncoder.encode(map.get(varName).toString(), charset)) : sb.append(URLEncoder.encode(
							varName, charset) + "=" + URLEncoder.encode(map.get(varName).toString(), charset));
				}
			}
			if (StringHelper.isNotBlank(sb.toString())) {
				url = url + "?" + sb.toString();
			}
			httpGet = new HttpGet(url);
			// 请求超时
			httpGet.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 45000);
			// 读取超时
			httpGet.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 45000);

			HttpResponse response = httpClient.execute(httpGet);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			if (httpGet != null) {
				httpGet.releaseConnection();
			}				
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
		return result;
	}

}
