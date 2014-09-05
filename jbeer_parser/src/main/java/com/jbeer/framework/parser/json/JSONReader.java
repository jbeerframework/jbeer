/**   
 * @Title: JSON.java
 * @Package com.jbeer.framework.parser.json
 * @author Bieber
 * @date 2014年7月16日 下午5:28:40
 * @version V1.0   
 */

package com.jbeer.framework.parser.json;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jbeer.framework.exception.JBeerException;
import com.jbeer.framework.parser.exception.ParseException;
import com.jbeer.framework.utils.CaseUtils;
import com.jbeer.framework.utils.ObjectUtil;
import com.jbeer.framework.utils.StringUtils;

/**
 * <p>
 * 类功能说明:JSON解析工具
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: JSON.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月16日 下午5:28:40
 * @version V1.0
 */

public final class JSONReader {

	/**
	 * 
	 * <p>
	 * 函数功能说明:将JSON解析成为制定类型的对象集合
	 * </p>
	 * <p>
	 * Bieber 2014年7月20日
	 * </p>
	 * <p>
	 * 修改者名字 修改日期
	 * </p>
	 * <p>
	 * 修改内容</a>
	 * 
	 * @return List<T>
	 */
	protected static <T> List<T> readList(byte[] jsonBytes, Class<T> type)
			throws JBeerException {
		ByteArrayInputStream bais = new ByteArrayInputStream(jsonBytes);
		return readList(bais, type);
	}/**
	 * 
	 * <p>
	 * 函数功能说明:将JSON解析成为制定类型的对象集合
	 * </p>
	 * <p>
	 * Bieber 2014年7月20日
	 * </p>
	 * <p>
	 * 修改者名字 修改日期
	 * </p>
	 * <p>
	 * 修改内容</a>
	 * 
	 * @return List<T>
	 */
	protected static <T> List<T> readList(byte[] jsonBytes, Class<T> type,String encoding)
			throws JBeerException {
		ByteArrayInputStream bais = new ByteArrayInputStream(jsonBytes);
		return readList(bais, type, encoding);
	}
	/**
	 * 
	 * <p>
	 * 函数功能说明:将JSON解析成为制定类型的对象集合
	 * </p>
	 * <p>
	 * Bieber 2014年7月20日
	 * </p>
	 * <p>
	 * 修改者名字 修改日期
	 * </p>
	 * <p>
	 * 修改内容</a>
	 * 
	 * @return List<T>
	 */
	protected static <T> List<T> readList(String json, Class<T> type,String encoding)
			throws JBeerException {
		return readList(json.getBytes(), type,encoding);
	}
	/**
	 * 
	 * <p>
	 * 函数功能说明:将JSON解析成为制定类型的对象集合
	 * </p>
	 * <p>
	 * Bieber 2014年7月20日
	 * </p>
	 * <p>
	 * 修改者名字 修改日期
	 * </p>
	 * <p>
	 * 修改内容</a>
	 * 
	 * @return List<T>
	 */
	protected static <T> List<T> readList(String json, Class<T> type)
			throws JBeerException {
		return readList(json.getBytes(), type);
	}
	/**
	 * 
	* <p>函数功能说明:将JSON解析成为制定类型的对象集合</p>
	* <p>Bieber  2014年7月20日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return List<T>
	 */
	protected static <T> List<T> readList(InputStream is,Class<T> type) throws JBeerException{
		return readList(is, type,null);
	}
	/**
	 * 
	* <p>函数功能说明:将JSON解析成为制定类型的对象集合,并且指定字符集</p>
	* <p>Bieber  2014年7月20日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return List<T>
	 */
	@SuppressWarnings("unchecked")
	protected static <T> List<T> readList(InputStream is,Class<T> type,String encoding) throws JBeerException{
		try {
			List<Object> list = (List<Object>) parseJson(is,encoding);
			List<T> values = new ArrayList<T>();
			if (CaseUtils.checkIsBasicType(type)) {
				for (Object item : list) {
					values.add((T) CaseUtils.caseType(type, item.toString()));
				}
			} else {
				for (Object item : list) {
					T value = type.newInstance();
					Map<String, Object> itemMap = (Map<String, Object>) item;
					ObjectUtil.mapToObject(itemMap, value);
					values.add(value);
				}
			}
			return values;
		} catch (Exception e) {
			throw new JBeerException(e);
		}
	}

	/**
	 * 
	 * <p>
	 * 函数功能说明:将json解析成指定类型的对象
	 * </p>
	 * <p>
	 * Bieber 2014年7月20日
	 * </p>
	 * <p>
	 * 修改者名字 修改日期
	 * </p>
	 * <p>
	 * 修改内容</a>
	 * 
	 * @return T
	 */
	protected static <T> T readObject(byte[] jsonBytes, Class<T> type)
			throws JBeerException {
		ByteArrayInputStream bais = new ByteArrayInputStream(jsonBytes);
		return readObject(bais,type); 
	}
	

	/**
	 * 
	 * <p>
	 * 函数功能说明:将json解析成指定类型的对象,指定编码集合
	 * </p>
	 * <p>
	 * Bieber 2014年7月20日
	 * </p>
	 * <p>
	 * 修改者名字 修改日期
	 * </p>
	 * <p>
	 * 修改内容</a>
	 * 
	 * @return T
	 */
	protected static <T> T readObject(byte[] jsonBytes, Class<T> type,String encoding)
			throws JBeerException {
		ByteArrayInputStream bais = new ByteArrayInputStream(jsonBytes);
		return readObject(bais,type,encoding); 
	}
	/**
	 * 
	 * <p>
	 * 函数功能说明:将json解析成指定类型的对象
	 * </p>
	 * <p>
	 * Bieber 2014年7月20日
	 * </p>
	 * <p>
	 * 修改者名字 修改日期
	 * </p>
	 * <p>
	 * 修改内容</a>
	 * 
	 * @return T
	 */
	protected static <T> T readObject(InputStream is, Class<T> type)
			throws JBeerException {
		return readObject(is,type,null);
	}
	
	/**
	 * 
	 * <p>
	 * 函数功能说明:将json解析成指定类型的对象
	 * </p>
	 * <p>
	 * Bieber 2014年7月20日
	 * </p>
	 * <p>
	 * 修改者名字 修改日期
	 * </p>
	 * <p>
	 * 修改内容</a>
	 * 
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	protected static <T> T readObject(InputStream is, Class<T> type,String encoding)
			throws JBeerException {
		try {
			Map<String, Object> map = (Map<String, Object>) parseJson(is,encoding);
			T value = type.newInstance();
			ObjectUtil.mapToObject(map, value);
			return value;
		} catch (Exception e) {
			throw new JBeerException(e);
		}
	}
	
	/**
	 * 
	 * <p>
	 * 函数功能说明:将json解析成指定类型的对象,指定编码集合
	 * </p>
	 * <p>
	 * Bieber 2014年7月20日
	 * </p>
	 * <p>
	 * 修改者名字 修改日期
	 * </p>
	 * <p>
	 * 修改内容</a>
	 * 
	 * @return T
	 */
	protected static <T> T readObject(String json, Class<T> type,String encoding)
			throws JBeerException {
		return readObject(json.getBytes(), type,encoding);
	}

	/**
	 * 
	 * <p>
	 * 函数功能说明:将json解析成指定类型的对象
	 * </p>
	 * <p>
	 * Bieber 2014年7月20日
	 * </p>
	 * <p>
	 * 修改者名字 修改日期
	 * </p>
	 * <p>
	 * 修改内容</a>
	 * 
	 * @return T
	 */
	protected static <T> T readObject(String json, Class<T> type)
			throws JBeerException {
		return readObject(json.getBytes(), type);
	}

	/**
	 * 
	 * <p>
	 * 函数功能说明:直接反馈json解析成的对象，如果是json数组则返回{@link List} 如果是json对象则解析成为{@link Map}
	 * </p>
	 * <p>
	 * Bieber 2014年7月20日
	 * </p>
	 * <p>
	 * 修改者名字 修改日期
	 * </p>
	 * <p>
	 * 修改内容</a>
	 * 
	 * @return Object
	 */
	protected static Object readOriginJsonData(String json) throws JBeerException {
		return readOriginJsonData(json,null);
	}
	/**
	 * 
	 * <p>
	 * 函数功能说明:直接反馈json解析成的对象，如果是json数组则返回{@link List} 如果是json对象则解析成为{@link Map}
	 * </p>
	 * <p>
	 * Bieber 2014年7月20日
	 * </p>
	 * <p>
	 * 修改者名字 修改日期
	 * </p>
	 * <p>
	 * 修改内容</a>
	 * 
	 * @return Object
	 */
	protected static Object readOriginJsonData(byte[] jsonBytes) throws JBeerException {
		ByteArrayInputStream bais = new ByteArrayInputStream(jsonBytes);
		return readOriginJsonData(bais,null);
	}
	/**
	 * 
	 * <p>
	 * 函数功能说明:直接反馈json解析成的对象，如果是json数组则返回{@link List} 如果是json对象则解析成为{@link Map}
	 * </p>
	 * <p>
	 * Bieber 2014年7月20日
	 * </p>
	 * <p>
	 * 修改者名字 修改日期
	 * </p>
	 * <p>
	 * 修改内容</a>
	 * 
	 * @return Object
	 */
	protected static Object readOriginJsonData(byte[] jsonBytes,String encodig) throws JBeerException {
		ByteArrayInputStream bais = new ByteArrayInputStream(jsonBytes);
		return readOriginJsonData(bais,encodig);
	}
	/**
	 * 
	 * <p>
	 * 函数功能说明:直接反馈json解析成的对象，如果是json数组则返回{@link List} 如果是json对象则解析成为{@link Map}
	 * </p>
	 * <p>
	 * Bieber 2014年7月20日
	 * </p>
	 * <p>
	 * 修改者名字 修改日期
	 * </p>
	 * <p>
	 * 修改内容</a>
	 * 
	 * @return Object
	 */
	protected static Object readOriginJsonData(String json,String encoding) throws JBeerException {
		try {
			return readOriginJsonData(json.getBytes(),encoding);
		} catch (Exception e) {
			throw new JBeerException(e);
		}
	}
	
	/**
	 * 
	 * <p>
	 * 函数功能说明:直接反馈json解析成的对象，如果是json数组则返回{@link List} 如果是json对象则解析成为{@link Map}
	 * </p>
	 * <p>
	 * Bieber 2014年7月20日
	 * </p>
	 * <p>
	 * 修改者名字 修改日期
	 * </p>
	 * <p>
	 * 修改内容</a>
	 * 
	 * @return Object
	 */
	protected static Object readOriginJsonData(InputStream is) throws JBeerException {
		try {
			return parseJson(is,null);
		} catch (Exception e) {
			throw new JBeerException(e);
		}
	}
	
	/**
	 * 
	 * <p>
	 * 函数功能说明:直接反馈json解析成的对象，如果是json数组则返回{@link List} 如果是json对象则解析成为{@link Map}
	 * </p>
	 * <p>
	 * Bieber 2014年7月20日
	 * </p>
	 * <p>
	 * 修改者名字 修改日期
	 * </p>
	 * <p>
	 * 修改内容</a>
	 * 
	 * @return Object
	 */
	protected static Object readOriginJsonData(InputStream is,String encoding) throws JBeerException {
		try {
			return parseJson(is,encoding);
		} catch (Exception e) {
			throw new JBeerException(e);
		}
	}
	
	private static Object parseJson(InputStream is, String encoding)
			throws IOException, ParseException {
		DefaultJSONContentHandler handler = new DefaultJSONContentHandler();
		if (StringUtils.isEmpty(encoding)) {
			JSONParser.newParser(is, handler).parseJSON();
		} else {
			JSONParser.newParser(is, handler, encoding).parseJSON();
		}
		if (handler.isArray()) {
			return handler.getList();
		} else {
			return handler.getMap();
		}
	}
}
