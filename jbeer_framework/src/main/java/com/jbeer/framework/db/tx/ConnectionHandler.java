/**   
* @Title: ConnectionHandler.java
* @Package com.jbeer.framework.db
* @author Bieber
* @date 2014年5月26日 下午3:47:36
* @version V1.0   
*/

package com.jbeer.framework.db.tx;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import com.jbeer.framework.logging.Log;
import com.jbeer.framework.utils.LoggerUtil;

/**
* <p>类功能说明:连接持有者</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: ConnectionHandler.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年5月26日 下午3:47:36
* @version V1.0
*/

public class ConnectionHandler {

    
    private Connection connection;
    
    private int requireCount;
    
    private int previousIsolation;
    
    
    private boolean previousReadonly;
    
    private static final String SAVEPOINT_PREFIX = "SAVEPOINT_";
        
    private static final Log logger = LoggerUtil.generateLogger(ConnectionHandler.class);
    
    public ConnectionHandler(Connection connection) throws SQLException{
        this.connection =connection;
        previousIsolation = connection.getTransactionIsolation();
        previousReadonly = connection.isReadOnly();
        requireCount=1;
    }
    
    public boolean hasConnection(){
        return connection==null?false:true;
    }
    
    public void startTranscation() throws SQLException{
        connection.setAutoCommit(false);
    }
    
    public void setReadonly(boolean readonly) throws SQLException{
        previousReadonly = connection.isReadOnly();
        connection.setReadOnly(readonly);
    }
    
    public void rollBack() throws SQLException{
    	if(logger.isDebugEnabled()){
    		logger.debug("rollback all transcation ");
    	}
        connection.rollback();
        releaseConnnection();
    }
    
    public void rollBackIsolation() throws SQLException{
        connection.setTransactionIsolation(previousIsolation);
    }
    
    public void rollBackReadonly() throws SQLException{
        connection.setReadOnly(previousReadonly);
    }
    /**
     * 
    * <p>函数功能说明:设置事务隔离级别</p>
    * <p>Bieber  2014年5月28日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     */
    public void setIoslation(int isolation) throws SQLException{
        previousIsolation = connection.getTransactionIsolation();
        connection.setTransactionIsolation(isolation);
    }
    
    /**
     * 
    * <p>函数功能说明:释放对连接的持有</p>
    * <p>Bieber  2014年5月28日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     */
    public void releaseConnnection() throws SQLException{
        requireCount--;
        if(requireCount==0){
        	if(logger.isDebugEnabled()){
        		logger.debug("release connection to pool ");
        	}
            connection.setTransactionIsolation(previousIsolation);
            connection.setReadOnly(previousReadonly);
            connection.close();
            connection = null;
        }
    }
    
    public void commit() throws SQLException{
    	if(logger.isDebugEnabled()){
    		logger.debug("commit transcation.... ");
    	}
        this.connection.commit();
        releaseConnnection();
    }
    /**
    * @param connection connection
     * @throws SQLException 
    */
    
    public void setConnection(Connection connection) throws SQLException {
        this.connection = connection;
        previousIsolation = connection.getTransactionIsolation();
        previousReadonly = connection.isReadOnly();
        requireCount=0;
    }

    public Connection getConnection(){
        return this.connection;
    }
    public void require(){
        requireCount++;
    }
    
    
    public Savepoint createSavepoint(String name) throws SQLException{
    	if(logger.isDebugEnabled()){
    		logger.debug("create new savepoint "+SAVEPOINT_PREFIX+name);
    	}
        return this.connection.setSavepoint(SAVEPOINT_PREFIX+name);
    }
    
    
   public  void releaseSavepoint(Savepoint sp) throws SQLException{
       this.connection.releaseSavepoint(sp);
   }
    
   
    
    
}
