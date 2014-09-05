/**   
* @Title: Properties.java
* @Package com.jbeer.framework.annotation
* @author Bieber
* @date 2014年6月3日 上午9:13:49
* @version V1.0   
*/

package com.jbeer.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* <p>类功能说明:获取配置信息注解</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: Properties.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月3日 上午9:13:49
* @version V1.0
*/
@Target({ElementType.PARAMETER,ElementType.FIELD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Properties {

    public String name();
}
