/**   
* @Title: ActionHandler.java
* @Package com.jbeer.framework.web
* @author Bieber
* @date 2014-5-11 上午11:29:59
* @version V1.0   
*/

package com.jbeer.framework.web;

import java.lang.reflect.Method;

/**
 * <p>类功能说明:TODO</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: ActionHandler.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-11 上午11:29:59
 * @version V1.0
 */

public final class ActionHandler {
	/**
	 * action调用的方法
	 */
	private final Method targetMethod;
	/**
	 * 方法传递的参数
	 */
	private Object[] args;
	/**
	 * 当前的controller对象
	 */
	private final Object controllerObject;
	
	/**
	 * 请求的路径
	 */
	private String currentRequestPath;
	
	 

	/**
	* <p>Title: 构造函数</p>
	* <p>Description:初始化一些列参数 </p>
	* @param targetMethod
	* @param args
	* @param controllerObject
	* @param currentRequestPath
	* @param requestPathParameterMap
	*/
	
	public ActionHandler(Method targetMethod, Object[] args,
			Object controllerObject, String currentRequestPath) {
		super();
		this.targetMethod = targetMethod;
		this.args = args;
		this.controllerObject = controllerObject;
		this.currentRequestPath = currentRequestPath;
	}

	/**
	 * @return targetMethod
	 */
	
	public Method getTargetMethod() {
		return targetMethod;
	}

	/**
	 * @return args
	 */
	
	public Object[] getArgs() {
		return args;
	}

	/**
	 * @return controllerObject
	 */
	
	public Object getControllerObject() {
		return controllerObject;
	}

	/**
	 * @return currentRequestPath
	 */
	
	public String getCurrentRequestPath() {
		return currentRequestPath;
	}

	 

}
