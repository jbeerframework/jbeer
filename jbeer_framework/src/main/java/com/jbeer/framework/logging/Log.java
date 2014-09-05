/**   
 * @Title: Logger.java
 * @Package com.jbeer.framework.logging
 * @author Bieber
 * @date 2014年7月12日 上午9:40:17
 * @version V1.0   
 */

package com.jbeer.framework.logging;

/**
 * <p>
 * 类功能说明:日志的抽象接口
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: Logger.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月12日 上午9:40:17
 * @version V1.0
 */

public interface Log {

	boolean isDebugEnabled();

	boolean isTraceEnabled();

	void error(String s, Throwable e);

	void error(String s);
	
	void error(Object o);
	
	void error(Throwable e);
	
	void debug(String s);
	
	void debug(Throwable e);
	
	void debug(Object o);
	
	void debug(String s, Throwable e);

	void trace(String s);
	
	void trace(Object o);
	
	void trace(Throwable e);
	
	void trace(String s, Throwable e);
	
	void warn(String s);
	
	void warn(Throwable e);
	
	void warn(Object o);
	
	void warn(String s, Throwable e);
}
