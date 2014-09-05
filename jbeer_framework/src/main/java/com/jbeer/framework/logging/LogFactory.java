/**   
 * @Title: LogFactory.java
 * @Package com.jbeer.framework.logging
 * @author Bieber
 * @date 2014年7月12日 上午9:40:09
 * @version V1.0   
 */

package com.jbeer.framework.logging;

import java.lang.reflect.Constructor;

/**
 * <p>
 * 类功能说明:log的工程类
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: LogFactory.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月12日 上午9:40:09
 * @version V1.0
 */

public final class LogFactory {

	public static final String MARKER = "JBEER";
	private static Constructor<? extends Log> logConstructor;

	static {
		tryLoadLog(Log4jImpl.class);
		tryLoadLog(CommonsLogImpl.class);
		tryLoadLog(Slf4jImpl.class);
		tryLoadLog(StdoLogImpl.class);
	}

	private LogFactory(){
		
	}
	public static Log getLog(String name){
		try {
			return logConstructor.newInstance(name);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	public static Log getLog(Class<?> clazz){
		return getLog(clazz.getName());
	}
	
	private static void tryLoadLog(Class<? extends Log> clazz) {
		try {
			if(logConstructor!=null){
				return ;
			}
			Constructor<? extends Log> constructor = clazz
					.getConstructor(String.class);
			Log log = constructor.newInstance(LogFactory.class.getName());
			if(log.isDebugEnabled()){
				log.debug("initialized log used  "+clazz.getName()+" adapter");
			}
			logConstructor=constructor;
		} catch (Exception e) {
			// do nothing
		}
	}

}
