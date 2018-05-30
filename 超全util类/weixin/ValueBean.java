package com.zjht.youoil.util.weixin;

import java.util.Map;

public class ValueBean {

	private String touser;
	private String url;
	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public Map<String, DataBean> getData() {
		return data;
	}

	public void setData(Map<String, DataBean> data) {
		this.data = data;
	}

	private String template_id;

	private Map<String, DataBean> data;
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
