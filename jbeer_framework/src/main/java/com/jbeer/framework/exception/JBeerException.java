/**   
* @Title: JBeerException.java
* @Package com.jbeer.framework.exception
* @author Bieber
* @date 2014-2-15 下午03:07:47
* @version V1.0   
*/

package com.jbeer.framework.exception;

/**
 * 类功能说明
 * 类修改者	    修改日期
 * 修改说明
 * <p>Title: JBeerException.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-2-15 下午03:07:47
 * @Description: JBeer框架异常底层类
 * @version V1.0
 */

public class JBeerException extends Exception{

	/**
	* @Fields serialVersionUID 
	*/
	
	private static final long serialVersionUID = -2282667947327443360L;

	public JBeerException() {
		super();
	}

	public JBeerException(String message, Throwable cause) {
		super(message, cause);
	}

	public JBeerException(String message) {
		super(message);
	}

	public JBeerException(Throwable cause) {
		super(cause);
	}

}
