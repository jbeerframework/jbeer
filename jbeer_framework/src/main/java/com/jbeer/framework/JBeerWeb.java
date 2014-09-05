/**   
 * @Title: JBeerWeb.java
 * @Package com.jbeer.framework
 * @author Bieber
 * @date 2014-4-22 上午12:08:58
 * @version V1.0   
 */

package com.jbeer.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.jbeer.framework.annotation.Controller;
import com.jbeer.framework.constant.JBeerConstant;
import com.jbeer.framework.enumeration.BeanScope;
import com.jbeer.framework.exception.GenerateActionException;
import com.jbeer.framework.exception.InitializationException;
import com.jbeer.framework.exception.ScanClassException;
import com.jbeer.framework.ioc.JBeerIOCContainerContext;
import com.jbeer.framework.logging.Log;
import com.jbeer.framework.support.ExceptionProcessor;
import com.jbeer.framework.utils.ClassUtils;
import com.jbeer.framework.utils.LoggerUtil;
import com.jbeer.framework.web.ActionInterceptor;
import com.jbeer.framework.web.JBeerWebContext;
import com.jbeer.framework.web.viewrender.Render;

/**
 * <p>
 * 类功能说明:MVC 模块
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: JBeerWeb.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-4-22 上午12:08:58
 * @version V1.0
 */

public final class JBeerWeb {
    private static final Log            logger             = LoggerUtil
                                                                  .generateLogger(JBeerWeb.class);

    protected static String                webTempFileDir;

    protected static String                viewPrefix="WEB-INF/pages";

    protected static String                viewSuffix=".jsp";

    protected static String                velocityProperties = null;

    protected static Boolean               isSingletonMode    = true;



    /**
     * 
    * <p>函数功能说明:初始化拦截器</p>
    * <p>Bieber  2014年6月24日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     */
    private synchronized static void initInterceptors() throws InitializationException {
        Map<String, ActionInterceptor> interceptorMap = JBeerIOCContainerContext
            .getBeansByType(ActionInterceptor.class);
        List<ActionInterceptor> interceptors = new ArrayList<ActionInterceptor>();
        for (Entry<String, ActionInterceptor> interceptor : interceptorMap.entrySet()) {
            interceptors.add(interceptor.getValue());
        }
        JBeerWebContext.setInterceptors(interceptors);
    }

    /**
     * 
    * <p>函数功能说明:初始化Web模块，从当前classpath中扫描被注解了{@link Controller}注解的类，并将其注册到IOC容器中</p>
    * <p>Bieber  2014年6月2日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     */
    protected static void init() throws GenerateActionException, ScanClassException,
                                InitializationException {
        if (logger.isDebugEnabled()) {
            logger.debug("initializing web module");
        }
        if (!JBeerWeb.viewPrefix.startsWith("/")) {
            JBeerWeb.viewPrefix = "/" + JBeerWeb.viewPrefix;
        }
        if (!JBeerWeb.viewPrefix.endsWith("/")) {
            JBeerWeb.viewPrefix = JBeerWeb.viewPrefix + "/";
        }

        /**
         * 扫描框架默认的所有渲染器，并注入的IOC中
         */
        Set<Class<?>> renderClassSet = ClassUtils.scanClassesByInterfaceClass(JBeerWeb.class
            .getPackage().getName(), Render.class);
        for (Class<?> clazz : renderClassSet) {
            JBeerIOCContainerContext.registBeanDefinition(clazz,
                JBeerConstant.IOC_DEFALUT_FACTORY_METHOD_NAME, BeanScope.SINGLETON,
                JBeerConstant.DEFAULT_BEANNAME);
        }

        
        /**
         * 扫描当前应用支持的所有异常处理器，并注入到IOC中
         */
        Set<Class<?>> excpetionProcessorClassSet = ClassUtils.scanClassesByInterfaceClass(ExceptionProcessor.class);
        
        for(Class<?> clazz:excpetionProcessorClassSet){
        	 JBeerIOCContainerContext.registBeanDefinition(clazz,
                     JBeerConstant.IOC_DEFALUT_FACTORY_METHOD_NAME, BeanScope.SINGLETON,
                     JBeerConstant.DEFAULT_BEANNAME);
        }
        
        /**
         * 扫描当前应用支持的所有拦截器，并注入到IOC中
         */
        Set<Class<?>> interceptorClassSet = ClassUtils
            .scanClassesByInterfaceClass(ActionInterceptor.class);
        for (Class<?> clazz : interceptorClassSet) {
            JBeerIOCContainerContext.registBeanDefinition(clazz,
                JBeerConstant.IOC_DEFALUT_FACTORY_METHOD_NAME, BeanScope.SINGLETON,
                JBeerConstant.DEFAULT_BEANNAME);
        }
        initInterceptors();

        /**
         * 扫描当前应用里面，第三方渲染器，并注入到IOC中
         */
        renderClassSet = ClassUtils.scanClassesByInterfaceClass(Render.class);
        for (Class<?> clazz : renderClassSet) {
            JBeerIOCContainerContext.registBeanDefinition(clazz,
                JBeerConstant.IOC_DEFALUT_FACTORY_METHOD_NAME, BeanScope.SINGLETON,
                JBeerConstant.DEFAULT_BEANNAME);
        }
        JBeerWebContext.initContext();
        Set<Class<?>> classSet = ClassUtils.scanClassesByAnnotation(Controller.class);
        if (logger.isDebugEnabled()) {
            logger.debug("has found " + classSet.size() + " controller in current application.");
        }
        for (Class<?> clazz : classSet) {
            BeanScope scope = BeanScope.PROTOTYPE;
            if (JBeerWeb.isSingletonMode) {
                scope = BeanScope.SINGLETON;
            }
            JBeerIOCContainerContext.registBeanDefinition(clazz,
                JBeerConstant.IOC_DEFALUT_FACTORY_METHOD_NAME, scope,
                JBeerConstant.DEFAULT_BEANNAME);
        }
    }
    
    /**
     * @return viewPrefix
     */

    public static String getViewPrefix() {
        return viewPrefix;
    }

    /**
    * @param viewPrefix viewPrefix
    */

    public static void setViewPrefix(String viewPrefix) {
        if (JBeerWeb.viewPrefix == null) {
            JBeerWeb.viewPrefix = viewPrefix;
        }
    }

    /**
    * @return viewSuffix
    */

    public static String getViewSuffix() {
        return viewSuffix;
    }

    /**
    * @param viewSuffix viewSuffix
    */

    public static void setViewSuffix(String viewSuffix) {
        if (JBeerWeb.viewSuffix == null) {
            JBeerWeb.viewSuffix = viewSuffix;
        }
    }

    /**
    * @return velocityProperties
    */

    public static String getVelocityProperties() {
        return velocityProperties;
    }

    /**
    * @param velocityProperties velocityProperties
    */

    public static void setVelocityProperties(String velocityProperties) {
        if (JBeerWeb.velocityProperties == null) {
            JBeerWeb.velocityProperties = velocityProperties;
        }
    }

    /**
    * @return isSingletonMode
    */

    public static boolean isSingletonMode() {
        if(JBeerWeb.isSingletonMode==null){
            return false;
        }
        return isSingletonMode;
    }

    /**
    * @param isSingletonMode isSingletonMode
    */

    public static void setSingletonMode(boolean isSingletonMode) {
        if(JBeerWeb.isSingletonMode==null){
        JBeerWeb.isSingletonMode = isSingletonMode;
        }
        }

    public static void setWebTempFileDir(String webTempFileDir) {
        if (JBeerWeb.webTempFileDir == null) {
            JBeerWeb.webTempFileDir = webTempFileDir;
        }
    }

    public static String getWebTempFileDir() {
        if (JBeerWeb.webTempFileDir == null) {
            return JBeerConstant.DEFAULT_TEMP_FILE_DIR;
        }
        return JBeerWeb.webTempFileDir;
    }
}
