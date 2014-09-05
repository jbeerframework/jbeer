/**   
* @Title: NotFoundFieldSetMetodException.java
* @Package com.jbeer.framework.exception
* @author Bieber
* @date 2014-2-22 下午02:26:19
* @version V1.0   
*/

package com.jbeer.framework.exception;

/**
 * <p>类功能说明:没有找对字段的set方法进行处理</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: NotFoundFieldSetMetodException.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-2-22 下午02:26:19
 * @version V1.0
 */

public class NotFoundFieldSetGetMetodException extends RuntimeException {

	/**
	* @Fields serialVersionUID : TODO
	*/
	
	private static final long serialVersionUID = -4148749310691723333L;

	 

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param message
	 */

	public NotFoundFieldSetGetMetodException(String fieldName,String className) {
		super("属性"+fieldName+"在类"+className+"没有对应的set方法，请检查");
	}
 
	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param cause
	 */

	public NotFoundFieldSetGetMetodException(Throwable cause) {
		super(cause);
	}

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param message
	 * @param cause
	 */

	public NotFoundFieldSetGetMetodException(String fieldName,String className, Throwable cause) {
		super("属性"+fieldName+"在类"+className+"没有对应的set/get方法，请检查", cause);
	}

}
