/**   
 * @Title: AppConfig.java
 * @Package org.jbeer.sample.config
 * @author Bieber
 * @date 2014年5月31日 下午1:33:09
 * @version V1.0   
 */

package org.jbeer.sample.config;

import org.jbeer.sample.bean.service.SimpleWebservice;
import org.jbeer.sample.ws.AuthDataProviderImpl;
import org.jbeer.sample.ws.WSSecurityValidate;

import com.jbeer.framework.config.AopConfig;
import com.jbeer.framework.config.Configurate;
import com.jbeer.framework.config.DBConfig;
import com.jbeer.framework.config.IN18Config;
import com.jbeer.framework.config.IOCConfig;
import com.jbeer.framework.config.JBeerConfig;
import com.jbeer.framework.config.PluginConfigHandler;
import com.jbeer.framework.config.PropertiesConfig;
import com.jbeer.framework.config.WebConfig;
import com.jbeer.framework.ws.config.WSConfig;

/**
 * <p>
 * 类功能说明:TODO
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: AppConfig.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年5月31日 下午1:33:09
 * @version V1.0
 */

public class AppConfig implements Configurate {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jbeer.framework.config.Configurate#configurateContext(com.jbeer.framework
	 * .config.JBeerConfig)
	 */
	@Override
	public void configurateContext(JBeerConfig config) {
		config.setApplicationEncode("UTF-8");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jbeer.framework.config.Configurate#configurateAop(com.jbeer.framework
	 * .config.AopConfig)
	 */
	@Override
	public void configurateAop(AopConfig config) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jbeer.framework.config.Configurate#configurateDB(com.jbeer.framework
	 * .config.DBConfig)
	 */
	@Override
	public void configurateDB(DBConfig config) {
		config.setDatasource("${jdbc_initSize}", "${jdbc_maxSize}",
				"${jdbc_minSize}", "${jdbc_timeout}", "${jdbc_userName}",
				"${jdbc_password}", "${jdbc_url}", "${jdbc_driver}");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jbeer.framework.config.Configurate#configurateWeb(com.jbeer.framework
	 * .config.WebConfig)
	 */
	@Override
	public void configurateWeb(WebConfig config) {
		config.setViewPrefix("WEB-INF/pages");
		config.setViewSuffix(".jsp");
		config.isSingletonMode(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jbeer.framework.config.Configurate#configureateIOC(com.jbeer.framework
	 * .config.IOCConfig)
	 */
	@Override
	public void configureateIOC(IOCConfig config) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jbeer.framework.config.Configurate#configurateIN18(com.jbeer.framework
	 * .config.IN18Config)
	 */
	@Override
	public void configurateIN18(IN18Config config) {
		config.setBaseName("in18_message");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jbeer.framework.config.Configurate#configurateProperties(com.jbeer
	 * .framework.config.PropertiesConfig)
	 */
	@Override
	public void configurateProperties(PropertiesConfig config) {
		config.setPropertiesPath("*_test.properties");
		config.setPropertiesPath("conf/*.properties");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jbeer.framework.config.Configurate#pluginConfig(com.jbeer.framework
	 * .config.PluginConfig)
	 */
	@Override
	public void pluginConfig(PluginConfigHandler handler) {
		//handler.registerPluginConfig(new MyBatisConfig());
		WSConfig config = new WSConfig();
		config.setInterceptorLogger(true);
		config.setSecurityWS(true);
		config.setAuthorizationValidate(new WSSecurityValidate());
		config.registeWSClient(SimpleWebservice.class, "${host}${simple_ws_url}");
		config.setAuthProvidor(new AuthDataProviderImpl());
		handler.registerPluginConfig(config);
	}

}
