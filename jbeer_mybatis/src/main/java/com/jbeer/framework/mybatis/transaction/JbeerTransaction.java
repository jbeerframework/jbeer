/**
 * 
 */
package com.jbeer.framework.mybatis.transaction;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;

import com.jbeer.framework.db.DataSourceUtil;

/**
 * @author bieber
 *
 */
public class JbeerTransaction implements Transaction {

	protected Connection connection;
	protected DataSource dataSource;
	protected TransactionIsolationLevel level;
	protected boolean autoCommmit;
	public JbeerTransaction(DataSource ds, TransactionIsolationLevel desiredLevel, boolean desiredAutoCommit) {
	    dataSource = ds;
	    level = desiredLevel;
	    autoCommmit = desiredAutoCommit;
	  }

	  public JbeerTransaction(Connection connection) {
	    this.connection = connection;
	  }
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.ibatis.transaction.Transaction#getConnection()
	 */
	public Connection getConnection() throws SQLException {
		if(connection!=null){
			return connection;
		}
		return DataSourceUtil.getConnection(dataSource);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.ibatis.transaction.Transaction#commit()
	 */
	public void commit() throws SQLException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.ibatis.transaction.Transaction#rollback()
	 */
	public void rollback() throws SQLException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.ibatis.transaction.Transaction#close()
	 */
	public void close() throws SQLException {

	}

}
