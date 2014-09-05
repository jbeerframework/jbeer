/**   
* @Title: BeanDefinition.java
* @Package com.jbeer.framework.ioc
* @author Bieber
* @date 2014-5-11 下午3:14:30
* @version V1.0   
*/

package com.jbeer.framework.ioc;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.jbeer.framework.constant.JBeerConstant;
import com.jbeer.framework.enumeration.BeanScope;

/**
 * <p>类功能说明:一个bean对象的描述</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: BeanDefinition.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-11 下午3:14:30
 * @version V1.0
 */

public final class BeanDefinition implements Serializable {

	/**
	* @Fields serialVersionUID : TODO
	*/
	
	private static final long serialVersionUID = 1390756010509534489L;

	private Class<?> beanClass;
	
	private String factoryMethodName;
	
	private BeanScope scope;
	
	
	private String beanName;
	
	private String beanId;
	
	private Object beanInstance;
	
	 
	public Object getBeanInstance() {
		return beanInstance;
	}

	private Map<String,Object> properties = new HashMap<String,Object>();
	
	public Map<String,Object> getProperties(){
		return properties;
	}
	public Object getProperty(String propertyName){
		return properties.get(propertyName);
	}
	public void setProperty(String propertyName,Object value){
		if(propertyName==null){
			return ;
		}
		this.properties.put(propertyName, value);
	}
	
	public void setProperties(Map<String,String> properties){
		if(properties==null){
			return;
		}
		this.properties.putAll(properties);
	}

	/**
	 * @return beanId
	 */
	
	public String getBeanId() {
		return beanId;
	}


	/**
	 * @param beanId beanId
	 */
	
	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}


	/**
	* <p>Title: 默认构造器，初始化参数</p>
	* <p>Description: 初始化一个bean构造的必备信息</p>
	* @param beanClass
	* @param factoryMethodName
	* @param scope
	* @param beanName
	*/
	
	public BeanDefinition(Object beanInstance,
			BeanScope scope, String beanName) {
		super();
		this.beanClass = beanInstance.getClass();
		this.scope = scope;
		this.beanName = beanName;
		this.beanInstance=beanInstance;
		if(JBeerConstant.DEFAULT_BEANNAME.equals(beanName)){
			beanId = beanClass.getName();
		}else{
			beanId = beanName;
		}
	}

	/**
	* <p>Title: 默认构造器，初始化参数</p>
	* <p>Description: 初始化一个bean构造的必备信息</p>
	* @param beanClass
	* @param factoryMethodName
	* @param scope
	* @param beanName
	*/
	
	public BeanDefinition(Class<?> beanClass, String factoryMethodName,
			BeanScope scope, String beanName) {
		super();
		this.beanClass = beanClass;
		this.factoryMethodName = factoryMethodName;
		this.scope = scope;
		this.beanName = beanName;
		if(JBeerConstant.DEFAULT_BEANNAME.equals(beanName)){
			beanId = beanClass.getName();
		}else{
			beanId = beanName;
		}
	}
	
	/**
	 * 
	* <p>函数功能说明:是否匹配当前类型的bean</p>
	* <p>Bieber  2014-5-11</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return boolean
	 */
	private boolean isMatchType(Class<?> type){
		if(type==null){
			return false;
		}
		return type.isAssignableFrom(beanClass);
	}
	
	/**
	 * 
	* <p>函数功能说明:是否匹配当前要找的beanname</p>
	* <p>Bieber  2014-5-11</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return boolean
	 */
	private boolean isMathchBeanId(String beanId){
		
		if(beanId==null){
			return false;
		}
		return beanId.equals(this.beanId);
	}
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj==null){
			return false;
		}
		if(this==obj){
			return true;
		}
		if(BeanDefinition.class.isAssignableFrom(obj.getClass())){
			BeanDefinition other = (BeanDefinition) obj;
			return isMathchBeanId(other.getBeanId())||isMatchType(other.getBeanClass());
		}else if(String.class.isAssignableFrom(obj.getClass())){
			return isMathchBeanId(obj.toString());
		}else if(Class.class.isAssignableFrom(obj.getClass())){
			return isMatchType((Class<?>) obj);
		}
		return false;
	}

	 

	 

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	/**
	 * @return beanClass
	 */
	
	protected Class<?> getBeanClass() {
		return beanClass;
	}

	/**
	 * @return factoryMethodName
	 */
	
	protected String getFactoryMethodName() {
		return factoryMethodName;
	}

	/**
	 * @return scope
	 */
	
	protected BeanScope getScope() {
		return scope;
	}

	/**
	 * @return beanName
	 */
	
	protected String getBeanName() {
		return beanName;
	}

	 
	
}
