/**   
 * @Title: AbstractJBeerDataSource.java
 * @Package com.jbeer.framework.db
 * @author Bieber
 * @date 2014年5月26日 下午1:06:42
 * @version V1.0   
 */

package com.jbeer.framework.db.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.jbeer.framework.pool.BoundBlockingPool;
import com.jbeer.framework.pool.BoundPool;
import com.jbeer.framework.pool.ObjectFactory;
import com.jbeer.framework.pool.Pool;
import com.jbeer.framework.properties.PropertiesContext;
import com.jbeer.framework.utils.Utils;

/**
 * <p>
 * 类功能说明:TODO
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: AbstractJBeerDataSource.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年5月26日 下午1:06:42
 * @see DataSource
 * @version V1.0
 */

public abstract class AbstractDataSource implements DataSource {
	protected Pool<Connection> connectionPool;

	protected int initSize = 20;

	protected String initSizePlaceholder;

	protected int maxSize = 40;

	protected String maxSizePlaceholder;

	protected int acquireIncrement = 10;

	protected String acquireIncrementPlaceholder;

	protected PrintWriter out;

	protected int timeout = 3;

	protected String timeoutPlaceholder;

	protected String userName;

	protected String password;

	protected String jdbcUrl;

	protected String driveName;

	protected ObjectFactory<Connection> connectionFactory;

	protected AtomicInteger reminderConnect;

	protected AtomicInteger totalConnect;

	protected DataSourceType type;

	protected volatile boolean initialized = false;

	/**
	 * <p>
	 * Title: 数据源构造函数
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param initSize
	 * @param maxSize
	 * @param timeout
	 * @param userName
	 * @param password
	 * @param jdbcUrl
	 * @param driveName
	 */

	public AbstractDataSource(int initSize, int maxSize, int acquireIncrement,
			int timeout, String userName, String password, String jdbcUrl,
			String driveName, DataSourceType type) {
		super();
		this.initSize = initSize;
		this.maxSize = maxSize;
		this.timeout = timeout;
		this.userName = userName;
		this.password = password;
		this.jdbcUrl = jdbcUrl;
		this.driveName = driveName;
		this.acquireIncrement = acquireIncrement;
		reminderConnect = new AtomicInteger(initSize);
		totalConnect = new AtomicInteger(initSize);
		this.type = type;
		init();
	}

	public AbstractDataSource(String userName, String password, String jdbcUrl,
			String driveName, DataSourceType type) {
		super();
		this.userName = userName;
		this.password = password;
		this.jdbcUrl = jdbcUrl;
		this.driveName = driveName;
		reminderConnect = new AtomicInteger(initSize);
		totalConnect = new AtomicInteger(initSize);
		init();
	}

	protected void init() {
		if (initialized) {
			return;
		}
		String jdbcUrlProperty = PropertiesContext.getProperties(Utils
				.checkIsPlaceholder(this.jdbcUrl));
		String userNameProperty = PropertiesContext.getProperties(Utils
				.checkIsPlaceholder(this.userName));
		String passwordProperty = PropertiesContext.getProperties(Utils
				.checkIsPlaceholder(this.password));
		String driveNameProperty = PropertiesContext.getProperties(Utils
				.checkIsPlaceholder(this.driveName));
		connectionFactory = new ConnectionFactory(
				jdbcUrlProperty == null ? this.jdbcUrl : jdbcUrlProperty,
				userNameProperty == null ? this.userName : userNameProperty,
				passwordProperty == null ? this.password : passwordProperty,
				driveNameProperty == null ? this.driveName : driveNameProperty,
				this);
		String maxSizeProperty =  PropertiesContext.getProperties(Utils
				.checkIsPlaceholder(this.maxSizePlaceholder));
		String initSizeProperty = PropertiesContext.getProperties(Utils
				.checkIsPlaceholder(this.initSizePlaceholder));
		String timeoutProperty = PropertiesContext.getProperties(Utils
				.checkIsPlaceholder(this.timeoutPlaceholder));
		if(reminderConnect==null){
		reminderConnect = new AtomicInteger(initSizeProperty==null?this.initSize:Integer.parseInt(initSizeProperty.trim()));
		}
		if(totalConnect==null){
		totalConnect = new AtomicInteger(initSizeProperty==null?this.initSize:Integer.parseInt(initSizeProperty.trim()));
		}
		if (type == DataSourceType.BLOCKING) {
			connectionPool = new BoundBlockingPool<Connection>(maxSizeProperty==null?this.maxSize:Integer.parseInt(maxSizeProperty.trim()),
					initSizeProperty==null?this.initSize:Integer.parseInt(initSizeProperty.trim()), new ConnectionValidator(),
					connectionFactory, timeoutProperty==null?this.timeout:Integer.parseInt(timeoutProperty));
		} else {
			connectionPool = new BoundPool<Connection>(maxSizeProperty==null?this.maxSize:Integer.parseInt(maxSizeProperty),
					initSizeProperty==null?this.initSize:Integer.parseInt(initSizeProperty), new ConnectionValidator(), connectionFactory);
		}
		initialized = true;
	}

	public AbstractDataSource(String userName, String password, String jdbcUrl,
			String driveName) {
		super();
		this.userName = userName;
		this.password = password;
		this.jdbcUrl = jdbcUrl;
		this.driveName = driveName;
		reminderConnect = new AtomicInteger(initSize);
		totalConnect = new AtomicInteger(initSize);
		this.type = DataSourceType.BLOCKING;
		init();
	}

	/**
	 * <p>
	 * Title: 数据源构造函数
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param initSize
	 * @param maxSize
	 * @param timeout
	 * @param userName
	 * @param password
	 * @param jdbcUrl
	 * @param driveName
	 */
	public AbstractDataSource(int initSize, int maxSize, int acquireIncrement,
			int timeout, String userName, String password, String jdbcUrl,
			String driveName) {
		super();
		this.initSize = initSize;
		this.maxSize = maxSize;
		this.timeout = timeout;
		this.userName = userName;
		this.password = password;
		this.jdbcUrl = jdbcUrl;
		this.driveName = driveName;
		this.acquireIncrement = acquireIncrement;
		reminderConnect = new AtomicInteger(initSize);
		totalConnect = new AtomicInteger(initSize);
		this.type = DataSourceType.BLOCKING;
		init();
	}

	/**
	 * <p>
	 * Title: 数据源构造函数
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param initSize
	 * @param maxSize
	 * @param timeout
	 * @param userName
	 * @param password
	 * @param jdbcUrl
	 * @param driveName
	 */
	public AbstractDataSource(String initSize, String maxSize,
			String acquireIncrement, String timeout, String userName,
			String password, String jdbcUrl, String driveName) {
		super();
		this.initSizePlaceholder = initSize;
		this.maxSizePlaceholder = maxSize;
		this.timeoutPlaceholder = timeout;
		this.userName = userName;
		this.password = password;
		this.jdbcUrl = jdbcUrl;
		this.driveName = driveName;
		this.acquireIncrementPlaceholder = acquireIncrement;
		/*
		 * reminderConnect = new AtomicInteger(initSize); totalConnect = new
		 * AtomicInteger(initSize);
		 */
		this.type = DataSourceType.BLOCKING;
	}

	public int getCurrentActivitySize() {
		return reminderConnect.get();
	}

	public int getCurrentTotalSize() {
		return totalConnect.get();
	}

	public static enum DataSourceType {
		BLOCKING, UNBLOCKING;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.CommonDataSource#getLogWriter()
	 */
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return this.out;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.CommonDataSource#setLogWriter(java.io.PrintWriter)
	 */
	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		this.out = out;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.CommonDataSource#setLoginTimeout(int)
	 */
	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		this.timeout = seconds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.CommonDataSource#getLoginTimeout()
	 */
	@Override
	public int getLoginTimeout() throws SQLException {
		return this.timeout;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Wrapper#unwrap(java.lang.Class)
	 */
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
	 */
	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.CommonDataSource#getParentLogger()
	 */
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

	public abstract void close() throws SQLException;

	public abstract void releaseConnection(Connection connection)
			throws SQLException;

}
