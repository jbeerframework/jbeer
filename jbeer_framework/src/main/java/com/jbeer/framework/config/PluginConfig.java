/**   
* @Title: PluginConfig.java
* @Package com.jbeer.framework.config
* @author Bieber
* @date 2014年7月20日 下午9:36:08
* @version V1.0   
*/

package com.jbeer.framework.config;

import com.jbeer.framework.bean.proxy.MustCGLIBProxy;

/**
 * <p>类功能说明:plugin的配置信息</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: PluginConfig.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月20日 下午9:36:08
 * @version V1.0
 */

public interface PluginConfig extends MustCGLIBProxy{
	
	/**
	 * 
	* <p>函数功能说明:插件命名空间</p>
	* <p>Bieber  2014年7月20日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return String
	 */
	public String pluginNamespace();
}
