/**   
* @Title: JBeerContext.java
* @Package com.jbeer.framework.core
* @author Bieber
* @date 2014-2-15 下午05:15:18
* @version V1.0   
*/

package com.jbeer.framework.ioc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.jbeer.framework.enumeration.BeanScope;
import com.jbeer.framework.exception.InitializationException;
import com.jbeer.framework.exception.JBeerException;

/**
 * <p>类功能说明:JBeer框架上下文</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: JBeerContext.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-2-15 下午05:15:18
 * @Description: TODO
 * @version V1.0
 */

public final  class JBeerIOCContainerContext {
	
	private final static DefaultBeanFactory factory = new DefaultBeanFactory();
	
	private final static ConcurrentHashMap<String,BeanDefinition> definitionBeanNameMap = new ConcurrentHashMap<String,BeanDefinition>();
	
	private final static ConcurrentHashMap<Class<?>,BeanDefinition> definitionBeanClassMap = new ConcurrentHashMap<Class<?>,BeanDefinition>();
	
	protected static final ThreadLocal<Map<BeanDefinition, Object>> threadLocalBeanCollection = new ThreadLocal<Map<BeanDefinition, Object>>();
	
	
	 
	protected final static String SESSION_BEANS="SESSION_BEANS";
	
	public static <T extends Object> T getBeanById(String beanId) throws InitializationException {
		return factory.getBeanById(beanId);
	}

	public static <T extends Object> T getBeanByType(Class<T> typeClass) throws InitializationException {
		return factory.getBeanByType(typeClass);
	}
	
	@SuppressWarnings("unchecked")
    public static <T> Map<String,T> getBeansByType(Class<T> typeClass) throws InitializationException {
		return (Map<String, T>) factory.getBeansByType(typeClass);
	}
	
	public synchronized final static void registBeanDefinition(Class<?> clazz,String factoryMethodName,BeanScope scope,String beanName) throws InitializationException{
	    BeanDefinition definition = new BeanDefinition(clazz, factoryMethodName, scope, beanName);
	    if(definitionBeanNameMap.containsKey(definition.getBeanId())){
			throw new InitializationException("already has bean name "+definition.getBeanId());
		}
		if(definitionBeanClassMap.containsKey(clazz)){
			return ;
		}
		
		definitionBeanNameMap.put(definition.getBeanId(), definition);
		definitionBeanClassMap.put(clazz, definition);
	}
	public synchronized final static void registBeanDefinition(Class<?> clazz,String factoryMethodName,BeanScope scope,String beanName,Map<String,String> properties) throws InitializationException{
	    BeanDefinition definition = new BeanDefinition(clazz, factoryMethodName, scope, beanName);
	    definition.setProperties(properties);
	    if(definitionBeanNameMap.containsKey(definition.getBeanId())){
			throw new InitializationException("already has bean name "+definition.getBeanId());
		}
		if(definitionBeanClassMap.containsKey(clazz)){
			return ;
		}
		
		definitionBeanNameMap.put(definition.getBeanId(), definition);
		definitionBeanClassMap.put(clazz, definition);
	}
	
	public synchronized final static void registBeanDefinition(Object beanInstance,BeanScope scope,String beanName) throws InitializationException{
		BeanDefinition definition = new BeanDefinition(beanInstance, scope, beanName);
		if(definitionBeanClassMap.containsKey(definition.getBeanClass())){
			return ;
		}
		if(definitionBeanNameMap.containsKey(definition.getBeanId())){
			throw new InitializationException("already has bean name "+definition.getBeanId());
		}
		definitionBeanNameMap.put(definition.getBeanId(), definition);
		definitionBeanClassMap.put(definition.getBeanClass(), definition);
	}
	
	protected static BeanDefinition getBeanDefinitionByBeanName(String beanName){
		return definitionBeanNameMap.get(beanName);
	}
	
	protected static List<BeanDefinition> getBeanDefinitionsByClass(Class<?> clazz){
		List<BeanDefinition> definitions = new ArrayList<BeanDefinition>();
		for(Entry<Class<?>,BeanDefinition> entry:definitionBeanClassMap.entrySet()){
			if(clazz.isAssignableFrom(entry.getKey())){
			definitions.add(entry.getValue());
			}
		}
		return definitions;
	}
	
	public static  <T extends Object> T createBeanProxy(Class<T> calzz,String beanName){
		return  factory.createProxy(calzz, beanName);
	}
	
	public static void destory() throws JBeerException{
	    try{
		factory.destory();
		definitionBeanNameMap.clear();
		definitionBeanClassMap.clear();
		threadLocalBeanCollection.remove();
		}catch(Exception e){
		    throw new JBeerException(e);
		}
	}
	
	public static void clearCache(){
		threadLocalBeanCollection.remove();
	}
}
