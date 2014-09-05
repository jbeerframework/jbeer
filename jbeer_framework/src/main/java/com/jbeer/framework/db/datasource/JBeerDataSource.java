/**   
 * @Title: JBeerDatasource.java
 * @Package com.jbeer.framework.db
 * @author Bieber
 * @date 2014-5-24 下午10:00:19
 * @version V1.0   
 */

package com.jbeer.framework.db.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.jbeer.framework.properties.PropertiesContext;
import com.jbeer.framework.utils.ProxyUtils;
import com.jbeer.framework.utils.Utils;

/**
 * <p>
 * 类功能说明:JBeer框架默认数据源
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: JBeerDatasource.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-24 下午10:00:19
 * @version V1.0
 */

public class JBeerDataSource extends AbstractDataSource {

	private Lock lock = new ReentrantLock();

	public JBeerDataSource(int initSize, int maxSize, int acquireIncrement,
			int timeout, String userName, String password, String jdbcUrl,
			String driveName, DataSourceType type) {
		super(initSize, maxSize, acquireIncrement, timeout, userName, password,
				jdbcUrl, driveName, type);
	}

	public JBeerDataSource(int initSize, int maxSize, int acquireIncrement,
			int timeout, String userName, String password, String jdbcUrl,
			String driveName) {
		super(initSize, maxSize, acquireIncrement, timeout, userName, password,
				jdbcUrl, driveName);
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param initSize
	* @param maxSize
	* @param acquireIncrement
	* @param timeout
	* @param userName
	* @param password
	* @param jdbcUrl
	* @param driveName
	*/
	
	public JBeerDataSource(String initSize, String maxSize,
			String acquireIncrement, String timeout, String userName,
			String password, String jdbcUrl, String driveName) {
		super(initSize, maxSize, acquireIncrement, timeout, userName, password,
				jdbcUrl, driveName);
	}

	public JBeerDataSource(String userName, String password, String jdbcUrl, String driveName,
                           DataSourceType type) {
        super(userName, password, jdbcUrl, driveName, type);
    }

    public JBeerDataSource(String userName, String password, String jdbcUrl, String driveName) {
        super(userName, password, jdbcUrl, driveName);
    }
    
    /**
	 * 获取数据库连接，连接池中数量不够时，则需要根据<code>acquireIncrement</code>进行初始化一定数量的连接
	 */
	@Override
	public Connection getConnection() throws SQLException {
		
			if(!initialized){
				try{
				lock.lock();
				if(!initialized){
				this.init();
				}
				}finally{
					lock.unlock();
				}
			}
		
		Connection connection = null;
		int current = reminderConnect.get();
		int total = totalConnect.get();
		if (current <= 0 && total < maxSize) {
			try {
				lock.lock();
				total = totalConnect.get();
				if (current <= 0 && total <maxSize) {
					int addSize = acquireIncrement;
					if ((maxSize - total) < acquireIncrement) {
						addSize = maxSize - total;
					}
					for (int i = 0; i < addSize; i++) {
						totalConnect.getAndIncrement();
						connection = getConnection(userName, password);
						releaseConnection(connection);
					}
				}
			} finally {
				lock.unlock();
			}

		}
		connection = connectionPool.get();
		reminderConnect.decrementAndGet();
		return connection;
	}

	public void close() {
		connectionPool.shutdown();
	}

	public void releaseConnection(Connection connection) throws SQLException {
		connectionPool.release(connection);
		reminderConnect.getAndIncrement();
	}

	/*
	 * (non-Javadoc) 该方法返回的connection不是从连接池中获取，是及时创建
	 */
	@Override
	public Connection getConnection(String username, String password)
			throws SQLException {
		try {
			String jdbcUrlProperty = PropertiesContext.getProperties(Utils
					.checkIsPlaceholder(this.jdbcUrl));
			String userNameProperty = PropertiesContext.getProperties(Utils
					.checkIsPlaceholder(this.userName));
			String passwordProperty = PropertiesContext.getProperties(Utils
					.checkIsPlaceholder(this.password));
			String driveNameProperty = PropertiesContext.getProperties(Utils
					.checkIsPlaceholder(this.driveName));
			Class.forName(driveNameProperty==null?driveName:driveNameProperty);
			Connection connection = DriverManager.getConnection(jdbcUrlProperty==null?jdbcUrl:jdbcUrlProperty,
					userNameProperty==null?username:userNameProperty, passwordProperty==null?password:passwordProperty);
			// 对这个类进行代理，从而可以进行截取close方法，将释放该connection到池中
			connection = ProxyUtils.createBeanProxy(new ConnectionProxyProcessor(
					this), Connection.class, connection);
			return connection;
		} catch (ClassNotFoundException e) {
			throw new IllegalAccessError("can not find class " + driveName);
		}
	}
}
