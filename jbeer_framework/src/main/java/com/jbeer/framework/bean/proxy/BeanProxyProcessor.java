/**   
 * @Title: Proxy.java
 * @Package com.jbeer.framework.ioc.proxy
 * @author Bieber
 * @date 2014-5-17 下午4:11:33
 * @version V1.0   
 */

package com.jbeer.framework.bean.proxy;


/**
 * <p>
 * 类功能说明:代理类
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: Proxy.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-17 下午4:11:33
 * @version V1.0
 */

public class BeanProxyProcessor implements ProxyTargetProcessor {
  
    /* (non-Javadoc)
     * @see com.jbeer.framework.ioc.proxy.BeanProxy#invokcation(java.lang.reflect.Method, java.lang.Object[])
     */
    public final Object invokeTarget(Object target,InvokeHandler handler) throws Throwable {
        return  handler.getInvokeMethod().invoke(target, handler.getArgs());
    }

  

	
}
