package com.zjht.youoil.util;

import com.zjhtc.htm.message.serializable.factory.MessageFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.zjhtc.htm.message.serializable.MessageRequest;
import com.zjhtc.htm.message.serializable.sms.SmsManyMobile;
import com.zjhtc.htm.message.serializable.sms.SmsMobiles;
import com.zjhtc.htm.message.serializable.sms.SmsOnlyMobile;
import com.zjhtc.htm.message.serializable.sms.SmsRequest;
import com.zjhtc.htm.message.serializable.util.RequestUtil;
import com.zjhtc.htm.message.serializable.util.SignUtil;

public class DiySmsMessageFactory implements MessageFactory {
	private static DiySmsMessageFactory factory = null;
	private static SmsRequest smsRequest;
	private static String channel = "YTH";//TODO 必须改为在属性文件中配置或者动态配置读取
	private static String key = "rMRM6FxylkmS6z7DQ5tu";//TODO 必须改为在属性文件中配置或者动态配置读取
	private static String url = "http://172.16.94.7:9616/message/send_v2.htm";//TODO 必须改为在属性文件中配置或者动态配置读取
	private static String content = "http://172.16.94.7:9616/message/send_v2.htm";//TODO 必须改为在属性文件中配置或者动态配置读取
	/**
	 * 配置文件结果保存
	 */
	public static Map<String,String> map = new HashMap<String,String>();

	public static DiySmsMessageFactory getFactory(String channel_param) {
		if((map.get("key_"+channel_param))==null && (map.get("url_"+channel_param))==null && (map.get("content_"+channel_param))==null){
			Properties properties = PropertyUtil.getPropertes("sms",channel_param);
			map.put("key_"+channel_param,properties.getProperty("key"));
			map.put("url_"+channel_param,properties.getProperty("url"));
			map.put("content_"+channel_param,properties.getProperty("mobile.code.content"));
			map.put("channel_"+channel_param,properties.getProperty("channel"));
			key=properties.getProperty("key");
			url=properties.getProperty("url");
			content=properties.getProperty("mobile.code.content");
			channel=properties.getProperty("channel");
		}

		return getFactory(channel, "", "");
	}

	private static DiySmsMessageFactory getFactory(String channel, String corporationCode, String businessName) {
		if (factory == null) {
			factory = new DiySmsMessageFactory();
		}
		smsRequest = new SmsRequest();
		smsRequest.setMethod("sendSms");
		smsRequest.setChannel(channel);
		smsRequest.setCorporationCode(corporationCode);
		smsRequest.setBusinessName(businessName);
		return factory;
	}

	public DiySmsMessageFactory createOnlyMessage(String mobile,String code) {
		SmsOnlyMobile onlySms = new SmsOnlyMobile();
		onlySms.setMobile(mobile);
		onlySms.setContent(content.replace("{code}", code));
		smsRequest.addSms(onlySms);
		return this;
	}

	public DiySmsMessageFactory createManyMessage(List<SmsMobiles> mobilesList, String content) {
		SmsManyMobile manySms = new SmsManyMobile();
		manySms.setContent(content);
		for (int i = 0; i < mobilesList.size(); ++i) {
			SmsMobiles smsMobiles = (SmsMobiles) mobilesList.get(i);
			manySms.addSmsMobiles(smsMobiles);
		}
		smsRequest.addSms(manySms);
		return this;
	}

	public DiySmsMessageFactory createManyMessage(String[] mobileArray, String content) {
		SmsManyMobile manySms = new SmsManyMobile();
		manySms.setContent(content);
		for (int i = 0; i < mobileArray.length; ++i) {
			SmsMobiles smsMobiles = new SmsMobiles(mobileArray[i]);
			manySms.addSmsMobiles(smsMobiles);
		}
		smsRequest.addSms(manySms);
		return this;
	}

	public MessageRequest getMessage() {
		Map<String,Object> signMap = new HashMap<String,Object>();
		signMap.put("method", smsRequest.getMethod());
		signMap.put("channel", smsRequest.getChannel());
		signMap.put("corporationCode", smsRequest.getCorporationCode());
		signMap.put("sign", smsRequest.getSign());
		smsRequest.setSign(SignUtil.computeSign(key, signMap, true));
		return smsRequest;
	}

	public int totalMsg() {
		return ((smsRequest.getSmsList() != null) ? smsRequest.getSmsList().size() : 0);
	}

	public void clear() {
		if (smsRequest.getSmsList() != null)
			smsRequest.getSmsList().clear();
	}

	public String send() {
		String response = RequestUtil.getInstance(url).send(getMessage(), "sendSms");
		System.out.println(url);
		clear();
		return response;
	}

	public String returnContent(String code){
		//return content.replace("{code}", code);
		return content;
	}

}
