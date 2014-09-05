/**   
* @Title: DisposableBean.java
* @Package com.jbeer.framework.support
* @author Bieber
* @date 2014年5月31日 下午7:21:57
* @version V1.0   
*/

package com.jbeer.framework.support;

/**
 * <p>类功能说明:可销毁的bean</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: DisposableBean.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年5月31日 下午7:21:57
 * @version V1.0
 */

public interface DisposableBean {

	
	/**
	 * 
	* <p>函数功能说明:销毁方法</p>
	* <p>Bieber  2014年5月31日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	public void destory() throws Exception;
}
