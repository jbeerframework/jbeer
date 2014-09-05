/**   
* @Title: Update.java
* @Package com.jbeer.framework.db.sqlunit
* @author Bieber
* @date 2014年6月6日 下午4:28:52
* @version V1.0   
*/

package com.jbeer.framework.db.sqlrunner;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jbeer.framework.db.ColumnModel;
import com.jbeer.framework.db.EntityModel;
import com.jbeer.framework.db.JBeerDBContext;
import com.jbeer.framework.db.sqlrunner.DefaultWhere.OperationType;
import com.jbeer.framework.db.type.TypeConverter;
import com.jbeer.framework.exception.DBException;
import com.jbeer.framework.logging.Log;
import com.jbeer.framework.utils.LoggerUtil;
import com.jbeer.framework.utils.StringUtils;

/**
* <p>类功能说明:update执行操作单元</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: Update.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月6日 下午4:28:52
* @version V1.0
*/

public class Update extends SQL{
    
    private static final Log logger = LoggerUtil.generateLogger(Update.class);

    private Class<?> entityClass;
    
    private StringBuffer set = new StringBuffer("set ");
    
    private StringBuffer update = new StringBuffer("update");
    
    private DefaultWhere where;

    private Connection conn;
    
    private List<Object> params = null;
    
    protected Update(Class<?> entityClass,Connection conn) {
        this.entityClass = entityClass;
        this.conn = conn;
        params = new ArrayList<Object>();
        update.append(" ").append(JBeerDBContext.getEntity(entityClass).getEntityName()).append(" ");
    }
    
    protected Update(Object entityObject,Connection conn) throws Exception{
    	 this.entityClass = entityObject.getClass();
    	 this.conn=conn;
    	 params = new ArrayList<Object>();
    	 EntityModel entity = JBeerDBContext.getEntity(entityClass);
    	 update.append(" ").append(entity.getEntityName()).append(" ");
    	 List<ColumnModel> columns = entity.getColumns();
    	 for(ColumnModel column:columns){
    		 if(column.isPrimarykey()||column.isAutoIncreasement()){
    			 continue;
    		 }
    		 Object columnValue = column.getGetMethod().invoke(entityObject, new Object[]{});
    		 if(columnValue!=null){
    			 defaultSet(column.getColumnName(), columnValue);
    		 }
    	 }
    }
    
    protected Update(String entityName,Connection conn){
        this.conn = conn;
        params = new ArrayList<Object>();
        update.append(" ").append(entityName).append(" ");
    }

    
    public DefaultWhere where(String columnName,OperationType operationType,Object columnValue){
        where = new DefaultWhere(this,params);
        where.defaultWhere(columnName, operationType, columnValue);
        return where;
    }
    public Update set(Object object) {
        EntityModel entity = JBeerDBContext.getEntity(entityClass);
        List<ColumnModel> columns = entity.getColumns();
        for(ColumnModel column:columns){
            Object columnValue=null;
            try {
                columnValue = column.getGetMethod().invoke(object, new Object[]{});
            } catch (Exception e) {
                logger.warn("invoke field "+column.getColumnName()+" get method error");
            }  
            if(columnValue!=null){
                defaultSet(column.getColumnName(),columnValue);
            }
        }
        return this;
    }

    public Update defaultSet(String columnName, Object columnValue) {
        params.add(TypeConverter.convertToJdbcType(columnValue));
        set.append(columnName).append("=").append("?").append(",");
        return this;
    }
    
    
    public Update set(String columnName, int columnValue) {
        return defaultSet(columnName,columnValue);
    }
    public Update set(String columnName, long columnValue) {
        return defaultSet(columnName,columnValue);
    }
    
    public Update set(String columnName, short columnValue) {
        return defaultSet(columnName,columnValue);
    }
    public Update set(String columnName, float columnValue) {
        return defaultSet(columnName,columnValue);
    }
    public Update set(String columnName, double columnValue) {
        return defaultSet(columnName,columnValue);
    }
    public Update set(String columnName, byte columnValue) {
        return defaultSet(columnName,columnValue);
    }
    public Update set(String columnName, String columnValue) {
        return defaultSet(columnName,columnValue);
    }
    public Update set(String columnName, boolean columnValue) {
        return defaultSet(columnName,columnValue);
    }
    public Update set(String columnName, char columnValue) {
        return defaultSet(columnName,columnValue);
    }
    public Update set(String columnName, BigInteger columnValue) {
        return defaultSet(columnName,columnValue);
    }
    public Update set(String columnName, BigDecimal columnValue) {
        return defaultSet(columnName,columnValue);
    }
    public Update set(String columnName, Date columnValue) {
        return defaultSet(columnName,columnValue);
    }

    /* (non-Javadoc)
     * @see com.jbeer.framework.db.sqlunit.SQLUnit#execute()
     */
    @Override
    public Object execute() throws DBException{
        PreparedStatement preparedStatement=null;
        try{
        String sql = sqlString();
        if(logger.isDebugEnabled()){
            logger.debug("execute sql "+sql);
            logger.debug("parameters "+params);
        }
        preparedStatement = conn.prepareStatement(sql);
        setParaments(preparedStatement, params);
        return preparedStatement.executeUpdate();
        }catch(Exception e){
            throw new DBException(e);
        }finally{
            finished(preparedStatement, null);
        }
    }


    /* (non-Javadoc)
     * @see com.jbeer.framework.db.sqlunit.SQLUnit#sqlString()
     */
    @Override
    public String sqlString() throws DBException{
        if(StringUtils.equals(set.toString(), "set")){
            throw new DBException("update statement must has set statement");
        }
        set.setLength(set.length()-1);
        update.append(set);
        if(where!=null){
            update.append(where.sqlString());
        }
        return update.toString();
    }

}
