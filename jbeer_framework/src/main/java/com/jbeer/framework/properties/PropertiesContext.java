/**   
 * @Title: PorpertiesContext.java
 * @Package com.jbeer.framework.properties
 * @author Bieber
 * @date 2014年6月1日 上午10:17:48
 * @version V1.0   
 */

package com.jbeer.framework.properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jbeer.framework.utils.StringUtils;

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
 * Title: PorpertiesContext.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年6月1日 上午10:17:48
 * @version V1.0
 */

public class PropertiesContext {

	private static final Map<String, String> properties = new HashMap<String, String>();
	private static final Pattern placeHolderPattern = Pattern
			.compile("\\$\\{\\w{1,}\\}");

	public static String getProperties(String propertyName) {
		if (propertyName == null) {
			return null;
		}
		Matcher matcher = placeHolderPattern.matcher(propertyName);
		List<String> groups = new ArrayList<String>();
		while (matcher.find()) {
			String group = matcher.group();
			groups.add(properties.get(group.substring(2, group.length() - 1)));
		}
		if (groups.size() > 0) {
			propertyName = matcher.replaceAll("%s");
			String property = String.format(propertyName, groups.toArray());
			if (!StringUtils.isEmpty(property)
					&& placeHolderPattern.matcher(property).find()) {
				return getProperties(property);
			} else {
				return property;
			}
		}
		String property = properties.get(propertyName);
		if (!StringUtils.isEmpty(property)
				&& placeHolderPattern.matcher(property).find()) {
			return getProperties(property);
		}
		return property;
	}

	public static void addProperty(String key, String value) {
		properties.put(key, value);
	}

}
