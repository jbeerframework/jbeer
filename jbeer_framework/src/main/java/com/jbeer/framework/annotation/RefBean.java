/**   
* @Title: RefBean.java
* @Package com.jbeer.framework.annotation
* @author Bieber
* @date 2014-5-11 下午3:07:26
* @version V1.0   
*/

package com.jbeer.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jbeer.framework.constant.JBeerConstant;
import com.jbeer.framework.ioc.FactoryBean;

/**
 * <p>类功能说明:TODO</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: RefBean.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-11 下午3:07:26
 * @version V1.0
 */
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface RefBean {

	public String value() default JBeerConstant.DEFAULT_BEANNAME;
	
	public Class<? extends FactoryBean> factoryBeanClass() default FactoryBean.class;
}
