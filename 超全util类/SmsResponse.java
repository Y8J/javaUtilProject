package com.zjht.youoil.util;

import java.util.List;

public class SmsResponse {
	//返回码00000表示成功
	private String stateCode;
	//返回结果描述
	private String stateCodeDesc;
	//返回结果
	private List<SmsSingle> smsList;
	
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getStateCodeDesc() {
		return stateCodeDesc;
	}
	public void setStateCodeDesc(String stateCodeDesc) {
		this.stateCodeDesc = stateCodeDesc;
	}
	public List<SmsSingle> getSmsList() {
		return smsList;
	}
	public void setSmsList(List<SmsSingle> smsList) {
		this.smsList = smsList;
	}
	
}
