/**   
 * @Title: JBeerIOC.java
 * @Package com.jbeer.framework
 * @author Bieber
 * @date 2014-5-11 下午2:51:47
 * @version V1.0   
 */

package com.jbeer.framework;

import java.util.Set;

import com.jbeer.framework.annotation.Bean;
import com.jbeer.framework.constant.JBeerConstant;
import com.jbeer.framework.enumeration.BeanScope;
import com.jbeer.framework.exception.InitializationException;
import com.jbeer.framework.exception.JBeerException;
import com.jbeer.framework.exception.ScanClassException;
import com.jbeer.framework.ioc.JBeerIOCContainerContext;
import com.jbeer.framework.logging.Log;
import com.jbeer.framework.support.InitializingBeanPostProcessor;
import com.jbeer.framework.utils.ClassUtils;
import com.jbeer.framework.utils.LoggerUtil;

/**
 * <p>
 * 类功能说明:IOC模块
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: JBeerIOC.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-11 下午2:51:47
 * @version V1.0
 */

public class JBeerIOC {

    private static final Log logger = LoggerUtil.generateLogger(JBeerIOC.class);
    
    /**
     * 
    * <p>函数功能说明:初始化IOC容器</p>
    * <p>Bieber  2014年6月2日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     */
	public static void init()
			throws ScanClassException, InitializationException {
	    if(logger.isDebugEnabled()){
	        logger.debug("initilazing ioc container......");
	    }
	    
	    if(logger.isDebugEnabled()){
            logger.debug("initilazing BeanPostProcessor......");
        }
		Set<Class<?>> processorClasses = ClassUtils.scanClassesByInterfaceClass(InitializingBeanPostProcessor.class);
		for(Class<?> clazz:processorClasses){
			JBeerIOCContainerContext.registBeanDefinition(clazz, JBeerConstant.IOC_DEFALUT_FACTORY_METHOD_NAME, BeanScope.SINGLETON, clazz.getName());
		}
		Set<Class<?>> classSet = ClassUtils.scanClassesByAnnotation(Bean.class);
		if(logger.isDebugEnabled()){
            logger.debug("found "+classSet.size()+" beans....");
        }
		for (Class<?> clazz : classSet) {
			if(InitializingBeanPostProcessor.class.isAssignableFrom(clazz)){
				continue;
			}
			Bean bean = clazz.getAnnotation(Bean.class);
			JBeerIOCContainerContext.registBeanDefinition(clazz,
					bean.factoryMethodName(), bean.scope(), bean.value());
		}
	}
	
	/**
	 * 
	* <p>函数功能说明:销毁IOC容器</p>
	* <p>Bieber  2014年6月2日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 * @throws JBeerException 
	 */
	public static void destory() throws JBeerException{
	    if(logger.isDebugEnabled()){
            logger.debug("destory ioc container......");
        }
	    JBeerIOCContainerContext.destory();
	}
}
