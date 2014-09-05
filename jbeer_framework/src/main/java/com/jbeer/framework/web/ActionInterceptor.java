/**   
* @Title: ActionInterceptor.java
* @Package com.jbeer.framework.web.interceptor
* @author Bieber
* @date 2014-4-22 上午12:06:55
* @version V1.0   
*/

package com.jbeer.framework.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * <p>类功能说明:Action拦截器抽象接口</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: ActionInterceptor.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-4-22 上午12:06:55
 * @version V1.0
 */

public interface ActionInterceptor {

    
    /**
     * 
    * <p>函数功能说明:对请求路径进行个过滤，判断是否需要进行当前拦截器的调用,返回true,则表示需要进行拦截，否则不需要</p>
    * <p>Bieber  2014年6月24日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return List<String>
     */
    public boolean matchedURI(String url);
	/**
	 * 
	* <p>函数功能说明:方法执行前</p>
	* <p>Bieber  2014年7月31日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	public boolean beforeInvokeAction(ActionHandler action,HttpServletRequest request,HttpServletResponse response);
	/**
	 * 
	* <p>函数功能说明:方法执行后</p>
	* <p>Bieber  2014年7月31日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	public boolean afterInvokeAction(ActionHandler action,ModelAndView modelAndView,HttpServletRequest request,HttpServletResponse response);
}
