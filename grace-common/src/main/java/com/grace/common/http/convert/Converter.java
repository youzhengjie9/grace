package com.grace.common.http.convert;

import java.io.IOException;

/**
 * 转换器顶层接口，实现类型转换.
 * <p>
 * 子实现类命名规则:
 * 例如:Integer转换成String,则新建一个IntegerStringConverter,这个类中写Integer转换成String的方法
 *
 * @param <T> 需要转换成的类型。例如:Integer转换成String,那么<T>就是String
 * @author youzhengjie
 * @date 2023/08/21 18:01:38
 */
public interface Converter<T> {

	/**
	 * 将source转成指定的类型（Converter的泛型T）
	 *
	 * @param source 原始值
	 * @return 转换后的值
	 */
	T convert(Object source) throws IOException;


}
