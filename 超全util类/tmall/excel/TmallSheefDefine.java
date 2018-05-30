package com.zjht.youoil.util.tmall.excel;

import com.zjht.channel.component.excel.ISheetDef;

/**
 * 
 * @Project:ZJHTOrderProc
 * @Title: TmallSheefDefine.java
 * @Package com.zjht.orderproc.tmall.excel
 * @Description: 文件列属性枚举
 * @See:
 * @author: bwzhang
 * @Email: zhangbowen@chinaexpresscard.com
 * @modified:
 * @date 2014年12月6日 上午11:09:08
 * @CopyEdition:中经汇通有限责任公司2014版权所有
 * @version V1.0
 */
public enum TmallSheefDefine implements ISheetDef {

	TEST("test", 0);

	private final String name;

	private final Integer order;

	private TmallSheefDefine(String name, Integer order) {
		this.name = name;
		this.order = order;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Integer getOrder() {
		return this.order;
	}

}
