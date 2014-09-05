/**   
* @Title: InitControllerInstanceException.java
* @Package com.jbeer.framework.exception
* @author Bieber
* @date 2014-2-22 下午02:20:32
* @version V1.0   
*/

package com.jbeer.framework.exception;

/**
 * <p>类功能说明:创建controller实例失败</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: InitControllerInstanceException.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-2-22 下午02:20:32
 * @version V1.0
 */

public class InitObjectException extends RuntimeException {

	/**
	* @Fields serialVersionUID : TODO
	*/
	
	private static final long serialVersionUID = 8318802737081130303L;

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 */

	public InitObjectException() {
	}

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param message
	 */

	public InitObjectException(String message) {
		super(message);
	}

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param cause
	 */

	public InitObjectException(Throwable cause) {
		super(cause);
	}

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param message
	 * @param cause
	 */

	public InitObjectException(String message, Throwable cause) {
		super(message, cause);
	}

}
