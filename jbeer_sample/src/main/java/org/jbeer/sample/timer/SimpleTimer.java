/**   
* @Title: SimpleTimer.java
* @Package org.jbeer.sample.timer
* @author Bieber
* @date 2014年7月27日 下午5:59:54
* @version V1.0   
*/

package org.jbeer.sample.timer;

import com.jbeer.framework.timer.annotation.Job;

/**
 * <p>类功能说明:TODO</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: SimpleTimer.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月27日 下午5:59:54
 * @version V1.0
 */

public class SimpleTimer {

	@Job(repeatIntervalTime="2000")
	public String doJob(){
		return "hello world timer";
	}
}
