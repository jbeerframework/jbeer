/**   
* @Title: ObjectFactory.java
* @Package com.jbeer.framework.pool
* @author Bieber
* @date 2014-5-24 下午7:48:23
* @version V1.0   
*/

package com.jbeer.framework.pool;

/**
 * <p>类功能说明:对象工厂</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: ObjectFactory.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-24 下午7:48:23
 * @version V1.0
 */

public interface ObjectFactory<T> {

	/**
	 * 
	* <p>函数功能说明:创建一个对象</p>
	* <p>Bieber  2014-5-24</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return T
	 */
	public T createObject();
	 
}
