/**   
* @Title: AuthorizationValidate.java
* @Package com.jbeer.framework.ws.security
* @author Bieber
* @date 2014年7月29日 上午6:12:54
* @version V1.0   
*/

package com.jbeer.framework.ws.security;

/**
 * <p>类功能说明:授权验证，用于服务提供者端进行校验</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: AuthorizationValidate.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月29日 上午6:12:54
 * @version V1.0
 */

public interface AuthorizationValidate {

	/**
	 * 
	* <p>函数功能说明:通过传递的用户名查询匹配的密码，如果没有则返回null</p>
	* <p>Bieber  2014年7月29日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return String
	 */
	public String getPassword(String userName);
}
