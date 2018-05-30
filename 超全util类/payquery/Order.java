package com.zjht.youoil.util.payquery;

import java.io.Serializable;

/**
 * 订单model
 * @author liujun
 * @since 2017年6月23日 下午3:20:50
 */
public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//支付流水号
	private String orderNo;
	
	//商户订单号
	private String partnerOrderNo;
	
	//商户号
	private String partnerCode;
	
	//订单支付金额
	private java.math.BigDecimal amount;
	
	//订单支付状态
	private String status;
	
	//支付渠道编码
	private String thirdPartyCode;
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getPartnerOrderNo() {
		return partnerOrderNo;
	}
	public void setPartnerOrderNo(String partnerOrderNo) {
		this.partnerOrderNo = partnerOrderNo;
	}
	
	public String getPartnerCode() {
		return partnerCode;
	}
	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}
	
	public java.math.BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(java.math.BigDecimal amount) {
		this.amount = amount;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getThirdPartyCode() {
		return thirdPartyCode;
	}
	public void setThirdPartyCode(String thirdPartyCode) {
		this.thirdPartyCode = thirdPartyCode;
	}
	
	
}
