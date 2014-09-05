/**   
 * @Title: BeanFactory.java
 * @Package com.jbeer.framework.ioc
 * @author Bieber
 * @date 2014-5-11 下午3:09:57
 * @version V1.0   
 */

package com.jbeer.framework.ioc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jbeer.framework.annotation.Aspect;
import com.jbeer.framework.annotation.Message;
import com.jbeer.framework.annotation.Properties;
import com.jbeer.framework.annotation.RefBean;
import com.jbeer.framework.aop.AOPAdviceProcessor;
import com.jbeer.framework.aop.Advice;
import com.jbeer.framework.aop.JBeerAopContext;
import com.jbeer.framework.bean.proxy.BeanProxyProcessor;
import com.jbeer.framework.constant.JBeerConstant;
import com.jbeer.framework.enumeration.BeanScope;
import com.jbeer.framework.exception.InitializationException;
import com.jbeer.framework.exception.JBeerException;
import com.jbeer.framework.exception.MessageException;
import com.jbeer.framework.exception.SupportException;
import com.jbeer.framework.logging.Log;
import com.jbeer.framework.message.MessageUtils;
import com.jbeer.framework.properties.PropertiesContext;
import com.jbeer.framework.support.BeanFactoryAware;
import com.jbeer.framework.support.BeanNameAware;
import com.jbeer.framework.support.DisposableBean;
import com.jbeer.framework.support.InitializingBean;
import com.jbeer.framework.support.InitializingBeanPostProcessor;
import com.jbeer.framework.utils.CaseUtils;
import com.jbeer.framework.utils.ClassUtils;
import com.jbeer.framework.utils.LoggerUtil;
import com.jbeer.framework.utils.ObjectUtil;
import com.jbeer.framework.utils.ProxyUtils;
import com.jbeer.framework.utils.StringUtils;
import com.jbeer.framework.web.JBeerWebContext;

/**
 * <p>
 * 类功能说明:jbeer的IOC容器构造工厂
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: BeanFactory.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-11 下午3:09:57
 * @version V1.0
 */

public class DefaultBeanFactory implements BeanFactory{

    private static final ConcurrentHashMap<BeanDefinition, Object> beanCollection      = new ConcurrentHashMap<BeanDefinition, Object>();

    private static final ConcurrentHashMap<String, Object>         beanProxyCollection = new ConcurrentHashMap<String, Object>();

    private static final Log logger = LoggerUtil.generateLogger(DefaultBeanFactory.class);
    /**
     * 
     * <p>
     * 函数功能说明:更具beanid获取bean
     * </p>
     * <p>
     * Bieber 2014-5-17
     * </p>
     * <p>
     * 修改者名字 修改日期
     * </p>
     * <p>
     * 修改内容</a>
     * 
     * @return T
     */
    @SuppressWarnings("unchecked")
    public <T extends Object> T getBeanById(String beanId) throws InitializationException {
        T object = getBean(beanId);
        if (object != null) {
            return object;
        }
        BeanDefinition definition = JBeerIOCContainerContext.getBeanDefinitionByBeanName(beanId);
        if (definition == null) {
            throw new InitializationException("not found bean name is  " + beanId
                                              + " bean definition");
        }
        Object bean = initBean(definition);
        return (T) bean;
    }

    /**
     * 
     * <p>
     * 函数功能说明:将会话级别的bean放入到session中
     * </p>
     * <p>
     * Bieber 2014-5-17
     * </p>
     * <p>
     * 修改者名字 修改日期
     * </p>
     * <p>
     * 修改内容</a>
     * 
     * @return void
     */
    @SuppressWarnings("unchecked")
    private synchronized void putBeanToSession(BeanDefinition definition, Object bean) {
        HttpServletRequest request = JBeerWebContext.getRequest();
        if (request != null) {
            HttpSession session = request.getSession();
            ConcurrentHashMap<BeanDefinition, Object> sessionBeans = (ConcurrentHashMap<BeanDefinition, Object>) session
                .getAttribute(JBeerIOCContainerContext.SESSION_BEANS);
            if (sessionBeans == null) {
                sessionBeans = new ConcurrentHashMap<BeanDefinition, Object>();
            }
            sessionBeans.put(definition, bean);
            session.setAttribute(JBeerIOCContainerContext.SESSION_BEANS, sessionBeans);
        }

    }

    /**
     * 
     * <p>
     * 函数功能说明:获取bean对象
     * </p>
     * <p>
     * Bieber 2014-5-17
     * </p>
     * <p>
     * 修改者名字 修改日期
     * </p>
     * <p>
     * 修改内容</a>
     * 
     * @return T
     */
    @SuppressWarnings("unchecked")
    private <T extends Object> T getBean(Object key) {
        for (Entry<BeanDefinition, Object> entry : beanCollection.entrySet()) {
            if (entry.getKey().equals(key)) {
                return (T) entry.getValue();
            }
        }

        Map<BeanDefinition, Object> beanMap = JBeerIOCContainerContext.threadLocalBeanCollection
            .get();
        if (beanMap != null) {
            for (Entry<BeanDefinition, Object> entry : beanMap.entrySet()) {
                if (entry.getKey().equals(key)) {
                    return (T) entry.getValue();
                }
            }
        }
        HttpServletRequest request = JBeerWebContext.getRequest();
        if (request != null) {
            ConcurrentHashMap<BeanDefinition, Object> sessionBeans = (ConcurrentHashMap<BeanDefinition, Object>) request
                .getSession().getAttribute(JBeerIOCContainerContext.SESSION_BEANS);
            if (sessionBeans != null) {
                for (Entry<BeanDefinition, Object> entry : sessionBeans.entrySet()) {
                    if (entry.getKey().equals(key)) {
                        return (T) entry.getValue();
                    }
                }
            }
        }
        return null;
    }

    /**
     * 
     * <p>
     * 函数功能说明:根据class的类获取对应的bean
     * </p>
     * <p>
     * Bieber 2014-5-17
     * </p>
     * <p>
     * 修改者名字 修改日期
     * </p>
     * <p>
     * 修改内容</a>
     * 
     * @return T
     */
    @SuppressWarnings("unchecked")
    public <T extends Object> T getBeanByType(Class<T> typeClass) throws InitializationException {
        T object = getBean(typeClass);
        if (object != null) {
            return object;
        }
        List<BeanDefinition> definitions = JBeerIOCContainerContext
            .getBeanDefinitionsByClass(typeClass);
        if (definitions == null || definitions.size() <= 0) {
            throw new InitializationException("not found bean class is  " + typeClass.getName()
                                              + " bean definition");
        }
        Object bean = null;
        for (BeanDefinition definition : definitions) {
            bean = initBean(definition);
        }
        return (T) bean;
    }

    /**
     * 
     * <p>
     * 函数功能说明:获取bean对象
     * </p>
     * <p>
     * Bieber 2014-5-17
     * </p>
     * <p>
     * 修改者名字 修改日期
     * </p>
     * <p>
     * 修改内容</a>
     * 
     * @return T
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> getBeans(Class<?> key) {
        Map<String, Object> beans = new HashMap<String, Object>();
        for (Entry<BeanDefinition, Object> entry : beanCollection.entrySet()) {
            if (entry.getKey().equals(key)) {
                beans.put(entry.getKey().getBeanId(), entry.getValue());
            }
        }

        Map<BeanDefinition, Object> beanMap = JBeerIOCContainerContext.threadLocalBeanCollection
            .get();
        if (beanMap != null) {
            for (Entry<BeanDefinition, Object> entry : beanMap.entrySet()) {
                if (entry.getKey().equals(key)) {
                    beans.put(entry.getKey().getBeanId(), entry.getValue());
                }
            }
        }
        HttpServletRequest request = JBeerWebContext.getRequest();
        if (request != null) {
            ConcurrentHashMap<BeanDefinition, Object> sessionBeans = (ConcurrentHashMap<BeanDefinition, Object>) request
                .getSession().getAttribute(JBeerIOCContainerContext.SESSION_BEANS);
            if (sessionBeans != null) {
                for (Entry<BeanDefinition, Object> entry : sessionBeans.entrySet()) {
                    if (entry.getKey().equals(key)) {
                        beans.put(entry.getKey().getBeanId(), entry.getValue());
                    }
                }
            }
        }
        return beans;
    }

    /**
     * 
     * <p>
     * 函数功能说明:获取BEAN的集合，根据类型
     * </p>
     * <p>
     * Bieber 2014-5-17
     * </p>
     * <p>
     * 修改者名字 修改日期
     * </p>
     * <p>
     * 修改内容</a>
     * 
     * @return Map<String,Object>
     */
    public Map<String, Object> getBeansByType(Class<?> typeClass) throws InitializationException {
        Map<String, Object> beans = getBeans(typeClass);

        if (beans == null) {
            beans = new HashMap<String, Object>();
        }
        List<BeanDefinition> definitions = JBeerIOCContainerContext
            .getBeanDefinitionsByClass(typeClass);
        if(beans.size()==definitions.size()){
        	return beans;
        }
        Object bean = null;
        for (BeanDefinition definition : definitions) {
            bean = initBean(definition);
            beans.put(definition.getBeanId(), bean);

        }
        return beans;
    }

    /**
     * 
    * <p>函数功能说明:织入增强到Bean中,通过装饰模式来对增强进行组装</p>
    * <p>Bieber  2014年5月19日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return Object
     */
    private Object weaveAdviceIntoBean(BeanDefinition definition, Object bean) {
    	Aspect aspect = definition.getBeanClass().getAnnotation(Aspect.class);
        if (aspect == null) {
            BeanProxyProcessor processor = new BeanProxyProcessor();
            AOPAdviceProcessor adviceProcessor = new AOPAdviceProcessor(processor);
            Collection<Advice> adviceBeans = JBeerAopContext.getAspectBeans();
            for(Advice adviceBean:adviceBeans){
                if(adviceBean.isMatchedClass(definition.getBeanClass().getName())){
                    adviceProcessor = new AOPAdviceProcessor(adviceProcessor, adviceBean);
                }
            }
            return ProxyUtils.createBeanProxy(adviceProcessor, bean.getClass(), bean);
        }
        return bean;
    }

    /**
     * 
     * <p>
     * 函数功能说明:初始化bean的入口，包括bean对象实例化以及内部属性赋值
     * </p>
     * <p>
     * Bieber 2014-5-17
     * </p>
     * <p>
     * 修改者名字 修改日期
     * </p>
     * <p>
     * 修改内容</a>
     * 
     * @return Object
     */
    private Object initBean(BeanDefinition definition) throws InitializationException {
        try {
        	if(logger.isDebugEnabled()){
        		logger.debug("init bean:"+definition.getBeanClass().getName());
        	}
            Object bean = generateBean(definition);
            bean = wrapBean(bean, definition);
            bean = weaveAdviceIntoBean(definition, bean);
            if (definition.getScope() == BeanScope.SINGLETON) {
                beanCollection.putIfAbsent(definition, bean);
                return bean;
            }
            if (definition.getScope() == BeanScope.PROTOTYPE) {
                return bean;
            }
            if (definition.getScope() == BeanScope.SESSION) {
                putBeanToSession(definition, bean);
                return bean;
            }
            if (definition.getScope() == BeanScope.THREADLOCAL) {
                Map<BeanDefinition, Object> beans = JBeerIOCContainerContext.threadLocalBeanCollection
                    .get();
                if (beans == null) {
                    beans = new HashMap<BeanDefinition, Object>();
                    JBeerIOCContainerContext.threadLocalBeanCollection.set(beans);
                }
                beans.put(definition, bean);
                return bean;
            }
            return bean;
        } catch (Exception e) {
            throw new InitializationException("fialed to initialization bean class is "
                                              + definition.getBeanClass().getName(), e);
        }
    }

    /**
     * 
     * <p>
     * 函数功能说明:构造实体Bean
     * </p>
     * <p>
     * Bieber 2014-5-17
     * </p>
     * <p>
     * 修改者名字 修改日期
     * </p>
     * <p>
     * 修改内容</a>
     * 
     * @return Object
     * @throws MessageException 
     */
    private Object generateBean(BeanDefinition definition) throws SecurityException,
                                                          NoSuchMethodException,
                                                          IllegalArgumentException,
                                                          InstantiationException,
                                                          IllegalAccessException,
                                                          InvocationTargetException, MessageException {
    	if(definition.getBeanInstance()!=null){
    		return definition.getBeanInstance();
    	}
        Class<?> clazz = definition.getBeanClass();
        Object beanInstance = null;
        if (!StringUtils.isEmpty(definition.getFactoryMethodName())
            && !JBeerConstant.IOC_DEFALUT_FACTORY_METHOD_NAME.equals(definition
                .getFactoryMethodName())) {
            Method factoryMethod = null;
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getName().equals(definition.getFactoryMethodName())) {
                    factoryMethod = method;
                    break;
                }
            }
            if(factoryMethod==null){
                throw new NoSuchMethodException("dit not found factory method "+definition.getFactoryMethodName());
            }
            Class<?>[] types = factoryMethod.getParameterTypes();
            Object[] args = null;
            if (types != null && types.length > 0) {
                Annotation[][] annotations = factoryMethod.getParameterAnnotations();
                args = new Object[types.length];
                for (int i = 0; i < types.length; i++) {
                    Annotation[] annos = annotations[i];
                    for (Annotation anno : annos) {
                        if (anno instanceof RefBean) {
                            RefBean refBean = (RefBean) anno;
                            args[i] = createProxy(types[i], refBean.value());
                        }else if(anno instanceof Properties){
                        	Properties properties = (Properties) anno;
                        	args[i]=PropertiesContext.getProperties(properties.name());
                        }else if(anno instanceof Message){
                        	Message message = (Message) anno;
                        	args[i]=MessageUtils.getMessage(message.name(), message.args());
                        }
                    }
                }
            }
            beanInstance = factoryMethod.invoke(clazz, args);
        } else {
            beanInstance = clazz.newInstance();
        }
        return beanInstance;
    }

    /**
     * 
     * <p>
     * 函数功能说明:对实例化好的bean进行封装，设置内部属性
     * </p>
     * <p>
     * Bieber 2014-5-17
     * </p>
     * <p>
     * 修改者名字 修改日期
     * </p>
     * <p>
     * 修改内容</a>
     * 
     * @return Object
     * @throws JBeerException 
     */
    private Object wrapBean(Object bean, BeanDefinition definition)
                                                                   throws IllegalArgumentException,
                                                                   IllegalAccessException,
                                                                   InvocationTargetException, JBeerException {
        Class<?> clazz = definition.getBeanClass();
        Collection<Field> fields = ClassUtils.listClassAllFields(clazz, false);
        for (Field field : fields) {
            RefBean refBean = field.getAnnotation(RefBean.class);
            field.setAccessible(true);
            if (refBean != null) {
            	if(refBean.factoryBeanClass()!=FactoryBean.class){
            		FactoryBean factory = getBeanByType(refBean.factoryBeanClass());
            		field.set(bean, factory.get(field.getType()));
            		continue;
            	}
                field.set(bean, createProxy(field.getType(), refBean.value()));
            }else{
                Properties proper = field.getAnnotation(Properties.class); 
                if(proper!=null){
                    field.set(bean, CaseUtils.caseType(field.getType(), PropertiesContext.getProperties(proper.name())));
                }else{
                    Message message = field.getAnnotation(Message.class); 
                    if(message!=null){
                        try {
                            field.set(bean, MessageUtils.getMessage(message.name(), message.args()));
                        } catch (MessageException e) {
                            if(logger.isDebugEnabled()){
                                logger.debug("fail to loading "+message.name()+" in18 property");
                            }
                        }
                    }
                }
            }
        }
        ObjectUtil.mapToObject(definition.getProperties(), bean);
        initializingBean(definition.getBeanId(),bean,clazz);
        beanNameAware(definition.getBeanId(),clazz,bean);
        setBeanFactory(bean,clazz);
        initializingBeanPostProcessor(definition.getBeanId(),bean);
        return bean;
    }
    
  
    
    private void initializingBeanPostProcessor(String beanName,Object bean) throws SupportException{
    	try {
    		if(InitializingBeanPostProcessor.class.isAssignableFrom(bean.getClass())){
    			return;
    		}
    		Map<String,Object> processors  = getBeansByType(InitializingBeanPostProcessor.class);
    		if(processors!=null&&processors.size()>0){
    			for(Entry<String, Object> entry:processors.entrySet()){
    				InitializingBeanPostProcessor processor = (InitializingBeanPostProcessor) entry.getValue();
    				processor.setBeanFactory(this);
    				processor.afterPropertiesSet(beanName, bean);
    			}
    		}
		} catch (Exception e) {
			 throw new SupportException(e);
		}
    }
    
    private void initializingBean(String beanName,Object bean,Class<?> clazz) throws SupportException{
    	 if(InitializingBean.class.isAssignableFrom(clazz)){
         	InitializingBean initializingBean = (InitializingBean) bean;
         	try {
 				initializingBean.afterPropertiesSet(beanName, bean);
 			} catch (Exception e) {
 				 throw new SupportException("invoke class "+clazz.getName()+" afterPropertiesSet error");
 			}
         }
    }
    
    private void beanNameAware(String beanName,Class<?> clazz,Object bean){
    	 if(BeanNameAware.class.isAssignableFrom(clazz)){
         	BeanNameAware beanNameAware = (BeanNameAware) bean;
         	beanNameAware.setBeanName(beanName);
         }
    }
    
    private void setBeanFactory(Object bean,Class<?> clazz){
    	if(BeanFactoryAware.class.isAssignableFrom(clazz)){
        	BeanFactoryAware beanFactoryAware = (BeanFactoryAware) bean;
        	beanFactoryAware.setBeanFactory(this);
        }
    }

    /**
     * 
     * <p>
     * 函数功能说明:创建Bean的代理实体
     * </p>
     * <p>
     * Bieber 2014-5-17
     * </p>
     * <p>
     * 修改者名字 修改日期
     * </p>
     * <p>
     * 修改内容</a>
     * 
     * @return Object
     */
    @SuppressWarnings("unchecked")
    public <T extends Object> T createProxy(Class<T> proxyClass, String proxyBeanName) {
        String key = proxyClass.getName() + proxyBeanName;
        if (!beanProxyCollection.containsKey(key)) {
            beanProxyCollection.putIfAbsent(key,
                ProxyUtils.createBeanProxy(new BeanProxyProcessor(), proxyClass, proxyBeanName));
        }
        return (T) beanProxyCollection.get(key);

    }

    public void destory() throws Exception {
        Collection<Object> beans = beanCollection.values();
        for(Object bean:beans){
            if(DisposableBean.class.isAssignableFrom(bean.getClass())){
                DisposableBean disposableBean = (DisposableBean) bean;
                disposableBean.destory();
            }
        }
        beanCollection.clear();
        beanProxyCollection.clear();
    }
}
