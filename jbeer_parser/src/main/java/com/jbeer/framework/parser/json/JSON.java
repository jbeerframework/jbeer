/**   
* @Title: JSON.java
* @Package com.jbeer.framework.parser.json
* @author Bieber
* @date 2014年7月23日 下午10:10:56
* @version V1.0   
*/

package com.jbeer.framework.parser.json;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.jbeer.framework.exception.JBeerException;

/**
 * <p>类功能说明:JSON工具入库类</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: JSON.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月23日 下午10:10:56
 * @version V1.0
 */

public abstract class JSON {

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
	public static <T> List<T> readList(byte[] jsonBytes, Class<T> type)
			throws JBeerException {
		return JSONReader.readList(jsonBytes, type);
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
	public static <T> List<T> readList(byte[] jsonBytes, Class<T> type,String encoding)
			throws JBeerException {
		return JSONReader.readList(jsonBytes, type, encoding);
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
	public static <T> List<T> readList(String json, Class<T> type,String encoding)
			throws JBeerException {
		return JSONReader.readList(json, type, encoding);
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
	public static <T> List<T> readList(String json, Class<T> type)
			throws JBeerException {
		return JSONReader.readList(json, type);
	}
	/**
	 * 
	* <p>函数功能说明:将JSON解析成为制定类型的对象集合</p>
	* <p>Bieber  2014年7月20日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return List<T>
	 */
	public static <T> List<T> readList(InputStream is,Class<T> type) throws JBeerException{
		return JSONReader.readList(is, type);
	}
	/**
	 * 
	* <p>函数功能说明:将JSON解析成为制定类型的对象集合,并且指定字符集</p>
	* <p>Bieber  2014年7月20日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return List<T>
	 */
	public static <T> List<T> readList(InputStream is,Class<T> type,String encoding) throws JBeerException{
		 return JSONReader.readList(is, type, encoding);
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
	public static <T> T readObject(byte[] jsonBytes, Class<T> type)
			throws JBeerException {
		return JSONReader.readObject(jsonBytes, type);
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
	public static <T> T readObject(byte[] jsonBytes, Class<T> type,String encoding)
			throws JBeerException {
		return JSONReader.readObject(jsonBytes, type, encoding);
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
	public static <T> T readObject(InputStream is, Class<T> type)
			throws JBeerException {
		return JSONReader.readObject(is, type);
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
	public static <T> T readObject(InputStream is, Class<T> type,String encoding)
			throws JBeerException {
		return JSONReader.readObject(is, type, encoding);
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
	public static <T> T readObject(String json, Class<T> type,String encoding)
			throws JBeerException {
		return JSONReader.readObject(json, type, encoding);
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
	public static <T> T readObject(String json, Class<T> type)
			throws JBeerException {
		return JSONReader.readObject(json, type);
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
	public static Object readOriginJsonData(String json) throws JBeerException {
		return JSONReader.readOriginJsonData(json);
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
	public static Object readOriginJsonData(byte[] jsonBytes) throws JBeerException {
		 return JSONReader.readOriginJsonData(jsonBytes);
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
	public static Object readOriginJsonData(byte[] jsonBytes,String encodig) throws JBeerException {
		return JSONReader.readOriginJsonData(jsonBytes, encodig);
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
	public static Object readOriginJsonData(String json,String encoding) throws JBeerException {
		return JSONReader.readOriginJsonData(json, encoding);
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
	public static Object readOriginJsonData(InputStream is) throws JBeerException {
		 return JSONReader.readOriginJsonData(is);
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
	public static Object readOriginJsonData(InputStream is,String encoding) throws JBeerException {
		return JSONReader.readOriginJsonData(is, encoding);
	}
	/**
	 * 
	* <p>函数功能说明:将一个对象解析成一个json字符串，使用默认编码集</p>
	* <p>Bieber  2014年7月23日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return String
	 */
	public static String writeToJson(Object obj) throws JBeerException {
		return JSONWriter.writeToJson(obj);
	}

	/**
	 * 
	* <p>函数功能说明:将一个对象解析成一个json字符串，使用指定字符集</p>
	* <p>Bieber  2014年7月23日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return String
	 */
	public static String writeToJson(Object obj, String encoding) throws JBeerException {
		 return JSONWriter.writeToJson(obj, encoding);
	}

	 
	
}
