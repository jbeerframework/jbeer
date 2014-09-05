/**   
 * @Title: ControllerInitialization.java
 * @Package com.jbeer.framework.web
 * @author Bieber
 * @date 2014-4-23 下午09:41:11
 * @version V1.0   
 */

package com.jbeer.framework.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import com.jbeer.framework.JBeerWeb;
import com.jbeer.framework.constant.JBeerConstant;
import com.jbeer.framework.exception.InitObjectException;
import com.jbeer.framework.exception.InitializationException;
import com.jbeer.framework.exception.JBeerException;
import com.jbeer.framework.exception.MessageException;
import com.jbeer.framework.exception.WebException;
import com.jbeer.framework.ioc.JBeerIOCContainerContext;
import com.jbeer.framework.logging.Log;
import com.jbeer.framework.message.MessageUtils;
import com.jbeer.framework.properties.PropertiesContext;
import com.jbeer.framework.utils.CaseUtils;
import com.jbeer.framework.utils.ClassUtils;
import com.jbeer.framework.utils.LoggerUtil;
import com.jbeer.framework.utils.ObjectUtil;
import com.jbeer.framework.utils.StringUtils;
import com.jbeer.framework.utils.WebUtils;
import com.jbeer.framework.web.ActionEntity.ActionMethodParam;

/**
 * <p>
 * 类功能说明:初始化Controller实体
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: ControllerInitialization.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-4-23 下午09:41:11
 * @version V1.0
 */

public class ControllerInitialization {
    
    private static final Log logger = LoggerUtil.generateLogger(ControllerInitialization.class);

	/**
	 * 
	 * <p>
	 * 函数功能说明:初始化Action的方法入参，包含应用的BEAN，properties以及IN18内容
	 * </p>
	 * <p>
	 * Bieber 2014-4-23
	 * </p>
	 * <p>
	 * 修改者名字 修改日期
	 * </p>
	 * <p>
	 * 修改内容</a>
	 * 
	 * @return Object[]
	 * @throws JBeerException 
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static Object[] initActionMethodParams(
			RequestTargetAction targetAction) throws JBeerException, Exception {
		Collection<ActionMethodParam> methodParams = targetAction
				.getMethodParams();
		Map<String, Object> requestPathParameters = targetAction
				.getRequestPathParameterMap();
		List<String> requestPathParameterSeq = targetAction
				.getRequestPathParameterSequence();
		Object[] methodArgs = null;
		if (methodParams != null) {
			methodArgs = new Object[methodParams.size()];
		} else {
			/**
			 * 这表示该Action的方法没有任何参数
			 */
			return methodArgs;
		}
		/**
		 * 将path的传递过来的参数拼装到请求的方法参数上面
		 */
		if (methodParams.size() == requestPathParameters.size()) {

			for (ActionMethodParam methodParam : methodParams) {
				methodArgs[methodParam.getParamIndex()] = CaseUtils.caseType(
						methodParam.getParameterType(), requestPathParameters
								.get(methodParam.getParamName()).toString());
			}
		} else {
			for (ActionMethodParam methodParam : methodParams) {
				if (requestPathParameters.containsKey(methodParam
						.getParamName())) {
					methodArgs[methodParam.getParamIndex()] = CaseUtils
							.caseType(
									methodParam.getParameterType(),
									requestPathParameters.get(
											methodParam.getParamName())
											.toString());
				} else if (ServletRequest.class.isAssignableFrom(methodParam
						.getParameterType())) {
					methodArgs[methodParam.getParamIndex()] = JBeerWebContext
							.getRequest();
				} else if (ServletResponse.class.isAssignableFrom(methodParam
						.getParameterType())) {
					methodArgs[methodParam.getParamIndex()] = JBeerWebContext
							.getResponse();
				} else if (HttpSession.class.isAssignableFrom(methodParam
						.getParameterType())) {
					methodArgs[methodParam.getParamIndex()] = JBeerWebContext
							.getSession();
				} else if (ServletContext.class.isAssignableFrom(methodParam
						.getParameterType())) {
					methodArgs[methodParam.getParamIndex()] = JBeerWebContext
							.getServletContext();
				} else if (methodParam.getParamName() == JBeerConstant.DEFAULT_PATH_PARAMETER_NAME
						&& !CaseUtils.checkIsBasicType(methodParam
								.getParameterType())) {
					methodArgs[methodParam.getParamIndex()] = ClassUtils
							.fillObjectField(methodParam.getParameterType(),
									requestPathParameters);
				} else if (methodParam.getParamName() == JBeerConstant.WEB_ACTION_PARAM_REF_BEAN) {
					methodArgs[methodParam.getParamIndex()] = JBeerIOCContainerContext
							.createBeanProxy(methodParam.getParameterType(),
									methodParam.getRefId());
				}else if(methodParam.getParamName()==JBeerConstant.WEB_ACTION_PARAM_REF_PROPERTIES){
				    methodArgs[methodParam.getParamIndex()] =  CaseUtils.caseType(methodParam.getParameterType(), PropertiesContext.getProperties(methodParam.getRefId()));
				} else if(methodParam.getParamName()==JBeerConstant.WEB_ACTION_PARAM_REF_MESSAGE){
                    try {
                        methodArgs[methodParam.getParamIndex()] =   MessageUtils.getMessage(methodParam.getRefId(), methodParam.getIn18Args());
                    } catch (MessageException e) {
                        if(logger.isDebugEnabled()){
                            logger.debug("fail to loading "+methodParam.getRefId()+" in18 property");
                        }
                    }
                } else {
                	if(!StringUtils.isEmpty(methodParam.getDefaultValue())){
                		methodArgs[methodParam.getParamIndex()] =CaseUtils.caseType(methodParam.getParameterType(), methodParam.getDefaultValue());
                	}
                	if(methodParam.isRequired()&&!JBeerWebContext.getRequestParameters().containsKey(methodParam.getParamName())){
                		if(StringUtils.isEmpty(methodParam.getDefaultValue())){
                			throw new WebException("parameter "+methodParam.getParamName()+" is required,but does not found in request.", WebUtils.NOT_REQUIRED_PARAMETER);	
                		}
                	}
                	if(JBeerWebContext.getRequestParameters().containsKey(methodParam.getParamName())){
                		Class<?> type = methodParam.getParameterType();
                		if(Collection.class.isAssignableFrom(type)){
                			Type[] types = targetAction.getActionMethod().getGenericParameterTypes();
                			if(types==null||types.length<=0||types[methodParam.getParamIndex()]==null){
                				methodArgs[methodParam.getParamIndex()]=JBeerWebContext.getRequestParameters().get(methodParam.getParamName());
                			}else{
                				Type genericType = types[methodParam.getParamIndex()];
                				Object object = JBeerWebContext.getRequestParameters().get(methodParam.getParamName());
                				List<Object> objects=null;
                				if(Collection.class.isAssignableFrom(object.getClass())){
                					objects=(List<Object>) object;
                				}else if(object.getClass().isArray()){
                					objects=new ArrayList<Object>();
                					Object[] objArray= (Object[]) object;
                					for(Object obj:objArray){
                						objects.add(obj);
                					}
                				}else{
                					objects = new ArrayList<Object>();
                					objects.add(object);
                				}
                				if(ParameterizedType.class.isAssignableFrom(genericType.getClass())){
                					ParameterizedType paramType = (ParameterizedType) genericType;
                					Type actualType = paramType.getActualTypeArguments()[0];
                					if(Class.class==actualType){
                						
                					}else{
                					methodArgs[methodParam.getParamIndex()]=ObjectUtil.fillCollection(objects,actualType, methodParam.getParameterType());
                					}
                				}else{
                					methodArgs[methodParam.getParamIndex()]=objects;
                				}
                			}
                		}else if(Map.class.isAssignableFrom(type)){
                			Type[] types = targetAction.getActionMethod().getGenericParameterTypes();
                			if(types==null||types.length<=0||types[methodParam.getParamIndex()]==null){
                				methodArgs[methodParam.getParamIndex()]=JBeerWebContext.getRequestParameters().get(methodParam.getParamName());
                			}else{
                				Object object = JBeerWebContext.getRequestParameters().get(methodParam.getParamName());
                				Type tempType =types[methodParam.getParamIndex()];
                				if(ParameterizedType.class.isAssignableFrom(tempType.getClass())){
                				ParameterizedType genericType =  (ParameterizedType) tempType;
                				methodArgs[methodParam.getParamIndex()]=ObjectUtil.fillMap((Map<String, Object>) object,genericType);
                				}else{
                				methodArgs[methodParam.getParamIndex()]=object;
                				}
                			}
                		}else if(type.isArray()){
                			methodArgs[methodParam.getParamIndex()]=RequestParameterUtil.getArray(type.getComponentType(), methodParam.getParamName());
                		}else{
                			methodArgs[methodParam.getParamIndex()]=RequestParameterUtil.getObject(methodParam.getParamName(), type);
                		}
                	}else{
                		if (requestPathParameterSeq.size() > methodParam
    							.getParamIndex()) {
    						methodArgs[methodParam.getParamIndex()] = requestPathParameterSeq
    								.get(methodParam.getParamIndex());
    					}
                	}
				}
			}
		}
		return methodArgs;
	}

	/**
	 * 
	 * <p>
	 * 函数功能说明:构造请求的控制类实体
	 * </p>
	 * <p>
	 * Bieber 2014-2-22
	 * </p>
	 * <p>
	 * 修改者名字 修改日期
	 * </p>
	 * <p>
	 * 修改内容</a>
	 * 
	 * @return Object
	 * @throws InitializationException 
	 */
	public static Object generateController(RequestTargetAction targetAction)
			throws InitObjectException, InitializationException {

		Object controllerObject = null;
		Map<String, Object> requestParameters = getRequestParameter();
		requestParameters.putAll(getMutilPart());
		try {
			Class<?> controllerClass = Class.forName(targetAction
					.getControllerClassFullName());
			JBeerWebContext.setRequestParameters(WebUtils.analyseRequestParameters(requestParameters));
			if (requestParameters.size() > 0&&!JBeerWeb.isSingletonMode()) {
				controllerObject = JBeerIOCContainerContext.getBeanByType(controllerClass);
				if(controllerObject==null){
					throw new InitObjectException("class:"
							+ targetAction.getControllerClassFullName()
							+ "fail to initialize"); 
				}
				ObjectUtil.mapToObject(requestParameters, controllerObject);
			} else {
				controllerObject = JBeerIOCContainerContext.getBeanByType(controllerClass);
			}
		} catch (Exception e) {
			throw new InitObjectException("class:"
					+ targetAction.getControllerClassFullName()
					+ "fail to initialize", e);
		}
		return controllerObject;
	}
	/**
	 * 
	 * <p>
	 * 函数功能说明:从request中获取请求的参数
	 * </p>
	 * <p>
	 * Bieber 2014-2-22
	 * </p>
	 * <p>
	 * 修改者名字 修改日期
	 * </p>
	 * <p>
	 * 修改内容</a>
	 * 
	 * @return Map<String,String>
	 */
	private static Map<String, Object> getRequestParameter() {
		String method = JBeerWebContext.getRequest().getMethod();
		Map<String, Object> requestParams = new HashMap<String, Object>();
		if (method.equalsIgnoreCase("put")) {
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(JBeerWebContext.getRequest()
								.getInputStream()));
				String line = reader.readLine();
				StringBuffer sb = new StringBuffer();
				while (line != null) {
					sb.append(line);
					line = reader.readLine();
				}
				String[] parameters = sb.toString().split("&");
				for (String param : parameters) {
					WebUtils.addParametervalue(requestParams,
							param.substring(0, param.indexOf("=")),
							param.substring(param.indexOf("=") + 1));
				}
			} catch (Exception e) {

			}
		} else {
			Enumeration<String> paramNames = JBeerWebContext.getRequest()
					.getParameterNames();
			while (paramNames.hasMoreElements()) {
				String paramName = paramNames.nextElement();
				requestParams.put(paramName, JBeerWebContext.getRequest()
						.getParameterValues(paramName));
			}
		}
		return requestParams;
	}

	/**
	 * 
	 * <p>
	 * 函数功能说明:获取上传文件信息
	 * </p>
	 * <p>
	 * Bieber 2014-2-22
	 * </p>
	 * <p>
	 * 修改者名字 修改日期
	 * </p>
	 * <p>
	 * 修改内容</a>
	 * 
	 * @return Map<String,File>
	 */
	private static Map<String, Object> getMutilPart() {
		String tempDir = JBeerWeb.getWebTempFileDir();
		tempDir = JBeerWebContext.getServletContext().getRealPath("/")
				+ tempDir;
		File dir = new File(tempDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		Map<String, Object> fileMap = null;
		try {
			fileMap = WebUtils.getParameterForUpload(
					JBeerWebContext.getRequest(), tempDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileMap;
	}
}
