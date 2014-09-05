/**   
 * @Title: Slf4jLocationAwareLoggerImpl.java
 * @Package com.jbeer.framework.logging
 * @author Bieber
 * @date 2014年7月12日 上午10:14:39
 * @version V1.0   
 */

package com.jbeer.framework.logging;

 
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.spi.LocationAwareLogger;

/**
 * <p>
 * 类功能说明:TODO
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: Slf4jLocationAwareLoggerImpl.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月12日 上午10:14:39
 * @version V1.0
 */

public class Slf4jLocationAwareLoggerImpl extends AbstractLog {

	private static Marker MARKER = MarkerFactory.getMarker(LogFactory.MARKER);

	private static final String FQCN = Slf4jImpl.class.getName();

	private LocationAwareLogger logger;

	Slf4jLocationAwareLoggerImpl(LocationAwareLogger logger) {
		this.logger = logger;
	}

	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	public void error(String s, Throwable e) {
		logger.log(MARKER, FQCN, LocationAwareLogger.ERROR_INT, s, null, e);
	}

	public void error(String s) {
		logger.log(MARKER, FQCN, LocationAwareLogger.ERROR_INT, s, null, null);
	}

	public void debug(String s) {
		logger.log(MARKER, FQCN, LocationAwareLogger.DEBUG_INT, s, null, null);
	}

	public void trace(String s) {
		logger.log(MARKER, FQCN, LocationAwareLogger.TRACE_INT, s, null, null);
	}

	public void warn(String s) {
		logger.log(MARKER, FQCN, LocationAwareLogger.WARN_INT, s, null, null);
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#debug(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void debug(String s, Throwable e) {
		logger.log(MARKER, FQCN, LocationAwareLogger.DEBUG_INT, s, null, e);
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#trace(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void trace(String s, Throwable e) {
		logger.log(MARKER, FQCN, LocationAwareLogger.TRACE_INT, s, null, e);
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#warn(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void warn(String s, Throwable e) {
		logger.log(MARKER, FQCN, LocationAwareLogger.WARN_INT, s, null, e);
		
	}
	 
}
