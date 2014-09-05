/**
 * 
 */
package com.jbeer.framework.ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebService;

import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.message.Message;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.wss4j.dom.handler.WSHandlerConstants;

import com.jbeer.framework.annotation.RefBean;
import com.jbeer.framework.constant.JBeerConstant;
import com.jbeer.framework.enumeration.BeanScope;
import com.jbeer.framework.exception.JBeerException;
import com.jbeer.framework.exception.ScanClassException;
import com.jbeer.framework.ioc.JBeerIOCContainerContext;
import com.jbeer.framework.plugin.Plugin;
import com.jbeer.framework.utils.ClassUtils;
import com.jbeer.framework.utils.ProxyUtils;
import com.jbeer.framework.utils.StringUtils;
import com.jbeer.framework.ws.annotation.WS;
import com.jbeer.framework.ws.client.WSClientFactory;
import com.jbeer.framework.ws.config.WSConfig;
import com.jbeer.framework.ws.proxy.WSBeanProcessor;
import com.jbeer.framework.ws.security.ServerPasswordCallback;

/**
 * @author bieber
 * webservice插件
 */
public class WSPlugin implements Plugin {


	@RefBean
	private WSConfig wsConfig;
	
	
	private static final List<Interceptor<? extends Message>> inInterceptor = new ArrayList<Interceptor<? extends Message>>();
	
	private static final List<Interceptor<? extends Message>> outInterceptor = new ArrayList<Interceptor<? extends Message>>();

	/* (non-Javadoc)
	 * @see com.jbeer.framework.plugin.Plugin#initialize()
	 */
	public void initialize() throws JBeerException {
		JBeerIOCContainerContext.registBeanDefinition(WSClientFactory.class, JBeerConstant.IOC_DEFALUT_FACTORY_METHOD_NAME, BeanScope.SINGLETON, JBeerConstant.DEFAULT_BEANNAME);
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.plugin.Plugin#destory()
	 */
	public void destory() throws JBeerException {

	}

	public void publishWebservice() throws ScanClassException{
		Set<Class<?>> webServiceClasses =  ClassUtils.scanClassesByAnnotation(WebService.class,true);
		
		for(Class<?> wsClass:webServiceClasses){
		JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
	    String address = getAddress(wsClass);
		WS ws = wsClass.getAnnotation(WS.class);
		String beanId = JBeerConstant.DEFAULT_BEANNAME;
		if(ws!=null){
			beanId = ws.refBean();
		}
		WSBeanProcessor processor = new WSBeanProcessor();
		configWebserviceBean(factory);
		factory.setAddress(address);
		factory.setServiceBean(ProxyUtils.createBeanProxy(processor, wsClass, beanId));
		factory.setServiceClass(wsClass);
		factory.create();
		}
	}
	
	private void configWebserviceBean(JaxWsServerFactoryBean factory){
		if(wsConfig!=null){
			inInterceptor.clear();
			outInterceptor.clear();
			if(wsConfig.isInterceptorLogger()){
				LoggingInInterceptor inLoggingInterceptor = new LoggingInInterceptor();
				LoggingOutInterceptor  outLoggingOutInterceptor = new LoggingOutInterceptor();
				inInterceptor.add(inLoggingInterceptor);
				outInterceptor.add(outLoggingOutInterceptor);
			}
			if(wsConfig.isSecurityWS()){
				 Map<String,Object> property = new HashMap<String,Object>();
				 property.put(WSHandlerConstants.ACTION, "UsernameToken");
				 property.put(WSHandlerConstants.PASSWORD_TYPE, "PasswordDigest");
				 property.put(WSHandlerConstants.PW_CALLBACK_CLASS,ServerPasswordCallback.class.getName());
				 WSS4JInInterceptor wss4jInInterceptor = new WSS4JInInterceptor(property);
				 inInterceptor.add(wss4jInInterceptor);
			}
			factory.setInInterceptors(inInterceptor);
			factory.setOutInterceptors(outInterceptor);
		}
	}
	
	private String getAddress(Class<?> clazz){
		String address = "/"+clazz.getSimpleName();
		WebService webservice = clazz.getAnnotation(WebService.class);
		if(!StringUtils.isEmpty(webservice.wsdlLocation())){
			address=webservice.wsdlLocation();
			if(!address.startsWith("/")){
				address="/"+address;
			}
		}
		return address;
	}
}
