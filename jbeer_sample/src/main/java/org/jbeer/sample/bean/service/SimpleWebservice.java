/**   
* @Title: SimpleWebservice.java
* @Package org.jbeer.sample.bean.service
* @author Bieber
* @date 2014年7月29日 上午6:57:31
* @version V1.0   
*/

package org.jbeer.sample.bean.service;

import javax.jws.WebService;

/**
 * <p>类功能说明:TODO</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: SimpleWebservice.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月29日 上午6:57:31
 * @version V1.0
 */
@WebService
public interface SimpleWebservice {

	public String sayHello(String name);
}
