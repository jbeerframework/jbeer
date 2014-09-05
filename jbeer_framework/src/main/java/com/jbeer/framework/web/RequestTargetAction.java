/**   
 * @Title: RequestTargetAction.java
 * @Package com.jbeer.framework.web
 * @author Bieber
 * @date 2014-4-23 下午09:14:09
 * @version V1.0   
 */

package com.jbeer.framework.web;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.jbeer.framework.web.ActionEntity.ActionMethodParam;

/**
 * <p>
 * 类功能说明:所要请求的目标Action封装实体
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: RequestTargetAction.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-4-23 下午09:14:09
 * @version V1.0
 */

public class RequestTargetAction {
	/**
	 * action需要调用的className
	 */
	private final String controllerClassFullName;
	/**
	 * action需要调用的方法对象
	 */
	private final Method actionMethod;

	/**
	 * 存储当前Action的方法参数信息
	 */
	private final Collection<ActionMethodParam> methodParams;

	/**
	 * 当前线程处理路径参数map集合
	 */
	private final Map<String, Object> requestPathParameterMap;
	/**
	 * 路径参数序列
	 */
	private final List<String> requestPathParameterSequence;
	
	private final ActionEntity targetAction;

	private RequestTargetAction(String controllerClassFullName,
			Method actionMethod, Collection<ActionMethodParam> methodParams,
			Map<String, Object> requestPathParameterMap,
			List<String> requestPathParameterSequence,ActionEntity targetAction) {
		this.actionMethod = actionMethod;
		this.controllerClassFullName = controllerClassFullName;
		this.methodParams = methodParams;
		this.requestPathParameterMap = requestPathParameterMap;
		this.requestPathParameterSequence = requestPathParameterSequence;
		this.targetAction = targetAction;
	}

	public static RequestTargetAction newInstance(ActionEntity actionEntity,
			Map<String, Object> requestPathParameterMap,
			List<String> requestPathParameterSequence,ActionEntity targetAction) {
		return new RequestTargetAction(actionEntity.getControllerClassName(),
				actionEntity.getActionInvokeMethod(),
				actionEntity.getMethodParams(),requestPathParameterMap,requestPathParameterSequence,targetAction);
	}

	public ActionEntity getTargetAction() {
		return targetAction;
	}

	/**
	 * @return controllerClassFullName
	 */
	
	public String getControllerClassFullName() {
		return controllerClassFullName;
	}

	/**
	 * @return actionMethod
	 */
	
	public Method getActionMethod() {
		return actionMethod;
	}

	/**
	 * @return methodParams
	 */
	
	public Collection<ActionMethodParam> getMethodParams() {
		return methodParams;
	}

	/**
	 * @return requestPathParameterMap
	 */
	
	public Map<String, Object> getRequestPathParameterMap() {
		return requestPathParameterMap;
	}

	/**
	 * @return requestPathParameterSequence
	 */
	
	public List<String> getRequestPathParameterSequence() {
		return requestPathParameterSequence;
	}

	
}
