/**   
* @Title: JBeerConfiguration.java
* @Package com.jbeer.framework.config
* @author Bieber
* @date 2014-2-15 下午03:57:51
* @version V1.0   
*/

package com.jbeer.framework.config;

import com.jbeer.framework.JBeer;

/**
 * 类功能说明
 * 类修改者	    修改日期
 * 修改说明
 * <p>Title: JBeerConfiguration.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-2-15 下午03:57:51
 * @Description: JBeer框架相关配置属性
 * @version V1.0
 */

public class JBeerConfig {

	/**
	 * @param applicationEncode applicationEncode
	 */
	
	public  void setApplicationEncode(String applicationEncode) {
		if(applicationEncode==null){
			return;
		}
		JBeer.setApplicationEncode(applicationEncode);
	}


	
}
