/**   
* @Title: FactoryBean.java
* @Package com.jbeer.framework.ioc
* @author Bieber
* @date 2014年7月11日 下午8:01:24
* @version V1.0   
*/

package com.jbeer.framework.ioc;

/**
 * <p>类功能说明:工厂bean</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: FactoryBean.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月11日 下午8:01:24
 * @version V1.0
 */

public interface FactoryBean {

	/**
	 * 
	* <p>函数功能说明:根据某个类型来获取某个对象</p>
	* <p>Bieber  2014年7月11日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return T
	 */
	public<T> T get(Class<T> type);
}
