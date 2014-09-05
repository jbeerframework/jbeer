/**   
* @Title: Job.java
* @Package com.jbeer.framework.timer.annotation
* @author Bieber
* @date 2014年7月27日 下午4:00:16
* @version V1.0   
*/

package com.jbeer.framework.timer.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>类功能说明:定时器任务注解标识</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: Job.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月27日 下午4:00:16
 * @version V1.0
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Timer {
}
