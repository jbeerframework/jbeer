/**   
* @Title: BeanFactory.java
* @Package com.jbeer.framework.ioc
* @author Bieber
* @date 2014年5月31日 下午7:15:24
* @version V1.0   
*/

package com.jbeer.framework.ioc;

import java.util.Map;

import com.jbeer.framework.exception.InitializationException;

/**
 * <p>类功能说明:TODO</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: BeanFactory.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年5月31日 下午7:15:24
 * @version V1.0
 */

public interface BeanFactory {
	 /**
     * 
     * <p>
     * 函数功能说明:更具beanid获取bean
     * </p>
     * <p>
     * Bieber 2014-5-17
     * </p>
     * <p>
     * 修改者名字 修改日期
     * </p>
     * <p>
     * 修改内容</a>
     * 
     * @return T
     */
	  public <T extends Object> T getBeanById(String beanId) throws InitializationException;
	  /**
	     * 
	     * <p>
	     * 函数功能说明:根据class的类获取对应的bean
	     * </p>
	     * <p>
	     * Bieber 2014-5-17
	     * </p>
	     * <p>
	     * 修改者名字 修改日期
	     * </p>
	     * <p>
	     * 修改内容</a>
	     * 
	     * @return T
	     */
	  public <T extends Object> T getBeanByType(Class<T> typeClass) throws InitializationException;
	  /**
	     * 
	     * <p>
	     * 函数功能说明:获取BEAN的集合，根据类型
	     * </p>
	     * <p>
	     * Bieber 2014-5-17
	     * </p>
	     * <p>
	     * 修改者名字 修改日期
	     * </p>
	     * <p>
	     * 修改内容</a>
	     * 
	     * @return Map<String,Object>
	     */
	  public Map<String, Object> getBeansByType(Class<?> typeClass) throws InitializationException;
}
