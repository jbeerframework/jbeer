/**   
* @Title: ExceptionHandler.java
* @Package com.jbeer.framework.web
* @author Bieber
* @date 2014年8月16日 下午1:31:41
* @version V1.0   
*/

package com.jbeer.framework.web;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jbeer.framework.exception.InitializationException;
import com.jbeer.framework.exception.WebException;
import com.jbeer.framework.ioc.JBeerIOCContainerContext;
import com.jbeer.framework.logging.Log;
import com.jbeer.framework.support.ExceptionProcessor;
import com.jbeer.framework.utils.LoggerUtil;
import com.jbeer.framework.utils.WebUtils;

/**
 * <p>类功能说明:web异常处理者</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: ExceptionHandler.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年8月16日 下午1:31:41
 * @version V1.0
 */

public class ExceptionHandler {

	private static final Log logger = LoggerUtil.generateLogger(ExceptionHandler.class);
	
	/**
	 * 
	* <p>函数功能说明:获取当前异常，并进行处理</p>
	* <p>Bieber  2014年8月16日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	public static void handleException(Throwable t,HttpServletRequest request,HttpServletResponse response){
		try {
			Map<String,ExceptionProcessor> processorMap = JBeerIOCContainerContext.getBeansByType(ExceptionProcessor.class);
			if(processorMap.size()>0){
				Collection<ExceptionProcessor> processors =   processorMap.values();
				boolean processored=false;
				for(ExceptionProcessor processor:processors){
					if(processor.isAccept(t)){
						processor.handleException(t, request, response);
						processored=true;
					}
				}
				if(!processored){
					defaultExceptionHandler(t);
				}
			}else{
				defaultExceptionHandler(t);
			}
		} catch (InitializationException e) {
			if(logger.isDebugEnabled()){
				logger.debug("failed to initialize exception processor", e);
			}
		}
	}
	
	private static void defaultExceptionHandler(Throwable t){
		try{
		if(WebException.class.isAssignableFrom(t.getClass())){
			((WebException)t).sendError();
		}else{
			WebUtils.sendError(500, t.getMessage());
		}
		}catch(Exception e){
			if(logger.isDebugEnabled()){
				logger.debug("failed to handle exception", e);
			}
		}
	}
}
