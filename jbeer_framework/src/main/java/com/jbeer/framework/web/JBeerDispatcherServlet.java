/**   
 * @Title: JBeerDispatchServlet.java
 * @Package com.jbeer.framework.web
 * @author Bieber
 * @date 2014-2-15 下午04:28:42
 * @version V1.0   
 */

package com.jbeer.framework.web;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jbeer.framework.ioc.JBeerIOCContainerContext;
import com.jbeer.framework.logging.Log;
import com.jbeer.framework.utils.LoggerUtil;

/**
 * 类功能说明 处理JBeer web组件各个请求的分发器 类修改者 修改日期 修改说明
 * <p>
 * Title: JBeerDispatchServlet.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-2-15 下午04:28:42
 * @Description: TODO
 * @version V1.0
 */
// @WebServlet(urlPatterns="/*",name="jbeerDispatchServlet",description="处理JBeer web组件各个请求的分发器")
public class JBeerDispatcherServlet extends HttpServlet {

    /**
     * @Fields serialVersionUID
     */

    private static final long   serialVersionUID = -515461318002645745L;

    private static final Log logger           = LoggerUtil
                                                     .generateLogger(JBeerDispatcherServlet.class);


    /**
     * 所有请求的入口，此处在切入到整个web框架，分发请求
     */
    protected void service(HttpServletRequest request, HttpServletResponse response)
                                                                                    throws ServletException,
                                                                                    IOException {

        try {
            if (logger.isDebugEnabled()) {
                logger.debug("Has a new request coming，request url is " + request.getRequestURI());
            }
            JBeerWebContext.newRequestComing(request, response);
            try {
                /**
                 * 匹配一个处理当前请求的action实体，如果没匹配到，将会404错误
                 */
                RequestTargetAction targetAction = ActionMapperHandler.mapperAction(request);
                if (logger.isDebugEnabled()) {
                    logger.debug("has matched a request for " + request.getRequestURI()
                                 + " controller class is "
                                 + targetAction.getControllerClassFullName() + " method is "
                                 + targetAction.getActionMethod().getName());
                }
                List<ActionInterceptor> interceptors = JBeerWebContext.getInterceptors();
                
                /**
                 * 构造出当前Action请求需要的controller实体对象
                 */
                Object controller = ControllerInitialization.generateController(targetAction);
                
                if (logger.isDebugEnabled()) {
                    logger.debug("initialize a controller instance for " + request.getRequestURI());
                }
                boolean intercepteResult = true;
                /**
                 * 构造调用这个action方法，需要的参数
                 */
                Object[] args = ControllerInitialization.initActionMethodParams(targetAction);
                ActionHandler handler=null;
                if(interceptors!=null){
                    handler = new ActionHandler(targetAction.getActionMethod(), args, controller, request.getRequestURI());
                   Iterator<ActionInterceptor> interceptorIterator = interceptors.iterator();
                   while(interceptorIterator.hasNext()){
                       ActionInterceptor interceptor = interceptorIterator.next();
                       if(interceptor.matchedURI(request.getRequestURI())){
                    	   intercepteResult =intercepteResult&&interceptor.beforeInvokeAction(handler,request,JBeerWebContext.getResponse());
                       }else{
                           interceptorIterator.remove();
                       }
                   }
                }
                if(!intercepteResult){
                	return;
                }
                /**
                 * 调用action的方法，返回实体以及需要渲染的数据
                 */
                ModelAndView modelView = ActionInvoker.invokeAction(controller,
                    targetAction.getActionMethod(), args);
                if(interceptors!=null){
                   Iterator<ActionInterceptor> interceptorIterator = interceptors.iterator();
                   while(interceptorIterator.hasNext()){
                       ActionInterceptor interceptor = interceptorIterator.next();
                       if(interceptor.matchedURI(request.getRequestURI())){
                    	   intercepteResult=intercepteResult&&interceptor.afterInvokeAction(handler, modelView,request,JBeerWebContext.getResponse());
                       }
                   }
                }
                if(!intercepteResult){
                	return;
                }
                if(modelView!=null){
                if (logger.isDebugEnabled()) {
                    logger.debug("had invoke action for " + request.getRequestURI() + " "
                                 + " return model and view is " + modelView.getClass().getName()+" and start rendering view");
                }
                /**
                 * 渲染视图
                 */
                RenderingView.rendering(request, response, modelView);
                }
            } catch (Throwable e) {
            	e.printStackTrace();
            	ExceptionHandler.handleException(e, request, response);
            }  
        } finally {
            if(logger.isDebugEnabled()){
                logger.debug("Finished processing "+request.getRequestURI()+" request");
            }
            List<File> tempFiles = JBeerWebContext.getTempFiles();
            if (tempFiles != null)
                for (File file : tempFiles) {
                    file.delete();
                }
            JBeerWebContext.processedRequest();
            JBeerIOCContainerContext.clearCache();
        }
    }

}
