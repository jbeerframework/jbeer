package com.jbeer.framework.mybatis.proxy;

import java.util.concurrent.ConcurrentHashMap;

import com.jbeer.framework.annotation.RefBean;
import com.jbeer.framework.ioc.FactoryBean;
import com.jbeer.framework.mybatis.MybatisPlugin;
import com.jbeer.framework.utils.ProxyUtils;

/**
 * 
 * @author bieber
 *
 */
public class MapperBean implements FactoryBean{

	@RefBean
	private MybatisPlugin plugin;
	
	private static final ConcurrentHashMap<Class<?>,Object> proxyInstanceCache = new ConcurrentHashMap<Class<?>,Object>();
	
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> type) {
		if(proxyInstanceCache.containsKey(type)){
			return (T) proxyInstanceCache.get(type);
		}
		T mapper = ProxyUtils.createProxy(new MapperProxyHandler(plugin), type);
		proxyInstanceCache.putIfAbsent(type, mapper);
		return mapper;
	}

	 

}
