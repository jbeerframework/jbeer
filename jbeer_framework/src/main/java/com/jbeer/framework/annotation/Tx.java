/**   
* @Title: Tx.java
* @Package com.jbeer.framework.annotation
* @author Bieber
* @date 2014年5月27日 下午1:06:19
* @version V1.0   
*/

package com.jbeer.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jbeer.framework.db.tx.Isolation;
import com.jbeer.framework.db.tx.Propagation;

/**
* <p>类功能说明:申明事务标识</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: Tx.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年5月27日 下午1:06:19
* @version V1.0
*/
@Target({ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Tx {

    public Propagation propagation() default Propagation.PROPAGATION_REQUIRED;
    
    public boolean readonly() default false;
    
    public Class<? extends Throwable>[] rollbackFor() default {Throwable.class};
    
    public Isolation isolation() default Isolation.ISOLATION_READ_COMMITTED;
    
}
