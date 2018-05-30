package com.zjht.youoil.util;

public class TokenEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1637231771993802019L;
	private String code;//生成状态（T标识成功，F标识失败）
	private String msg;//生成描述（生成成功）
	private String mobile;//手机号
	private String token;//验证令牌
	private String timestamp;//时间戳
	private String nonce;//随机数
	private String ipAddress;//ip地址
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}
