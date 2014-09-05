/**   
 * @Title: DefaultActionMapHandler.java
 * @Package com.jbeer.framework.web.handler
 * @author Bieber
 * @date 2014-4-22 上午12:19:08
 * @version V1.0   
 */

package com.jbeer.framework.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.jbeer.framework.enumeration.RequestType;
import com.jbeer.framework.exception.ActionInvokeException;
import com.jbeer.framework.exception.NotFoundActionException;
import com.jbeer.framework.logging.Log;
import com.jbeer.framework.utils.LoggerUtil;
import com.jbeer.framework.utils.WebUtils;
import com.jbeer.framework.web.ActionEntity.PatternableUrl;
import com.jbeer.framework.web.ActionEntity.PatternableUrl.URLParam;

/**
 * <p>
 * 类功能说明:默认的action匹配器
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: DefaultActionMapHandler.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-4-22 上午12:19:08
 * @version V1.0
 */

public class ActionMapperHandler{

    private static final Log logger = LoggerUtil.generateLogger(ActionMapperHandler.class);
	 /**
	  * 
	 * <p>函数功能说明:通过某个请求，获取匹配该请求的Action信息，如果没有匹配到，则抛出404异常</p>
	 * <p>Bieber  2014-4-23</p>
	 * <p>修改者名字 修改日期</p>
	 * <p>修改内容</a>  
	 * @return RequestTargetAction
	 * @throws ActionInvokeException 
	  */
	public static RequestTargetAction mapperAction(HttpServletRequest request) throws ActionInvokeException {
		Collection<ActionEntity> actions = JBeerWebContext.getActionEntities();
		String uri = request.getRequestURI().replace(request.getSession().getServletContext().getContextPath(),"");
		if(actions==null){
		    if(logger.isDebugEnabled()){
		        logger.debug("request url:"+uri+" not matched action for the request.");
		    }
			throw new NotFoundActionException("request url:"+uri+" not matched action for the request.");
		}
		
		String method = request.getMethod();
		RequestTargetAction targetAction = null;
		for(ActionEntity action:actions){
			targetAction = matchReuqest(action,uri);
			if(targetAction!=null){
				break;
			}
		}
		if(targetAction!=null){
			if(targetAction.getTargetAction().getRequestType()!=RequestType.ANY&&!targetAction.getTargetAction().getRequestType().toString().equalsIgnoreCase(method)){
				throw new ActionInvokeException("not support "+method+" method", WebUtils.NOT_SUPPORT_METHOD);
			}
			return targetAction;
		}else{
			throw new NotFoundActionException("request rul:"+uri+" not matched action for response.");
		}
	}

	private static RequestTargetAction matchReuqest(ActionEntity action,String uri) {
		Collection<PatternableUrl> patternedUrls = action.getPatternedUrls();
		for (PatternableUrl patternableUrl : patternedUrls) {
			Pattern pattern = Pattern.compile(patternableUrl.getUrlMatcher());
			Matcher matcher = pattern.matcher(uri);
			if (matcher.matches()) {
				Map<String, Object> urlPatameter = new HashMap<String, Object>();
				List<String> values = new ArrayList<String>();
				if (patternableUrl.getUrlParams() != null) {
					Iterator<URLParam> urlParams = patternableUrl
							.getUrlParams().iterator();
					for (int i = 1; i <= matcher.groupCount(); i++) {
						String value = matcher.group(i);
						values.add(i - 1, value);
						while (urlParams.hasNext()) {
							URLParam param = urlParams.next();
							if (param.getUrlParamIndex() == (i - 1)) {
								urlPatameter.put(param.getParamName(), value);
								urlParams = patternableUrl.getUrlParams()
										.iterator();
								break;
							}
						}
					}
				}
				return RequestTargetAction.newInstance(action, urlPatameter, values,action);
			}
		}
		return null;
	}
}
