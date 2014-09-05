/**   
 * @Title: JBeerAop.java
 * @Package com.jbeer.framework
 * @author Bieber
 * @date 2014-5-18 下午8:06:17
 * @version V1.0   
 */

package com.jbeer.framework;

import java.lang.reflect.Method;
import java.util.Set;

import com.jbeer.framework.annotation.Aspect;
import com.jbeer.framework.aop.JBeerAopContext;
import com.jbeer.framework.aop.MethodInterceptor;
import com.jbeer.framework.bean.proxy.InvokeHandler;
import com.jbeer.framework.constant.JBeerConstant;
import com.jbeer.framework.enumeration.BeanScope;
import com.jbeer.framework.exception.InitializationException;
import com.jbeer.framework.exception.ScanClassException;
import com.jbeer.framework.ioc.JBeerIOCContainerContext;
import com.jbeer.framework.utils.ClassUtils;

/**
 * <p>
 * 类功能说明:aop模块
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: JBeerAop.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-18 下午8:06:17
 * @version V1.0
 */

public class JBeerAop {

	public static void init() throws ScanClassException,
			InitializationException {
		Set<Class<?>> classSet = ClassUtils
				.scanClassesByAnnotation(Aspect.class);
		if (classSet == null || classSet.size() <= 0) {
			return;
		}
		for (Class<?> clazz : classSet) {
			if (!MethodInterceptor.class.isAssignableFrom(clazz)) {
				Method[] methods = clazz.getDeclaredMethods();
				Aspect aop = clazz.getAnnotation(Aspect.class);
				Method beforeM = null;
				Method afterM = null;
				Method excepM = null;
				for (Method method : methods) {
					if (method.getName().equals("before")
							&& method.getParameterTypes().length == 1
							&& method.getParameterTypes()[0] == InvokeHandler.class) {
						beforeM = method;
					} else if (method.getName().equals("after")
							&& method.getParameterTypes().length == 2
							&& method.getParameterTypes()[0] == InvokeHandler.class) {
						afterM = method;
					} else if (method.getName().equals("exception")
							&& method.getParameterTypes().length == 2
							&& method.getParameterTypes()[0] == InvokeHandler.class
							&& Throwable.class.isAssignableFrom(method
									.getParameterTypes()[1])) {
						excepM = method;
					}
					if(beforeM!=null&&afterM!=null&&excepM!=null){
						break;
					}
				}
				JBeerAopContext.registAspectBean(aop.classRegex(),
						aop.methodRegex(), aop.argTypes(),
						aop.handleException(), clazz.getName(), beforeM,
						afterM, excepM);
				JBeerIOCContainerContext.registBeanDefinition(clazz,
						JBeerConstant.IOC_DEFALUT_FACTORY_METHOD_NAME,
						BeanScope.SINGLETON, clazz.getName());
			}
		}
	}

}
