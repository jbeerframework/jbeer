/**   
 * @Title: ObjectUtil.java
 * @Package com.jbeer.framework.utils
 * @author Bieber
 * @date 2014年7月17日 下午5:13:14
 * @version V1.0   
 */

package com.jbeer.framework.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

import com.jbeer.framework.exception.JBeerException;

/**
 * <p>
 * 类功能说明:对象工具类
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: ObjectUtil.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月17日 下午5:13:14
 * @version V1.0
 */

public class ObjectUtil {

	/**
	 * 
	 * <p>
	 * 函数功能说明:将map属性设置到对象的字段中去
	 * </p>
	 * <p>
	 * Bieber 2014年7月17日
	 * </p>
	 * <p>
	 * 修改者名字 修改日期
	 * </p>
	 * <p>
	 * 修改内容</a>
	 * 
	 * @return void
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	@SuppressWarnings({ "unchecked" })
	public static void mapToObject(Map<String, Object> map, Object target)
			throws JBeerException {
		if(map==null||map.size()<=0){
			return;
		}
		try {
			Class<?> targetClass = target.getClass();
			Field[] fields = targetClass.getDeclaredFields();
			for (Field field : fields) {
				// field.setAccessible(true);
				if(!map.containsKey(field.getName())){
					continue;
				}
				Method setMethod = ClassUtils.searchSetMethod(targetClass,
						field.getName(), field.getType());
				if (setMethod == null) {
					continue;
				}
				setMethod.setAccessible(true);
				Object fieldValue = null;
				if (CaseUtils.checkIsBasicType(field.getType())) {
					fieldValue = CaseUtils.caseType(field.getType(),
							map.get(field.getName()));
				} else if (Collection.class.isAssignableFrom(field.getType())) {// 集合类型
					Object tempFieldValue = map.get(field.getName());
					List<Object> list =null;
					if(tempFieldValue.getClass().isArray()){
						list=new ArrayList<Object>();
						Object[] temps = (Object[]) tempFieldValue;
						for(Object temp:temps){
							list.add(temp);
						}
					}else{
						list= (List<Object>) map.get(field.getName());
					}

					ParameterizedType fieldType = (ParameterizedType) field
							.getGenericType();
					if (fieldType == null) {// 没有用泛型的集合
						// field.set(target, list);
						fieldValue = list;
					} else {// 指明了泛型
						Type fieldGenericTypClass = fieldType
								.getActualTypeArguments()[0];
						fieldValue = fillCollection(list, fieldGenericTypClass,
								field.getType());
					}

				} else if (Map.class.isAssignableFrom(field.getType())) {// Map类型
					Map<String, Object> fillMap = (Map<String, Object>) map
							.get(field.getName());
					ParameterizedType genericType = (ParameterizedType) field
							.getGenericType();
					if (genericType != null) {// 指定了泛型的map
						fieldValue = fillMap(fillMap, genericType);
					} else {// 没有制定泛型的map
						fieldValue = fillMap;
					}
				}else if(field.getType().isEnum()){
					Class<? extends Enum> enumClass=(Class<? extends Enum>) field.getType();
					fieldValue=Enum.valueOf(enumClass, map.get(field.getName()).toString());
				}else{
					fieldValue= map.get(field.getName());
				}
				setMethod.invoke(target, fieldValue);
			}
		} catch (Exception e) {
			throw new JBeerException(e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map fillMap(Map<String, Object> metaData,
			ParameterizedType genericType) throws Exception, JBeerException {
		if (metaData == null) {
			return null;
		}
		Map fillMap = new HashMap();
		Type[] types = genericType.getActualTypeArguments();
		if (types.length <= 0) {// 未指定Map的泛型
			fillMap.putAll(metaData);
		} else {
			Class<?> keyType = (Class<?>) types[0];// key的类型
			if (ParameterizedType.class.isAssignableFrom(types[1].getClass())) {// 嵌套泛型
				ParameterizedType valueType = (ParameterizedType) types[1];
				Class<?> rowType = (Class<?>) valueType.getRawType();
				if (Map.class.isAssignableFrom(rowType)) {// 嵌套的Map泛型
					for (Entry<String, Object> entry : metaData.entrySet()) {
						Map<String, Object> valueMap = (Map<String, Object>) metaData
								.get(entry.getKey());
						fillMap.put(
								CaseUtils.caseType(keyType, entry.getKey()),
								fillMap(valueMap, valueType));
					}
				} else if (Collection.class.isAssignableFrom(rowType)) {// 嵌套集合的泛型
					for (Entry<String, Object> entry : metaData.entrySet()) {
						List<Object> valueList = (List<Object>) metaData
								.get(entry.getKey());
						Type valueGene = valueType.getActualTypeArguments()[0];// 可能还是泛型
						fillMap.put(
								CaseUtils.caseType(keyType, entry.getKey()),
								fillCollection(valueList, valueGene, rowType));
					}
				}
			} else {
				Class<?> valueType = (Class<?>) types[1];
				if (CaseUtils.checkIsBasicType(valueType)) {// 基础类型
					for (Entry<String, Object> entry : metaData.entrySet()) {
						fillMap.put(
								CaseUtils.caseType(keyType, entry.getKey()),
								CaseUtils.caseType(valueType, entry.getValue()));
					}
				} else if (Collection.class.isAssignableFrom(valueType)) {// 未指定类型的LIST
					for (Entry<String, Object> entry : metaData.entrySet()) {
						List<Object> entryList = (List<Object>) entry
								.getValue();
						fillMap.put(
								CaseUtils.caseType(keyType, entry.getKey()),
								fillCollection(entryList, Object.class,
										valueType));
					}
				} else {// 自定义引用类型
					for (Entry<String, Object> entry : metaData.entrySet()) {
						Object valueObject = valueType.newInstance();
						mapToObject((Map<String, Object>) entry.getValue(),
								valueType);
						fillMap.put(
								CaseUtils.caseType(keyType, entry.getKey()),
								valueObject);
					}
				}
			}

		}
		return fillMap;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Collection<T> fillCollection(List<Object> mateData,
			Type genericType, Class<?> fieldType) throws Exception,
			JBeerException {
		Collection collection = null;
		if (mateData == null) {
			return null;
		}
		if (Set.class == fieldType) {
			collection = new HashSet();
		} else if (Iterable.class == fieldType || List.class == fieldType) {
			collection = new ArrayList();
		} else if (Queue.class == fieldType || Deque.class == fieldType) {
			collection = new LinkedList();
		} else {
			collection = (Collection) fieldType.newInstance();
		}
		if (ParameterizedType.class.isAssignableFrom(genericType.getClass())) {// 嵌套泛型
			ParameterizedType paramedType = (ParameterizedType) genericType;
			Class<?> rowType = (Class<?>) paramedType.getRawType();
			if (Map.class.isAssignableFrom(rowType)) {
				for (Object item : mateData) {
					Map<String, Object> itemMap = (Map<String, Object>) item;
					collection.add(fillMap(itemMap, paramedType));
				}
				return collection;
			} else if (Collection.class.isAssignableFrom(rowType)) {
				for (Object item : mateData) {
					List<Object> itemCollection = (List<Object>) item;
					collection.add(fillCollection(itemCollection,
							paramedType.getActualTypeArguments()[0], rowType));
				}
				return collection;
			} else {
				throw new JBeerException("did not know " + rowType);
			}
		} else {
			Class<?> genericClass = (Class<?>) genericType;
			/**
			 * 基础类型
			 */
			if (CaseUtils.checkIsBasicType(genericClass)) {
				for (Object item : mateData) {
					collection.add(CaseUtils.caseType(genericClass, item));
				}
				return collection;
			} else if (genericClass == Object.class
					|| Map.class.isAssignableFrom(genericClass)
					|| Collection.class.isAssignableFrom(genericClass)) {// 未指定类型
				return (Collection<T>) mateData;
			} else {// 自定义引用类型
				for (Object item : mateData) {
					Object fieldValue = genericClass.newInstance();
					mapToObject((Map<String, Object>) item, fieldValue);
					collection.add(fieldValue);
				}
				return collection;
			}
		}
	}
}
