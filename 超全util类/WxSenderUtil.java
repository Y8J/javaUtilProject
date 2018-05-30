package com.zjht.youoil.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.zjht.youoil.entity.WxOpenid;
import com.zjht.youoil.manager.WxOpenidMng;
import com.zjht.youoil.util.weixin.MyX509TrustManager;
import com.zjht.youoil.util.weixin.TemplateData;
import com.zjht.youoil.util.weixin.WxTemplate;

/**
 * ClassName: WxSenderUtil
 * @Description:  微信工具类
 * @author zhj
 * @date 2015-12-28
 */
/**
 * ClassName: WxSenderUtil
 * @Description: TODO
 * @author zhj
 * @date 2016-1-4
 */
/**
 * ClassName: WxSenderUtil
 * @Description: TODO
 * @author zhj
 * @date 2016-1-4
 */
public class WxSenderUtil {
	
//	private String template_id = "0Qnxe8cyxHvdjdV8bL76rNa7bgR3KKgVMtp0uvAEQU4";
	/**
	 * @Fields template_id : 缺省短信模板
	 */
	private String template_id = "lyXOUCMWWlEIRUt717UCwuLhQQ0Uqgj9Qx0-CkLkgos";
	
	public static final Logger log = LoggerFactory.getLogger(WxSenderUtil.class);
	
	@Autowired
	private  WxOpenidMng wxOpenidMng ;

	
	/**
	 * @Description: 发送请求
	 * @param @param requestUrl
	 * @param @param requestMethod
	 * @param @param outputStr
	 * @param @return
	 * @return JSONObject
	 * @throws
	 * @author zhj
	 * @date 2015-12-28
	 */
	public  JSONObject httpRequest(String requestUrl,String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.parseObject(buffer.toString());
		} catch (ConnectException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	/**
	 * 发送微信(默认模板)
	 * 测试用
	 */
	public  boolean sendWX(String mobile) {
		Map<String,TemplateData> msgData = Maps.newHashMap();
		TemplateData first = new TemplateData();
		first.setValue("恭喜亲！您的“油我发起”加油单已经成功发起，请等待油站接单通知，感谢您的支持和参与！");
		msgData.put("first", first);
		TemplateData keyword1 = new TemplateData();
		keyword1.setValue("油品：92#");
		msgData.put("keyword1", keyword1);
		TemplateData keyword2 = new TemplateData();
		keyword2.setValue("订单金额1500元");
		msgData.put("keyword2", keyword2);
		TemplateData keyword3 = new TemplateData();
		keyword3.setValue("支付金额1425元");
		msgData.put("keyword3", keyword3);
		TemplateData keyword4 = new TemplateData();
		keyword4.setValue("优惠5%");
		msgData.put("keyword4", keyword4);
		TemplateData remark = new TemplateData();
		remark.setValue("详询4006630666或登陆“油我发起”查询。-中经汇通");
		msgData.put("remark", remark);
		return sendWX(mobile,WxMsgType.CUSTOMER_REQUEST,msgData);
	}
	
	
	/**
	 * 发送微信
	 */
	public  boolean sendWX(String mobile, WxMsgType wxMsgType, Map<String, TemplateData> msgData) {
		String access_token =wxOpenidMng.getAccessToken();
		WxOpenid wxOpenid = wxOpenidMng.findByUserName(StringUtils.isNotBlank(mobile) ? mobile : "");
		WxTemplate t = new WxTemplate();
		//		t.setUrl("http://www.youoil.cn/webapp/login.do");
		if(wxOpenid == null){
			log.error("【微信工具类】：未获得授权！mobile:"+mobile);
			return false;
		}
		if(!StringUtils.isNotBlank(wxOpenid.getOpenid())){
			log.error("【微信工具类】：获取openId失败！mobile:"+mobile);
			return false;
		}
		if(!isAttention(access_token,wxOpenid.getOpenid())){
			log.error("【微信工具类】：没有关注公众号！mobile:"+mobile);
			return false;
		}
		t.setTouser(wxOpenid.getOpenid());
		t.setTopcolor("#000000");
		t.setTemplate_id(wxMsgType!=null ? wxMsgType.getId() : this.template_id );
		t.setData(msgData);
		JSONObject jsonObj = httpRequest("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token, "POST", JSONObject.toJSONString(t).toString());
		System.out.println("---" + jsonObj);
		if(!jsonObj.getString("errcode").equals("0")){
			log.error("【微信工具类】："+jsonObj.getString("errmsg")+"mobile:"+mobile);
			return false;
		}
		log.info("【微信工具类】:发送成功 ！mobile:"+mobile);
		return true;
	}
	
	/**
	 * 组装数据（格式如下）
	 * 
	 * first
	 * 	订单编号：keyword1
	 * 	油品：keyword2
	 * 	订单金额：keyword3
	 * 	支付金额：keyword4
	 *  优惠折扣：keyword5
	 * remark
	 */
	public Map<String,TemplateData> getMsgData(String first , String keyword1, String keyword2, String keyword3, String keyword4, String keyword5, String remark){
		Map<String,TemplateData> msgData = Maps.newHashMap();
		TemplateData firstData = new TemplateData();
		firstData.setValue(first);
		msgData.put("first", firstData);
		TemplateData keyword1Data = new TemplateData();
		keyword1Data.setValue(keyword1);
		msgData.put("keyword1", keyword1Data);
		TemplateData keyword2Data = new TemplateData();
		keyword2Data.setValue(keyword2);
		msgData.put("keyword2", keyword2Data);
		TemplateData keyword3Data = new TemplateData();
		keyword3Data.setValue(keyword3);
		msgData.put("keyword3", keyword3Data);
		TemplateData keyword4Data = new TemplateData();
		keyword4Data.setValue(keyword4);
		msgData.put("keyword4", keyword4Data);
		TemplateData keyword5Data = new TemplateData();
		keyword5Data.setValue(keyword5);
		msgData.put("keyword5", keyword5Data);
		TemplateData remarkData = new TemplateData();
		remarkData.setValue(remark);
		msgData.put("remark", remarkData);
		return msgData;
	}
	
	/**
	 * 判断是否关注微信公众号
	 * 
	 */
	public boolean isAttention(String mobile){
		String access_token =wxOpenidMng.getAccessToken();
		WxOpenid wxOpenid = wxOpenidMng.findByUserName(StringUtils.isNotBlank(mobile) ? mobile : "");
		JSONObject jsonObject = httpRequest("https://api.weixin.qq.com/cgi-bin/user/info?access_token="+access_token+"&openid="+wxOpenid.getOpenid()+"&lang=zh_CN", "POST", "");
		String subscribe = jsonObject.getString("subscribe");
		if(subscribe != null){
			return !subscribe.equals("0");
		}
		log.error(jsonObject.getString("errmsg"));
		return false;
	}
	
	/**
	 * 判断是否关注微信公众号
	 */
	public boolean isAttention(String accessToken, String openId){
		JSONObject jsonObject = httpRequest("https://api.weixin.qq.com/cgi-bin/user/info?access_token="+accessToken+"&openid="+openId+"&lang=zh_CN", "POST", "");
		String subscribe = jsonObject.getString("subscribe");
		if(subscribe != null){
			return !subscribe.equals("0");
		}
		log.error(jsonObject.getString("errMsg"));
		return false;
	}
	
	
	
	/**
	 * 判断是否微信浏览器打开发送请求；   
	 */
	public boolean isWXBrowser(HttpRequester request){
		//判断是否为微信浏览器
		String ua = ((HttpServletRequest) request).getHeader("user-agent")
		          .toLowerCase();        				
	    return ua.indexOf("micromessenger") > 0;
	}
	
	
	/**
	 * ClassName: WxMsgType
	 * @Description: 类型关联模板id
	 * @author zhj
	 * @date 2015-12-30
	 */
	public static enum WxMsgType{
		/*
		 * 发起需求消息
		 */
		 CUSTOMER_REQUEST("lyXOUCMWWlEIRUt717UCwuLhQQ0Uqgj9Qx0-CkLkgos"),
		 /*
		  * 应答成功客户消息
		  */
		 CUSTOMER_RESPONSE("7PhakkBtcpeGHSQPrtLpEkskCby2uRmuTuKbvA82Gts"),
		 /*
		  * 油站抢单消息
		  */
		 OIL_STATION_REQUEST("c7uqgL-eqDcW4ycpjw1MMyGgwLZdKqS2-1KLYCF6SmQ"),
		 /*
		  * 油站抢单回执消息
		  */
		 OIL_STATION_RESPONSE("UrjmR7-snyw8b7ziEAfes7AFnc_tGRc9L2bpq0h5_bY"),
		 /*
		  * 无应答消息
		  */
		 NO_REPLY("7PhakkBtcpeGHSQPrtLpEkskCby2uRmuTuKbvA82Gts"),
		 /*
		  *退款消息 
		  */
		 REFUND("em7w3ykbk5HTejPrFU5txKGahKhZ4LyR6gKJ4W79hJY");
		 WxMsgType(String id){
			 this.id = id;
		 }
		 
		 private String id;

		public String getId() {
			return id;
		}
		 
	 }
	
	public static enum STATUS{
		STATUS_SUCCESS("应答成功"),
		STATUS_FAIL("应答失败"),
		STATUS_FAIL_REFUND("应答失败退款中"),
		STATUS_STATION_ACCEPT_SUCCESS("接单成功"),
		STATUS_STATION_SNATCH("等待抢单");
		STATUS(String value){
			this.value = value;
		}
		String value ;
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
	
	
	
	/**
	 * 
	 * @Description: 获取模板id
	 * @param @param templateType
	 * @param @return
	 * @return String
	 * @throws
	 * @author zhj
	 * @date 2015-12-30
	 */
	public String getTemplateId(int templateType){
		switch(templateType){
		case 1 : return "" ; 
		case 2 : return "" ; 
		case 3 : return "" ; 
		case 4 : return "" ; 
		case 5 : return "" ; 
		case 6 : return "" ;
		default : return "";
		}
		
	}
	
}
