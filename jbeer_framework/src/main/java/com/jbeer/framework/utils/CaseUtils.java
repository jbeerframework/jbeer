/**   
 * @Title: CaseUtils.java
 * @Package com.jbeer.framework.utils
 * @author Bieber
 * @date 2014-2-22 下午02:31:36
 * @version V1.0   
 */

package com.jbeer.framework.utils;

import java.io.File;

/**
 * <p>
 * 类功能说明:TODO
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: CaseUtils.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-2-22 下午02:31:36
 * @version V1.0
 */

public class CaseUtils {

	public static Object caseType(Class<?> targetType, Object value) {
		if(value==null){
			return null;
		}
		if (targetType == int.class || targetType == Integer.class) {
			return Integer.parseInt(value.toString().trim());
		} else if (targetType == short.class || targetType == Short.class) {
			return Short.parseShort(value.toString().trim());
		} else if (targetType == long.class || targetType == Long.class) {
			return Long.parseLong(value.toString().trim());
		} else if (targetType == float.class || targetType == Float.class) {
			return Float.parseFloat(value.toString().trim());
		} else if (targetType == double.class || targetType == Double.class) {
			return Double.parseDouble(value.toString().trim());
		} else if (targetType == boolean.class || targetType == Boolean.class) {
			return Boolean.parseBoolean(value.toString().trim());
		} else if(targetType==char.class||targetType==Character.class){
			return value.toString().charAt(0);
		}else if(targetType.isEnum()){
			Class<? extends Enum> enumClass = (Class<? extends Enum>) targetType;
			return Enum.valueOf(enumClass, value.toString());
		}else {
			return value;
		}
	}

	public static boolean checkIsBasicType(Class<?> targetType) {
		return targetType.isEnum()||targetType.isPrimitive() || String.class == targetType
				|| targetType == Integer.class || targetType == Short.class
				|| targetType == Long.class || targetType == Boolean.class
				|| targetType == Double.class || targetType == Float.class||targetType==File.class||targetType==Character.class;
	}
}
