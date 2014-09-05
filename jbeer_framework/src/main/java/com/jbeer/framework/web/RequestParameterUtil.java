/**   
 * @Title: RequestParameterUtil.java
 * @Package com.jbeer.framework.utils
 * @author Bieber
 * @date 2014年6月16日 上午11:27:37
 * @version V1.0   
 */

package com.jbeer.framework.web;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.jbeer.framework.exception.JBeerException;
import com.jbeer.framework.utils.CaseUtils;
import com.jbeer.framework.utils.ObjectUtil;

/**
 * <p>
 * 类功能说明:包装请求参数的工具类
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: RequestParameterUtil.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年6月16日 上午11:27:37
 * @version V1.0
 */

public class RequestParameterUtil {

	@SuppressWarnings("unchecked")
	public static <T> T getObject(String fieldName, Class<T> type) {
		try {
			Map<String, Object> requestParameters = JBeerWebContext
					.getRequestParameters();
			if (CaseUtils.checkIsBasicType(type)) {
				return (T) CaseUtils.caseType(type,
						requestParameters.get(fieldName).toString());
			} else {
				Object fieldMap = requestParameters.get(fieldName);
				if (fieldMap!=null&&Map.class.isAssignableFrom(fieldMap.getClass())) {
					T result = type.newInstance();
					ObjectUtil
							.mapToObject((Map<String, Object>) fieldMap, result);
					return result;
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			throw new IllegalAccessError("failed to get parameter "+fieldName+" case by:"+e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> getList(String fieldName, Class<T> type) {
		Map<String, Object> requestParameters = JBeerWebContext
				.getRequestParameters();
		try {
			Object fieldValue = requestParameters.get(fieldName);
			if(fieldValue==null){
				return null;
			}else if(List.class.isAssignableFrom(fieldValue.getClass())){
				List<Map<String,Object>> fieldList = (List<Map<String, Object>>) fieldValue;
				List<T> results = new ArrayList<T>(fieldList.size()); 
				for(Map<String,Object> field:fieldList){
					T value = type.newInstance();
					ObjectUtil.mapToObject(field, value);
					results.add(value);
				}
				return results;
			}else if(Map.class.isAssignableFrom(fieldValue.getClass())){
				List<T> results = new ArrayList<T>(1);
				T value = type.newInstance();
				ObjectUtil.mapToObject((Map<String, Object>) fieldValue, value);
				results.add(value);
				return results;
			}else{
				return null;
			}
		} catch (Exception e) {
			throw new IllegalAccessError("fialed to get "+fieldName+" case by:"+e.getMessage());
		}
	}

	public static File getFile(String fieldName) {
		try {
			Object value = getParameter(fieldName);
			return (File) value;
		} catch (Exception e) {
			throw new IllegalAccessError(
					"not found request parameter,which fieldname is "
							+ fieldName+" case by "+e.getMessage());
		}
	}

	public static List<File> getFiles(String fieldName) {
		Map<String, Object> requestParameters = JBeerWebContext
				.getRequestParameters();
		Object obj = requestParameters.get(fieldName);
		if (obj!=null&&obj.getClass().isArray()) {
			List<File> files = new ArrayList<File>();
			Object[] parameters = (Object[]) obj;
			for (Object param : parameters) {
				files.add((File) param);
			}
			return files;
		}else if(obj!=null){
			throw new IllegalAccessError("field " + fieldName + " is not list");
		}else{
			return null;
		}
		
	}
	
	public static <T> T getRequestBody(Class<T> type) {
		Map<String, Object> requestParameters = JBeerWebContext
				.getRequestParameters();
		if(requestParameters==null){
			return null;
		}
		T value;
		try {
			value = type.newInstance();
			ObjectUtil.mapToObject(requestParameters, value);
			return value;
		} catch (Exception e) {
			throw new IllegalAccessError("failed to convert map to object "+e.getMessage());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T[] getArray(Class<T> t, String fieldName) {
		try{
		Map<String, Object> requestParameters = JBeerWebContext
				.getRequestParameters();
		Object obj = requestParameters.get(fieldName);
		if (obj!=null&&(obj.getClass().isArray()||Collection.class.isAssignableFrom(obj.getClass()))) {
			Object[] objs =null;
			if(List.class.isAssignableFrom(obj.getClass())){
				List list = (List) obj;
				objs=list.toArray(new Object[1]);
			}else{
				objs =  (Object[]) obj;
			}
			T[] ts = (T[]) Array.newInstance(t, objs.length);
			if (CaseUtils.checkIsBasicType(t)) {
				for (int i = 0; i < objs.length; i++) {
					ts[i] = (T) CaseUtils.caseType(t, objs[i]);
				}
			} else {
				for(int i=0;i<objs.length;i++){
					T value = t.newInstance();
					Object temp = objs[i];
					ts[i]=value;
				    ObjectUtil.mapToObject((Map<String, Object>) temp, value);
				}
			}
			return ts;
		}else if(obj!=null){
			return null;
		}
		}catch(Exception e){
			throw new IllegalAccessError("field " + fieldName + " is not a array. case by:"+e.getMessage());
		}
		throw new IllegalAccessError("field " + fieldName + " is not a array");
	}

	@SuppressWarnings("unchecked")
	public static <T> T getParameter(String fieldName, Class<T> type) {
		Object param = getParameter(fieldName);
		return (T) (param == null ? null : CaseUtils.caseType(type,
				param.toString()));
	}

	public static String getString(String fieldName) {
		return getParameter(fieldName, String.class);
	}

	private static Object getParameter(String fieldName) {
		Map<String, Object> requestParameters = JBeerWebContext
				.getRequestParameters();
		if (requestParameters == null
				|| requestParameters.get(fieldName) == null) {
			return null;
		}
		return requestParameters.get(fieldName);
	}

	public static Integer getInteger(String fieldName) {
		return getParameter(fieldName, Integer.class);
	}

	public static Float getFloat(String fieldName) {
		return getParameter(fieldName, Float.class);
	}

	public static Double getDouble(String fieldName) {
		return getParameter(fieldName, Double.class);
	}

	public static Short getShort(String fieldName) {
		return getParameter(fieldName, Short.class);
	}

}
