package com.zjht.youoil.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class AjaxResp implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1145871811158126726L;
	
	private Integer repCode;
	private String respMsg;
	private Integer total;
	private Integer index;
	public Integer getRepCode() {
		return repCode;
	}
	public void setRepCode(Integer repCode) {
		this.repCode = repCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	/**
	 * 0-100  处理进度
	 * @return
	 */
	public int getPrecent() {
		if (index!=null&&index.longValue()>0l&&total!=null&&total.longValue()>0l) {
			BigDecimal p=new BigDecimal(index).divide(new BigDecimal(total),2,RoundingMode.DOWN).setScale(2, RoundingMode.DOWN);
			return p.multiply(new BigDecimal(100)).intValue();
		}
		return 0;
	}
	
	public static void main(String [] args){
		BigDecimal b=new BigDecimal(0);
		BigDecimal t=new BigDecimal(1);
		BigDecimal p=b.divide(t,2,RoundingMode.DOWN).setScale(2, RoundingMode.DOWN);
		System.out.println(p.multiply(new BigDecimal(100)).intValue());
	}
}
