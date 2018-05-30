package com.zjht.youoil.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 异步请求返回类
 * 
 */
public class AjaxResult<T> {
	public static final String STATUS_SUCCESS = "1";
	public static final String STATUS_FAILED = "-1";
	/**
	 * 提示信息
	 */
	private String msg;
	/**
	 * 请求结果状态
	 */
	private String status;
	/**
	 * 接口处理返回
	 */
	private String handleCode;
	/**
	 * 接口处理返回信息
	 */
	private String handleDesc;
	/**
	 * 直接跳转页
	 */
	private String toUrl;
	/**
	 * 自动跳转页
	 */
	private String autoUrl;
	/**
	 * 延迟秒数跳转
	 */
	private int lazyMimillionSecond = 2000;
	/**
	 * 是否重新加载页面
	 */
	private boolean isRefresh = true;
	/**
	 * 是否Post提交
	 */
	private String method = "get";
	/**
	 * 提交参数
	 */
	private Map<String, Object> data;

	public AjaxResult() {
		this.data = new HashMap<String, Object>();
	}

	public boolean getisRefresh() {
		return isRefresh;
	}

	public void setIsRefresh(boolean isRefresh) {
		this.isRefresh = isRefresh;
	}

	private T model;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public T getModel() {
		return model;
	}

	public void setModel(T model) {
		this.model = model;
	}

	public String getHandleCode() {
		return handleCode;
	}

	public void setHandleCode(String handleCode) {
		this.handleCode = handleCode;
	}

	public String getHandleDesc() {
		return handleDesc;
	}

	public void setHandleDesc(String handleDesc) {
		this.handleDesc = handleDesc;
	}

	public String getToUrl() {
		return toUrl;
	}

	public void setToUrl(String toUrl) {
		this.toUrl = toUrl;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public boolean getIsRefresh() {
		return isRefresh;
	}

	public int getLazyMimillionSecond() {
		return lazyMimillionSecond;
	}

	public void setLazyMimillionSecond(int lazyMimillionSecond) {
		this.lazyMimillionSecond = lazyMimillionSecond;
	}

	public String getAutoUrl() {
		return autoUrl;
	}

	public void setAutoUrl(String autoUrl) {
		this.autoUrl = autoUrl;
	}
}
