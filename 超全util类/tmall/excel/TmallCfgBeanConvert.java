package com.zjht.youoil.util.tmall.excel;

import java.util.List;

import jxl.Cell;

import com.zjht.channel.component.excel.AbstractChannelCfgBeanConvertImpl.AbstractChannelCfgBeanConvert;
import com.zjht.channel.spi.define.config.ChannelTupleConfigBean;
import com.zjht.channel.spi.define.config.IChannelConfigBean;
import com.zjht.channel.spi.msg.tuple.Tuple;

/**
 * 
 * @Project:ZJHTOrderProc
 * @Title: TmallCfgBeanConvert.java
 * @Package com.zjht.orderproc.tmall.excel
 * @Description: tm excel文件读取转换类
 * @See:
 * @author: bwzhang
 * @Email: zhangbowen@chinaexpresscard.com
 * @modified:
 * @date 2014年12月6日 上午10:58:25
 * @CopyEdition:中经汇通有限责任公司2014版权所有
 * @version V1.0
 */
public class TmallCfgBeanConvert extends AbstractChannelCfgBeanConvert {
	@Override
	public IChannelConfigBean getChannelConfigBean() {
		return new ChannelTupleConfigBean();
	}

	/**
	 * 收件人姓名、手机号、地址、订单信息列表、客户信息列表、地址信息列表
	 */
	@Override
	public void wrapCellsAndCheck(IChannelConfigBean cfgBean, Cell[] cells, List<String> errorList) {
		ChannelTupleConfigBean tupleConfigBean = (ChannelTupleConfigBean) cfgBean;

		String orderNo = cells[0].getContents();//订单号

		String custName = cells[1].getContents();//客户名

		String recvName = cells[2].getContents();//收件人名字

		String address = cells[3].getContents();//收货地址

		String mobile = cells[4].getContents();//收货人手机号

		Tuple tupleMsg = Tuple.of(new Object[] { recvName, mobile, address, orderNo, custName, "" });

		tupleConfigBean.setTupleCfgMsg(tupleMsg);
	}

	@Override
	public String getCfgBeanKey(IChannelConfigBean cfgBean) {
		ChannelTupleConfigBean tupleConfigBean = (ChannelTupleConfigBean) cfgBean;

		Tuple tupleMsg = tupleConfigBean.getTupleCfgMsg();

		Object[] array = tupleMsg.toArray();

		return new StringBuilder().append(array[0]).append(array[3]).append(array[4]).toString();
	}

	@Override
	public boolean checkCfgBeanKey() {
		return false;
	}
}
