package com.zjht.youoil.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.rpc.ServiceException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sf.integration.warehouse.service.IOutsideToLscmService;
import com.sf.integration.warehouse.service.OutsideToLscmServiceService;
import com.sf.integration.warehouse.service.OutsideToLscmServiceServiceLocator;
import com.zjht.youoil.entity.CouponConsumeRecord;
import com.zjht.youoil.service.CcrConfig;

/**
 * 顺丰仓储系统接口工具栏
 * 包含：接口报文组装，返回结果解析，接口方法调用
 * @author huangshipiao
 * @time   2015-8-18 下午6:26:45
 */
public class ExpressInterfaceUtil {
	
	public static final Properties prop = PropertyUtil.getPropertes("ccr", "express");
	/**
	 * 顺丰仓储系统-接口地址
	 */
	public static final String url = prop.getProperty(CcrConfig.CCR_WEBSERVICEURL);
	/**
	 * 顺丰仓储系统-校验字段
	 */
	public static final String checkWord = prop.getProperty(CcrConfig.CCR_CHECKWORD);
	/**
	 * 顺丰仓储系统-货主代码
	 */
	public static final String company = prop.getProperty(CcrConfig.CCR_COMPANY);
	/**
	 * 顺丰仓储系统-月结账号
	 */
	public static final String monthly_account = prop.getProperty(CcrConfig.CCR_MONTHLY_ACCOUNT);
	/**
	 * 顺丰仓储系统-供应商
	 */
	public static final String vendor = prop.getProperty(CcrConfig.CCR_VENDOR);
	/**
	 * 顺丰仓储系统-仓库代码
	 */
	public static final String warehouse = prop.getProperty(CcrConfig.CCR_WAREHOUSE);
	
		
	/**
	 * 
	 * @author huangshipiao
	 * @time   2015-8-18 下午7:17:20 
	 * @param ccr
	 * @return
	 */
	public static String  wmsSailOrderRequestXml(CouponConsumeRecord ccr,String[] ccrProuct){
		StringBuffer xml=new StringBuffer();
		xml.append("<wmsSailOrderRequest>");
			xml.append("<checkword>"+checkWord+"</checkword>");
			xml.append("<header>");
				xml.append("<company>"+company+"</company>");//货主
				xml.append("<warehouse>"+warehouse+"</warehouse>");//仓库
				xml.append("<shop_name>陶陶居</shop_name>");//商家店铺名称
				xml.append("<erp_order>"+ccr.getOrderNo()+"</erp_order>");//订单号码
				xml.append("<order_type>销售订单</order_type>");//订单类型：销售订单，返厂订单，换货订单，调拨订单，虚拟订单，余量订单
				xml.append("<order_date>"+DateTimeUtils.formatDateStr(ccr.getTxnTime(), "yyyy-MM-dd HH:mm:ss")+"</order_date>");//订单时间
				xml.append("<ship_to_name>"+ccr.getReceiverName()+"</ship_to_name>");//收件公司
				xml.append("<ship_to_attention_to>"+ccr.getReceiverName()+"</ship_to_attention_to>");//收件人
				xml.append("<ship_to_country>中国</ship_to_country>");//国家或地区
				xml.append("<ship_to_province>"+ccr.getReceiverProvince()+"</ship_to_province>");//收件省
				xml.append("<ship_to_city>"+ccr.getReceiverCity()+"</ship_to_city>");//收件市
				xml.append("<ship_to_area>"+ccr.getReceiverArea()+"</ship_to_area>");//收件区/县
				xml.append("<ship_to_address>"+ccr.getReceiverProvince()+ccr.getReceiverCity()+ccr.getReceiverArea()+ccr.getReceiverAddress()+"</ship_to_address>");//地址
				xml.append("<ship_to_postal_code>"+ccr.getReceiver_zip()+"</ship_to_postal_code>");//邮编
				xml.append("<ship_to_phone_num>"+ccr.getReceiverMobile()+"</ship_to_phone_num>");//手机号码
//				xml.append("<packing_note>礼品包装</packing_note>");//货品包装备注：礼品包装，易碎品，高价值，需特殊包装
				xml.append("<payment_of_charge>寄付</payment_of_charge>");//
				xml.append("<cod>N</cod>");//是否货到付款:Y/N
				xml.append("<value_insured>N</value_insured>");//是否保价:Y/N
				xml.append("<return_receipt_service>N</return_receipt_service>");//签回单:Y/N
				xml.append("<from_flag>N</from_flag>");//寄方标识:Y/N
				xml.append("<ship_from_name>广州陶陶居食品有限公司</ship_from_name>");//寄件公司:Y/N
				xml.append("<ship_from_country>中国</ship_from_country>");//寄件国家或地区
				xml.append("<ship_from_province>广东省</ship_from_province>");//寄件省
				xml.append("<ship_from_city>广州市</ship_from_city>");//寄件市
				xml.append("<ship_from_area>天河区</ship_from_area>");//寄件区/县
				xml.append("<ship_from_address>天河区天河路228号正佳广场6楼6C059铺</ship_from_address>");//寄件地址
				xml.append("<ship_from_tel_num>4006630666</ship_from_tel_num>");
				if(ccr.getRemarks()!=null){
					xml.append("<delivery_requested>"+ccr.getRemarks()+"</delivery_requested>");//配送要求	
					xml.append("<order_note>"+ccr.getRemarks()+"</order_note>");//订单备注	
				}
				xml.append("<monthly_account>"+monthly_account+"</monthly_account>");//月结账户				
			xml.append("</header>");
			xml.append("<detailList>");
				xml.append("<item>");
					xml.append("<erp_order_line_num>"+ccr.getTraceNo()+"</erp_order_line_num>");//订单明显序列号
					if(PropertyUtil.isProductDeploy()){
						xml.append("<item>"+ccrProuct[1]+"</item>");//商品编号	生产环境					
					}else{
						xml.append("<item>LSCMINTERAUTO01</item>");//顺丰测试商品编码	
//						xml.append("<item>6959070901806</item>");//顺丰测试商品编码	
					}
					xml.append("<item_name>"+ccr.getGoods()+"</item_name>");//商品名称
					xml.append("<uom>盒</uom>");//商品单位
					xml.append("<qty>"+ccr.getConsumeCount()+"</qty>");//商品数量
				xml.append("</item>");
			xml.append("</detailList>");
		xml.append("</wmsSailOrderRequest>");		
		return xml.toString();
	}
	/**
	 * 一个订单多种商品
	 * @author huangshipiao
	 * @time   2015-9-15 下午12:37:53 
	 * @param ccr
	 * @param goods
	 * @return
	 */
	public static String  wmsSailOrderRequestXmlWithMultiGoods(CouponConsumeRecord ccr,List<Map<String,Object>> goods){
		StringBuffer xml=new StringBuffer();
		xml.append("<wmsSailOrderRequest>");
			xml.append("<checkword>"+checkWord+"</checkword>");
			xml.append("<header>");
				xml.append("<company>"+company+"</company>");//货主
				xml.append("<warehouse>"+warehouse+"</warehouse>");//仓库
				xml.append("<shop_name>陶陶居</shop_name>");//商家店铺名称
				xml.append("<erp_order>"+ccr.getOrderNo()+"</erp_order>");//订单号码
				xml.append("<order_type>销售订单</order_type>");//订单类型：销售订单，返厂订单，换货订单，调拨订单，虚拟订单，余量订单
				xml.append("<order_date>"+DateTimeUtils.formatDateStr(ccr.getTxnTime(), "yyyy-MM-dd HH:mm:ss")+"</order_date>");//订单时间
				xml.append("<ship_to_name>"+ccr.getReceiverName()+"</ship_to_name>");//收件公司
				xml.append("<ship_to_attention_to>"+ccr.getReceiverName()+"</ship_to_attention_to>");//收件人
				xml.append("<ship_to_country>中国</ship_to_country>");//国家或地区
				xml.append("<ship_to_province>"+ccr.getReceiverProvince()+"</ship_to_province>");//收件省
				xml.append("<ship_to_city>"+ccr.getReceiverCity()+"</ship_to_city>");//收件市
				xml.append("<ship_to_area>"+ccr.getReceiverArea()+"</ship_to_area>");//收件区/县
				xml.append("<ship_to_address>"+ccr.getReceiverProvince()+ccr.getReceiverCity()+ccr.getReceiverArea()+ccr.getReceiverAddress()+"</ship_to_address>");//地址
				xml.append("<ship_to_postal_code>"+ccr.getReceiver_zip()+"</ship_to_postal_code>");//邮编
				xml.append("<ship_to_phone_num>"+ccr.getReceiverMobile()+"</ship_to_phone_num>");//手机号码
//				xml.append("<packing_note>礼品包装</packing_note>");//货品包装备注：礼品包装，易碎品，高价值，需特殊包装
				xml.append("<payment_of_charge>寄付</payment_of_charge>");//
				xml.append("<cod>N</cod>");//是否货到付款:Y/N
				xml.append("<value_insured>N</value_insured>");//是否保价:Y/N
				xml.append("<return_receipt_service>N</return_receipt_service>");//签回单:Y/N
				xml.append("<from_flag>N</from_flag>");//寄方标识:Y/N
				xml.append("<ship_from_name>广州陶陶居食品有限公司</ship_from_name>");//寄件公司:Y/N
				xml.append("<ship_from_country>中国</ship_from_country>");//寄件国家或地区
				xml.append("<ship_from_province>广东省</ship_from_province>");//寄件省
				xml.append("<ship_from_city>广州市</ship_from_city>");//寄件市
				xml.append("<ship_from_area>天河区</ship_from_area>");//寄件区/县
				xml.append("<ship_from_address>天河区天河路228号正佳广场6楼6C059铺</ship_from_address>");//寄件地址
				xml.append("<ship_from_tel_num>4006630666</ship_from_tel_num>");
				if(ccr.getRemarks()!=null){
					xml.append("<delivery_requested>"+ccr.getRemarks()+"</delivery_requested>");//配送要求	
					xml.append("<order_note>"+ccr.getRemarks()+"</order_note>");//订单备注	
				}
				xml.append("<monthly_account>"+monthly_account+"</monthly_account>");//月结账户				
			xml.append("</header>");
			xml.append("<detailList>");
			for (int i = 0; i < goods.size(); i++) {
				xml.append("<item>");
				xml.append("<erp_order_line_num>"+i+1+"</erp_order_line_num>");//订单明显序列号
				if(PropertyUtil.isProductDeploy()){
					xml.append("<item>"+goods.get(i).get("item")+"</item>");//商品编号	生产环境					
				}else{
					xml.append("<item>LSCMINTERAUTO01</item>");//顺丰测试商品编码	
//						xml.append("<item>6959070901806</item>");//顺丰测试商品编码	
				}
				xml.append("<item_name>"+goods.get(i).get("itemName")+"</item_name>");//商品名称
				xml.append("<uom>盒</uom>");//商品单位
				xml.append("<qty>"+goods.get(i).get("qty")+"</qty>");//商品数量
				xml.append("</item>");				
			}
			xml.append("</detailList>");
		xml.append("</wmsSailOrderRequest>");		
		return xml.toString();
	}
	
	
	/**
	 * 调用顺丰仓储系统的webservice接口
	 * @author huangshipiao
	 * @time   2015-8-19 下午5:35:02 
	 * @param xml
	 * @return
	 */
	public static String webserviceToSF(String xml){
		OutsideToLscmServiceService svr = new OutsideToLscmServiceServiceLocator();
		IOutsideToLscmService service;
		String result ="";
		try {
			service = svr.getOutsideToLscmServicePort(new URL(url));
//			String xml = "<wmsSailOrderRequest><checkword>01f18980363f40e48416464baf4cc7c0</checkword><header><company>TTJ072401</company><warehouse>571DCF</warehouse><shop_name>苹果中国</shop_name><erp_order>zjht_0022</erp_order><order_type>销售订单</order_type><order_date>2015-7-19 00:00:00</order_date><ship_from_name>苹果公司</ship_from_name><ship_from_attention_to>寄件方</ship_from_attention_to><ship_from_country>中国</ship_from_country><ship_from_province>广东省</ship_from_province><ship_from_city>深圳市</ship_from_city><ship_from_area>福田区</ship_from_area><ship_from_address>新洲十一街万基商务大厦</ship_from_address><ship_from_postal_code>530001</ship_from_postal_code><ship_from_phone_num>13925299967</ship_from_phone_num><ship_from_tel_num>0755-36646961</ship_from_tel_num><ship_from_email_address>lp@qq.com</ship_from_email_address><ship_to_name>个人</ship_to_name><ship_to_attention_to>收件方</ship_to_attention_to><ship_to_country>中国</ship_to_country><ship_to_province></ship_to_province><ship_to_city></ship_to_city><ship_to_area></ship_to_area><ship_to_address>浙江乐清市火车西站沟西路社区</ship_to_address><ship_to_postal_code>530002</ship_to_postal_code><ship_to_phone_num>13925299967</ship_to_phone_num><ship_to_tel_num>0755-36646962</ship_to_tel_num><ship_to_email_address>sf111@sf.com</ship_to_email_address><payment_of_charge>寄付</payment_of_charge><payment_district>深圳市</payment_district><cod>N</cod><amount></amount><self_pickup>否</self_pickup><value_insured>否</value_insured><declared_value></declared_value><return_receipt_service>Y</return_receipt_service><invoice>否</invoice><invoice_type></invoice_type><invoice_title></invoice_title><invoice_content></invoice_content><order_note>订单备注：正常数据测试</order_note><company_note>商家备注：苹果中国正常数据测试</company_note><priority>2</priority><order_total_amount>100</order_total_amount><order_discount></order_discount><balance_amount></balance_amount><coupons_amount></coupons_amount><gift_card_amount></gift_card_amount><other_charge></other_charge><actual_amount>100</actual_amount><customer_payment_method>支付宝</customer_payment_method><monthly_account>7550144315</monthly_account><from_flag>N</from_flag><user_def76>N</user_def76></header><detailList><item><erp_order_line_num>1</erp_order_line_num><item>LSCMINTERAUTO01</item><item_name>正常数据</item_name><uom>个</uom><lot></lot><qty>2</qty><item_price>10</item_price><item_discount>0</item_discount><bom_action></bom_action></item></detailList></wmsSailOrderRequest>";
		    result = service.outsideToLscmService(xml);			
		}catch (RemoteException e) {
			e.printStackTrace();		
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}	
		return result;
	}
	
	
	
	/**
	 * 解析出库单接口返回结果
	 * @author huangshipiao
	 * @time   2015-8-19 上午8:39:12 
	 * @param resultxml
	 * @return
	 */
	public static Map<String,Object> parseResultXML(String strResult){
		/**
		 * 正常调用接口得到的返回结果
		 * <wmsSailOrderResponse>
		 * 		<orderid>订单号</orderid>
		 * 		<result>处理结果：1-成功，2-失败</result>
		 * 		<remark>备注，如处理失败，此处可备注原因</remark>
		 * </wmsSailOrderResponse>
		 */
		
		/**
		 * 顺丰系统异常时候返回的结果
		 * <responseFail>
			  <reasoncode>1101</reasoncode>
			  <remark>系统发生数据错误或运行时异常</remark>
			</responseFail>
		 */
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();  
		Document doc;
		Map<String,Object> resultMap=new HashMap<String, Object>();				
		try {
			DocumentBuilder builder = factory.newDocumentBuilder(); 
			InputStream iStream = new ByteArrayInputStream(strResult.getBytes());
			Reader reader = new InputStreamReader(iStream,"UTF-8");
		    InputSource iSource = new InputSource(reader);
//		    iSource.setEncoding("UTF-8");
			doc =  builder.parse(iSource);
			NodeList nodeList =null;
			//判断返回的XML结果是成功的还是失败的格式
			
			if(doc.getElementsByTagName("wmsSailOrderResponse").getLength()>0){
				 nodeList = doc.getElementsByTagName("wmsSailOrderResponse").item(0).getChildNodes(); 
			}else{
				 nodeList = doc.getElementsByTagName("responseFail").item(0).getChildNodes(); 
			}
			String orderid="";//订单号    调用成功才会存在的值
			String result="";//返回处理结果  调用成功才会存在的值
			String remark="";//备注
			String reasoncode="";//返回的错误代码  调用错误异常才会存在的值
			for (int i = 0; i < nodeList.getLength(); i++) 
			{ 
				Node n =  (Node) nodeList.item(i);
				//先判断是否为文本类型
				if (n.getNodeType() != Node.TEXT_NODE)  
				{ 	//查找出<RETURNCODE>标记的内容
					if(n.getNodeName().equals("orderid"))
					{	
						orderid=n.getTextContent();		
						resultMap.put("orderid", orderid);
					}
					if(n.getNodeName().equals("result"))
					{	
						result=n.getTextContent();		
						resultMap.put("result", result);
					}
					if(n.getNodeName().equals("remark"))
					{	
						remark=n.getTextContent();
						resultMap.put("remark", remark);
					}
					if(n.getNodeName().equals("reasoncode"))
					{	
						reasoncode=n.getTextContent();
						resultMap.put("reasoncode", reasoncode);
					}
					
				}
			} 
		}catch(Exception e){
			e.printStackTrace();
		}		
		
		return resultMap;
	}
	/**
	 * 解析顺丰出库单状态与明细推送接口推送过来的报文信息
	 * 报文规则说明：
	 * a,当出库单状态变更（LSCM状态变更，WMS状态变更、“已签收”）时，仅仅主动推送header信息。
	 * b,当出库单状态为“900”(已出库)时，一并推送detailList、containerList、serialNuberList信息
	 * @author huangshipiao
	 * @time   2015-8-24 上午9:50:13 
	 * @param xml
	 * @return
	 */
	public static List<Map<String,Object>> parseWmSailOrderPushInfoForList(String xml){
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();  
		Document doc;
		List<Map<String, Object>> resultList=new ArrayList<Map<String,Object>>();
		Map<String,Object> resultMap=new HashMap<String, Object>();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder(); 
			InputStream iStream = new ByteArrayInputStream(xml.getBytes());
			doc =  builder.parse(iStream);
			//获取header节点
			NodeList nodeList =		
						 doc.getElementsByTagName("header").item(0).getChildNodes();
			//循环遍历XML的header节点，转换成MAP数据
			for (int i = 0; i < nodeList.getLength(); i++) 
			{ 
				Node n =  (Node) nodeList.item(i);
				//先判断是否为文本类型
				if (n.getNodeType() != Node.TEXT_NODE)  
				{ 	
					resultMap.put(n.getNodeName(), n.getTextContent());					
				}
			} 
			//存入header 头文件信息
			resultList.add(resultMap);
			
			//获取detailList节点
			nodeList =
					 doc.getElementsByTagName("detailList").item(0).getChildNodes();
			resultMap=new HashMap<String, Object>();
			if(nodeList!=null){//只有订单物流第一次出库时，才会推送此节点信息
				//循环遍历XML的header节点，转换成MAP数据
				for (int i = 0; i < nodeList.getLength(); i++) 
				{ 
					Node n =  (Node) nodeList.item(i);
					//先判断是否为文本类型
					if (n.getNodeType() != Node.TEXT_NODE)  
					{ 	
						resultMap.put(n.getNodeName(), n.getTextContent());					
					}
				} 
				//存入detailList 节点信息
				resultList.add(resultMap);
			}
			//获取containerList节点
			nodeList =
					 doc.getElementsByTagName("containerList").item(0).getChildNodes();
			resultMap=new HashMap<String, Object>();
			if(nodeList!=null){//只有订单物流第一次出库时，才会推送此节点信息
				//循环遍历XML的header节点，转换成MAP数据
				for (int i = 0; i < nodeList.getLength(); i++) 
				{ 
					Node n =  (Node) nodeList.item(i);
					//先判断是否为文本类型
					if (n.getNodeType() != Node.TEXT_NODE)  
					{ 	
						resultMap.put(n.getNodeName(), n.getTextContent());					
					}
				} 
				//存入containerList 节点信息
				resultList.add(resultMap);
			}
			//获取serialNumberList节点
			nodeList =
					 doc.getElementsByTagName("serialNumberList").item(0).getChildNodes();
			resultMap=new HashMap<String, Object>();
			if(nodeList!=null){//只有订单物流第一次出库时，才会推送此节点信息
				//循环遍历XML的header节点，转换成MAP数据
				for (int i = 0; i < nodeList.getLength(); i++) 
				{ 
					Node n =  (Node) nodeList.item(i);
					//先判断是否为文本类型
					if (n.getNodeType() != Node.TEXT_NODE)  
					{ 	
						resultMap.put(n.getNodeName(), n.getTextContent());					
					}
				} 
				//存入serialNumberList 节点信息
				resultList.add(resultMap);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultList;	
	}
	/**
	 * 解析顺丰出库单状态与明细推送接口推送过来的报文信息
	 * 报文规则说明：
	 * a,当出库单状态变更（LSCM状态变更，WMS状态变更、“已签收”）时，仅仅主动推送header信息。
	 * b,当出库单状态为“900”(已出库)时，一并推送detailList、containerList、serialNuberList信息
	 * @author huangshipiao
	 * @time   2015-8-25 上午9:53:34 
	 * @param xml
	 * @param nodeName 要获取数据的父节点名字 ,可传{header,detailList,containerList,serialNuberList}
	 * @return
	 */
	public static Map<String,Object> parseWmSailOrderPushInfoForMap(String xml,String nodeName){
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();  
		Document doc;		
		Map<String,Object> resultMap=new HashMap<String, Object>();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder(); 
			InputStream iStream = new ByteArrayInputStream(xml.getBytes());
			doc =  builder.parse(iStream);
			//获取header节点
			NodeList nodeList =		
						 doc.getElementsByTagName(nodeName).item(0).getChildNodes();
			//循环遍历XML的header节点，转换成MAP数据
			for (int i = 0; i < nodeList.getLength(); i++) 
			{ 
				Node n =  (Node) nodeList.item(i);
				//先判断是否为文本类型
				if (n.getNodeType() != Node.TEXT_NODE)  
				{ 	
					resultMap.put(n.getNodeName(), n.getTextContent());					
				}
			} 	
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultMap;
	}
	
	/**
	 * 针对顺丰主动推送接口信息给予的处理结果返回值
	 * 处理结果：true-成功,false-失败
	 * 备注，如果处理失败，reason处可备注原因
	 * @author huangshipiao
	 * @time   2015-8-24 上午10:53:34 
	 * @param result
	 * @param reason
	 * @return
	 */
	public static String responseXml(String result,String reason){
		StringBuffer xml= new StringBuffer();
		xml.append("<Response>");
			xml.append("<success>"+result+"</success>");
			xml.append("<reason>"+reason+"</reason>");
		xml.append("</Response>");
		return xml.toString();
	}
	
	
	public static void main(String[] args) throws MalformedURLException, ServiceException, RemoteException {
		String xml="<wmsSailOrderPushInfo><header><company>货主</company><warehouse>仓库</warehouse></header><detailList><item>商品编号</item><quantity>实发数量</quantity></detailList><containerList><item><container_id>箱号</container_id><item>货品编码</item><quantity>数量</quantity><serialNumberList><serial_number>串号</serial_number></serialNumberList></item></containerList></wmsSailOrderPushInfo>";
		List<Map<String,Object>> result=ExpressInterfaceUtil.parseWmSailOrderPushInfoForList(xml);
		for (Map<String, Object> map : result) {
			System.out.println(map);			
		}
	}
}
