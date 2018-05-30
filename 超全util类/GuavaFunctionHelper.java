package com.zjht.youoil.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class GuavaFunctionHelper {
	private GuavaFunctionHelper() {

	}

	/**
	 * 转换为Collection的内部函数式
	 * 
	 * @param fromCollection
	 * @param function
	 * @return
	 * @version: 2013-10-30 上午9:33:16
	 * @See:
	 */
	public static <F, T> Collection<T> transform(Collection<F> fromCollection, Function<? super F, T> function) {
		return Collections2.transform(fromCollection, function);
	}

	/**
	 * 转换成List的内部函数式
	 * 
	 * @param fromList
	 * @param function
	 * @return
	 * @version: 2013-10-30 上午9:33:31
	 * @See:
	 */
	public static <F, T> List<T> transformList(List<F> fromList, Function<? super F, T> function) {
		return Lists.transform(fromList, function);
	}

	/**
	 * 
	 * @Title: transformCollection
	 * @Description: 转换collection函数
	 * @param @param fromCollection
	 * @param @param function
	 * @param @return
	 * @return Collection<T>
	 * @throws
	 */
	public static <F, T> Collection<T> transformCollection(Collection<F> fromCollection, Function<? super F, T> function) {
		return Collections2.transform(fromCollection, function);
	}

	/**
	 * 构造functionMap
	 * 
	 * @param paraMap
	 * @return
	 * @version: 2013-10-30 上午9:35:27
	 * @See:
	 */
	public static <K, V> Function<K, V> transformMap(Map<K, V> paraMap) {
		return Functions.forMap(paraMap);
	}

	/**
	 * 根据分组大小对于list进行分组
	 * 
	 * @param list
	 * @param size
	 * @return
	 * @version: 2013-9-27 下午1:52:11
	 * @See:
	 */
	public static <T> List<List<T>> getRangeList(List<T> list, int size) {
		return Lists.partition(list, size);
	}

	/**
	 * 
	 * @Title: transformValues
	 * @Description: 将fromMap中的value进行转换
	 * @param @param fromMap
	 * @param @param function
	 * @param @return
	 * @return Map<K,V2>
	 * @throws
	 */
	public static <K, V1, V2> Map<K, V2> transformValues(Map<K, V1> fromMap, Function<? super V1, V2> function) {
		return Maps.transformValues(fromMap, function);
	}
}
