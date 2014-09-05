/**   
* @Title: JBeerWebContext.java
* @Package com.jbeer.framework.web
* @author Bieber
* @date 2014-2-15 下午04:36:48
* @version V1.0   
*/

package com.jbeer.framework.web;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jbeer.framework.JBeer;
import com.jbeer.framework.exception.GenerateActionException;
import com.jbeer.framework.logging.Log;
import com.jbeer.framework.utils.LoggerUtil;

/**
 * 
* <p>类功能说明:JBeer web处理上下文，该类防止进行扩展进行了final定义</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: JBeerWebContext.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014-2-15 下午04:42:47
* @version V1.0
 */
public final class JBeerWebContext {

	/**
	 * 为了在多并发情况下能够获取当前线程的reques和response对象，将其放到ThreadLocal中
	 */
	private static ThreadLocal<HttpServletRequest> threadRequest = new ThreadLocal<HttpServletRequest>();
	
	private static ThreadLocal<HttpServletResponse> threadResponse = new ThreadLocal<HttpServletResponse>();
	
	/**
	 * 如果当前请求有文件，将缓存临时文件
	 */
	private static ThreadLocal<List<File>> tempFiles = new ThreadLocal<List<File>>();
	
	
	private static ConcurrentHashMap<Class<?>,Map<String,Method>> classGetMethodCache = new ConcurrentHashMap<Class<?>,Map<String,Method>>(); 
 
	private static Collection<ActionEntity> actionEntityCollection;
	
	private static List<ActionInterceptor> interceptors = null;
	
	private static final Log logger = LoggerUtil.generateLogger(JBeerWebContext.class);
	
	private static ThreadLocal<Map<String, Object>> requestParamtersHolder = new ThreadLocal<Map<String,Object>>();
	
	public static void setInterceptors(List<ActionInterceptor> interceptors){
	    if(JBeerWebContext.interceptors==null&&!JBeer.isStartup()){
	        JBeerWebContext.interceptors=interceptors;
	    }
	}
	
	public static List<ActionInterceptor> getInterceptors(){
	    return interceptors;
	}
	
	protected static void setRequestParameters(Map<String, Object> parameters){
	    requestParamtersHolder.set(parameters);
	}
	
	protected static Map<String, Object> getRequestParameters(){
	    return requestParamtersHolder.get();
	}
	
	protected static synchronized void cacheClassGetMethods(Class<?> clazz,Map<String,Method> map){
	    if(classGetMethodCache.containsKey(clazz)){
	        return ;
	    }
	    classGetMethodCache.put(clazz, map);
	}
	
	protected static Map<String,Method> getClassGetMethodsFromCache(Class<?> clazz){
	    return classGetMethodCache.get(clazz);
	}
 
	/**
	 * @param tempFiles tempFiles
	 */
	
	public static void setTempFiles(List<File> tempFile) {
		tempFiles.set(tempFile);
	}
  
	/**
	 * @param tempFiles tempFiles
	 */
	
	public static void setTempFile(File tempFile) {
		 if(tempFiles.get()==null){
			 tempFiles.set(new ArrayList<File>());
		 }
		 tempFiles.get().add(tempFile);
	}
	/**
	 * @return tempFiles
	 */
	
	protected static List<File> getTempFiles() {
		return tempFiles.get();
	}


	protected static void setActionEntityCollection(Collection<ActionEntity> actionCollection){
		actionEntityCollection=actionCollection;
	}
	
	public static Collection<ActionEntity> getActionEntities(){
		if(actionEntityCollection==null){
			return actionEntityCollection;
		}
		return Collections.unmodifiableCollection(actionEntityCollection);
	}
	
	
	//该方法只能在包级别调用，为了安全性考虑
	protected static void setRequest(HttpServletRequest request){
		threadRequest.set(request);
	}
	//该方法只能在包级别调用，为了安全性考虑
	protected static void setResponse(HttpServletResponse response){
		threadResponse.set(response);
	}
	//该方法只能在包级别调用，为了安全性考虑
	protected static void newRequestComing(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding(JBeer.getApplicationEncode());
		} catch (UnsupportedEncodingException e) {
		    logger.error("fail to set reequest encoding", e);
		}
		response.setCharacterEncoding(JBeer.getApplicationEncode());
		threadRequest.set(request);
		threadResponse.set(response);
	}
	
	protected static void processedRequest(){
	    threadRequest.remove();
	    threadResponse.remove();
	}
	
	public static void initContext() throws GenerateActionException{
		if(actionEntityCollection==null){
		actionEntityCollection=GenerateAction.generateAction();
		}else{
			throw new GenerateActionException("框架Web模块已经启动，请不需要再次启动.....");
		}
	}
	
     public static ServletContext getServletContext(){
    	 HttpServletRequest request = getRequest();
    	 if(request!=null){
    		 return request.getSession().getServletContext();
    	 }else{
    		 return null;
    	 }
     }	
	//提供静态方法访问当前请求的Request对象
	public static HttpServletRequest getRequest(){
		if(threadRequest.get()!=null){
		return threadRequest.get();
		}else{
		return null;
		}
	}
	//提供静态方法访问当前请求的response对象
	public static HttpServletResponse getResponse(){
		if(threadResponse.get()!=null){
			return threadResponse.get();
			}else{
			return null;
			}
	}
	//提供静态方法访问当前请求的session对象
	public static HttpSession getSession(){
		if(threadRequest.get()!=null){
			return threadRequest.get().getSession();
			}else{
			return null;
			}
	}
	//提供清空当前上下文方法
	public static void destory(){
		threadRequest.remove();
		threadResponse.remove();
		tempFiles.remove();
		requestParamtersHolder.remove();
		if(actionEntityCollection!=null){
			actionEntityCollection.clear();
			actionEntityCollection=null;
		}
	}
	//将属性值塞入到Request中
	public static void setAttribute2Request(String attributeName,Object value){
		HttpServletRequest request = getRequest();
		if(request!=null){
			request.setAttribute(attributeName, value);
		}
	}
	//将属性值塞入到Session中
	public static void setAttribute2Session(String attributeName,Object value){
		HttpSession session = getSession();
		if(session!=null){
			session.setAttribute(attributeName, value);
		}
	}
	
	public static void setAttribute2ServletContext(String attributeName,Object value){
		ServletContext servletContext = getServletContext();
		if(servletContext!=null){
			servletContext.setAttribute(attributeName, value);
		}
	}


 
	
}
