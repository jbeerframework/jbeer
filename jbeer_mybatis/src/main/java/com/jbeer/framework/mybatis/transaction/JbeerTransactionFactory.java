/**
 * 
 */
package com.jbeer.framework.mybatis.transaction;

import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;

/**
 * @author bieber
 *
 */
public class JbeerTransactionFactory implements TransactionFactory {

	/* (non-Javadoc)
	 * @see org.apache.ibatis.transaction.TransactionFactory#setProperties(java.util.Properties)
	 */
	public void setProperties(Properties props) {
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.transaction.TransactionFactory#newTransaction(java.sql.Connection)
	 */
	public Transaction newTransaction(Connection conn) {
		return new JbeerTransaction(conn);
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.transaction.TransactionFactory#newTransaction(javax.sql.DataSource, org.apache.ibatis.session.TransactionIsolationLevel, boolean)
	 */
	public Transaction newTransaction(DataSource dataSource,
			TransactionIsolationLevel level, boolean autoCommit) {
		return new JbeerTransaction(dataSource, level, autoCommit);
	}

}
