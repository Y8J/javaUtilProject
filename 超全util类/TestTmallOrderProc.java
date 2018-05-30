package com.zjht.youoil.util;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.zjht.channel.component.excel.IChannelCfgBeanConvert;
import com.zjht.channel.component.excel.ISheetDef;
import com.zjht.channel.spi.define.config.ChannelTupleConfigBean;
import com.zjht.channel.spi.define.config.IChannelConfigBean;
import com.zjht.channel.spi.msg.tuple.Tuple;
import com.zjht.channel.spi.service.compute.IChannelTupleMsgComputor;
import com.zjht.channel.spi.service.compute.impl.AbstractChannelTupleMsgComputeTask;
import com.zjht.channel.spi.service.compute.impl.DefaultChannelTupleMsgComputor;
import com.zjht.channel.spi.util.StreamFileHelper;
import com.zjht.youoil.util.tmall.computor.TmallSimilarComputor;
import com.zjht.youoil.util.tmall.excel.TmallOrderExcelConvertImpl;
import com.zjht.youoil.util.tmall.excel.TmallSheefDefine;

public class TestTmallOrderProc {
	public static void main(String[] args) throws Exception {
		InputStream input = StreamFileHelper.getBufferedInputStream(new File("test2.xls"));

		IChannelCfgBeanConvert tmConvert = new TmallOrderExcelConvertImpl();

		Map<ISheetDef, List<IChannelConfigBean>> paraMap = tmConvert.getExcelConvertBeanMap(input);

		List<IChannelConfigBean> cfgList = paraMap.get(TmallSheefDefine.TEST);
		
		System.out.println(cfgList.size());

		List<Tuple> batchMsg = GuavaFunctionHelper.transformList(cfgList, new Function<IChannelConfigBean, Tuple>() {

			@Override
			public Tuple apply(IChannelConfigBean input) {
				ChannelTupleConfigBean tupleCfgBean = (ChannelTupleConfigBean) input;

				return tupleCfgBean.getTupleCfgMsg();
			}

		});

		IChannelTupleMsgComputor<Tuple> tmTupleComputor = new TmTupleMsgComputor();

		List<Tuple> results = tmTupleComputor.getComputeTupleList(batchMsg);
		
		for (Tuple tuple : results) {
			System.out.println(tuple.toString());
			//System.out.println(tuple.get(0).toString()+tuple.get(1).toString()+tuple.get(2).toString());;
		}
		
		//System.out.println("compute===" + results.toString());
		
		
//		Tuple test = Tuple.of("aa","bb");
//		
//		Object[] array = test.toArray();
//		
//		System.out.println(array[0].toString() + " " + array[1].toString());

		
	}

	private static class TmTupleMsgComputor extends DefaultChannelTupleMsgComputor<Tuple> {

		@Override
		public long getComputorTimeout() {
			return DEFAULT_COMPUTOR_TIMEOUT;
		}

		@Override
		public AbstractChannelTupleMsgComputeTask<Tuple> getChannelTupleForkJoinTask(List<Tuple> batchMsg) {
			return new TmallSimilarComputor(batchMsg);
		}

	}
}
