/**   
* @Title: SupportException.java
* @Package com.jbeer.framework.exception
* @author Bieber
* @date 2014年5月31日 下午7:28:18
* @version V1.0   
*/

package com.jbeer.framework.exception;

/**
 * <p>类功能说明:第三方切入接口异常</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: SupportException.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年5月31日 下午7:28:18
 * @version V1.0
 */

public class SupportException extends JBeerException {

	/**
	* @Fields serialVersionUID : TODO
	*/
	
	private static final long serialVersionUID = -2114740356513643514L;

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 */

	public SupportException() {
	}

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param message
	 * @param cause
	 */

	public SupportException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param message
	 */

	public SupportException(String message) {
		super(message);
	}

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param cause
	 */

	public SupportException(Throwable cause) {
		super(cause);
	}

}
