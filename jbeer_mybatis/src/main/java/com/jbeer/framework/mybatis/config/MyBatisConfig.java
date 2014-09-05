/**
 * 
 */
package com.jbeer.framework.mybatis.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jbeer.framework.JBeer;
import com.jbeer.framework.config.PluginConfig;
import com.jbeer.framework.properties.PropertiesContext;
import com.jbeer.framework.support.DisposableBean;

/**
 * @author bieber
 *
 */
public class MyBatisConfig implements PluginConfig,DisposableBean{
	
	public static final String PLUGIN_NAMESPACE="mybatis_config";

	private Class<?> superClass;
	private String mapperPackageName=JBeer.getApplicationBasePackageName();
	private String environmentId="JBEER_FRAMEWORK_MYBATIS_ENV";
	private Map<String,Class<?>> aliasMap;
	private List<Class<?>> mapperClasses;
	private String xmlConfigResource;
	private String superClassName;
	private String aliasPackageName=JBeer.getApplicationBasePackageName();
	
	public String getAliasPackageName() {
		String property = PropertiesContext.getProperties(aliasPackageName);
		return property==null?aliasPackageName:property;
	}


	public Class<?> getSuperClass() throws ClassNotFoundException {
		 if(superClassName!=null){
			String property = PropertiesContext.getProperties(superClassName);
			if(property!=null){
				return Class.forName(property);
			}
			return Class.forName(superClassName);
		}else{
			return superClass;
		}
	}


	public String getMapperPackageName() {
		String property = PropertiesContext.getProperties(mapperPackageName);
		return property==null?mapperPackageName:property;
	}


	public String getEnvironmentId() {
		String property = PropertiesContext.getProperties(environmentId);
		return property==null?environmentId:property;
	}


	public Map<String, Class<?>> getAliasMap() {
		return aliasMap;
	}
	


	public List<Class<?>> getMapperClasses() {
		return mapperClasses;
	}


	public String getXmlConfigResource() {
		String property = PropertiesContext.getProperties(xmlConfigResource);
		return property==null?xmlConfigResource:property;
	}


	public void setMyBatisXMLConfigResource(String xmlConfigResource){
		this.xmlConfigResource=xmlConfigResource;
	}
	
	
	public void setSupperType(Class<?> superType){
		if(this.superClass==null){
			this.superClass=superType;
		}
	}
	public void setSupperType(String superClass){
		if(this.superClassName==null){
			this.superClassName=superClass;
		}
	}
	
	public void setMapperPackageName(String packageName){
		if(this.mapperPackageName==null){
			this.mapperPackageName=packageName;
		}
	}
	
	public void setEnvironmentId(String environmentId){
		if(this.environmentId==null){
			this.environmentId=environmentId;
		}
	}
	
	public void registerAlias(String aliasName,Class<?> type){
		if(aliasMap==null){
			aliasMap=new HashMap<String,Class<?>>();
		}
		if(aliasMap.containsKey(aliasName)){
			aliasMap.put(aliasName, type);
		}
	}
	
	public void setAliasPackage(String aliasPackage){
		this.aliasPackageName=aliasPackage;
	}
	 
	public void addMapper(Class<?> mapperClass){
		if(mapperClasses==null){
			mapperClasses=new ArrayList<Class<?>>();
		}
		if(mapperClasses.contains(mapperClass)){
			mapperClasses.add(mapperClass);
		}
	}

	public String pluginNamespace() {
		return PLUGIN_NAMESPACE;
	}

	public void destory() throws Exception {
		this.aliasMap.clear();
		this.mapperClasses.clear();
	}
	
	 
	
}
