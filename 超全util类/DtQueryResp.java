package com.zjht.youoil.util;

import java.io.Serializable;

public class DtQueryResp implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -280119023590895265L;
	private String service;
	private String state;
	private DtQueryContent content;
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public DtQueryContent getContent() {
		return content;
	}
	public void setContent(DtQueryContent content) {
		this.content = content;
	}
	
}
