/**   
* @Title: WebserviceClientRegister.java
* @Package com.jbeer.framework.ws.register
* @author Bieber
* @date 2014年7月30日 上午5:30:04
* @version V1.0   
*/

package com.jbeer.framework.ws.register;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jbeer.framework.properties.PropertiesContext;

import net.sf.ehcache.util.concurrent.ConcurrentHashMap;

/**
 * <p>类功能说明:webservice客户端注册器</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: WebserviceClientRegister.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月30日 上午5:30:04
 * @version V1.0
 */

public class WebserviceClientRegister {
	
	
	private static final ConcurrentHashMap<Class<?>,String> clientMap = new ConcurrentHashMap<Class<?>,String>();
	
	private static final Pattern PLACE_HOLDER_PATTERN = Pattern.compile("\\$\\{\\w{1,}\\}");
	/**
	 * 
	* <p>函数功能说明:注册客户端，interfaceClass是ws接口，wsurl时服务的地址</p>
	* <p>Bieber  2014年7月30日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	public void registeWSClient(Class<?> interfaceClass,String wsurl){
		clientMap.putIfAbsent(interfaceClass, wsurl);
	}
	/**
	 * 
	* <p>函数功能说明:根据接口查询ws服务地址</p>
	* <p>Bieber  2014年7月30日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return String
	 */
	public String getWSUrl(Class<?> interfaceClass){
		String wsurl = clientMap.get(interfaceClass);
		Matcher matcher = PLACE_HOLDER_PATTERN.matcher(wsurl);
		if(matcher.find()){
			clientMap.put(interfaceClass, PropertiesContext.getProperties(wsurl));
		}
		return clientMap.get(interfaceClass);
	}
}
