/**   
* @Title: MethodInterceptor.java
* @Package com.jbeer.framework.aop
* @author Bieber
* @date 2014年7月7日 下午3:25:12
* @version V1.0   
*/

package com.jbeer.framework.aop;

import com.jbeer.framework.bean.proxy.InvokeHandler;

/**
 * <p>类功能说明:AOP拦截器接口</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: MethodInterceptor.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月7日 下午3:25:12
 * @version V1.0
 */

public interface MethodInterceptor {
	 /**
     * 
    * <p>函数功能说明:在被拦截的方法前执行</p>
    * <p>Bieber  2014-5-18</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     */
    public  void before(InvokeHandler handler) throws Throwable;

    /**
     * 
    * <p>函数功能说明:被拦截方法执行后执行</p>
    * <p>Bieber  2014-5-18</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     */
    public  void after(InvokeHandler heandler, Object ret) throws Throwable;

    /**
     * 
    * <p>函数功能说明:出现异常之后执行</p>
    * <p>Bieber  2014-5-18</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     */
    public  void exception(InvokeHandler handler, Throwable e) throws Throwable;
}
