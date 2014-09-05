/**   
* @Title: BeanProxy.java
* @Package com.jbeer.framework.ioc.proxy
* @author Bieber
* @date 2014年5月19日 上午10:18:19
* @version V1.0   
*/

package com.jbeer.framework.bean.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

import com.jbeer.framework.constant.JBeerConstant;
import com.jbeer.framework.exception.InitializationException;
import com.jbeer.framework.ioc.JBeerIOCContainerContext;
import com.jbeer.framework.utils.StringUtils;

/**
* <p>类功能说明:Bean代理的抽象类,定义代理类的模板，整合了JDK代理和cglib代理</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: BeanProxy.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年5月19日 上午10:18:19
* @version V1.0
*/

public final class BeanProxyHandler extends DefaultProxyHandler{

    protected String targetBeanName;
    
    protected Object beanInstance;

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param processor
	* @param targetClass
	*/
	
	public BeanProxyHandler(ProxyTargetProcessor processor, Class<?> targetClass) {
		super(processor, targetClass);
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param targetClass
	* @param processor
	* @param beanInstance
	*/
	
	public BeanProxyHandler(Class<?> targetClass, ProxyTargetProcessor processor,
			Object beanInstance) {
		super(processor,targetClass);
		this.beanInstance = beanInstance;
	}

	/**
     * 
    * <p>Title:初始化参数 </p>
    * <p>Description: </p>
    * @param targetClass
    * @param targetBeanName
     */
    public BeanProxyHandler(Class<?> targetClass, String targetBeanName,ProxyTargetProcessor processor) {
    	super(processor,targetClass);
        this.targetBeanName = targetBeanName;
    }
    
    /* (non-Javadoc)
     * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
     */
    @Override
    public final Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        InvokeHandler handler = new InvokeHandler();
        handler.setArgs(args);
        handler.setInvokeMethod(method);
        handler.setTargetClass(targetClass);
        handler.setProxyObject(proxy);
        try{
        return processor.invokeTarget(getTragetInstance(),handler);
        }catch(InvocationTargetException e){
        	throw e.getTargetException();
        }
    }

    /* (non-Javadoc)
     * @see net.sf.cglib.proxy.MethodInterceptor#intercept(java.lang.Object, java.lang.reflect.Method, java.lang.Object[], net.sf.cglib.proxy.MethodProxy)
     */
    @Override
    public final Object intercept(Object proxy, Method method, Object[] args, MethodProxy handler) throws Throwable{
        InvokeHandler invokeHandler = new InvokeHandler();
        invokeHandler.setArgs(args);
        invokeHandler.setInvokeMethod(method);
        invokeHandler.setTargetClass(targetClass);
        invokeHandler.setProxyObject(proxy);
        try{
        return processor.invokeTarget(getTragetInstance(),invokeHandler);
        }catch(InvocationTargetException e){
        	throw e.getTargetException();
        }
    }

    

    
    /**
     * 
    * <p>函数功能说明:从IOC容器中获取目标对象bean</p>
    * <p>Bieber  2014年5月19日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return Object
     */
    protected final Object getTragetInstance() throws InitializationException {
    	if(beanInstance!=null){
    		return beanInstance;
    	}
        if (StringUtils.isEmpty(targetBeanName)||StringUtils.equals(JBeerConstant.DEFAULT_BEANNAME, targetBeanName)) {
            return JBeerIOCContainerContext.getBeanByType(targetClass);
        } else {
            return JBeerIOCContainerContext.getBeanById(targetBeanName);
        }
    }
    
     
     
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((targetClass == null) ? 0 : targetClass.hashCode());
        result = prime * result
                + ((targetBeanName == null) ? 0 : targetBeanName.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BeanProxyHandler other = (BeanProxyHandler) obj;
        if (targetClass == null) {
            if (other.targetClass != null)
                return false;
        } else if (!targetClass.equals(other.targetClass))
            return false;
        if (targetBeanName == null) {
            if (other.targetBeanName != null)
                return false;
        } else if (!targetBeanName.equals(other.targetBeanName))
            return false;
        return true;
    }

    
}
