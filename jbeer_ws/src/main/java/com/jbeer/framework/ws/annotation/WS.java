/**   
* @Title: WS.java
* @Package com.jbeer.framework.ws.annotation
* @author Bieber
* @date 2014年7月29日 上午6:24:50
* @version V1.0   
*/

package com.jbeer.framework.ws.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jbeer.framework.constant.JBeerConstant;

/**
 * <p>类功能说明:TODO</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: WS.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月29日 上午6:24:50
 * @version V1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WS {
	/**
	 * 
	* <p>函数功能说明:关联的bean,默认时接口的第一个实现子类</p>
	* <p>Bieber  2014年7月29日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return String
	 */
	public String refBean() default JBeerConstant.DEFAULT_BEANNAME;
	
}
