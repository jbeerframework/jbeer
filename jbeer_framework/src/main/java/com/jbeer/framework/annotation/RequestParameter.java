/**   
* @Title: RequestParameter.java
* @Package com.jbeer.framework.annotation
* @author Bieber
* @date 2014年7月20日 下午5:27:21
* @version V1.0   
*/

package com.jbeer.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>类功能说明:获取请求参数注解</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: RequestParameter.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月20日 下午5:27:21
 * @version V1.0
 */

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParameter {

	public String value();
	
	public boolean required() default true;
	
	public String defaultValue() default "";
}
