/**   
* @Title: AbstractLog.java
* @Package com.jbeer.framework.logging
* @author Bieber
* @date 2014年7月12日 上午11:15:45
* @version V1.0   
*/

package com.jbeer.framework.logging;

/**
 * <p>类功能说明:TODO</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: AbstractLog.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月12日 上午11:15:45
 * @version V1.0
 */

public abstract class AbstractLog implements Log {

	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#error(java.lang.Object)
	 */
	@Override
	public void error(Object o) {
		error(o.toString());
	}
	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#error(java.lang.Throwable)
	 */
	@Override
	public void error(Throwable e) {
		error(e.getMessage());
	}
	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#debug(java.lang.Throwable)
	 */
	@Override
	public void debug(Throwable e) {
		debug(e.getMessage());
	}
	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#debug(java.lang.Object)
	 */
	@Override
	public void debug(Object o) {
		debug(o.toString());
	}
	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#trace(java.lang.Object)
	 */
	@Override
	public void trace(Object o) {
		trace(o.toString());
	}
	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#trace(java.lang.Throwable)
	 */
	@Override
	public void trace(Throwable e) {
		trace(e.getMessage());
	}
	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#warn(java.lang.Throwable)
	 */
	@Override
	public void warn(Throwable e) {
		warn(e.getMessage());
	}
	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#warn(java.lang.Object)
	 */
	@Override
	public void warn(Object o) {
		warn(o.toString());
	}

}
