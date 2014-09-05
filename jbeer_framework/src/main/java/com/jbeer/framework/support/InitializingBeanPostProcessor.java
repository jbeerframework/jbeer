/**   
* @Title: InitializingBeanPostProcesser.java
* @Package com.jbeer.framework.support
* @author Bieber
* @date 2014年5月31日 下午7:32:46
* @version V1.0   
*/

package com.jbeer.framework.support;

import com.jbeer.framework.ioc.BeanFactory;

/**
 * <p>类功能说明:监控所有bean实例化接口</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: InitializingBeanPostProcesser.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年5月31日 下午7:32:46
 * @version V1.0
 */

public interface InitializingBeanPostProcessor {

	
	/**
	 * 
	* <p>函数功能说明:注入{@link BeanFactory}</p>
	* <p>Bieber  2014年5月31日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	public void setBeanFactory(BeanFactory beanFactory);
	
	/**
	 * 
	* <p>函数功能说明:将每个Bean初始化，传入该接口实现类，进行二次加工</p>
	* <p>Bieber  2014年5月31日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	public void afterPropertiesSet(String beanName,Object bean) throws Exception;
}
