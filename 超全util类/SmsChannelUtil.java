package com.zjht.youoil.util;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SmsChannelUtil {

	private static Logger logger = LoggerFactory.getLogger(SmsChannelUtil.class);
	/**
	 * 发送成功结果
	 */
	public static final String SUCCESS="success";



	/**
	 * 发送短信2.0
	 * 发送成功返回：success 请使用SmsSenderUtil.SUCCESS.equals(result)确定短信是否发送正常
	 * 发送错误放回错误详情：xxxxx
	 * @param mobile 手机号
	 * @param content 短信内容
	 * @return result ==>正常发送：success 失败：返回错误详情
	 */
	public static String send(String mobile,String channel,String code){
		DiySmsMessageFactory factory = DiySmsMessageFactory.getFactory(channel);
        factory.createOnlyMessage(mobile,code);//单个手机号短信发送
        String respMsg = factory.send();
        //logger.info("给手机"+mobile+",发送短信:" + content +"，发送结果==>"+respMsg);
        try {
            JSONObject respMsgJson=new JSONObject(respMsg);
            if ("00000".equals(respMsgJson.optString("stateCode"))&&respMsgJson.optJSONArray("smsList").length()>0&&"00000".equals(respMsgJson.optJSONArray("smsList").optJSONObject(0).optString("respCode"))) {
                return factory.returnContent(code);
            }else{
                String m=respMsgJson.optString("stateCodeDesc");
                if (respMsgJson.optJSONArray("smsList").length()>0&&!"00000".equals(respMsgJson.optJSONArray("smsList").optJSONObject(0).optString("respCode"))) {
                    m=respMsgJson.optJSONArray("smsList").optJSONObject(0).optString("respMsg");
                }
                return StringUtils.isNotBlank(m)?m:"发送失败！";
            }
        } catch (Exception e) {
            return "发送短信结果无法解析！";
        }
	}
	public static void main(String[] args) {
        //String result=SmsSenderUtil.send("13538852364", "您的有油网的短信验证码为 123456 ，感谢您的支持。");
		//String result=SmsChannelUtil.send("13538852364", "您的汇通宝短信验证码为 123456  感谢您的支持。","");
		String result=SmsChannelUtil.send("13538852364","",CodeBuilder.sixNO());
        System.out.println(SmsChannelUtil.SUCCESS.equals(result));
    }
}
