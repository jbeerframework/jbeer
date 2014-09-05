/**   
 * @Title: JSONWriter.java
 * @Package com.jbeer.framework.parser.json
 * @author Bieber
 * @date 2014年7月23日 下午9:11:40
 * @version V1.0   
 */

package com.jbeer.framework.parser.json;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.jbeer.framework.exception.JBeerException;
import com.jbeer.framework.utils.CaseUtils;
import com.jbeer.framework.utils.ClassUtils;

/**
 * <p>
 * 类功能说明:输出json工具类
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: JSONWriter.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月23日 下午9:11:40
 * @version V1.0
 */

public final class JSONWriter {

	/**
	 * 
	* <p>函数功能说明:将一个对象解析成一个json字符串，使用默认编码集</p>
	* <p>Bieber  2014年7月23日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return String
	 */
	protected static String writeToJson(Object obj) throws JBeerException {
		StringBuffer jsonStr = new StringBuffer();
		writeJson(obj,jsonStr);
		return jsonStr.toString();
	}

	/**
	 * 
	* <p>函数功能说明:将一个对象解析成一个json字符串，使用指定字符集</p>
	* <p>Bieber  2014年7月23日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return String
	 */
	protected static String writeToJson(Object obj, String encoding) throws JBeerException {
		try{
		String jsonStr=writeToJson(obj);
		return new String(jsonStr.getBytes(),encoding);
		}catch(Exception e){
			throw new JBeerException("write json str error", e);
		}
	}

	private static void writeJson(Object obj,StringBuffer jsonStr) throws JBeerException {
		try{
		if(obj==null){
			jsonStr.append("null");
		}else if (Collection.class.isAssignableFrom(obj.getClass())
				|| Iterator.class.isAssignableFrom(obj.getClass())||obj.getClass().isArray()) {
			 writeJsonArray(obj,jsonStr);
		}else if(CaseUtils.checkIsBasicType(obj.getClass())){
			 writeJsonValue(obj,jsonStr);
		}else{
			 writeJsonObject(obj,jsonStr);
		}
		}catch(Exception e){
			throw new JBeerException("write json error", e);
		}
		
	}

	@SuppressWarnings({ "unchecked" })
	private static void writeJsonObject(Object obj,StringBuffer jsonStr) throws Exception, JBeerException {
		jsonStr.append("{");
		boolean isNotEmpty=false;
		if(Map.class.isAssignableFrom(obj.getClass())){
			Map<Object,Object> mapObj = (Map<Object, Object>) obj;
			if(mapObj.size()>0){
				isNotEmpty=true;
			}
			for(Entry<Object,Object> entry:mapObj.entrySet()){
				writeJsonString(entry.getKey(),jsonStr);
				jsonStr.append(":");
				Object value = entry.getValue();
				writeJson(value,jsonStr);
				jsonStr.append(",");
			}
		}else{
			Field[] fields = obj.getClass().getDeclaredFields();
			if(fields.length>0){
				isNotEmpty=true;
			}
			for(Field field:fields){
				Method  getMethod= ClassUtils.searchGetMethod(obj.getClass(), field.getName());
				if(getMethod!=null){
					writeJsonString(field.getName(),jsonStr);
					jsonStr.append(":");
					writeJson(getMethod.invoke(obj, new Object[]{}),jsonStr);
					jsonStr.append(",");
				}
			}
		}
		if(isNotEmpty){
		jsonStr.setLength(jsonStr.length()-1);
		}
		jsonStr.append("}");
	}

	@SuppressWarnings("unchecked")
	private static void writeJsonArray(Object obj,StringBuffer jsonStr) throws JBeerException {
		jsonStr.append("[");
		boolean isNotEmpty=false;
		if(obj.getClass().isArray()){
			Object[] objs = (Object[]) obj;
			if(objs.length>0){
				isNotEmpty=true;
			}
			for(Object element:objs){
				 writeJson(element,jsonStr);
				 jsonStr.append(",");
			}
		}else if(Collection.class.isAssignableFrom(obj.getClass())){
			Collection<Object> collection = (Collection<Object>) obj;
			if(collection.size()>0){
				isNotEmpty=true;
			}
			for(Object element:collection){
				 writeJson(element,jsonStr);
				 jsonStr.append(",");
			}
		}else if(Iterator.class.isAssignableFrom(obj.getClass())){
			Iterator<Object> iterator = (Iterator<Object>) obj;
			while(iterator.hasNext()){
				isNotEmpty=true;
				Object element = iterator.next();
				 writeJson(element,jsonStr);
				 jsonStr.append(",");
			}
		}
		if(isNotEmpty){
		jsonStr.setLength(jsonStr.length()-1);
		}
		jsonStr.append("]");
	}

	private static void writeJsonValue(Object obj,StringBuffer jsonStr) {
		if(CaseUtils.checkIsBasicType(obj.getClass())){
			if(String.class==obj.getClass()||obj.getClass().isEnum()){
				writeJsonString(obj,jsonStr);
			}else{
				jsonStr.append(obj.toString());
			}
		}else{
			jsonStr.append(obj.toString());
		}
	}

	private static void writeJsonString(Object obj,StringBuffer jsonStr) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] contentBytes = obj.toString().getBytes();
		for(byte b:contentBytes){
			if(b==34){
				bos.write(92);
				bos.write(b);
			}else if(b==9){
				bos.write(92);
				bos.write('t');
			}else if(b==13){
				bos.write(92);
				bos.write('r');
			}else if(b==10){
				bos.write(92);
				bos.write('n');
			}else{
				bos.write(b);
			}
		}
		jsonStr.append("\"").append(new String(bos.toByteArray())).append("\"");
	}

	
	
}
