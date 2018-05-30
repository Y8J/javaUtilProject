package com.zjht.youoil.util.tmall.computor;

import java.util.List;

import com.google.common.base.Function;
import com.zjht.channel.component.util.CommonHelper;
import com.zjht.channel.component.util.math.SimilarHelper;
import com.zjht.channel.component.util.string.RegExpHelper;
import com.zjht.channel.spi.msg.tuple.Tuple;
import com.zjht.channel.spi.service.compute.impl.AbstractChannelTupleMsgSimilarComputeTask;
import com.zjht.channel.spi.util.HashCodeHelper;
import com.zjht.youoil.util.GuavaFunctionHelper;

/**
 * 
 * @Project:ZJHTOrderProc
 * @Title: TmallSimilarComputor.java
 * @Package com.zjht.orderproc.tmall.computor
 * @Description: tm订单相似计算辅助类
 * @See:
 * @author: bwzhang
 * @Email: zhangbowen@chinaexpresscard.com
 * @modified:
 * @date 2014年12月6日 上午11:09:22
 * @CopyEdition:中经汇通有限责任公司2014版权所有
 * @version V1.0
 */
public class TmallSimilarComputor extends AbstractChannelTupleMsgSimilarComputeTask<Tuple> {
	public TmallSimilarComputor(List<Tuple> batchMsg) {
		super(batchMsg);
	}

	@Override
	public String getCheckValue(Tuple tupleMsg) {
		String address = (String) (tupleMsg.get(3));

		return address.replaceAll(RegExpHelper.RegExp.BRACKET_ZIPCODE.getRegExp(), "");
	}

	@Override
	public boolean checkSimilar(String value1, String value) {
		return SimilarHelper.checkIsSimilar(value1, value);
	}

	/**
	 * 收件人姓名、手机号、地址、订单信息列表、客户信息列表、地址信息列表
	 */
	@Override
	public Tuple getWrapTupleMsg(List<Tuple> similarTupleList) {
		List<String> addrList = GuavaFunctionHelper.transformList(similarTupleList, new Function<Tuple, String>() {

			@Override
			public String apply(Tuple input) {
				return (String) input.get(3);
			}

		});

		List<String> orderList = GuavaFunctionHelper.transformList(similarTupleList, new Function<Tuple, String>() {

			@Override
			public String apply(Tuple input) {
				return (String) input.get(0);
			}

		});

		List<String> custNameList = GuavaFunctionHelper.transformList(similarTupleList, new Function<Tuple, String>() {

			@Override
			public String apply(Tuple input) {
				return (String) input.get(2);
			}

		});

		Tuple similarTupleMsg = similarTupleList.get(0);

		Object[] similarTupleParams = new Object[] { similarTupleMsg.get(0), similarTupleMsg.get(1), similarTupleMsg.get(2),similarTupleMsg.get(3),similarTupleMsg.get(4),
				CommonHelper.getPrintCollection(orderList), CommonHelper.getPrintCollection(custNameList),
				CommonHelper.getPrintCollection(addrList) };

		return Tuple.of(similarTupleParams);
	}

	@Override
	public int getComputeThreshHold() {
		return 500;
	}

	/**
	 * 收件人姓名、手机号、地址、订单信息列表、客户信息列表、地址信息列表
	 * 
	 * ---使用(收件人姓名,手机号)的hash
	 */
	@Override
	public int getMsgHash(Tuple msg) {
		Object[] array = msg.toArray();

		int result = HashCodeHelper.SEED;

		result = HashCodeHelper.hash(result, array[2]);

		result = HashCodeHelper.hash(result, array[4]);

		return result;
	}

	@Override
	public Tuple getTupleMsg(Tuple msg) {
		return msg;
	}

	@Override
	public List<Tuple> getComputeMsgList(List<Tuple> tupleList) {
		return tupleList;
	}

	@Override
	public List<List<Tuple>> getThreshHoldGroupList(List<Tuple> batchMsg, int threshHold) {
		return GuavaFunctionHelper.getRangeList(batchMsg, threshHold);
	}

}
