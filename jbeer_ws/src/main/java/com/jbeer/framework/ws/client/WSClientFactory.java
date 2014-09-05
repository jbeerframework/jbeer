/**   
* @Title: WSClientFactory.java
* @Package com.jbeer.framework.ws.client
* @author Bieber
* @date 2014年7月30日 上午6:18:58
* @version V1.0   
*/

package com.jbeer.framework.ws.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.handler.WSHandlerConstants;

import com.jbeer.framework.annotation.RefBean;
import com.jbeer.framework.ioc.FactoryBean;
import com.jbeer.framework.support.InitializingBean;
import com.jbeer.framework.utils.StringUtils;
import com.jbeer.framework.ws.config.WSConfig;
import com.jbeer.framework.ws.security.AuthDataProvider;
import com.jbeer.framework.ws.security.ClientPasswordCallback;

/**
 * <p>类功能说明:client端的工厂类，通过在{@link RefBean}中的{@link factoryBeanClass}配置该类，那么该属性将会
 * 委托该类的工厂方法产生一个实例</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: WSClientFactory.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月30日 上午6:18:58
 * @version V1.0
 */

public class WSClientFactory implements FactoryBean,InitializingBean{

	@RefBean
	private WSConfig wsConfig;
	
	private 	JaxWsProxyFactoryBean clientProxyFactoryBean;
	
	private static final List<Interceptor<? extends Message>> inInterceptor = new ArrayList<Interceptor<? extends Message>>();
	
	private static final List<Interceptor<? extends Message>> outInterceptor = new ArrayList<Interceptor<? extends Message>>();
	
	/* (non-Javadoc)
	 * @see com.jbeer.framework.ioc.FactoryBean#get(java.lang.Class)
	 */
	@Override
	public <T> T get(Class<T> type) {
		String wsUrl = wsConfig.getWSClient(type);
		if(StringUtils.isEmpty(wsUrl)){
			throw new IllegalArgumentException("didn't found ws url for interface "+type.getName());
		}
		clientProxyFactoryBean.setAddress(wsUrl);
		clientProxyFactoryBean.setServiceClass(type);
		configClientBean();
		T t = clientProxyFactoryBean.create(type);
		setTimeout(t);
		return t;
	}
	/**
	 * 
	* <p>函数功能说明:配置client端的bean，判断是否需要加如安全校验，判断是否需要加入报文传递日志</p>
	* <p>Bieber  2014年7月30日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	private void configClientBean(){
		if(wsConfig!=null){
			outInterceptor.clear();
			inInterceptor.clear();
			if(wsConfig.isSecurityWS()){
				AuthDataProvider authProvidor = wsConfig.getAuthProvidor();
				if(authProvidor==null){
					throw new IllegalArgumentException("didn't found auth data prividor,but is set security web service.");
				}
				Map<String,Object> property = new HashMap<String,Object>();
				 property.put(WSHandlerConstants.ACTION, "UsernameToken");
				 property.put(WSHandlerConstants.PASSWORD_TYPE, "PasswordDigest");
				 property.put(WSHandlerConstants.PW_CALLBACK_CLASS,ClientPasswordCallback.class.getName());
				 property.put(WSHandlerConstants.USER, authProvidor.getUser());
				 WSS4JOutInterceptor wss4jOutInterceptor = new WSS4JOutInterceptor(property);
				 outInterceptor.add(wss4jOutInterceptor);
			}
			if(wsConfig.isInterceptorLogger()){
				LoggingInInterceptor inLoggingInterceptor = new LoggingInInterceptor();
				LoggingOutInterceptor  outLoggingOutInterceptor = new LoggingOutInterceptor();
				inInterceptor.add(inLoggingInterceptor);
				outInterceptor.add(outLoggingOutInterceptor);
			}
			clientProxyFactoryBean.setInInterceptors(inInterceptor);
			clientProxyFactoryBean.setOutInterceptors(outInterceptor);
		}
	}
	
	/**
	 * 
	* <p>函数功能说明:设置超时时间，用于限定接收超时和链接超时</p>
	* <p>Bieber  2014年7月30日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	private void setTimeout(Object object){
		if(wsConfig!=null){
		  Client client = ClientProxy.getClient(object);
		  HTTPConduit conduit = (HTTPConduit) client.getConduit();
		  HTTPClientPolicy policy = new HTTPClientPolicy();
		  policy.setReceiveTimeout(wsConfig.getReceiveTimeout());
		  policy.setConnectionTimeout(wsConfig.getConnectionTimeout());
		  conduit.setClient(policy);
		  }
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.support.InitializingBean#afterPropertiesSet(java.lang.String, java.lang.Object)
	 */
	@Override
	public void afterPropertiesSet(String beanName, Object bean)
			throws Exception {
		clientProxyFactoryBean = new JaxWsProxyFactoryBean();
	}

}
