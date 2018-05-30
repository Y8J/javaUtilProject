package com.zjht.youoil.util;

import java.io.Serializable;

public class DtQueryContent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8023417273734561893L;
	private String assistCode;
	private String status;
	private String statusDesc;
	private String parPrice;
	public String getAssistCode() {
		return assistCode;
	}
	public void setAssistCode(String assistCode) {
		this.assistCode = assistCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public String getParPrice() {
		return parPrice;
	}
	public void setParPrice(String parPrice) {
		this.parPrice = parPrice;
	}
}
