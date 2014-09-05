/**   
* @Title: BeanNameAware.java
* @Package com.jbeer.framework.support
* @author Bieber
* @date 2014年5月31日 下午7:23:06
* @version V1.0   
*/

package com.jbeer.framework.support;

/**
 * <p>类功能说明:注入BeanName的接口</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: BeanNameAware.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年5月31日 下午7:23:06
 * @version V1.0
 */

public interface BeanNameAware {

	/**
	 * 
	* <p>函数功能说明:注入beanName方法</p>
	* <p>Bieber  2014年5月31日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	public void setBeanName(String beanName);
}
