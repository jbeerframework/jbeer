/**   
* @Title: BeanPorcessor.java
* @Package org.jbeer.sample.bean.service
* @author Bieber
* @date 2014年5月31日 下午8:52:36
* @version V1.0   
*/

package org.jbeer.sample.bean.service;

import com.jbeer.framework.ioc.BeanFactory;
import com.jbeer.framework.support.InitializingBeanPostProcessor;

/**
 * <p>类功能说明:TODO</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: BeanPorcessor.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年5月31日 下午8:52:36
 * @version V1.0
 */

public class BeanPorcessor implements InitializingBeanPostProcessor {
 

	/* (non-Javadoc)
	 * @see com.jbeer.framework.support.InitializingBeanPostProcessor#setBeanFactory(com.jbeer.framework.ioc.BeanFactory)
	 */
	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		System.out.println(this.getClass().getName()+"->"+beanFactory);

	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.support.InitializingBeanPostProcessor#afterPropertiesSet(java.lang.String, java.lang.Object)
	 */
	@Override
	public void afterPropertiesSet(String beanName, Object bean)
			throws Exception {
		 System.out.println(this.getClass().getName()+"->"+beanName);

	}
 

}
