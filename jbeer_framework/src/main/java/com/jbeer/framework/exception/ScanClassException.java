/**   
* @Title: ScanClassException.java
* @Package com.jbeer.framework.exception
* @author Bieber
* @date 2014-2-15 下午04:22:32
* @version V1.0   
*/

package com.jbeer.framework.exception;

/**
 * 类功能说明  扫描类异常
 * 类修改者	    修改日期
 * 修改说明
 * <p>Title: ScanClassException.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-2-15 下午04:22:32
 * @Description: TODO
 * @version V1.0
 */

public class ScanClassException extends JBeerException {

	/**
	* @Fields serialVersionUID : TODO
	*/
	
	private static final long serialVersionUID = 8156042873517318297L;

	public ScanClassException() {
		super();
	}

	public ScanClassException(String message, Throwable cause) {
		super(message, cause);
	}

	public ScanClassException(String message) {
		super(message);
	}

	public ScanClassException(Throwable cause) {
		super(cause);
	}

}
