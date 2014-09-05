/**   
* @Title: RequestMethodError.java
* @Package com.jbeer.framework.exception
* @author Bieber
* @date 2014-2-22 下午01:13:32
* @version V1.0   
*/

package com.jbeer.framework.exception;

/**
 * <p>类功能说明:请求的方法不匹配异常</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: RequestMethodError.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-2-22 下午01:13:32
 * @version V1.0
 */

public class RequestMethodError extends RuntimeException {

	/**
	* @Fields serialVersionUID : TODO
	*/
	
	private static final long serialVersionUID = 8665308028600450679L;

	public RequestMethodError() {
		super();
	}

	public RequestMethodError(String message, Throwable cause) {
		super(message, cause);
	}

	public RequestMethodError(String message) {
		super(message);
	}

	public RequestMethodError(Throwable cause) {
		super(cause);
	}

}
