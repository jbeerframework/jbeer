/**   
 * @Title: Insert.java
 * @Package com.jbeer.framework.db.sqlunit
 * @author Bieber
 * @date 2014年6月6日 下午4:28:42
 * @version V1.0   
 */

package com.jbeer.framework.db.sqlrunner;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jbeer.framework.db.ColumnModel;
import com.jbeer.framework.db.EntityModel;
import com.jbeer.framework.db.JBeerDBContext;
import com.jbeer.framework.db.type.TypeConverter;
import com.jbeer.framework.exception.DBException;
import com.jbeer.framework.logging.Log;
import com.jbeer.framework.utils.CaseUtils;
import com.jbeer.framework.utils.LoggerUtil;

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
 * Title: Insert.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年6月6日 下午4:28:42
 * @version V1.0
 */

public class Insert extends SQL {

	private static final Log logger = LoggerUtil
			.generateLogger(Insert.class);

	private Connection conn;

	private List<Object> params;

	private String entityName;

	private StringBuffer insert;

	private StringBuffer columns;

	private StringBuffer values;

	private boolean isGenerateKeys = false;

	private Object entityObject;

	private EntityModel entity;

	protected Insert(Object entityObject, Connection conn) throws Exception {
		this.conn = conn;
		entity = JBeerDBContext.getEntity(entityObject.getClass());
		entityName = entity.getEntityName();
		this.entityObject = entityObject;
		init();
		List<ColumnModel> models = entity.getColumns();
		for (ColumnModel model : models) {
			Object columnValue = model.getGetMethod().invoke(entityObject,
					new Object[] {});
			if (model.isAutoIncreasement()) {
				isGenerateKeys = true;
			}
			if (columnValue != null) {
				defaultInsert(model.getColumnName(), columnValue);
			}
		}
	}

	protected Insert(Class<?> entityClass, Connection conn) {
		this.conn = conn;
		entityName = JBeerDBContext.getEntity(entityClass).getEntityName();
		init();
	}

	protected Insert(String entityName, Connection conn) {
		this.entityName = entityName;
		this.conn = conn;
		init();
	}

	public Insert defaultInsert(String columnName, Object columnValue) {
		if (columns == null) {
			columns = new StringBuffer("( ");
			values = new StringBuffer("values(");
			params = new ArrayList<Object>();
		}
		columns.append(columnName).append(",");
		values.append("?,");
		params.add(TypeConverter.convertToJdbcType(columnValue));
		return this;
	}

	public Insert insert(String columnName, int columnValue) {
		return defaultInsert(columnName, columnValue);
	}

	public Insert insert(String columnName, short columnValue) {
		return defaultInsert(columnName, columnValue);
	}

	public Insert insert(String columnName, long columnValue) {
		return defaultInsert(columnName, columnValue);
	}

	public Insert insert(String columnName, char columnValue) {
		return defaultInsert(columnName, columnValue);
	}

	public Insert insert(String columnName, byte columnValue) {
		return defaultInsert(columnName, columnValue);
	}

	public Insert insert(String columnName, float columnValue) {
		return defaultInsert(columnName, columnValue);
	}

	public Insert insert(String columnName, double columnValue) {
		return defaultInsert(columnName, columnValue);
	}

	public Insert insert(String columnName, BigInteger columnValue) {
		return defaultInsert(columnName, columnValue);
	}

	public Insert insert(String columnName, BigDecimal columnValue) {
		return defaultInsert(columnName, columnValue);
	}

	public Insert insert(String columnName, Date columnValue) {
		return defaultInsert(columnName, columnValue);
	}

	public Insert insert(String columnName, String columnValue) {
		return defaultInsert(columnName, columnValue);
	}

	private void init() {
		if (insert == null) {
			insert = new StringBuffer();
		}
		insert.append("insert into ").append(entityName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jbeer.framework.db.sqlrunner.SQLUnit#execute()
	 */
	@Override
	public Object execute() throws DBException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			String sql = sqlString();
			if (logger.isDebugEnabled()) {
				logger.debug("execute sql " + sql);
				logger.debug("parameters " + params);
			}
			if (isGenerateKeys && entity != null && entityObject != null) {
				preparedStatement = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
			} else {
				preparedStatement = conn.prepareStatement(sql);
			}
			setParaments(preparedStatement, params);
			boolean result = preparedStatement.execute();
			if (isGenerateKeys && entity != null && entityObject != null) {
				rs = preparedStatement.getGeneratedKeys();
				List<Map<String, Object>> results = getResults(rs);
				if (results.size() > 0) {
					Collection<Object> values = results.get(0).values();
					Iterator<Object> iterator = values.iterator();
					if (iterator.hasNext()) {
						ColumnModel column = entity.getColumn(entity.getAutoGeneratedColumnName());
						column
								.getSetMethod()
								.invoke(entityObject,
										new Object[] { CaseUtils.caseType(column.getType(), iterator.next().toString()) });
					}
				}
				return entityObject;
			}
			return result;
		} catch (Exception e) {
			throw new DBException(e);
		} finally {
			finished(preparedStatement, rs);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jbeer.framework.db.sqlrunner.SQLUnit#sqlString()
	 */
	@Override
	public String sqlString() throws DBException {
		if (columns == null || values == null) {
			throw new DBException("dit not set insert columns or values");
		}
		columns.setLength(columns.length() - 1);
		values.setLength(values.length() - 1);
		columns.append(")");
		values.append(")");
		insert.append(" ").append(columns).append(" ").append(values);
		return insert.toString();
	}

}
