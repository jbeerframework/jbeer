/**   
* @Title: Aspect.java
* @Package com.jbeer.framework.annotation
* @author Bieber
* @date 2014-5-18 下午8:10:54
* @version V1.0   
*/

package com.jbeer.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jbeer.framework.constant.JBeerConstant;

/**
 * <p>类功能说明:AOP注解</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: Aspect.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-18 下午8:10:54
 * @version V1.0
 */
@Target({ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface  Aspect{
	public String[] classRegex() default {JBeerConstant.DEFAULT_CLASS_REGEX};
	
	public String[] methodRegex() default {JBeerConstant.DEFAULT_METHOD_REGEX};
	
	public Class<?>[] argTypes() default {};
	
	public Class<? extends Throwable>[] handleException() default {Throwable.class};
 	
	
}
