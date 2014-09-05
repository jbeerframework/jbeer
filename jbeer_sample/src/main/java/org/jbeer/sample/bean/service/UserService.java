/**   
* @Title: UserService.java
* @Package org.jbeer.sample.bean.service
* @author Bieber
* @date 2014-5-17 下午9:38:14
* @version V1.0   
*/

package org.jbeer.sample.bean.service;

import java.util.Date;

import org.jbeer.sample.bean.User;

import com.jbeer.framework.annotation.Bean;
import com.jbeer.framework.annotation.RefBean;
import com.jbeer.framework.annotation.Tx;
import com.jbeer.framework.exception.DBException;
import com.jbeer.framework.ws.client.WSClientFactory;

/**
 * <p>类功能说明:TODO</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: UserService.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-17 下午9:38:14
 * @version V1.0
 */
@Bean
public class UserService {

	@RefBean
	private CodeService codeService;
/*	@RefBean(factoryBeanClass=WSClientFactory.class)
	private UserMapper userMapper;*/
	@Tx
	public String getName() throws DBException{
	/*	try{
		int id = codeService.hello();
		}catch(Exception e){
			e.printStackTrace();
		}
		DB.update(User.class).set("userName", "test").where("id", OperationType.EQUAL, 17375).execute();*/
		User user = new User();
		user.setCreateTime(new Date());
		user.setUserAge(12);
		user.setUserName("bieber");
		//userMapper.insert(user);
		///throw new DBException();
		return user.getId().toString();
	}
}
