/**
 * 
 */
package com.jbeer.framework.mybatis;

import java.io.InputStream;
import java.util.Map.Entry;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.jbeer.framework.annotation.RefBean;
import com.jbeer.framework.constant.JBeerConstant;
import com.jbeer.framework.db.JBeerDBContext;
import com.jbeer.framework.enumeration.BeanScope;
import com.jbeer.framework.exception.JBeerException;
import com.jbeer.framework.ioc.JBeerIOCContainerContext;
import com.jbeer.framework.logging.Log;
import com.jbeer.framework.mybatis.config.MyBatisConfig;
import com.jbeer.framework.mybatis.proxy.MapperBean;
import com.jbeer.framework.mybatis.transaction.JbeerTransactionFactory;
import com.jbeer.framework.plugin.Plugin;
import com.jbeer.framework.utils.LoggerUtil;

/**
 * @author bieber
 *
 */
public class MybatisPlugin implements Plugin {

	private static final Log logger = LoggerUtil
			.generateLogger(MybatisPlugin.class);
	private SqlSessionFactory factory;
	@RefBean
	private MyBatisConfig myBatisConfig;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jbeer.framework.plugin.Plugin#initialize()
	 */
	public void initialize() throws JBeerException {
		if (logger.isDebugEnabled()) {
			logger.debug("initialize mybatis plugin");
		}
		try {
			if (myBatisConfig.getXmlConfigResource() != null) {
				InputStream is = Resources.getResourceAsStream(myBatisConfig
						.getXmlConfigResource());
				factory = new SqlSessionFactoryBuilder().build(is,
						myBatisConfig.getEnvironmentId());
				Configuration config = factory.getConfiguration();
				Environment env = factory.getConfiguration().getEnvironment();
				if(env==null){
					env = new Environment(myBatisConfig.getEnvironmentId(), new JbeerTransactionFactory(), JBeerDBContext.getDataSource());
					config.setEnvironment(env);
				}else{
					JBeerDBContext.setDataSource(env.getDataSource());
				}
				configuration(myBatisConfig,config);
			} else {
				Configuration configuration = new Configuration();
				Environment environment = new Environment(
						myBatisConfig.getEnvironmentId(),
						new JbeerTransactionFactory(),
						JBeerDBContext.getDataSource());
				configuration.setEnvironment(environment);
				configuration(myBatisConfig,configuration);
				factory = new SqlSessionFactoryBuilder().build(configuration);
			}
			JBeerIOCContainerContext.registBeanDefinition(MapperBean.class,
					JBeerConstant.IOC_DEFALUT_FACTORY_METHOD_NAME,
					BeanScope.SINGLETON, JBeerConstant.DEFAULT_BEANNAME);
		} catch (Exception e) {
			throw new JBeerException("fail to initialize mybatis plugin",e);
		}
	}

	
	private void configuration(MyBatisConfig config,Configuration configuration) throws ClassNotFoundException{
		if(config.getAliasMap()!=null){
			for(Entry<String,Class<?>> entry:config.getAliasMap().entrySet()){
				configuration.getTypeAliasRegistry().registerAlias(entry.getKey(), entry.getValue());
			}
		}
		configuration.getTypeAliasRegistry().registerAliases(config.getAliasPackageName());
		if(config.getMapperClasses()!=null){
			for(Class<?> mapperClass:config.getMapperClasses()){
				configuration.addMapper(mapperClass);
			}
		}
		
		if(config.getSuperClass()!=null){
			configuration.addMappers(config.getMapperPackageName(), config.getSuperClass());
		}else{
			configuration.addMappers(config.getMapperPackageName());
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jbeer.framework.plugin.Plugin#destory()
	 */
	public void destory() throws JBeerException {
		factory = null;
	}

	public <T> T createMapper(Class<T> mapperClass) {
		if (factory == null) {
			return null;
		}
		return factory.openSession().getMapper(mapperClass);
	}

	public void registeMapper(Class<?> type) {
		factory.getConfiguration().addMapper(type);
	}

	public boolean hasMapper(Class<?> type) {
		return factory.getConfiguration().hasMapper(type);
	}

}
