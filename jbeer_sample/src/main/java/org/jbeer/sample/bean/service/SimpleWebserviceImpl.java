/**   
* @Title: SimpleWebserviceImpl.java
* @Package org.jbeer.sample.bean.service
* @author Bieber
* @date 2014年7月29日 上午8:34:08
* @version V1.0   
*/

package org.jbeer.sample.bean.service;

import com.jbeer.framework.annotation.Bean;

/**
 * <p>类功能说明:TODO</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: SimpleWebserviceImpl.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月29日 上午8:34:08
 * @version V1.0
 */
@Bean
public class SimpleWebserviceImpl implements SimpleWebservice {

	/* (non-Javadoc)
	 * @see org.jbeer.sample.bean.service.SimpleWebservice#sayHello(java.lang.String)
	 */
	@Override
	public String sayHello(String name) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "hello "+name;
	}

}
