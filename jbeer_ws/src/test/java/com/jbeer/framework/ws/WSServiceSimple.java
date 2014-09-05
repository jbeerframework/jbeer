/**
 * 
 */
package com.jbeer.framework.ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.message.Message;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.wss4j.dom.handler.WSHandlerConstants;

import com.jbeer.framework.ws.impl.SimpleServiceImpl;
import com.jbeer.framework.ws.security.ServerPasswordCallback;

/**
 * @author bieber
 *
 */
public class WSServiceSimple {

	 public static void main(String[] args){
		 JaxWsServerFactoryBean factoryBean = new JaxWsServerFactoryBean();
		 factoryBean.setServiceClass(SimpleService.class);
		 factoryBean.setServiceBean(new SimpleServiceImpl());
		 factoryBean.setAddress("http://localhost:8080/ws/simpleService");
		 Map<String,Object> property = new HashMap<String,Object>();
		 property.put(WSHandlerConstants.ACTION, "UsernameToken");
		 property.put(WSHandlerConstants.PASSWORD_TYPE, "PasswordDigest");
		 property.put(WSHandlerConstants.PW_CALLBACK_CLASS,new ServerPasswordCallback());
		 WSS4JInInterceptor interfacInterceptor = new WSS4JInInterceptor(property);
		 List<Interceptor<? extends Message>> interceptors = new ArrayList<Interceptor<? extends Message>>();
		 interceptors.add(interfacInterceptor);
		 List<LoggingFeature> features = new ArrayList<LoggingFeature>();
		 features.add(new LoggingFeature());
		 factoryBean.getBus().setFeatures(features);
		 factoryBean.setInInterceptors(interceptors);
		 factoryBean.create();
	 }
}
 
