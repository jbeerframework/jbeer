/**   
* @Title: StdoLogImpl.java
* @Package com.jbeer.framework.logging
* @author Bieber
* @date 2014年7月12日 上午9:52:49
* @version V1.0   
*/

package com.jbeer.framework.logging;

/**
 * <p>类功能说明:使用标准输出的log</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: StdoLogImpl.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月12日 上午9:52:49
 * @version V1.0
 */

public class StdoLogImpl extends AbstractLog{

	private String logName;
	
	private static enum LogType{
		WARN,DEBUG,TRACE,ERROR
	}
	
	public StdoLogImpl(String name){
		logName = name;
	}
	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#isDebugEnabled()
	 */
	@Override
	public boolean isDebugEnabled() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#isTraceEnabled()
	 */
	@Override
	public boolean isTraceEnabled() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#error(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void error(String s, Throwable e) {
		log(LogType.ERROR, s, e);
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#error(java.lang.String)
	 */
	@Override
	public void error(String s) {
		log(LogType.ERROR,s,null);
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#debug(java.lang.String)
	 */
	@Override
	public void debug(String s) {
		log(LogType.DEBUG,s,null);
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#trace(java.lang.String)
	 */
	@Override
	public void trace(String s) {
		log(LogType.TRACE,s,null);
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#warn(java.lang.String)
	 */
	@Override
	public void warn(String s) {
		log(LogType.WARN,s,null);
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#debug(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void debug(String s, Throwable e) {
		log(LogType.DEBUG,s,e);
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#trace(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void trace(String s, Throwable e) {
		log(LogType.TRACE,s,e);
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.logging.Log#warn(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void warn(String s, Throwable e) {
		log(LogType.WARN,s,e);
	}

	private void log(LogType type,String msg,Throwable e){
		System.out.printf("[%s]", logName);
		System.out.printf("[%s]",type.toString());
		System.out.printf("[%s]",msg);
		if(e!=null)
		System.out.printf("%s",e.getMessage());
		System.out.println();
	}
}
