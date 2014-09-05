/**   
* @Title: ViewDataConvertException.java
* @Package com.jbeer.framework.exception
* @author Bieber
* @date 2014-4-13 下午02:51:02
* @version V1.0   
*/

package com.jbeer.framework.exception;

/**
 * <p>类功能说明:TODO</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: ViewDataConvertException.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-4-13 下午02:51:02
 * @version V1.0
 */

public class RenderingViewException extends Exception{

	/**
	* @Fields serialVersionUID : TODO
	*/
	
	private static final long serialVersionUID = 9096566804374884969L;

	public RenderingViewException() {
		super();
	}

	public RenderingViewException(String message, Throwable cause) {
		super(message, cause);
	}

	public RenderingViewException(String message) {
		super(message);
	}

	public RenderingViewException(Throwable cause) {
		super(cause);
	}
	
}
