package com.zjht.youoil.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zjht.youoil.entity.PaymentInfoCommon;

public class PayCommonUtils {
	private static final Logger log = LoggerFactory.getLogger(PayCommonUtils.class);
	/**
	 * 生成一个新的存储键名（支付配置code+"_"+当前时间（HHmmssSSS））
	 * @param code 支付配置代码
	 * @return
	 */
	public static String getNewKeyName(String code){
	   return code+"_"+DateTimeUtils.getCurrentDateStrFormat("HHmmssSSS")+DateTimeUtils.getRandomCode(3);
	}
	/**
	 * 准备session支付信息
	 * @param request
	 * @param paymentInfo 支付信息对象
	 * @param keyName session储存键名
	 * @return
	 */
	public static boolean preparePayData(HttpServletRequest request, PaymentInfoCommon paymentInfo,String keyName) {
	    boolean readyFlag=false;
		// 初始化支付凭证
		readyFlag=paymentInfo.readySignContent();
		if (readyFlag) {
		    // 保存到支付数据到SESSION
	        RequestUtils.addSessionItem(request, keyName, paymentInfo);
	        log.info(String.format("支付信息准备：{orderNo:'%1$s',amount:%2$s,extend1:'%3$s',timestamp:'%4$s',openId:'%5$s',body:'%6$s',mobile:'%7$s',extend2:'%8$s'}", paymentInfo.getOrderNo(), paymentInfo.getAmount(), paymentInfo.getExtend1(), paymentInfo.getOrderTime(),paymentInfo.getOpenid(),paymentInfo.getBody(),paymentInfo.getMobile(),paymentInfo.getExtend2()));
        }
		return readyFlag;
	}
	/**
	 * 清除session支付信息
	 * @param request
	 */
	public static void clearPayData(HttpServletRequest request,String keyName) {
        if (RequestUtils.getSessionItem(request, keyName) != null) {
            RequestUtils.removeSessionItem(request, keyName);
        }
	}
	/**
	 * session保存支付回调信息
	 * @param request
	 * @param paymentInfo
	 * @param keyName
	 * @return token 返回UrlEncode（Base64(keyName)）
	 * @throws UnsupportedEncodingException: URL encode exception
	 */
	public static String preparePayCallBackData(HttpServletRequest request, PaymentInfoCommon paymentInfo,String keyName) throws UnsupportedEncodingException{
	    paymentInfo.readySignContent();
	    RequestUtils.addSessionItem(request, keyName, paymentInfo);
	    return URLEncoder.encode(Base64Helper.encodeToString(keyName), "UTF-8");
	}
	/**
	 * 清除支付回调信息
	 * @param request
	 * @param keyName
	 */
	public static void clearCallBackData(HttpServletRequest request,String keyName){
	    if (RequestUtils.getSessionItem(request, keyName) != null) {
	        RequestUtils.removeSessionItem(request, keyName);
        }
	}
	/**
	 * 验证支付用到数据
	 * 
	 * @param orderNo
	 * @param handler
	 * @param amount
	 * @param code
	 * @return
	 */
	public static boolean validate(final PaymentInfoCommon paymentInfo, final String code) {
		if (StringUtils.isBlank(paymentInfo.getOrderNo())) {
			return false;
		}
		if (StringUtils.isBlank(paymentInfo.getExtend1())) {
			return false;
		}
		if (StringUtils.isBlank("" + paymentInfo.getAmount())) {
			return false;
		}
		if (StringUtils.isBlank(paymentInfo.getOrderTime())) {
			return false;
		}
		if (StringUtils.isBlank(code)) {
			return false;
		}
		String newCode = CodeBuilder.encryptMD5(paymentInfo.getOrderNo() + paymentInfo.getExtend1() + (int) (Double.valueOf(paymentInfo.getAmount()) * 100)
				+ paymentInfo.getOrderTime());

		if (code.equals(newCode)) {
			return true;
		}
		return false;
	}	
}
