/**   
* @Title: AuthDataProvidorImpl.java
* @Package org.jbeer.sample.ws
* @author Bieber
* @date 2014年7月30日 上午6:48:16
* @version V1.0   
*/

package org.jbeer.sample.ws;

import com.jbeer.framework.ws.security.AuthDataProvider;

/**
 * <p>类功能说明:TODO</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: AuthDataProvidorImpl.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月30日 上午6:48:16
 * @version V1.0
 */

public class AuthDataProviderImpl implements AuthDataProvider {

	/* (non-Javadoc)
	 * @see com.jbeer.framework.ws.security.AuthDataProvidor#getUser()
	 */
	@Override
	public String getUser() {
		return "test";
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.ws.security.AuthDataProvidor#getPassword()
	 */
	@Override
	public String getPassword(String userName) {
		return "test";
	}

}
