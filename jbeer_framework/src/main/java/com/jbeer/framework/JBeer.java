/**   
 * @Title: JBeer.java
 * @Package com.jbeer.framework.config
 * @author Bieber
 * @date 2014-2-15 下午02:58:47
 * @version V1.0   
 */

package com.jbeer.framework;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.jbeer.framework.config.AopConfig;
import com.jbeer.framework.config.Configurate;
import com.jbeer.framework.config.DBConfig;
import com.jbeer.framework.config.IN18Config;
import com.jbeer.framework.config.IOCConfig;
import com.jbeer.framework.config.JBeerConfig;
import com.jbeer.framework.config.PluginConfig;
import com.jbeer.framework.config.PluginConfigHandler;
import com.jbeer.framework.config.PropertiesConfig;
import com.jbeer.framework.config.WebConfig;
import com.jbeer.framework.constant.JBeerConstant;
import com.jbeer.framework.enumeration.BeanScope;
import com.jbeer.framework.exception.InitializationException;
import com.jbeer.framework.exception.JBeerException;
import com.jbeer.framework.ioc.JBeerIOCContainerContext;
import com.jbeer.framework.plugin.CallStart;
import com.jbeer.framework.utils.ClassUtils;
import com.jbeer.framework.utils.StringUtils;
import com.jbeer.framework.web.JBeerWebContext;

/**
 * 
 * 类功能说明 类修改者 修改日期 修改说明
 * <p>
 * Title: JBeer.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-2-15 下午03:00:29
 * @Description: JBeer框架初始化唯一入口
 * @version V1.0
 */
public class JBeer {
	private static String applicationBasePackageName;

	private static String applicationEncode = JBeerConstant.DEFAULT_ENCODE;

	private static Map<String, String> contextConfigurate = new HashMap<String, String>();

	private static volatile boolean isSartup = false;

	private static void configJBeer() throws JBeerException {
		Set<Class<?>> configurates = ClassUtils
				.scanClassesByInterfaceClass(Configurate.class);
		if (configurates != null && configurates.size() > 0) {
			try {
				Configurate config = (Configurate) configurates.iterator()
						.next().newInstance();
				configJBeer(config);
			} catch (Exception e) {
				throw new InitializationException(
						"initialization jbeer configurate error", e);
			}
		} else {
			throw new JBeerException("not found jbeer configurate");
		}
	}

	private static void configJBeer(Configurate config) throws JBeerException {
		if (config != null) {
			try {
				AopConfig aop = new AopConfig();
				DBConfig db = new DBConfig();
				IOCConfig ioc = new IOCConfig();
				WebConfig web = new WebConfig();
				JBeerConfig jbeer = new JBeerConfig();
				IN18Config in18 = new IN18Config();
				PropertiesConfig proper = new PropertiesConfig();
				PluginConfigHandler pluginHandler = new PluginConfigHandler();
				config.configurateContext(jbeer);
				config.configurateAop(aop);
				config.configurateDB(db);
				config.configurateWeb(web);
				config.configureateIOC(ioc);
				config.configurateIN18(in18);
				config.configurateProperties(proper);
				/**
				 * 加载插件配置信息
				 */
				config.pluginConfig(pluginHandler);
				Map<String, PluginConfig> plugins = pluginHandler.getPlugins();
				if(plugins!=null){
				for (Entry<String, PluginConfig> entry : plugins.entrySet()) {
					JBeerIOCContainerContext.registBeanDefinition(entry
							.getValue(), BeanScope.SINGLETON, entry.getValue()
							.pluginNamespace());
				}
				}
			} catch (Exception e) {
				throw new InitializationException(
						"initialization jbeer configurate error", e);
			}
		} else {
			throw new JBeerException("not found jbeer configurate");
		}
	}

	/**
	 * 
	 * 函数功能说明: JBeer框架初始化唯一入口方法 Bieber 2014-2-15 修改者名字 修改日期 修改内容
	 * 
	 * @param
	 * @return void
	 * @throws JBeerException
	 */
	public static void initFramework4Web() throws JBeerException {
		String basePackageName = getContextConfig("basePackageName");
		if (StringUtils.isEmpty(basePackageName)) {
			basePackageName = "";
		}
		applicationBasePackageName = basePackageName;
		configJBeer();
		initFramework();
	}

	/**
	 * 
	 * 函数功能说明: JBeer框架初始化唯一入口方法 Bieber 2014-2-15 修改者名字 修改日期 修改内容
	 * 
	 * @param
	 * @return void
	 * @throws JBeerException
	 */
	public static void initFramework(String basePackageName, Configurate config)
			throws JBeerException {
		if (StringUtils.isEmpty(basePackageName)) {
			basePackageName = "";
		}
		applicationBasePackageName = basePackageName;
		configJBeer(config);
		initFramework();
	}

	private static void initFramework() throws JBeerException {
		JBeerProperties.init();
		JBeerIN18.init();
		JBeerPlugin.init();
		JBeerAop.init();
		JBeerIOC.init();
		JBeerWeb.init();
		JBeerDB.init();
		isSartup = true;
		Map<String,CallStart> needCalls = JBeerIOCContainerContext.getBeansByType(CallStart.class);
		for(Entry<String,CallStart> needCall:needCalls.entrySet()){
			needCall.getValue().start();
		}
	}

	public static void destoryFramework() throws JBeerException {
		JBeerPlugin.destory();
		JBeerWebContext.destory();
		JBeerIOCContainerContext.destory();
		isSartup = false;
	}

	public static boolean isStartup() {
		return isSartup;
	}

	public static void setContextConfig(String key, String value) {
		if (!isSartup) {
			contextConfigurate.put(key, value);
		}
	}

	public static String getContextConfig(String key) {
		 return contextConfigurate.get(key);
	}

	public static void setApplicationBasePackageName(
			String applicationBasePackageName) {
		if (applicationBasePackageName == null && !isSartup) {
			JBeer.applicationBasePackageName = applicationBasePackageName;
		}
	}

	public static String getApplicationBasePackageName() {
		if (contextConfigurate.get("basePackageName") == null) {
			return applicationBasePackageName;
		}
		return contextConfigurate.get("basePackageName");
	}

	public static void setApplicationEncode(String applicationEncode) {
		if (!isSartup) {
			JBeer.applicationEncode = applicationEncode;
		}
	}

	public static String getApplicationEncode() {
		return JBeer.applicationEncode;
	}

}
