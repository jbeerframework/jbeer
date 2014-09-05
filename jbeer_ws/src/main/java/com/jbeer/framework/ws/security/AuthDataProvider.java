/**   
* @Title: AuthDataProvidor.java
* @Package com.jbeer.framework.ws.security
* @author Bieber
* @date 2014年7月29日 上午6:08:06
* @version V1.0   
*/

package com.jbeer.framework.ws.security;

/**
 * <p>类功能说明:授权数据提供者</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: AuthDataProvidor.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月29日 上午6:08:06
 * @version V1.0
 */

public interface AuthDataProvider {

	/**
	 * 
	* <p>函数功能说明:ws客户端获取调用服务的用户名</p>
	* <p>Bieber  2014年7月30日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return String
	 */
	public String getUser();
	/**
	 * 
	* <p>函数功能说明:ws客户端获取调用服务的密码</p>
	* <p>Bieber  2014年7月30日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return String
	 */
	public String getPassword(String userName);
}
