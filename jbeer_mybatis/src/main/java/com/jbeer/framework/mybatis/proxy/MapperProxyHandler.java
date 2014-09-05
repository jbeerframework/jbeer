/**
 * 
 */
package com.jbeer.framework.mybatis.proxy;

import com.jbeer.framework.bean.proxy.InvokeHandler;
import com.jbeer.framework.bean.proxy.ProxyTargetProcessor;
import com.jbeer.framework.mybatis.MybatisPlugin;

/**
 * @author bieber
 *
 */
public class MapperProxyHandler implements ProxyTargetProcessor {

	private MybatisPlugin plugin;
	
	public MapperProxyHandler(MybatisPlugin plugin){
		this.plugin=plugin;
	}
	/* (non-Javadoc)
	 * @see com.jbeer.framework.bean.proxy.ProxyTargetProcessor#invokeTarget(java.lang.Object, com.jbeer.framework.bean.proxy.InvokeHandler)
	 */
	public Object invokeTarget(Object target, InvokeHandler handler)
			throws Throwable {
		if(!plugin.hasMapper(handler.getTargetClass())){
			plugin.registeMapper(handler.getTargetClass());
		}
		Object mapper = plugin.createMapper(handler.getTargetClass());
		return handler.getInvokeMethod().invoke(mapper, handler.getArgs());
	}

}
