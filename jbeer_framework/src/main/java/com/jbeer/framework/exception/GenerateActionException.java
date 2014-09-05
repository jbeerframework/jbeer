/**   
* @Title: GenerateActionException.java
* @Package com.jbeer.framework.exception
* @author Bieber
* @date 2014-2-15 下午07:17:24
* @version V1.0   
*/

package com.jbeer.framework.exception;

/**
 * <p>类功能说明:构造Action异常</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: GenerateActionException.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-2-15 下午07:17:24
 * @version V1.0
 */

public class GenerateActionException extends JBeerException {

	/**
	* @Fields serialVersionUID : 
	*/
	
	private static final long serialVersionUID = -4238509187855727294L;

	public GenerateActionException() {
		super();
	}

	public GenerateActionException(String message, Throwable cause) {
		super(message, cause);
	}

	public GenerateActionException(String message) {
		super(message);
	}

	public GenerateActionException(Throwable cause) {
		super(cause);
	}
	

}
