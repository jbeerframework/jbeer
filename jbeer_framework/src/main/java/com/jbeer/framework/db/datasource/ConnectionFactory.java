/**   
 * @Title: ConnectionFactory.java
 * @Package com.jbeer.framework.db
 * @author Bieber
 * @date 2014-5-24 下午10:03:12
 * @version V1.0   
 */

package com.jbeer.framework.db.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.jbeer.framework.pool.ObjectFactory;
import com.jbeer.framework.utils.ProxyUtils;

/**
 * <p>
 * 类功能说明:数据库连接工厂
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: ConnectionFactory.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-24 下午10:03:12
 * @see ObjectFactory
 * @version V1.0
 */

public class ConnectionFactory implements ObjectFactory<Connection> {

	private String jdbcUrl;

	private String userName;

	private String password;

	private String driveName;
	
	 private AbstractDataSource datasource;
	/**
	 * <p>
	 * Title: 工厂构造函数
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param jdbcUrl
	 * @param userName
	 * @param password
	 * @param driveName
	 */

	public ConnectionFactory(String jdbcUrl, String userName, String password,
			String driveName,AbstractDataSource datasource) {
		super();
		this.jdbcUrl = jdbcUrl;
		this.userName = userName;
		this.password = password;
		this.driveName = driveName;
		this.datasource =datasource;
	}

	
 

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.jbeer.framework.pool.ObjectFactory#createObject()
	 */
	@Override
	public Connection createObject() {
		Connection connection = null;;
		try {
			Class.forName(driveName);
			connection = DriverManager.getConnection(jdbcUrl, userName, password);
			//对这个类进行代理，从而可以进行截取close方法，将释放该connection到池中
			connection=ProxyUtils.createBeanProxy(new ConnectionProxyProcessor(datasource), Connection.class, connection);
		} catch (ClassNotFoundException e) {
			throw new IllegalAccessError(driveName + " is not found");
		} catch (SQLException e) {
			throw new IllegalAccessError("could not connection database url:"
					+ jdbcUrl + ",userName：" + userName + ",password:"
					+ password);
		}
		return connection;
	}

}
