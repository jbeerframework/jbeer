/**   
* @Title: WSSecurityValidate.java
* @Package org.jbeer.sample.ws
* @author Bieber
* @date 2014年7月29日 上午8:24:35
* @version V1.0   
*/

package org.jbeer.sample.ws;

import com.jbeer.framework.ws.security.AuthorizationValidate;

/**
 * <p>类功能说明:TODO</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: WSSecurityValidate.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月29日 上午8:24:35
 * @version V1.0
 */

public class WSSecurityValidate implements AuthorizationValidate{

	/* (non-Javadoc)
	 * @see com.jbeer.framework.ws.security.AuthorizationValidate#getPassword(java.lang.String)
	 */
	@Override
	public String getPassword(String userName) {
		if(userName.equals("test")){
		return "test";
		}
		return null;
	}

}
