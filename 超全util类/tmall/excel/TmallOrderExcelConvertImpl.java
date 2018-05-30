package com.zjht.youoil.util.tmall.excel;

import java.util.Map;

import com.zjht.channel.component.excel.AbstractChannelCfgBeanConvertImpl;
import com.zjht.channel.component.excel.ISheetDef;
import com.zjht.channel.spi.constant.ZJHTCore;
import com.zjht.channel.spi.util.ObjectHelper;

/**
 * 
 * @Project:ZJHTOrderProc 
 * @Title: TmallOrderExcelConvertImpl.java
 * @Package com.zjht.orderproc.tmall.excel
 * @Description: tm excel文件转换入口类(对应流读取等)
 * @See:
 * @author: bwzhang
 * @Email:  zhangbowen@chinaexpresscard.com
 * @modified: 
 * @date 2014年12月6日 上午11:08:26
 * @CopyEdition:中经汇通有限责任公司2014版权所有
 * @version V1.0
 */
public class TmallOrderExcelConvertImpl extends AbstractChannelCfgBeanConvertImpl {
	private final static Map<String, ISheetDef> sheetDefMap = ObjectHelper.newHashMap();

	static {
		sheetDefMap.put(TmallSheefDefine.TEST.getName(), TmallSheefDefine.TEST);
	}

	@Override
	public String[] getFilterSheetNames() {
		return ZJHTCore.DEFAULT_EMPTY_STRS;
	}

	@Override
	public int getSheetStartRwIndex(String sheetName) {
		return 1;
	}

	@Override
	public Map<String, ISheetDef> getSheetDfMap() {
		return sheetDefMap;
	}

	@Override
	public Map<ISheetDef, IChannelConfigBeanConvert> getChannelCfgBeanConvertMap() throws Exception {
		Map<ISheetDef, IChannelConfigBeanConvert> beanConvertMap = ObjectHelper.newLinkedHashMap();

		beanConvertMap.put(TmallSheefDefine.TEST, new TmallCfgBeanConvert());

		return beanConvertMap;
	}
}
