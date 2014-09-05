/**   
* @Title: SQLUnit.java
* @Package com.jbeer.framework.db.sqlunit
* @author Bieber
* @date 2014年6月6日 下午6:04:32
* @version V1.0   
*/

package com.jbeer.framework.db.sqlrunner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jbeer.framework.db.EntityModel;
import com.jbeer.framework.db.type.TypeConverter;
import com.jbeer.framework.exception.DBException;
import com.jbeer.framework.logging.Log;
import com.jbeer.framework.utils.LoggerUtil;

/**
* <p>类功能说明:TODO</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: SQLUnit.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月6日 下午6:04:32
* @version V1.0
*/

public abstract class SQL {
    
    private static final Log logger = LoggerUtil.generateLogger(SQL.class);
    
    protected List<Object> params = new ArrayList<Object>();
    
    
    /**
     * 
    * <p>函数功能说明:执行该条SQL语句</p>
    * <p>Bieber  2014年6月7日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return Object
     */
    public abstract Object execute() throws DBException;
    
    /**
     * 
    * <p>函数功能说明:输出该条SQL语句</p>
    * <p>Bieber  2014年6月7日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return String
     */
    public abstract String sqlString() throws DBException;
    
    /**
     * 
    * <p>函数功能说明:设置参数</p>
    * <p>Bieber  2014年6月7日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     */
    protected static void setParaments(PreparedStatement ps ,List<Object> args) throws SQLException{
        if(args==null){
            return;
        }
        for(int i=0;i<args.size();i++){
            ps.setObject(i+1, args.get(i));
        }
    }
    
    /**
     * 
    * <p>函数功能说明:后的结果</p>
    * <p>Bieber  2014年6月6日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     */
    protected static void getOneResult(ResultSet rs, Object o, EntityModel entity) throws Exception {
        ResultSetMetaData rsmd = rs.getMetaData();
        List<String> columns = new ArrayList<String>(rsmd.getColumnCount());
        List<Integer> columnTypes = new ArrayList<Integer>(rsmd.getColumnCount());
        analysisResultSet(rsmd, columns, columnTypes);
        if(logger.isDebugEnabled()){
        	logger.debug(columns);
        }
        if (rs.next()) {
        	StringBuffer rowStr = new StringBuffer();
        	Object columnValue = null;
            for (int i = 0; i < columns.size(); i++) {
            	columnValue = TypeConverter.convertToPojoType(rs, i+1, columnTypes.get(i));
            	rowStr.append(columnValue).append(" ");
                entity.getColumn(columns.get(i)).getSetMethod()
                    .invoke(o, columnValue);
            }
            if(logger.isDebugEnabled()){
            	logger.debug(rowStr);
            }
        }
    }
    /**
     * 
    * <p>函数功能说明:从resultset对象中获取一个Map集合</p>
    * <p>Bieber  2014年6月7日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return List<Map<String,Object>>
     */
    protected static List<Map<String, Object>> getResults(ResultSet rs) throws Exception {
        ResultSetMetaData rsmd = rs.getMetaData();
        List<String> columns = new ArrayList<String>(rsmd.getColumnCount());
        List<Integer> columnTypes = new ArrayList<Integer>(rsmd.getColumnCount());
        analysisResultSet(rsmd, columns, columnTypes);
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        if(logger.isDebugEnabled()){
        	logger.debug(columns);
        }
        StringBuffer rowStr = new StringBuffer();
    	Object columnValue = null;
        while (rs.next()) {
        	rowStr.setLength(0);
            Map<String, Object> row = new HashMap<String, Object>();
            results.add(row);
            for (int i = 0; i < columns.size(); i++) {
            	columnValue = TypeConverter.convertToPojoType(rs, i+1, columnTypes.get(i));
            	rowStr.append(columnValue).append(" ");
                row.put(columns.get(i), TypeConverter.convertToPojoType(rs, i+1, columnTypes.get(i)));
            }
            if(logger.isDebugEnabled()){
            	logger.debug(rowStr);
            }
        }
        return results;
    }

    /**
     * 
    * <p>函数功能说明:从resultset中获取制定类型的实体类集合</p>
    * <p>Bieber  2014年6月7日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return List<Object>
     */
    protected static List<Object> getResults(ResultSet rs, EntityModel entity) throws Exception {
        ResultSetMetaData rsmd = rs.getMetaData();
        List<String> columns = new ArrayList<String>(rsmd.getColumnCount());
        List<Integer> columnTypes = new ArrayList<Integer>(rsmd.getColumnCount());
        analysisResultSet(rsmd, columns, columnTypes);
        List<Object> results = new ArrayList<Object>();
        if(logger.isDebugEnabled()){
        	logger.debug(columns);
        }
        StringBuffer rowStr = new StringBuffer();
    	Object columnValue = null;
        while (rs.next()) {
        	rowStr.setLength(0);
            Object row = entity.getEntityInstance();
            for (int i = 0; i < columns.size(); i++) {
            	columnValue = TypeConverter.convertToPojoType(rs, i+1, columnTypes.get(i));
            	rowStr.append(columnValue).append(" ");
                entity.getColumn(columns.get(i)).getSetMethod()
                    .invoke(row, TypeConverter.convertToPojoType(rs, i+1, columnTypes.get(i)));
            }
            if(logger.isDebugEnabled()){
            	logger.debug(rowStr);
            }
            results.add(row);
        }
        return results;
    }

    protected static void analysisResultSet(ResultSetMetaData rsmd, List<String> columns,
                                          List<Integer> columnTypes) throws Exception {
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            columns.add(rsmd.getColumnLabel(i));
            columnTypes.add(rsmd.getColumnType(i));
        }
    }
    
    protected static void finished(PreparedStatement preparedStatement,ResultSet rs){
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                logger.warn("close preparedStatement fail", e);
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.warn("close resultset fail", e);
            }
        }
    }

}
