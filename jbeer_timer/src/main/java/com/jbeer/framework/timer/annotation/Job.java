/**   
* @Title: Job.java
* @Package com.jbeer.framework.timer.annotation
* @author Bieber
* @date 2014年7月27日 下午4:01:49
* @version V1.0   
*/

package com.jbeer.framework.timer.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>类功能说明:任务注解标识</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: Job.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月27日 下午4:01:49
 * @version V1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Job {
	public String cronExpression() default "";
	
	public String groupId() default "JBEER_JOB_GROUP";
	
	public String jobName() default "";
	
	public String startDelay() default "0";
	
	public String repeatIntervalTime() default "1000";
	
	public String repeatTimes() default "-1";
	
}
