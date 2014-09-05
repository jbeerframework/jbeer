/**   
* @Title: WSConfig.java
* @Package com.jbeer.framework.ws.config
* @author Bieber
* @date 2014年7月29日 上午5:59:29
* @version V1.0   
*/

package com.jbeer.framework.ws.config;

import com.jbeer.framework.config.PluginConfig;
import com.jbeer.framework.properties.PropertiesContext;
import com.jbeer.framework.ws.register.WebserviceClientRegister;
import com.jbeer.framework.ws.security.AuthDataProvider;
import com.jbeer.framework.ws.security.AuthorizationValidate;

/**
 * <p>类功能说明:WS插件配置信息实体</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: WSConfig.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月29日 上午5:59:29
 * @version V1.0
 */

public class WSConfig implements PluginConfig {
	
	private static final String WS_PLUGIN_NAMESPACE="ws_plugin_namespace";

	/* (non-Javadoc)
	 * @see com.jbeer.framework.config.PluginConfig#pluginNamespace()
	 */
	public String pluginNamespace() {
		return WS_PLUGIN_NAMESPACE;
	}
	/**
	 * 是否启用安全模式的webservice
	 */
	private boolean isSecurityWS=false;
	/**
	 * 链接超时时间
	 */
	private long connectionTimeout=1000l;
	/**
	 * 超时时间配置属性
	 */
	private String connectionTimeoutProperty;
	
	/**
	 * 接收数据超时时间
	 */
	private long receiveTimeout=12000l;
	/**
	 * 超时时间配置属性
	 */
	private String receiveTimeoutProperty;
	/**
	 * 授权数据提供者，用户服务消费者端提供用户名和密码，{@link isSecurityWS}为true时候有用
	 */
	private AuthDataProvider autoProvidor;
	/**
	 * 授权密码校验，用于服务提供者校验用户名密码 {@link isSecurityWS}为true时候有用
	 */
	private AuthorizationValidate authorizationValidate;
	
	private boolean isInterceptorLogger;
	
	private WebserviceClientRegister wsClientRegister = new WebserviceClientRegister();
	
	

	/**
	 * @return isInterceptorLogger
	 */
	
	public boolean isInterceptorLogger() {
		return isInterceptorLogger;
	}
	/**
	 * @param isInterceptorLogger isInterceptorLogger
	 */
	
	public void setInterceptorLogger(boolean isInterceptorLogger) {
		this.isInterceptorLogger = isInterceptorLogger;
	}
	/**
	 * @return isSecurityWS
	 */
	
	public boolean isSecurityWS() {
		return isSecurityWS;
	}
	/**
	 * @param isSecurityWS isSecurityWS
	 */
	
	public void setSecurityWS(boolean isSecurityWS) {
		this.isSecurityWS = isSecurityWS;
	}
	
	/**
	 * @return connectionTimeout
	 */
	
	public long getConnectionTimeout() {
		if(this.connectionTimeoutProperty==null){
		return connectionTimeout;
		}
		return Long.parseLong(PropertiesContext.getProperties(this.connectionTimeoutProperty));
	}
	/**
	 * @param connectionTimeout connectionTimeout
	 */
	
	public void setConnectionTimeout(long connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
	/**
	 * @return receiveTimeout
	 */
	
	public long getReceiveTimeout() {
		if(this.receiveTimeoutProperty==null){
			return receiveTimeout;
		}
		return Long.parseLong(PropertiesContext.getProperties(this.receiveTimeoutProperty));
	}
	
	public void registeWSClient(Class<?> interfaceClass,String wsUrl){
		wsClientRegister.registeWSClient(interfaceClass, wsUrl);
	}
	
	public String getWSClient(Class<?> interfaceClass){
		return wsClientRegister.getWSUrl(interfaceClass);
	}
	/**
	 * @param receiveTimeout receiveTimeout
	 */
	
	public void setReceiveTimeout(long receiveTimeout) {
		this.receiveTimeout = receiveTimeout;
	}
	/**
	 * 
	* <p>函数功能说明:配置配置文件的key来读取超时时间</p>
	* <p>Bieber  2014年7月29日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	public void setReceiveTimeout(String receiveTimeout){
		this.receiveTimeoutProperty=receiveTimeout;
	}
	
	public void setConnectionTimeout(String connectionTimeout){
		this.connectionTimeoutProperty=connectionTimeout;
	}
	/**
	 * @return autoProvidor
	 */
	
	public AuthDataProvider getAuthProvidor() {
		return autoProvidor;
	}
	/**
	 * @param autoProvidor autoProvidor
	 */
	
	public void setAuthProvidor(AuthDataProvider autoProvidor) {
		this.autoProvidor = autoProvidor;
	}
	/**
	 * @return authorizationValidate
	 */
	
	public AuthorizationValidate getAuthorizationValidate() {
		return authorizationValidate;
	}
	/**
	 * @param authorizationValidate authorizationValidate
	 */
	
	public void setAuthorizationValidate(AuthorizationValidate authorizationValidate) {
		this.authorizationValidate = authorizationValidate;
	}
	
	
	
}
