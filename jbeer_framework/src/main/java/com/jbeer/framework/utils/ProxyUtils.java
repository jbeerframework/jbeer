/**   
* @Title: ProxyUtils.java
* @Package com.jbeer.framework.utils
* @author Bieber
* @date 2014年5月19日 上午10:14:15
* @version V1.0   
*/

package com.jbeer.framework.utils;

import java.lang.reflect.Proxy;

import net.sf.cglib.proxy.Enhancer;

import com.jbeer.framework.bean.proxy.BeanProxyHandler;
import com.jbeer.framework.bean.proxy.DefaultProxyHandler;
import com.jbeer.framework.bean.proxy.MustCGLIBProxy;
import com.jbeer.framework.bean.proxy.ProxyTargetProcessor;
import com.jbeer.framework.config.PluginConfig;
import com.jbeer.framework.plugin.Plugin;

/**
* <p>类功能说明:TODO</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: ProxyUtils.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年5月19日 上午10:14:15
* @version V1.0
*/

public class ProxyUtils {
    
    
    /**
     * 
    * <p>函数功能说明:创建代理类的入口，内部判断是生成jdk的方式还是cglib方式</p>
    * <p>Bieber  2014年5月19日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return T
     */
    public static <T extends Object> T createBeanProxy(ProxyTargetProcessor processor,Class<T> clazz,String proxyBeanName){
        Class<?> superClass = clazz.getSuperclass();
        Class<?>[] interfaces = clazz.getInterfaces();
        /**
         * 判断当前需要代理的类是否只是只有接口，如果只是实现了接口，而没有继承父类，则使用jdk的代理模式
         * 否则使用cglib代理模式
         */
       if(!clazz.isInterface()&&superClass.isAssignableFrom(Object.class)&&interfaces!=null&&interfaces.length>0&&!MustCGLIBProxy.class.isAssignableFrom(clazz)){
           return  createJDKInvoketionProxy(processor,clazz,proxyBeanName);
        }else {
           return createCGLIBProxy(processor,clazz, proxyBeanName);
        }
    }
    
    /**
     * 
    * <p>函数功能说明:创建代理类的入口，内部判断是生成jdk的方式还是cglib方式</p>
    * <p>Bieber  2014年5月19日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return T
     */
    public static <T extends Object> T createBeanProxy(ProxyTargetProcessor processor,Class<T> clazz,Object beanInstance){
        Class<?> superClass = clazz.getSuperclass();
        Class<?>[] interfaces = clazz.getInterfaces();
        /**
         * 判断当前需要代理的类是否只是只有接口，如果只是实现了接口，而没有继承父类，则使用jdk的代理模式
         * 否则使用cglib代理模式
         */
       if(!clazz.isInterface()&&superClass.isAssignableFrom(Object.class)&&interfaces!=null&&interfaces.length>0&&!MustCGLIBProxy.class.isAssignableFrom(clazz)){
           return  createJDKInvoketionProxy(processor, clazz, beanInstance);
        }else {
           return createCGLIBProxy(processor, clazz, beanInstance);
        }
    }
    
    public static <T extends Object> T createProxy(ProxyTargetProcessor processor,Class<T> clazz){
    	  Class<?> superClass = clazz.getSuperclass();
          Class<?>[] interfaces = clazz.getInterfaces();
          DefaultProxyHandler handler = new DefaultProxyHandler(processor,clazz);
          /**
           * 判断当前需要代理的类是否只是只有接口，如果只是实现了接口，而没有继承父类，则使用jdk的代理模式
           * 否则使用cglib代理模式
           */
         if(!clazz.isInterface()&&superClass.isAssignableFrom(Object.class)&&interfaces!=null&&interfaces.length>0&&!MustCGLIBProxy.class.isAssignableFrom(clazz)){
             return  jdkAdvice(clazz, handler);
          }else {
             return cglibAdvice(clazz, handler);
          }
    }
    /**
     * 
    * <p>函数功能说明:创建JDK自带的代理类</p>
    * <p>Bieber  2014年5月19日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return T
     */
    private static <T extends Object> T createJDKInvoketionProxy(ProxyTargetProcessor processor,Class<T> clazz,String proxyBeanName){
        BeanProxyHandler proxy = new BeanProxyHandler(clazz, proxyBeanName,processor);
        return jdkAdvice(clazz,proxy);
    }
    /**
     * 
    * <p>函数功能说明:创建JDK自带的代理类</p>
    * <p>Bieber  2014年5月19日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return T
     */
    private static <T extends Object> T createJDKInvoketionProxy(ProxyTargetProcessor processor,Class<T> clazz,Object beanInstance){
        BeanProxyHandler proxy = new BeanProxyHandler(clazz, processor, beanInstance);
        return jdkAdvice(clazz,proxy);
    }
    
    
    @SuppressWarnings("unchecked")
	private static  <T extends Object> T jdkAdvice(Class<T> clazz,DefaultProxyHandler handler){
    	 Class<?>[] interfaces = clazz.getInterfaces();
         return (T) Proxy.newProxyInstance(clazz.getClassLoader(), interfaces, handler);
    }
    /**
     * 
    * <p>函数功能说明:创建CGLIB代理类</p>
    * <p>Bieber  2014年5月19日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return T
     */
    private static <T extends Object> T createCGLIBProxy(ProxyTargetProcessor processor,Class<T> proxyClass,String proxyBeanName){
        BeanProxyHandler proxy = new BeanProxyHandler(proxyClass, proxyBeanName,processor);
        return cglibAdvice(proxyClass,proxy);
    }
    
    @SuppressWarnings("unchecked")
	private static  <T extends Object> T cglibAdvice(Class<T> proxyClass,DefaultProxyHandler handler){
    	Enhancer enhancer = new Enhancer();
        Class<?>[] interfaces = proxyClass.getInterfaces();
        if(interfaces!=null&&interfaces.length>0){
            enhancer.setInterfaces(interfaces);
        }
        enhancer.setClassLoader(proxyClass.getClassLoader());
        enhancer.setSuperclass(proxyClass);
        enhancer.setCallback(handler);
        return (T) enhancer.create();
    }
    /**
     * 
    * <p>函数功能说明:创建CGLIB代理类</p>
    * <p>Bieber  2014年5月19日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return T
     */
    private static <T extends Object> T createCGLIBProxy(ProxyTargetProcessor processor,Class<T> proxyClass,Object beanInstance){
        BeanProxyHandler proxy = new BeanProxyHandler(proxyClass, processor, beanInstance);
        return cglibAdvice(proxyClass,proxy);
    }
}
