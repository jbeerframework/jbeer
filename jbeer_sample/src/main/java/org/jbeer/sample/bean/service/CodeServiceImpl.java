/**   
* @Title: CodeServiceImpl.java
* @Package org.jbeer.sample.bean.service
* @author Bieber
* @date 2014-5-17 下午10:32:01
* @version V1.0   
*/

package org.jbeer.sample.bean.service;

import java.util.Date;

import org.jbeer.sample.bean.User;

import com.jbeer.framework.annotation.Bean;
import com.jbeer.framework.annotation.Tx;
import com.jbeer.framework.db.sqlrunner.DB;
import com.jbeer.framework.db.tx.Propagation;
import com.jbeer.framework.enumeration.BeanScope;
import com.jbeer.framework.exception.DBException;

/**
 * <p>类功能说明:TODO</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: CodeServiceImpl.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-17 下午10:32:01
 * @version V1.0
 */
@Bean(scope=BeanScope.SESSION,factoryMethodName="ini")
public class CodeServiceImpl implements CodeService {

	
	public static CodeService ini(){
		return new CodeServiceImpl();
	}
	/* (non-Javadoc)
	 * @see org.jbeer.sample.bean.service.CodeService#hello()
	 */
	@Override
	@Tx(propagation=Propagation.PROPAGATION_NESTED)
	public Integer hello() throws DBException {
		User user = new User();
		user.setCreateTime(new Date());
		user.setUserAge(12);
		user.setUserName("bieber");
		DB.insert(user).execute();
		throw new DBException("111");
	}
	 

}
