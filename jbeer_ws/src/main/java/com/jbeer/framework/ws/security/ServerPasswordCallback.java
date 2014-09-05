/**   
* @Title: ServerPasswordCallback.java
* @Package com.jbeer.framework.ws.security
* @author Bieber
* @date 2014年7月29日 上午8:12:54
* @version V1.0   
*/

package com.jbeer.framework.ws.security;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.wss4j.common.ext.WSPasswordCallback;

import com.jbeer.framework.exception.InitializationException;
import com.jbeer.framework.ioc.JBeerIOCContainerContext;
import com.jbeer.framework.logging.Log;
import com.jbeer.framework.utils.LoggerUtil;
import com.jbeer.framework.ws.config.WSConfig;

/**
 * <p>类功能说明:服务器端的用户密码校验</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: ServerPasswordCallback.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月29日 上午8:12:54
 * @version V1.0
 */

public class ServerPasswordCallback implements CallbackHandler {
	
	private static final Log logger = LoggerUtil.generateLogger(ServerPasswordCallback.class);
	

	/* (non-Javadoc)
	 * @see javax.security.auth.callback.CallbackHandler#handle(javax.security.auth.callback.Callback[])
	 */
	@Override
	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {
		try {
			WSPasswordCallback callback = (WSPasswordCallback) callbacks[0];
			WSConfig wsConfig = JBeerIOCContainerContext.getBeanByType(WSConfig.class);
			AuthorizationValidate authorizationValidate = wsConfig.getAuthorizationValidate();
			callback.setPassword(authorizationValidate.getPassword(callback.getIdentifier()));
		} catch (InitializationException e) {
			logger.debug("fail to validate ws password", e);
		}
	}

}
