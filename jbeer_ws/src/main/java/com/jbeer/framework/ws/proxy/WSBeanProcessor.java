/**   
* @Title: WSBeanProcessor.java
* @Package com.jbeer.framework.ws.proxy
* @author Bieber
* @date 2014年7月29日 上午6:28:47
* @version V1.0   
*/

package com.jbeer.framework.ws.proxy;

import com.jbeer.framework.bean.proxy.InvokeHandler;
import com.jbeer.framework.bean.proxy.ProxyTargetProcessor;

/**
 * <p>类功能说明:ws端的代理执行者</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: WSBeanProcessor.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月29日 上午6:28:47
 * @version V1.0
 */

public class WSBeanProcessor implements ProxyTargetProcessor {

	/* (non-Javadoc)
	 * @see com.jbeer.framework.bean.proxy.ProxyTargetProcessor#invokeTarget(java.lang.Object, com.jbeer.framework.bean.proxy.InvokeHandler)
	 */
	public Object invokeTarget(Object target, InvokeHandler handler)
			throws Throwable {
		System.out.println(handler);
		return handler.getInvokeMethod().invoke(target, handler.getArgs());
	}

}
