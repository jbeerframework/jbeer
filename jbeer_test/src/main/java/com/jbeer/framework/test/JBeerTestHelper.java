/**   
* @Title: JBeerTestHelper.java
* @Package com.jbeer.framework.test
* @author Bieber
* @date 2014-5-24 下午9:23:12
* @version V1.0   
*/
package com.jbeer.framework.test;

import org.junit.runner.RunWith;

import com.jbeer.framework.config.Configurate;

/**
 * <p>类功能说明:TODO</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: JBeerTestHelper.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-24 下午9:23:12
 * @version V1.0
 */
@RunWith(JBeer4JUintTestRunner.class)
public abstract class JBeerTestHelper {

    public abstract String applicationBasePackage();
    
    public abstract Configurate configurate();
    
}
