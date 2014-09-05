/**   
* @Title: Controller.java
* @Package com.jbeer.framework.annotation
* @author Bieber
* @date 2014-2-15 下午03:27:18
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
 * 类功能说明
 * 类修改者	    修改日期
 * 修改说明
 * <p>Title: Controller.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-2-15 下午03:27:18
 * @Description: controller标注注解
 * @version V1.0
 */
@Target({ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
	
	public String urlPattern() default JBeerConstant.DEFAULT_URLPATTERN;

}
