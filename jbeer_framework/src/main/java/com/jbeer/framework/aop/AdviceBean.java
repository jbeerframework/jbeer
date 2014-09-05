/**   
* @Title: AspectBean.java
* @Package com.jbeer.framework.ioc.aop
* @author Bieber
* @date 2014-5-18 下午8:08:53
* @version V1.0   
*/

package com.jbeer.framework.aop;

import java.lang.reflect.Method;

import com.jbeer.framework.bean.proxy.InvokeHandler;

/**
 * <p>类功能说明:拦截页面的Bean</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: AspectBean.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-18 下午8:08:53
 * @version V1.0
 */

public class AdviceBean extends AbstractAdvice{

 
  
	
	public AdviceBean(String[] classRegex, String[] methodRegex,
			Class<?>[] argTypes, Class<? extends Throwable>[] handleException,
			String beanName, Method before, Method after, Method throwable) {
		super(classRegex, methodRegex, argTypes, handleException, beanName, before,
				after, throwable);
	}

	/* (non-Javadoc)
     * @see com.jbeer.framework.ioc.aop.Aspect#before(com.jbeer.framework.ioc.aop.InvokeHandler)
     */
    @Override
    public final void before(InvokeHandler handler) throws Throwable {
        if(before!=null&&isMatchedMethod(handler.getInvokeMethod().getName())){
            Object bean = getAdviceObject();
            before.invoke(bean, handler);
        }
    }

    /* (non-Javadoc)
     * @see com.jbeer.framework.ioc.aop.Aspect#after(com.jbeer.framework.ioc.aop.InvokeHandler)
     */
    @Override
    public final void after(InvokeHandler handler,Object ret) throws Throwable{
        if(after!=null&&isMatchedMethod(handler.getInvokeMethod().getName())){
            Object bean = getAdviceObject();
            after.invoke(bean, handler,ret);
        }
    }

    /* (non-Javadoc)
     * @see com.jbeer.framework.ioc.aop.Aspect#exception(com.jbeer.framework.ioc.aop.InvokeHandler, java.lang.Exception)
     */
    @Override
    public final void exception(InvokeHandler handler, Throwable e) throws Throwable{
        if(throwable!=null&&isMatchedMethod(handler.getInvokeMethod().getName())&&isMatchException(e.getClass())){
            Object bean = getAdviceObject();
            throwable.invoke(bean, handler,e);
        }
    }
}
