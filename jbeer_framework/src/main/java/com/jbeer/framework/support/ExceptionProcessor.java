/**   
* @Title: ExceptionProcessor.java
* @Package com.jbeer.framework.support
* @author Bieber
* @date 2014年8月16日 下午1:25:32
* @version V1.0   
*/

package com.jbeer.framework.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>类功能说明:异常处理者</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: ExceptionProcessor.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年8月16日 下午1:25:32
 * @version V1.0
 */

public interface ExceptionProcessor {

	/**
	 * 
	* <p>函数功能说明:当前异常处理者是否接受该异常的处理</p>
	* <p>Bieber  2014年8月16日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return boolean
	 */
	public boolean isAccept(Throwable t);
	
	/**
	 * 
	* <p>函数功能说明:异常处理方法</p>
	* <p>Bieber  2014年8月16日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	public void handleException(Throwable t,HttpServletRequest request,HttpServletResponse response);
}
