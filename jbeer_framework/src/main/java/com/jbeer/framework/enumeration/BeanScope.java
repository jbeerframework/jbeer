/**   
* @Title: BeanScope.java
* @Package com.jbeer.framework.ioc
* @author Bieber
* @date 2014-5-11 下午3:00:56
* @version V1.0   
*/

package com.jbeer.framework.enumeration;

/**
 * <p>类功能说明:bean的范围，单例，原型，会话，以及线程</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: BeanScope.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-11 下午3:00:56
 * @version V1.0
 */

public enum BeanScope {

	 SINGLETON,PROTOTYPE,SESSION,THREADLOCAL;
}
