/**   
* @Title: CallStart.java
* @Package com.jbeer.framework.plugin
* @author Bieber
* @date 2014年8月7日 下午10:28:55
* @version V1.0   
*/

package com.jbeer.framework.plugin;

import com.jbeer.framework.exception.JBeerException;

/**
 * <p>类功能说明:通知启动接口，该接口表示当框架初始化之后再通知插件启动</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: CallStart.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年8月7日 下午10:28:55
 * @version V1.0
 */

public interface CallStart {
	/**
     * 
    * <p>函数功能说明:启动插件</p>
    * <p>Bieber  2014年8月7日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     */
    public void start() throws JBeerException ;
}
