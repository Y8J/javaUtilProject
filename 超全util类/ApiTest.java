package com.zjht.youoil.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.zjht.youoil.service.ApiConfig;

//import com.zjht.youoil.service.ApiConfig;

public class ApiTest {
	public void run()throws JSONException{
//		Properties prop=PropertyUtil.getPropertes("esb","api");
//		String appNo=prop.getProperty(ApiConfig.APPNO_KEY);
//		String key=prop.getProperty(ApiConfig.APP_KEY);
//		String service= prop.getProperty(ApiConfig.SERVICE_QUERYALLCOUPONS);
//		String ver= prop.getProperty(ApiConfig.VER_KEY);
//		String url= prop.getProperty(ApiConfig.APPURL_KEY);
		
		
		String appNo="ZJ206180";
		String key="Ja6M49/Ba54smLB5ttoZ6dpdx3mYOJuu";
		String service="trade.queryAllCoupons";
		String ver= "1.0";
		String url= "http://172.16.111.104:9060/zjhtplatform/";
		JSONObject contentJson=new JSONObject();
		contentJson.put("pageSize", 10);
		contentJson.put("pageIndex", 1);
		
		JSONObject json=new JSONObject();
		json.put("content", contentJson);
		json.put("ver", ver);
		json.put("service", service);
		json.put("appNo", appNo);
		String strSrcBase=key+json.toString()+key;
		Map<String,String> map=new HashMap<String, String>();
		map.put("appNo", appNo);
		map.put("service", service);
		map.put("ver", ver);
		map.put("msg", json.toString());
		map.put("sign", MessageDigestHelper.encode("SHA-1", strSrcBase, null));
		String strUrl=url+service+"/"+ver+"/";

		try {
			//Thread.sleep(3000);
			System.out.println("=============正在获取商品列表...==============");
			System.err.println(HttpUtils.URLPost(strUrl, map));
			Thread.sleep(3000);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		service="trade.queryAllCategories";
		contentJson=new JSONObject();
		contentJson.put("shopCode", "10086020");
		json=new JSONObject();
		json.put("content", contentJson);
		json.put("ver", ver);
		json.put("service", service);
		json.put("appNo", appNo);
		strSrcBase=key+json.toString()+key;
		strUrl=url+service+"/"+ver+"/";
		map=new HashMap<String, String>();
		map.put("appNo", appNo);
		map.put("service", service);
		map.put("ver", ver);
		map.put("msg", json.toString());
		map.put("sign", MessageDigestHelper.encode("SHA-1", strSrcBase, null));
		try {
			System.out.println("=============正在获取所有商品目录...==============");
			String result=HttpUtils.URLPost(strUrl, map);
			System.err.println(result);
			Thread.sleep(3000);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
//		service="trade.queryShop";
//		contentJson=new JSONObject();
//		json=new JSONObject();
//		json.put("content", contentJson);
//		json.put("ver", ver);
//		json.put("service", service);
//		json.put("appNo", appNo);
//		strSrcBase=key+json.toString()+key;
//		strUrl=url+service+"/"+ver+"/";
//		map=new HashMap<String, String>();
//		map.put("appNo", appNo);
//		map.put("service", service);
//		map.put("ver", ver);
//		map.put("msg", json.toString());
//		map.put("sign", MessageDigestHelper.encode("SHA-1", strSrcBase, null));
//		try {
//			System.out.println("=============正在获取queryShop...==============");
//			String result=HttpUtils.URLPost(strUrl, map);
//			System.err.println(result);
//			Thread.sleep(3000);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
//		service="trade.queryCategoryCouponList";
//		contentJson=new JSONObject();
//		contentJson.put("pageIndex", 1);
//		contentJson.put("pageSize", 15);
//		contentJson.put("provinceCode", "44");
//		contentJson.put("cityCode", "4401");
//		contentJson.put("shopCode", "10086020");
//		contentJson.put("categoryCode", "GZYD");
//		json=new JSONObject();
//		json.put("content", contentJson);
//		json.put("ver", ver);
//		json.put("service", service);
//		json.put("appNo", appNo);
//		strSrcBase=key+json.toString()+key;
//		strUrl=url+service+"/"+ver+"/";
//
//		map=new HashMap<String, String>();
//		map.put("appNo", appNo);
//		map.put("service", service);
//		map.put("ver", ver);
//		map.put("msg", json.toString());
//		map.put("sign", MessageDigestHelper.encode("SHA-1", strSrcBase, null));
//		try {
//			System.out.println("=============正在获取分类商品信息列表查询...==============");
//			String result=HttpUtils.URLPost(strUrl, map);
//			System.err.println(result);
//			Thread.sleep(3000);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

//		service="channel.viewPicFileContent";
//		String picId="B21371868112608.jpg";//B21371613643185.gif B279102666214200456854.jpg  B21376536414529.jpg B21371868112608.jpg B248023090903403870518.jpg
//		String msg="picFileId="+picId;
//		strSrcBase=key+msg+key;
//		//strUrl=url+service+"/"+ver+"/?appNo="+appNo+"&service="+service+"&ver="+ver+"&sign="+MessageDigestHelper.encode("SHA-1", strSrcBase, null)+"&"+msg;
//		strUrl=url+service+"/"+ver+"/?appNo="+appNo+"&service="+service+"&ver="+ver+"&sign="+MessageDigestHelper.encode("SHA-1", strSrcBase, null)+"&"+msg;
//		System.out.println(strUrl);
//		try {
//			System.out.println("=============正在获取channel.viewPicFileContent信息...==============");
//			String result=HttpUtils.URLGet(strUrl, null);
//			System.err.println(result);
//			Thread.sleep(3000);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
//		service="trade.queryCouponDetail";
//		contentJson=new JSONObject();
//		contentJson.put("couponCode", "gfczyj_z1wsw");
//		json=new JSONObject();
//		json.put("content", contentJson);
//		json.put("ver", ver);
//		json.put("service", service);
//		json.put("appNo", appNo);
//		strSrcBase=key+json.toString()+key;
//		strUrl=url+service+"/"+ver+"/";
//
//		map=new HashMap<String, String>();
//		map.put("appNo", appNo);
//		map.put("service", service);
//		map.put("ver", ver);
//		map.put("msg", json.toString());
//		map.put("sign", MessageDigestHelper.encode("SHA-1", strSrcBase, null));
//		try {
//			System.out.println("=============正在获取3.4.5	商品明细查询...==============");
//			String result=HttpUtils.URLPost(strUrl, map);
//			System.err.println(result);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
//		service="exchange.getPointExchange";
//		String validCode="000000000000";//兑换码
//		String mobile="13538852364";//手机号
//		String goodsResource="0";//兑换商品来源(1位数字表示，暂时支持汇生活) 0:汇生活
//		String goodsCode="azydxm_1i59o";//兑换商品编码
//		String goodsType="0";//兑换商品类型 0:固定商品 1：动态商品
//		BigDecimal goodsPrice=new BigDecimal("111");//兑换商品单价（单位：元,最多保留两位小数）
//		String amount="1";//商品购买数量(单位：个)
//		String channel="CM";//积分兑换渠道 CM(中国移动)、CU(中国联通)、CT（中国电信）
//		String ext=null;//扩展字段(选填)
//		/** 参数构造 **/
//		contentJson = new JSONObject();
//		contentJson.put("validCode",CertificateUtil.encode(validCode, "UTF-8", PropertyUtil.getPropertyValueDir("app", "pePublicKeyFileName")));
//		contentJson.put("mobile", mobile);
//		contentJson.put("goodsResource", goodsResource);
//		contentJson.put("goodsCode", goodsCode);
//		contentJson.put("goodsType", goodsType);
//		contentJson.put("goodsPrice", goodsPrice.setScale(2, BigDecimal.ROUND_DOWN));
//		contentJson.put("amount", amount);
//		contentJson.put("channel", channel);
//		contentJson.put("ext", ext == null ? "" : ext);
//		/** msg参数结构构造 **/
//		json = new JSONObject();
//		json.put("content", contentJson);
//		json.put("ver", ver);
//		json.put("service", service);
//		json.put("appNo", appNo);
//		map = new HashMap<String, String>();
//		map.put("appNo", appNo);
//		map.put("service", service);
//		map.put("ver", ver);
//		map.put("msg", json.toString());
//		map.put("sign", ApiConfig.genSign(key, json.toString()));
//		StringBuffer strExUrl = new StringBuffer(url);
//		strExUrl.append(service).append("/").append(ver).append("/");
//		try {
//			JSONObject response = new JSONObject(HttpUtils.URLPost(strExUrl.toString(), map));
//			System.out.println(response);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
	}
	
	public static void main(String[] args) {
		ApiTest testApi=new ApiTest();
		try {
			testApi.run();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
