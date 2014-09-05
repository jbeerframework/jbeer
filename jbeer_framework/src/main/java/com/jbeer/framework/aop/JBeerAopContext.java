/**   
* @Title: JBeerAopContext.java
* @Package com.jbeer.framework.ioc.aop
* @author Bieber
* @date 2014-5-18 下午8:31:42
* @version V1.0   
*/

package com.jbeer.framework.aop;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>类功能说明:TODO</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: JBeerAopContext.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-18 下午8:31:42
 * @version V1.0
 */

public class JBeerAopContext {

	private static final Set<Advice> aspectBeanCollection = new HashSet<Advice>();
	
	public synchronized static void registAspectBean(String[] classRegrex, String[] methodRegex,Class<?>[] argTypes,Class<? extends Throwable>[] handleException,String beanName,
			Method before, Method after, Method throwable){
		aspectBeanCollection.add(new AdviceBean(classRegrex, methodRegex,argTypes,handleException, beanName, before, after, throwable));
	}
	
	public synchronized static void registAspectBean(Advice advice){
	    aspectBeanCollection.add(advice);
	}
	
	public static Collection<Advice> getAspectBeans(){
		return Collections.unmodifiableCollection(aspectBeanCollection);
	}
}
