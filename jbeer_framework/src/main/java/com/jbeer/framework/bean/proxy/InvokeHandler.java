/**   
* @Title: MethodHandler.java
* @Package com.jbeer.framework.ioc.aop
* @author Bieber
* @date 2014-5-18 下午2:03:14
* @version V1.0   
*/

package com.jbeer.framework.bean.proxy;

import java.lang.reflect.Method;

/**
 * <p>类功能说明:调用的劫持者</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: MethodHandler.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-18 下午2:03:14
 * @version V1.0
 */

public class InvokeHandler {
	
	
	private Method invokeMethod;
	
	private Class<?> targetClass;
	
	private Object[] args;
	
	private Object proxyObject;
	
	

	/**
	 * @return proxyObject
	 */
	
	public Object getProxyObject() {
		return proxyObject;
	}

	/**
	 * @param proxyObject proxyObject
	 */
	
	public void setProxyObject(Object proxyObject) {
		this.proxyObject = proxyObject;
	}

	/**
	 * @return invokeMethod
	 */
	
	public Method getInvokeMethod() {
		return invokeMethod;
	}

	/**
	 * @param invokeMethod invokeMethod
	 */
	
	protected void setInvokeMethod(Method invokeMethod) {
		this.invokeMethod = invokeMethod;
	}

	/**
	 * @return targetClass
	 */
	
	public Class<?> getTargetClass() {
		return targetClass;
	}

	/**
	 * @param targetClass targetClass
	 */
	
	protected void setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
	}

	/**
	 * @return args
	 */
	
	public Object[] getArgs() {
		return args;
	}

	/**
	 * @param args args
	 */
	
	protected void setArgs(Object[] args) {
		this.args = args;
	}

}
