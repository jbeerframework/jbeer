/**   
* @Title: WebException.java
* @Package com.jbeer.framework.exception
* @author Bieber
* @date 2014年7月20日 下午5:39:06
* @version V1.0   
*/

package com.jbeer.framework.exception;

import java.io.IOException;

import com.jbeer.framework.logging.Log;
import com.jbeer.framework.utils.CaseUtils;
import com.jbeer.framework.utils.LoggerUtil;
import com.jbeer.framework.utils.WebUtils;

/**
 * <p>类功能说明:Web相关的异常</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: WebException.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月20日 下午5:39:06
 * @version V1.0
 */

public class WebException extends JBeerException {

	/**
	* @Fields serialVersionUID : TODO
	*/
	
	private static final long serialVersionUID = -3961253871000121347L;

	protected String errorCode=WebUtils.SYSTEM_ERROR;
	
	private static final Log logger = LoggerUtil.generateLogger(WebException.class);

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	*/
	
	public WebException() {
		super();
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param message
	* @param cause
	*/
	
	public WebException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param message
	*/
	
	public WebException(String message) {
		super(message);
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param cause
	*/
	
	public WebException(Throwable cause) {
		super(cause);
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param message
	* @param cause
	*/
	
	public WebException(String message, Throwable cause,String errorCode) {
		super(message, cause);
		this.errorCode=errorCode;
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param message
	*/
	
	public WebException(String message,String errorCode) {
		super(message);
		this.errorCode=errorCode;
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param cause
	*/
	
	public WebException(Throwable cause,String errorCode) {
		super(cause);
		this.errorCode=errorCode;
	}
	
	public void sendError() throws IOException{
			WebUtils.sendError((Integer)CaseUtils.caseType(Integer.class, errorCode), this);
	}
}
