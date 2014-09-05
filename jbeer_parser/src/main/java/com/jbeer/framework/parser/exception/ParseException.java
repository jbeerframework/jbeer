/**   
* @Title: ParseXMLException.java
* @Package com.jbeer.framework.parser.exception
* @author Bieber
* @date 2014年7月15日 下午5:39:12
* @version V1.0   
*/

package com.jbeer.framework.parser.exception;

import com.jbeer.framework.exception.JBeerException;

/**
 * <p>类功能说明:TODO</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: ParseXMLException.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月15日 下午5:39:12
 * @version V1.0
 */

public class ParseException extends JBeerException {

	/**
	* @Fields serialVersionUID : TODO
	*/
	
	private static final long serialVersionUID = 842912560569941124L;

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 */

	public ParseException() {
	}

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param message
	 * @param cause
	 */

	public ParseException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param message
	 */

	public ParseException(String message) {
		super(message);
	}

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param cause
	 */

	public ParseException(Throwable cause) {
		super(cause);
	}

}
