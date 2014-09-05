/**   
* @Title: ConnectionProxyProcessor.java
* @Package com.jbeer.framework.db
* @author Bieber
* @date 2014年5月26日 上午9:54:23
* @version V1.0   
*/

package com.jbeer.framework.db.datasource;

import java.sql.Connection;

import com.jbeer.framework.bean.proxy.InvokeHandler;
import com.jbeer.framework.bean.proxy.ProxyTargetProcessor;

/**
* <p>类功能说明:数据库连接代理类</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: ConnectionProxyProcessor.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年5月26日 上午9:54:23
* @see ProxyTargetProcessor
* @version V1.0
*/

public class ConnectionProxyProcessor implements ProxyTargetProcessor {

    private AbstractDataSource datasource;

    public ConnectionProxyProcessor(AbstractDataSource datasource) {
        this.datasource = datasource;
    }

    /* (non-Javadoc)
     * @see com.jbeer.framework.bean.proxy.ProxyTargetProcessor#invokeTarget(java.lang.Object, com.jbeer.framework.bean.proxy.InvokeHandler)
     */
    @Override
    public Object invokeTarget(Object target, InvokeHandler handler) throws Throwable {
        String methodName = handler.getInvokeMethod().getName();
        if (methodName.equals("close")) {
            datasource.releaseConnection((Connection) handler.getProxyObject());
            return null;
        } else {
            return handler.getInvokeMethod().invoke(target, handler.getArgs());
        }
    }

}
