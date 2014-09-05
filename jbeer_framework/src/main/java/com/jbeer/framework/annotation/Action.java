/**   
* @Title: RequestPath.java
* @Package com.jbeer.framework.annotation
* @author Bieber
* @date 2014-2-15 下午03:28:38
* @version V1.0   
*/

package com.jbeer.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jbeer.framework.enumeration.RequestType;

/**
 * 类功能说明
 * 类修改者	    修改日期
 * 修改说明
 * <p>Title: RequestPath.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-2-15 下午03:28:38
 * @Description: servlet请求路径以及请求方式配置注解
 * @version V1.0
 */
@Target({ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {
	
	public RequestType requestType() default RequestType.ANY;
	
	public String[] urlPatterns() default "/";
}
