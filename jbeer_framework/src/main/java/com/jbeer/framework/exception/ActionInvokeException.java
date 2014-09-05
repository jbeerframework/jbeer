/**   
* @Title: ActionInvokeException.java
* @Package com.jbeer.framework.exception
* @author Bieber
* @date 2014-2-15 下午07:10:36
* @version V1.0   
*/

package com.jbeer.framework.exception;

/**
 * <p>类功能说明:Action执行异常类</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: ActionInvokeException.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-2-15 下午07:10:36
 * @Description: TODO
 * @version V1.0
 */

public class ActionInvokeException extends WebException {

	/**
	* @Fields serialVersionUID : TODO
	*/
	
	private static final long serialVersionUID = 8326663502580779617L;

	public ActionInvokeException() {
		super();
	}

	public ActionInvokeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ActionInvokeException(String message) {
		super(message);
	}

	public ActionInvokeException(Throwable cause) {
		super(cause);
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param message
	* @param errorCode
	*/
	
	public ActionInvokeException(String message, String errorCode) {
		super(message, errorCode);
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param message
	* @param cause
	* @param errorCode
	*/
	
	public ActionInvokeException(String message, Throwable cause,
			String errorCode) {
		super(message, cause, errorCode);
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param cause
	* @param errorCode
	*/
	
	public ActionInvokeException(Throwable cause, String errorCode) {
		super(cause, errorCode);
	}

}
