/**   
* @Title: Slf4jLoggerImpl.java
* @Package com.jbeer.framework.logging
* @author Bieber
* @date 2014年7月12日 上午10:11:29
* @version V1.0   
*/

package com.jbeer.framework.logging;

import org.slf4j.Logger;

/**
 * <p>类功能说明:TODO</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: Slf4jLoggerImpl.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月12日 上午10:11:29
 * @version V1.0
 */

public class Slf4jLoggerImpl extends AbstractLog {

	private Logger logger;
	public Slf4jLoggerImpl(Logger logger){
		this.logger = logger;
	}
	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#isDebugEnabled()
	 */
	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#isTraceEnabled()
	 */
	@Override
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#error(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void error(String s, Throwable e) {
		logger.error(s, e);
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#error(java.lang.String)
	 */
	@Override
	public void error(String s) {
		logger.error(s);
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#debug(java.lang.String)
	 */
	@Override
	public void debug(String s) {
		logger.debug(s);
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#debug(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void debug(String s, Throwable e) {
		logger.debug(s, e);
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#trace(java.lang.String)
	 */
	@Override
	public void trace(String s) {
		logger.trace(s);
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#trace(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void trace(String s, Throwable e) {
		logger.trace(s, e);
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#warn(java.lang.String)
	 */
	@Override
	public void warn(String s) {
		logger.warn(s);
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#warn(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void warn(String s, Throwable e) {
		logger.warn(s, e);
	}
	 
}
