package com.zjht.youoil.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * 阳光车险工具类
 * @author huangshipiao
 * @time   2015-10-13 下午3:43:10
 */
public class SunshineUtil {
	
	/**
	 * 获取XMl指定节点的属性值
	 * @author huangshipiao
	 * @time   2015-10-14 下午3:29:15 
	 * @param xml
	 * @param nodeName 指定节点 如request
	 * @param arrtName 指定属性值
	 * @return
	 */
	public static String getXmlAttrValueByName(String xml,String nodeName,String arrtName){
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();  
		Document doc;			
		String value="";
		try {
			DocumentBuilder builder = factory.newDocumentBuilder(); 
			InputStream iStream = new ByteArrayInputStream(xml.getBytes("GBK"));
			doc =  builder.parse(iStream);			
			NodeList nodeList=doc.getDocumentElement().getElementsByTagName(nodeName);
			if(nodeList==null){
				return value;
			}
			Node node=nodeList.item(0);
			if(node==null){
				return value;
			}
			NamedNodeMap attributes=node.getAttributes();
			if(attributes==null||attributes.getLength()==0){				
				return value;
			}		
			for(int j=0;j<attributes.getLength();j++)
			{
				Node attribute=attributes.item(j);
				//得到属性名
				String attributeName=attribute.getNodeName();
//				System.out.println("属性名:"+attributeName);
				//得到属性值
//				String attributeValue=attribute.getNodeValue();
//				System.out.println("属性值:"+attributeValue);
				if(attributeName.equals(arrtName)){
					value=attribute.getNodeValue();
				}				
				
			}

			
		}catch(Exception e){
			e.printStackTrace();
		}
		return value;
	}
	/**
	 * 获取指定节点的所有属性值
	 * @author huangshipiao
	 * @time   2015-10-14 下午3:48:08 
	 * @param xml
	 * @param nodeName
	 * @return
	 */
	public static Map<String,Object> getXmlAttrValue(String xml,String nodeName){
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();  
		Document doc;
		Map<String,Object> map=null;
		try {
			DocumentBuilder builder = factory.newDocumentBuilder(); 
			InputStream iStream = new ByteArrayInputStream(xml.getBytes("GBK"));
			doc =  builder.parse(iStream);			
			NodeList nodeList=doc.getDocumentElement().getElementsByTagName(nodeName);
			if(nodeList==null){
				return null;
			}
			Node node=nodeList.item(0);
			if(node==null){
				return null;
			}
			NamedNodeMap attributes=node.getAttributes();
			if(attributes==null||attributes.getLength()==0){				
				return null;
			}
			map=new HashMap<String, Object>();
			for(int j=0;j<attributes.getLength();j++)
			{
				Node attribute=attributes.item(j);
				//得到属性名
				String attributeName=attribute.getNodeName().trim();
//				System.out.println("属性名:"+attributeName);
				//得到属性值
				String attributeValue=attribute.getNodeValue().trim();
//				System.out.println("属性值:"+attributeValue);
				map.put(attributeName, attributeValue);	
				
			}

			
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	
	public static void main(String[] args) throws UnsupportedEncodingException {
//		String xml="<?xml version='1.0' encoding='GBK'?><message finishTime='2013-09-04 10:16:28'  ><request func='car_info_sync' return='true' from='35'><content><name></name><mobile>13770001011</mobile><email>chenchanghao@163.com</email><carLicence>苏J11H58</carLicence><cityCode>320900</cityCode><registerDate></registerDate><bizBeginDate></bizBeginDate><forBeginDate></forBeginDate></content></request></message>";
//		String value=getXmlAttrValueByName(xml, "message1","func");
//		System.out.println("value:"+value);
		String xml2="<?xml version='1.0' encoding='GBK'?><message finishTime='2015-03-20 19:33:51'><request func='car_proposal_sync' return='true' from='1'><content><orderInfoList><orderInfo><tbOrderNo>3268677163736</tbOrderNo><insuranceType>0</insuranceType><outOrderId>T085105072015000393</outOrderId><compulsory><insurePremium>950.0</insurePremium><travelTax>0.0</travelTax></compulsory><payUrl><![CDATA[http://219.143.230.175:7002/Net/netProposalPayAction.action?orderNo=aeda96645c647d3f&paraMap.agentCode=W02410566]]></payUrl><payOutTime>2015-03-20 23:00:00</payOutTime><quoteTime>2015-03-20 19:33:51</quoteTime></orderInfo><orderInfo><tbOrderNo>3268678163736</tbOrderNo><insuranceType>1</insuranceType><outOrderId>T085105092015000413</outOrderId><commercial><insurePremium>5247.55</insurePremium>" +
				"<insuranceList>" +
				"<insure><insureCode>200</insureCode><insureType>1</insureType><insureAmount>189800.00</insureAmount><insurePremium>2397.84</insurePremium></insure>" +
				"<insure><insureCode>600</insureCode><insureType>1</insureType><insureAmount>300000.00</insureAmount><insurePremium>1040.26</insurePremium></insure>" +
				"<insure><insureCode>701</insureCode><insureType>1</insureType><insureAmount>10000.00</insureAmount><insurePremium>33.92</insurePremium></insure>" +
				"<insure><insureCode>702</insureCode><insureType>1</insureType><insureAmount>40000.00</insureAmount><insurePremium>87.21</insurePremium></insure>" +
				"<insure><insureCode>500</insureCode><insureType>1</insureType><insureAmount>189800.00</insureAmount><insurePremium>847.9</insurePremium></insure>" +
				"<insure><insureCode>231</insureCode><insureType>2</insureType><insureAmount>0.00</insureAmount><insurePremium>306.53</insurePremium></insure>" +
				"<insure><insureCode>900</insureCode><insureType>2</insureType><insureAmount>0.00</insureAmount><insurePremium>533.89</insurePremium></insure>" +
				"</insuranceList></commercial><payUrl><![CDATA[http://219.143.230.175:7002/Net/netProposalPayAction.action?orderNo=aeda96645c647d3f&paraMap.agentCode=W02410566]]></payUrl><payOutTime>2015-03-20 23:00:00</payOutTime><quoteTime>2015-03-20 19:33:51</quoteTime></orderInfo></orderInfoList><applyInfo><effectiveDate>2015-03-21</effectiveDate><buyerId></buyerId><buyerNick></buyerNick><auctionId></auctionId><auctionTitle></auctionTitle><promotionInfo></promotionInfo><vehicleInfo><VIN>67656584686765754</VIN><carCity>440100</carCity><carProvince>440000      </carProvince><carID>粤A55514</carID><carType>北京现代BH7240MW轿车</carType><engineId>547856755464</engineId><firstRegisterDate>2015-03-10</firstRegisterDate></vehicleInfo><vehicleOwnerInfo><cardType>01</cardType><cardNo>130721199005055159</cardNo></vehicleOwnerInfo><contact><mobile>13618206119</mobile><email>5244345@163.com</email><address></address></contact></applyInfo></content></request></message>";
		
//		byte[] bs = xml2.getBytes("utf-8");
//		   //用新的字符编码生成字符串
//		   
//		String xml=new String(bs,"utf-8");
//		System.out.println(xml);
		List<Map<String,Object>> lists=parseSunshineForList( xml2, "orderInfo");
		System.out.println(lists.size());
	}
	/**
	 * 解析阳光车险XML。
	 * 
	 * @author huangshipiao
	 * @time   2015-10-14 上午11:05:38 
	 * @param xml
	 * @param nodeName 可以传【orderInfo】解析成两个订单信息。传【applyInfo】单独解析出投保信息
	 * @return
	 */
	public static List<Map<String,Object>> parseSunshineForList(String xml,String nodeName){
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();  
		Document doc;	
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		Map<String,Object> resultMap=null;
		try {
			DocumentBuilder builder = factory.newDocumentBuilder(); 
			InputStream iStream = new ByteArrayInputStream(xml.getBytes("GBK"));
//			Reader reader = new InputStreamReader(iStream,"GBK");
//		    InputSource iSource = new InputSource(reader);
//		    iSource.setEncoding("GBK");
//		    doc =  builder.parse(iSource);
			doc =  builder.parse(iStream);
			NodeList nodeList=doc.getElementsByTagName(nodeName);
//			System.out.println("nodeList.getLength():"+nodeList.getLength());
			for (int i = 0; i < nodeList.getLength(); i++) {
				//获取header节点
				NodeList nodeList2 =nodeList.item(i).getChildNodes();				
				resultMap=new HashMap<String, Object>();
				//循环遍历XML的header节点，转换成MAP数据
				for (int j = 0; j < nodeList2.getLength(); j++) 
				{ 
//					System.out.println(j+"|nodeList2.getLength():"+nodeList2.getLength());
					Node n =  (Node) nodeList2.item(j);
					
					if(n!=null){
						//先判断是否为文本类型
						if (n.getNodeType() != Node.TEXT_NODE)  
						{ 	
//							System.out.println(n.getNodeName());
							if(n.getNodeName().equals("commercial")){//商业险节点(Commercial)//								
								resultMap.putAll(parseXmByNodeName(xml,"commercial"));
								//保障条款节点  有许多重复字段，需要单独解析单独存储
								resultMap.put("insuranceList", parseInsuranceList(xml));
							}else if(n.getNodeName().equals("compulsory")){//交强险节点
								resultMap.putAll(parseXmByNodeName(xml,"compulsory"));								
							}else if(n.getNodeName().equals("applyInfo")){//投保信息节点(ApplyInfo)
								resultMap.putAll(parseXmByNodeName(xml,"applyInfo"));
							}else if(n.getNodeName().equals("vehicleInfo")){//车辆信息节点(VehicleInfo)
								resultMap.putAll(parseXmByNodeName(xml,"vehicleInfo"));
							}else if(n.getNodeName().equals("vehicleOwnerInfo")){//车主信息节点（VehicleOwnerInfo）
								resultMap.putAll(parseXmByNodeName(xml,"vehicleOwnerInfo"));
							}else if(n.getNodeName().equals("contact")){//联系信息节点(Contact)
								resultMap.putAll(parseXmByNodeName(xml,"contact"));
							}else{
								resultMap.put(n.getNodeName(), n.getTextContent().trim());
							}
						}						
					}
				} 
				System.out.println(i+":"+resultMap);
				resultList.add(resultMap);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultList;
	}
	/**
	 * 解析普通的XML节点
	 * @author huangshipiao
	 * @time   2015-10-14 下午1:54:24 
	 * @param xml
	 * @param nodeName
	 * @return
	 */
	private static Map<String,Object> parseXmByNodeName(String xml,String nodeName){
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();  
		Document doc;		
		Map<String,Object> resultMap=new HashMap<String, Object>();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder(); 
			InputStream iStream = new ByteArrayInputStream(xml.getBytes("GBK"));
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
//					System.out.println(n.getNodeName());
					resultMap.put(n.getNodeName(), n.getTextContent().trim());					
				}
			} 	
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultMap;
	}
	/**
	 * 单独解析保障条款明显
	 * @author huangshipiao
	 * @time   2015-10-14 下午1:53:12 
	 * @param  xml
	 * @return
	 */
	private static List<Map<String,Object>> parseInsuranceList(String xml){
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();  
		Document doc;	
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		Map<String,Object> resultMap=null;
		try {
			DocumentBuilder builder = factory.newDocumentBuilder(); 
			InputStream iStream = new ByteArrayInputStream(xml.getBytes("GBK"));
			doc =  builder.parse(iStream);
			NodeList nodeList=doc.getElementsByTagName("insure");
			for (int i = 0; i < nodeList.getLength(); i++) {
				//获取header节点
				NodeList nodeList2 =nodeList.item(i).getChildNodes();				
				resultMap=new HashMap<String, Object>();
				//循环遍历XML的header节点，转换成MAP数据
				for (int j = 0; j < nodeList2.getLength(); j++) 
				{ 
					Node n =  (Node) nodeList2.item(j);
					if(n!=null){
						//先判断是否为文本类型
						if (n.getNodeType() != Node.TEXT_NODE)  
						{ 	
							resultMap.put(n.getNodeName(), n.getTextContent().trim());
						}						
					}
				} 
				resultList.add(resultMap);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultList;
	}
	/**
	 * 推销接口响应报文
	 * @author huangshipiao
	 * @time   2015-10-14 下午2:03:07 
	 * @param isSuccess   必传字段  返回成功为T，接收失败为F，需要重试为W
	 * @param outOrderId  非必传字段  阳光内部投保单号
	 * @param errorCode	      非必传字段  错误码
	 * @param errorReason 非必传字段 错误说明
	 * @return
	 */
	public static String responseXmlForSunshine(String isSuccess,String outOrderId,String errorCode,String errorReason){
		StringBuffer xml=new StringBuffer();
		xml.append("<?xml version='1.0' encoding='UTF-8'?>");
		xml.append("<response finishTime='"+DateTimeUtils.getCurrentDateStrFormat(DateTimeUtils.YYYYMMDDHHMMSS)+"'>");
		xml.append("<isSuccess>"+isSuccess+"</isSuccess>");
		xml.append("<outOrderId>"+outOrderId+"</outOrderId>");
		xml.append("<errorCode>"+errorCode+"</errorCode>");
		xml.append("<errorReason>"+errorReason+"</errorReason>");
		xml.append("</response>");		
		return xml.toString();
	}
	
}
