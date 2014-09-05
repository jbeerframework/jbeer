/**   
* @Title: ConnectionValidator.java
* @Package com.jbeer.framework.db
* @author Bieber
* @date 2014-5-24 下午10:16:03
* @version V1.0   
*/

package com.jbeer.framework.db.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import com.jbeer.framework.pool.Pool.Validator;

/**
 * <p>类功能说明:TODO</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: ConnectionValidator.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-24 下午10:16:03
 * @see Validator
 * @version V1.0
 */

public class ConnectionValidator implements Validator<Connection> {

	/* (non-Javadoc)
	 * @see com.jbeer.framework.pool.Pool.Validator#isValid(java.lang.Object)
	 */
	@Override
	public boolean isValid(Connection t) {
		try {
			return t.isClosed()?false:true;
		} catch (SQLException e) {
			 throw new IllegalStateException("check connection close stutus",e);
		}
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.pool.Pool.Validator#invalidate(java.lang.Object)
	 */
	@Override
	public void invalidate(Connection t) {
		try {
			t.close();
		} catch (SQLException e) {
			throw new IllegalStateException("close connection fail",e);
		}
	}

}
