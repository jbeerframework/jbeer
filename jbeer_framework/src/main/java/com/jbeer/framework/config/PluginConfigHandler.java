/**   
* @Title: PluginConfigHandler.java
* @Package com.jbeer.framework.config
* @author Bieber
* @date 2014年7月20日 下午10:01:55
* @version V1.0   
*/

package com.jbeer.framework.config;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>类功能说明:插件配置管理者</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: PluginConfigHandler.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月20日 下午10:01:55
 * @version V1.0
 */

public class PluginConfigHandler {

	/**
	 * 插件配置集合
	 */
	private static final ConcurrentHashMap<String,PluginConfig> plugins = new ConcurrentHashMap<String,PluginConfig>();
	
	/**
	 * 
	* <p>函数功能说明:注册插件配置信息</p>
	* <p>Bieber  2014年7月20日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	public void registerPluginConfig(PluginConfig pluginConfig){
		plugins.putIfAbsent(pluginConfig.pluginNamespace(), pluginConfig);
	}
	
	/**
	 * 
	* <p>函数功能说明:获取所有的插件配置信息</p>
	* <p>Bieber  2014年7月20日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return Map<String,PluginConfig>
	 */
	public Map<String,PluginConfig> getPlugins(){
		if(plugins.size()<0){
			return null;
		}
		return Collections.unmodifiableMap(plugins);
	}
}
