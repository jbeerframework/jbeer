/**   
* @Title: NotFoundActionException.java
* @Package com.jbeer.framework.exception
* @author Bieber
* @date 2014-3-2 下午03:17:40
* @version V1.0   
*/

package com.jbeer.framework.exception;

/**
 * <p>类功能说明:为找到请求的action</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: NotFoundActionException.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-3-2 下午03:17:40
 * @version V1.0
 */

public class NotFoundActionException extends ActionInvokeException{

	/**
	* @Fields serialVersionUID : TODO
	*/
	
	private static final long serialVersionUID = -4466962939347122450L;

	public NotFoundActionException() {
		super();
		this.errorCode="404";
	}

	public NotFoundActionException(String message, Throwable cause) {
		super(message, cause);
		this.errorCode="404";
	}

	public NotFoundActionException(String message) {
		super(message);
		this.errorCode="404";
	}

	public NotFoundActionException(Throwable cause) {
		super(cause);
		this.errorCode="404";
	}
}
