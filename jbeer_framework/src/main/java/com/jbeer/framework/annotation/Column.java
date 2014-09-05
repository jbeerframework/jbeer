/**   
* @Title: Column.java
* @Package com.jbeer.framework.annotation
* @author Bieber
* @date 2014年6月3日 下午2:55:57
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
* <p>类功能说明:TODO</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: Column.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月3日 下午2:55:57
* @version V1.0
*/
@Target({ElementType.FIELD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    public String name() default JBeerConstant.DEFAULT_COLUMN_NAME;
    
    public boolean isPrimarykey() default false;
    
    public boolean isAutoGenerate() default false;
}
