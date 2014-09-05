/**   

* @Title: InitializingBean.java
* @Package com.jbeer.framework.support
* @author Bieber
* @date 2014年5月31日 下午7:19:21
* @version V1.0   
*/

package com.jbeer.framework.support;

/**
 * <p>类功能说明:初始化Bean的接口</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: InitializingBean.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年5月31日 下午7:19:21
 * @version V1.0
 */

public interface InitializingBean {

	/**
	 * 
	* <p>函数功能说明:设置完Bean参数 后，提供第三方切入</p>
	* <p>Bieber  2014年5月31日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	public void afterPropertiesSet(String beanName,Object bean) throws Exception;
}
