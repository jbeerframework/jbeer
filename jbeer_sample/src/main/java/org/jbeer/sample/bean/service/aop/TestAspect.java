/**   
* @Title: TestAspect.java
* @Package org.jbeer.sample.bean.service.aop
* @author Bieber
* @date 2014-5-18 下午9:03:08
* @version V1.0   
*/

package org.jbeer.sample.bean.service.aop;

import com.jbeer.framework.annotation.Aspect;
import com.jbeer.framework.aop.MethodInterceptor;
import com.jbeer.framework.bean.proxy.InvokeHandler;

/**
 * <p>类功能说明:TODO</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: TestAspect.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-18 下午9:03:08
 * @version V1.0
 */
@Aspect(classRegex="org.jbeer.sample.bean.service.*Impl")
public class TestAspect implements MethodInterceptor{

	public void before(InvokeHandler handler){
		System.out.println(this.getClass().getName()+handler.getClass().getName()+"."+handler.getInvokeMethod().getName()+"before");
	}
	
	public void after(InvokeHandler handler,Object ret){
		System.out.println(this.getClass().getName()+handler.getClass().getName()+"."+handler.getInvokeMethod().getName()+",return:"+ret+"after");
	}
	
 

	/* (non-Javadoc)
	 * @see com.jbeer.framework.aop.MethodInterceptor#exception(com.jbeer.framework.bean.proxy.InvokeHandler, java.lang.Throwable)
	 */
	@Override
	public void exception(InvokeHandler handler, Throwable e) throws Throwable {
		System.out.println(this.getClass().getName()+handler.getClass().getName()+"."+handler.getInvokeMethod().getName()+",exception:"+e.getMessage());
	}
}
