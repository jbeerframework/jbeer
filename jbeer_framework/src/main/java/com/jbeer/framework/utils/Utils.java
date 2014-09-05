/**   
 * @Title: Utils.java
 * @Package com.jbeer.framework.utils
 * @author Bieber
 * @date 2014年7月26日 下午1:52:16
 * @version V1.0   
 */

package com.jbeer.framework.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 类功能说明:工具类
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: Utils.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月26日 下午1:52:16
 * @version V1.0
 */

public class Utils {

	private static final Pattern placeHolderPattern = Pattern
			.compile("\\$\\{\\w{1,}\\}");

	/**
	 * 
	 * <p>
	 * 函数功能说明:检查该字符串是不是占位符,${xxxxxx}
	 * </p>
	 * <p>
	 * Bieber 2014年7月26日
	 * </p>
	 * <p>
	 * 修改者名字 修改日期
	 * </p>
	 * <p>
	 * 修改内容</a>
	 * 
	 * @return String
	 */
	public static String checkIsPlaceholder(String name) {
		if(name==null){
			return null;
		}
		boolean isPlaceholder = placeHolderPattern.matcher(name).matches();
		if (isPlaceholder) {
			return name.substring(2, name.length() - 1);
		} else {
			return null;
		}
	}
	private static final Map<String,String> properties = new HashMap<String,String>();
	public static void main(String[] args){
		properties.put("a", "1");
		properties.put("b", "2");
		properties.put("c", "3");
		properties.put("d", "4");
		String propertyName = "${a}${c}";
		Matcher matcher = placeHolderPattern.matcher(propertyName);
    	List<String> groups = new ArrayList<String>();
    	while(matcher.find()){
    		String group = matcher.group();
    		groups.add(properties.get(group.substring(2, group.length()-1)));	
    	}
    	propertyName = matcher.replaceAll("%s");
    	propertyName=String.format(propertyName, groups.toArray());
    	System.out.println(propertyName);
	}
}
