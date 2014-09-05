/**   
* @Title: Delete.java
* @Package com.jbeer.framework.db.sqlunit
* @author Bieber
* @date 2014年6月6日 下午4:29:13
* @version V1.0   
*/

package com.jbeer.framework.db.sqlrunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.jbeer.framework.db.EntityModel;
import com.jbeer.framework.db.JBeerDBContext;
import com.jbeer.framework.db.sqlrunner.DefaultWhere.OperationType;
import com.jbeer.framework.exception.DBException;
import com.jbeer.framework.logging.Log;
import com.jbeer.framework.utils.LoggerUtil;

/**
* <p>类功能说明:delete sql语句</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: Delete.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月6日 下午4:29:13
* @version V1.0
*/

public class Delete extends SQL{

    private static final Log logger = LoggerUtil.generateLogger(Delete.class);
    
    private DefaultWhere where;
    
    private List<Object> params;
    
    private StringBuffer delete;
    
    private Connection conn;
    
    protected Delete(Class<?> entityClass,Connection conn) throws DBException{
        delete = new StringBuffer();
        EntityModel entity = JBeerDBContext.getEntity(entityClass);
        if(entity==null){
            throw new DBException("dit not found entity for class "+entityClass);
        }
        delete.append("delete from ").append(entity.getEntityName());
        this.conn =conn;
    }
    
    protected Delete(String entityName,Connection conn){
        delete = new StringBuffer();
        this.conn =conn;
        delete.append("delete from ").append(entityName);
    }
    
    
    public DefaultWhere where(String columnName,OperationType operationType,Object columnValue){
        params = new ArrayList<Object>();
        where = new DefaultWhere(this,params);
        where.defaultWhere(columnName, operationType, columnValue);
        return where;
    }
    
    
    
    
    /* (non-Javadoc)
     * @see com.jbeer.framework.db.sqlrunner.SQLUnit#execute()
     */
    @Override
    public Object execute() throws DBException {
        PreparedStatement preparedStatement = null;
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
     * @see com.jbeer.framework.db.sqlrunner.SQLUnit#sqlString()
     */
    @Override
    public String sqlString() throws DBException {
        if(where!=null){
            delete.append(where.sqlString());
        }
        return delete.toString();
    }

}
