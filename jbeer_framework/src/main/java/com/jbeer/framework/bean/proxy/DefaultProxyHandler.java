/**   
 * @Title: DefaultProxyHandler.java
 * @Package com.jbeer.framework.bean.proxy
 * @author Bieber
 * @date 2014年7月11日 下午8:48:16
 * @version V1.0   
 */

package com.jbeer.framework.bean.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

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
 * Title: DefaultProxyHandler.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月11日 下午8:48:16
 * @version V1.0
 */

public class DefaultProxyHandler implements MethodInterceptor,
		InvocationHandler {

	protected ProxyTargetProcessor processor;
	protected Class<?> targetClass;

	public DefaultProxyHandler(ProxyTargetProcessor processor,
			Class<?> targetClass) {
		this.processor = processor;
		this.targetClass = targetClass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
	 * java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		InvokeHandler handler = new InvokeHandler();
		handler.setArgs(args);
		handler.setInvokeMethod(method);
		handler.setTargetClass(targetClass);
		handler.setProxyObject(proxy);
		try{
		return processor.invokeTarget(proxy, handler);
		}catch(InvocationTargetException e){
			throw e.getTargetException();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.cglib.proxy.MethodInterceptor#intercept(java.lang.Object,
	 * java.lang.reflect.Method, java.lang.Object[],
	 * net.sf.cglib.proxy.MethodProxy)
	 */
	@Override
	public Object intercept(Object proxy, Method method, Object[] args,
			MethodProxy arg3) throws Throwable {
		InvokeHandler handler = new InvokeHandler();
		handler.setArgs(args);
		handler.setInvokeMethod(method);
		handler.setTargetClass(targetClass);
		handler.setProxyObject(proxy);
		try{
		return processor.invokeTarget(proxy, handler);
		}catch(InvocationTargetException e){
			throw e.getTargetException();
		}
	}

}
