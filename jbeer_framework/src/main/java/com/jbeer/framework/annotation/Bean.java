/**   
* @Title: Bean.java
* @Package com.jbeer.framework.annotation
* @author Bieber
* @date 2014-5-11 下午2:58:28
* @version V1.0   
*/

package com.jbeer.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jbeer.framework.constant.JBeerConstant;
import com.jbeer.framework.enumeration.BeanScope;

/**
 * <p>类功能说明:TODO</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: Bean.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-11 下午2:58:28
 * @version V1.0
 */
@Target({ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {

	public String value() default JBeerConstant.DEFAULT_BEANNAME;
	
	public BeanScope scope() default BeanScope.SINGLETON;
	
	public String factoryMethodName() default JBeerConstant.IOC_DEFALUT_FACTORY_METHOD_NAME;
	
	public String destroyMethod() default JBeerConstant.DEFALUT_DESTROY_METHOD;
	
}
