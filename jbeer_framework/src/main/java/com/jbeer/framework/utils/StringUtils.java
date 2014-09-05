/**   
* @Title: StringUtils.java
* @Package com.jbeer.framework.utils
* @author Bieber
* @date 2014-5-17 下午4:36:07
* @version V1.0   
*/

package com.jbeer.framework.utils;

/**
 * <p>类功能说明:字符串工具类</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: StringUtils.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-17 下午4:36:07
 * @version V1.0
 */

public class StringUtils {

	public static boolean isEmpty(String str){
		
		if(str==null){
			return true;
		}
		if(str.trim().equals("")){
			return true;
		}
		return false;
	}
	
	public static boolean equals(String str1,String str2){
		if(str1==str2){
			return true;
		}
		if(str1==null||str2==null){
			return false;
		}
		str1=str1.trim();
		str2=str2.trim();
		if(str1.equals(str2)){
			return true;
		}
		return false;
	}
}
