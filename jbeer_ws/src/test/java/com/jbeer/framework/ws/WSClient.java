/**
 * 
 */
package com.jbeer.framework.ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.handler.WSHandlerConstants;

import com.jbeer.framework.ws.security.ClientPasswordCallback;

/**
 * @author bieber
 *
 */
public class WSClient {


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 Map<String,Object> property = new HashMap<String,Object>();
		 property.put(WSHandlerConstants.ACTION, "UsernameToken");
		 property.put(WSHandlerConstants.PASSWORD_TYPE, "PasswordDigest");
		 property.put(WSHandlerConstants.PW_CALLBACK_CLASS,ClientPasswordCallback.class.getName());
		 property.put(WSHandlerConstants.USER, "TEST");
		 WSS4JOutInterceptor outInterceptor = new WSS4JOutInterceptor(property);
		 List<Interceptor<? extends Message>> interceptors = new ArrayList<Interceptor<? extends Message>>();
		 interceptors.add(outInterceptor);
		 JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		 factory.setAddress("http://localhost:8080/ws/simpleService");
		 factory.setServiceClass(SimpleService.class);
		 factory.setOutInterceptors(interceptors);
		 List<LoggingFeature> features = new ArrayList<LoggingFeature>();
		 features.add(new LoggingFeature());
		 Bus bus = BusFactory.getDefaultBus();
		 factory.setBus(bus);
		 factory.getBus().setFeatures(features);
		 SimpleService simpleService = factory.create(SimpleService.class);
		 String ret =	 simpleService.sayHello("bieber");
		 System.out.println(ret);
		 //设置超时时间
		 Client client = ClientProxy.getClient(null);
		  HTTPConduit conduit = (HTTPConduit) client.getConduit();
		  HTTPClientPolicy policy = new HTTPClientPolicy();
		  policy.setReceiveTimeout(110000);
		  policy.setConnectionTimeout(1000);
		  conduit.setClient(policy);
		  
	}

}
 