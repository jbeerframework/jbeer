/**   
* @Title: Log4jImpl.java
* @Package com.jbeer.framework.logging
* @author Bieber
* @date 2014年7月12日 上午9:44:56
* @version V1.0   
*/

package com.jbeer.framework.logging;

import org.apache.log4j.Logger;

/**
 * <p>类功能说明:基于log4j的日志接口</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: Log4jImpl.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月12日 上午9:44:56
 * @version V1.0
 */

public class Log4jImpl extends AbstractLog{

	private Logger log;
	public Log4jImpl(String name){
		log = Logger.getLogger(name);
	}
	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#isDebugEnabled()
	 */
	@Override
	public boolean isDebugEnabled() {
		return log.isDebugEnabled();
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#isTraceEnabled()
	 */
	@Override
	public boolean isTraceEnabled() {
		return log.isTraceEnabled();
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#error(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void error(String s, Throwable e) {
		log.error(s, e);
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#error(java.lang.String)
	 */
	@Override
	public void error(String s) {
		log.error(s);
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#debug(java.lang.String)
	 */
	@Override
	public void debug(String s) {
		log.debug(s);
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#trace(java.lang.String)
	 */
	@Override
	public void trace(String s) {
		log.trace(s);
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#warn(java.lang.String)
	 */
	@Override
	public void warn(String s) {
		log.warn(s);
	}
	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#debug(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void debug(String s, Throwable e) {
		log.debug(s, e);
	}
	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#trace(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void trace(String s, Throwable e) {
		log.trace(s, e);
	}
	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#warn(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void warn(String s, Throwable e) {
		log.warn(s,e);
	}

	 
}
