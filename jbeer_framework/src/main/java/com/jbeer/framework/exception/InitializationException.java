/**   
* @Title: InitializationException.java
* @Package com.jbeer.framework.exception
* @author Bieber
* @date 2014-5-17 下午4:39:40
* @version V1.0   
*/

package com.jbeer.framework.exception;

/**
 * <p>类功能说明:类初始化异常</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: InitializationException.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-17 下午4:39:40
 * @version V1.0
 */

public class InitializationException extends JBeerException {

	/**
	* @Fields serialVersionUID : TODO
	*/
	
	private static final long serialVersionUID = 1L;

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	*/
	
	public InitializationException() {
		super();
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param message
	* @param cause
	*/
	
	public InitializationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param message
	*/
	
	public InitializationException(String message) {
		super(message);
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param cause
	*/
	
	public InitializationException(Throwable cause) {
		super(cause);
	}
	
	

}
