package com.zjht.youoil.util.weixin;

public class DataBean {
	private String value;

	private String color;

	public DataBean(String value, String color) {
		this.value = value;
		this.color = color;
	}

	public String getValue() {
		return this.value;
	}

	public String getColr() {
		return this.color;
	}
}
