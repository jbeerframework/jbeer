/**   
* @Title: JobHandler.java
* @Package com.jbeer.framework.timer
* @author Bieber
* @date 2014年7月27日 下午4:55:28
* @version V1.0   
*/

package com.jbeer.framework.timer;

import java.lang.reflect.Method;

/**
 * <p>类功能说明:TODO</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: JobHandler.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月27日 下午4:55:28
 * @version V1.0
 */

public class JobHandler {

	private Method jobMethod;
	
	private String timerInstanceId;

	/**
	 * @return jobMethod
	 */
	
	public Method getJobMethod() {
		return jobMethod;
	}

	/**
	 * @param jobMethod jobMethod
	 */
	
	public void setJobMethod(Method jobMethod) {
		this.jobMethod = jobMethod;
	}

	/**
	 * @return timerInstanceId
	 */
	
	public String getTimerInstanceId() {
		return timerInstanceId;
	}

	/**
	 * @param timerInstanceId timerInstanceId
	 */
	
	public void setTimerInstanceId(String timerInstanceId) {
		this.timerInstanceId = timerInstanceId;
	}
	
	
}
