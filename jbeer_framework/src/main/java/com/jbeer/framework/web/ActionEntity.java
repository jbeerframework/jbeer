/**   
 * @Title: ActionEntity.java
 * @Package com.jbeer.framework.web
 * @author Bieber
 * @date 2014-2-15 下午06:41:10
 * @version V1.0   
 */

package com.jbeer.framework.web;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import com.jbeer.framework.enumeration.RequestType;

/**
 * <p>
 * 类功能说明:一个Action实体封装类，用于封装当前Action的属性
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: ActionEntity.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-2-15 下午06:41:10
 * @version V1.0
 */

public final class ActionEntity {

	/**
	 * action需要调用的方法对象
	 */
	private Method actionInvokeMethod;

	/**
	 * action需要调用的className
	 */
	private String controllerClassName;
	/**
	 * 存储当前Action的方法参数信息
	 */
	private Collection<ActionMethodParam> methodParams;
	/**
	 * 当前Action处理的请求方式
	 */
	private RequestType requestType;


	/**
	 * 当前Action可以匹配的请求路径集合
	 */
	private Collection<PatternableUrl> patternedUrls;
	/**
	 * 
	 * <p>
	 * Title:构造函数
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param actionInvokeMethod
	 * @param controllerClassName
	 * @param paramSplitStr
	 * @param urlPatterns
	 */
	protected ActionEntity(Method actionInvokeMethod,
			String controllerClassName, RequestType requestType) {
		super();
		this.actionInvokeMethod = actionInvokeMethod;
		this.controllerClassName = controllerClassName;
		this.requestType = requestType;
	}

	 


	/**
	 * 
	 * <p>
	 * 函数功能说明:创建一个可以匹配的路径对象
	 * </p>
	 * <p>
	 * Bieber 2014-2-15
	 * </p>
	 * <p>
	 * 修改者名字 修改日期
	 * </p>
	 * <p>
	 * 修改内容</a>
	 * 
	 * @return PatternedUrl
	 */
	protected PatternableUrl generatePatternableUrl(String urlMatcher) {
		if (patternedUrls == null) {
			patternedUrls = new HashSet<PatternableUrl>();
		}
		PatternableUrl url = new PatternableUrl();
		url.setUrlMatcher(urlMatcher);
		patternedUrls.add(url);
		return url;
	}

	/**
	 * 
	 * <p>
	 * 函数功能说明:创建Action方法参数对象
	 * </p>
	 * <p>
	 * Bieber 2014-2-15
	 * </p>
	 * <p>
	 * 修改者名字 修改日期
	 * </p>
	 * <p>
	 * 修改内容</a>
	 * 
	 * @return ActionMethodParam
	 */
	protected ActionMethodParam generateActionMethodParam() {
		ActionMethodParam methodParam = new ActionMethodParam();
		if (methodParams == null) {
			methodParams = new HashSet<ActionMethodParam>();
		}
		methodParams.add(methodParam);
		return methodParam;
	}

	 
	/**
	 * @return actionInvokeMethod
	 */

	protected Method getActionInvokeMethod() {
		return actionInvokeMethod;
	}

	/**
	 * @return methodParams
	 */

	protected Collection<ActionMethodParam> getMethodParams() {
		if(methodParams==null){
			return null;
		}
		return Collections.unmodifiableCollection(methodParams);
	}

	/**
	 * @return controllerClassName
	 */

	protected String getControllerClassName() {
		return controllerClassName;
	}

	/**
	 * @return requestType
	 */

	protected RequestType getRequestType() {
		return requestType;
	}

	/**
	 * @return patternedUrls
	 */

	protected Collection<PatternableUrl> getPatternedUrls() {
		if(patternedUrls==null){
			return null;
		}
		return Collections.unmodifiableCollection(patternedUrls);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return controllerClassName + "->" + actionInvokeMethod.getName();
	}

	/**
	 * 
	 * <p>
	 * 类功能说明:可以匹配的路径对象
	 * </p>
	 * <p>
	 * 类修改者 修改日期
	 * </p>
	 * <p>
	 * 修改说明
	 * </p>
	 * <p>
	 * Title: ActionEntity.java
	 * </p>
	 * 
	 * @author Bieber <a
	 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
	 * @date 2014-2-15 下午09:35:22
	 * @version V1.0
	 */
	public static class PatternableUrl {
		/**
		 * 匹配路径的正则表达式
		 */
		private String urlMatcher;
		/**
		 * 配置的url路径占位符相关信息
		 */
		private Collection<URLParam> urlParams;

		/**
		 * @return urlMatcher
		 */

		public String getUrlMatcher() {
			return String.valueOf(urlMatcher);
		}

		/**
		 * @param urlMatcher
		 *            urlMatcher
		 */

		protected void setUrlMatcher(String urlMatcher) {
			urlMatcher = urlMatcher.replaceAll("\\$\\{[a-zA-Z0-9_$]{1,}\\}",
					"(.*)");
			this.urlMatcher = urlMatcher;
		}

		protected PatternableUrl() {

		}

		/**
		 * @return urlParams
		 */

		protected Collection<URLParam> getUrlParams() {
			if(urlParams==null){
				return null;
			}
			return Collections.unmodifiableCollection(urlParams);
		}

		/**
		 * 
		 * <p>
		 * 函数功能说明:构造一个url占位符参数对象
		 * </p>
		 * <p>
		 * Bieber 2014-2-15
		 * </p>
		 * <p>
		 * 修改者名字 修改日期
		 * </p>
		 * <p>
		 * 修改内容</a>
		 * 
		 * @return URLParam
		 */
		protected URLParam generateUrlParam() {
			if (urlParams == null) {
				urlParams = new HashSet<URLParam>();
			}
			URLParam urlParam = new URLParam();
			urlParams.add(urlParam);
			return urlParam;
		}

		/**
		 * 
		 * <p>
		 * 类功能说明:支持Restful风格的URL地址参数传递
		 * </p>
		 * <p>
		 * 类修改者 修改日期
		 * </p>
		 * <p>
		 * 修改说明
		 * </p>
		 * <p>
		 * Title: ActionEntity.java
		 * </p>
		 * 
		 * @author Bieber <a
		 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.
		 *         com</a>
		 * @date 2014-2-15 下午07:03:37
		 * @version V1.0
		 */
		public class URLParam {
			/**
			 * 占位符参数名
			 */
			private String paramName;
			/**
			 * 占位符参数所在的位置是第几个，方面在请求时，确定第几个占位符的值是当前参数的值
			 */
			private int urlParamIndex;

			protected URLParam() {

			}

			/**
			 * @return paramName
			 */

			public String getParamName() {
				return String.valueOf(paramName);
			}

			/**
			 * @param paramName
			 *            paramName
			 */

			protected void setParamName(String paramName) {
				this.paramName = paramName;
			}

			/**
			 * @return urlParamIndex
			 */

			public int getUrlParamIndex() {
				return Integer.valueOf(urlParamIndex);
			}

			/**
			 * @param urlParamIndex
			 *            urlParamIndex
			 */

			protected void setUrlParamIndex(int urlParamIndex) {
				this.urlParamIndex = urlParamIndex;
			}

		}
	}

	/**
	 * 
	 * <p>
	 * 类功能说明:Action方法参数封装类
	 * </p>
	 * <p>
	 * 类修改者 修改日期
	 * </p>
	 * <p>
	 * 修改说明
	 * </p>
	 * <p>
	 * Title: ActionEntity.java
	 * </p>
	 * 
	 * @author Bieber <a
	 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
	 * @date 2014-2-15 下午06:55:17
	 * @version V1.0
	 */
	public class ActionMethodParam {
		/**
		 * 方法参数名
		 */
		private String paramName;
		/**
		 * 方法参数位置
		 */
		private int paramIndex;

		private Class<?> parameterType;
		/**
		 * 关联的ID，可以是bean的ID，也可以是引用Properties或者IN18消息信息
		 */
		private String refId;
		
		private String[] in18Args;
		
		private String defaultValue;
		
		
		private boolean required=false;
		
		
		protected boolean isRequired() {
			return required;
		}

		protected void setRequired(boolean required) {
			this.required = required;
		}

		protected String getDefaultValue() {
			return defaultValue;
		}

		protected void setDefaultValue(String defaultValue) {
			this.defaultValue = defaultValue;
		}

		/**
        * @return refId
        */
        
        protected String getRefId() {
            return refId;
        }

        /**
        * @param refId refId
        */
        
        protected void setRefId(String refId) {
            this.refId = refId;
        }

        /**
        * @return in18Args
        */
        
        protected String[] getIn18Args() {
            return in18Args;
        }

        /**
        * @param in18Args in18Args
        */
        
        protected void setIn18Args(String[] in18Args) {
            this.in18Args = in18Args;
        }

       
		/**
		 * @return parameterType
		 */

		protected Class<?> getParameterType() {
			return parameterType;
		}

		/**
		 * @param parameterType
		 *            parameterType
		 */

		protected void setParameterType(Class<?> parameterType) {
			this.parameterType = parameterType;
		}

		/**
		 * @return paramName
		 */

		protected String getParamName() {
			return paramName;
		}

		/**
		 * @param paramName
		 *            paramName
		 */

		protected void setParamName(String paramName) {
			this.paramName = paramName;
		}

		/**
		 * @return paramIndex
		 */

		protected int getParamIndex() {
			return paramIndex;
		}

		/**
		 * @param paramIndex
		 *            paramIndex
		 */

		protected void setParamIndex(int paramIndex) {
			this.paramIndex = paramIndex;
		}

	}
}
