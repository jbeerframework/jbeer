/**   
* @Title: GenerateAction.java
* @Package com.jbeer.framework.web
* @author Bieber
* @date 2014-2-15 下午07:15:55
* @version V1.0   
*/

package com.jbeer.framework.web;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jbeer.framework.annotation.Action;
import com.jbeer.framework.annotation.Controller;
import com.jbeer.framework.annotation.Message;
import com.jbeer.framework.annotation.PathParam;
import com.jbeer.framework.annotation.Properties;
import com.jbeer.framework.annotation.RefBean;
import com.jbeer.framework.annotation.RequestParameter;
import com.jbeer.framework.constant.JBeerConstant;
import com.jbeer.framework.enumeration.RequestType;
import com.jbeer.framework.exception.GenerateActionException;
import com.jbeer.framework.exception.ScanClassException;
import com.jbeer.framework.logging.Log;
import com.jbeer.framework.utils.ClassUtils;
import com.jbeer.framework.utils.LoggerUtil;
import com.jbeer.framework.web.ActionEntity.ActionMethodParam;
import com.jbeer.framework.web.ActionEntity.PatternableUrl;
import com.jbeer.framework.web.ActionEntity.PatternableUrl.URLParam;

/**
 * <p>类功能说明:初始化系统的所有Action参数</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: GenerateAction.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-2-15 下午07:15:55
 * @Description: TODO
 * @version V1.0
 */

public final class GenerateAction {
    
    private static final Log logger =LoggerUtil.generateLogger(GenerateAction.class);

	/**
	 * 
	* <p>函数功能说明:遍历整个classpath，寻找注解了Controller的类，并且遍历里面注解了Action的方法，并对相关信息进行分析</p>
	* <p>Bieber  2014-2-15</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	protected static synchronized Collection<ActionEntity> generateAction() throws GenerateActionException{
		try {
			if(logger.isDebugEnabled()){
			    logger.debug("initializing action.....");
			}
			//获得所有Controller的类
			Collection<Class<?>> controllerClasses = ClassUtils.scanClassesByAnnotation(Controller.class);
			Collection<ActionEntity> actions =null;
			for(Class<?> controllerClass:controllerClasses){
				if(logger.isDebugEnabled()){
				    logger.debug("scaned "+controllerClass.getName()+".....");
				}
				Method[] methods = controllerClass.getMethods();
				for(Method method:methods){
					//构造一个Action实体
					ActionEntity action = generateActionEntity(method,controllerClass);
					if(action!=null){
						if(actions==null){
							actions = new HashSet<ActionEntity>();
						}
						actions.add(action);
					}
				}
			}
			return actions;
		} catch (ScanClassException e) {
			throw new GenerateActionException(e);
		}
	}
	/**
	 * 
	* <p>函数功能说明:通过Action的方法以及Controller的类进行构造一个Action实体信息</p>
	* <p>Bieber  2014-2-15</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return ActionEntity
	 */
	private static ActionEntity generateActionEntity(Method actionMethod,Class<?> controllerClass){
		//判断当前方法是否注解了Action，如果没有注解表示这个方法不是一个Action
		Action action = actionMethod.getAnnotation(Action.class);
		Controller controller = controllerClass.getAnnotation(Controller.class);
		if(action==null){
			return null;
		}
		RequestType requestType = action.requestType();
		String[] urlPatterns = action.urlPatterns();
		ActionEntity actionEntity = new ActionEntity(actionMethod, controllerClass.getName(), requestType);
		/**
		 * 下面是分析方法参数信息，名构造相应的对象来存储相关信息
		 * 1、获取参数注解，得到Param注解信息，得到需要传入的参数名称，与url上面的${xxx}对应
		 */
		Annotation[][] paramAnnotations = actionMethod.getParameterAnnotations();
		Class<?>[] parameterTypes = actionMethod.getParameterTypes();
		
		int parameterSize = parameterTypes.length;
		for(int i=0;i<parameterSize;i++){
			
			ActionMethodParam  methodParam = actionEntity.generateActionMethodParam();
			methodParam.setParameterType(parameterTypes[i]);
			methodParam.setParamIndex(i);
			boolean status = false;
			if(paramAnnotations!=null){
			Annotation[] annotations = paramAnnotations[i];
			if(annotations!=null){
			for(Annotation annotation:annotations){
				if(annotation instanceof PathParam){
				PathParam param  = (PathParam) annotation;
				methodParam.setParamName(param.value());
				status=true;
				}else if(annotation instanceof RefBean){
					RefBean ref = (RefBean) annotation;
					methodParam.setParamName(JBeerConstant.WEB_ACTION_PARAM_REF_BEAN);
					methodParam.setRefId(ref.value());
					status=true;
				}else if(annotation instanceof Properties){
				    Properties ref = (Properties) annotation;
				    methodParam.setParamName(JBeerConstant.WEB_ACTION_PARAM_REF_PROPERTIES);
				    methodParam.setRefId(ref.name());
				    status=true;
				}else if(annotation instanceof Message){
				    Message ref = (Message) annotation;
                    methodParam.setParamName(JBeerConstant.WEB_ACTION_PARAM_REF_MESSAGE);
                    methodParam.setRefId(ref.name());
                    methodParam.setIn18Args(ref.args());
                    status=true;
				}else if(annotation instanceof RequestParameter){
					RequestParameter requestParam = (RequestParameter) annotation;
					methodParam.setParamName(requestParam.value());
					methodParam.setRequired(requestParam.required());
					methodParam.setDefaultValue(requestParam.defaultValue());
					status=true;
				}
			}
			}
			}
			if(!status){
				methodParam.setParamName(JBeerConstant.DEFAULT_PATH_PARAMETER_NAME);
			}
		}
		/**
		 * 分析请求的url信息，并且分析是否是restful格式的url，并对配置的参数占位符进行分解，并通过相关对象进行存储相关信息以确保和方法中的参数对应
		 */
		for(String urlPattern:urlPatterns){
			String prefix = controller.urlPattern();
			prefix="//"+prefix;
			urlPattern="//"+urlPattern;
			urlPattern=prefix+urlPattern;
			urlPattern = urlPattern.replaceAll("[/]{2,}", "/");
			PatternableUrl patternUrl= actionEntity.generatePatternableUrl(urlPattern);
			if(logger.isDebugEnabled()){
			    logger.debug("found a action,handle request method is "+actionMethod.getName()+" and can matche url "+urlPattern+" ......");
			}
			analysisRestfulUrlPattern(patternUrl,urlPattern);
		}
		
		return  actionEntity;
	}
	
	/**
	 * 
	* <p>函数功能说明:分析url路径是否是restful的占位符风格，例如product/${productId}.html这种风格，并通过相关对象进行存储相关信息</p>
	* <p>Bieber  2014-2-15</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return String 返回url路径的前缀，如果路径中没有占位符，则将整个路径直接返回，如果有则截取0到第一个占位符的起始位置，并返回,例如：product/${productId}.html，返回应该是product/
	 */
	private static void analysisRestfulUrlPattern(PatternableUrl patternUrl,String urlPattern){
		//抓取占位符信息${xxxx}
		Pattern pattern = Pattern.compile("(\\$\\{[a-zA-Z0-9_$]{1,}\\})");
		Matcher matcher = pattern.matcher(urlPattern);
		int index=0;
		while(matcher.find()){
			URLParam urlParam = patternUrl.generateUrlParam();
			urlParam.setUrlParamIndex(index);
			String matchedGroup = matcher.group();
			urlParam.setParamName(matchedGroup.substring(2,matchedGroup.length()-1));
			index++;
		}
	}
}
