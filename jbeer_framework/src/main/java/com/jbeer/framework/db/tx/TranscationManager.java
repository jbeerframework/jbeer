/**   
* @Title: TranscationManager.java
* @Package com.jbeer.framework.db.tx
* @author Bieber
* @date 2014年5月27日 上午10:34:00
* @version V1.0   
*/

package com.jbeer.framework.db.tx;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.sql.DataSource;

import com.jbeer.framework.exception.TranscationException;
import com.jbeer.framework.logging.Log;
import com.jbeer.framework.utils.LoggerUtil;

/**
* <p>类功能说明:事务管理者</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: TranscationManager.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年5月27日 上午10:34:00
* @version V1.0
*/

public class TranscationManager {

    private static final ThreadLocal<Map<DataSource, Stack<ConnectionHandler>>> currentThreadConnections       = new ThreadLocal<Map<DataSource, Stack<ConnectionHandler>>>();

    private static final ThreadLocal<Stack<Savepoint>>                          currentThreadSavepoint         = new ThreadLocal<Stack<Savepoint>>();

    private static final ThreadLocal<Map<Connection, Stack<TranscationStatus>>> currentThreadTranscationStatus = new ThreadLocal<Map<Connection, Stack<TranscationStatus>>>();

    private static final ThreadLocal<Boolean>                                   setIsolation                   = new ThreadLocal<Boolean>();

    private static final ThreadLocal<Boolean>                                   createSavepoint                = new ThreadLocal<Boolean>();

    private static final ThreadLocal<Boolean>                                   setReadonly                    = new ThreadLocal<Boolean>();

    private static final ThreadLocal<Boolean>                                   setTranscationStatus           = new ThreadLocal<Boolean>();
    
    private static final ThreadLocal<Boolean> hasTranscation = new ThreadLocal<Boolean>();
    
    
    private static final Log logger = LoggerUtil.generateLogger(TranscationManager.class);
    

    /**
     * 
    * <p>函数功能说明:初始化当前线程的事务管理器</p>
    * <p>Bieber  2014年5月28日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     */
    public static void initializeTranscationManager() {
        if (currentThreadConnections.get() == null) {
            currentThreadConnections.set(new HashMap<DataSource, Stack<ConnectionHandler>>());
        }
        if (currentThreadTranscationStatus.get() == null) {
            currentThreadTranscationStatus.set(new HashMap<Connection, Stack<TranscationStatus>>());
        }
        hasTranscation.set(true);
    }

    public static TranscationStatus getCurrentTranscation(DataSource ds){
    	if(currentThreadConnections.get().get(ds)==null||currentThreadConnections.get().get(ds).size()<=0){
    		return null;
    	}
        ConnectionHandler currentHandler = currentThreadConnections.get().get(ds).peek();
        return currentThreadTranscationStatus.get().get(currentHandler.getConnection()).peek();
    }
    
    public static void registTranscation(TranscationDefinition definition, DataSource ds)
                                                                                         throws TranscationException {
        try {
            ConnectionHandler currentConnectionHandler =  getCurrentConnectionHandler(ds);
            Connection currentConnection = null;
            if(currentConnectionHandler!=null){
            	currentConnection = currentConnectionHandler.getConnection();
            }
           
            switch (definition.getPropagation()) {
                case PROPAGATION_MANDATORY: {
                    if (!hasTranscation(currentConnection)) {
                        throw new TranscationException(Propagation.PROPAGATION_MANDATORY);
                    }else{
                        currentConnectionHandler.require();
                        registTranscationStatus(currentConnection, false, false, false);
                    }
                    break;
                }
                case PROPAGATION_NESTED: {
                    //如果当前环境没有事务，则和Required一样
                    if (!hasTranscation(currentConnection)) {
                        createNewTranscation(ds,definition);
                    }else{
                      Savepoint sp =currentConnectionHandler.createSavepoint(definition.getName());
                      registSavepoint(sp);
                      currentConnectionHandler.require();
                      registTranscationStatus(currentConnection, false, true, false);
                    }
                    break;
                }
                case PROPAGATION_NEVER:
                    //如果当前上下文有事务，则抛出异常
                    if (hasTranscation(currentConnection)) {
                        throw new TranscationException(Propagation.PROPAGATION_MANDATORY);
                    }else{
                        createNewConnectionHandler(ds);
                    }
                    break;
                case PROPAGATION_NOT_SUPPORTED:
                    //不管当前是否存在事务，需要创建一个没事务的连接
                    createNewConnectionHandler(ds);
                    break;
                case PROPAGATION_REQUIRED:
                    if(!hasTranscation(currentConnection)){
                        createNewTranscation(ds,definition);
                    }else{
                        currentConnectionHandler.require();
                        registTranscationStatus(currentConnection, false, false, false);
                    }
                    break;
                case PROPAGATION_REQUIRED_NEW:
                    //创建一个新的事务
                    createNewTranscation(ds,definition);
                    break;
                case PROPAGATION_SUPPORTS:
                    //当前如果有事务则按照事务进行，如果没有则按照非实物运行
                    if(hasTranscation(currentConnection)){
                        currentConnectionHandler.require();
                        registTranscationStatus(currentConnection, false, false, false);
                    }else{
                        createNewConnectionHandler(ds);
                    }
                    break;
                default:
                    throw new TranscationException("Not support current propagation "+definition.getPropagation().toString());
            }
        } catch (Exception e) {
            try {
                //当创建事务时，出现异常，则需要还原现场
                if (createSavepoint.get() != null && createSavepoint.get()) {
                    Savepoint sp = currentThreadSavepoint.get().pop();
                    ConnectionHandler handler = currentThreadConnections.get().get(ds).peek();
                    handler.releaseSavepoint(sp);
                }
                if (setIsolation.get() != null && setIsolation.get()) {
                    ConnectionHandler handler = currentThreadConnections.get().get(ds).peek();
                    handler.rollBackIsolation();
                }
                if (setReadonly.get() != null && setReadonly.get()) {
                    ConnectionHandler handler = currentThreadConnections.get().get(ds).peek();
                    handler.rollBackReadonly();
                }
                if (setTranscationStatus.get() != null && setTranscationStatus.get()) {
                }
            } catch (Exception e1) {
                throw new TranscationException(e1);
            }
            throw new TranscationException(e);
        }finally{
            createSavepoint.set(false);
            setIsolation.set(false);
            setReadonly.set(false);
            setTranscationStatus.set(false);
        }
    }

    private static void createNewTranscation(DataSource ds, TranscationDefinition definition) throws SQLException {
    	if(logger.isDebugEnabled()){
    		logger.debug("start new transcation for datasource "+ds.getClass().getName());
    	}
       ConnectionHandler handler =  createNewConnectionHandler(ds);
       handler.startTranscation();
       handler.setIoslation(definition.getIsolation().getValue());
       setIsolation.set(true);
       handler.setReadonly(definition.isReadonly());
       setReadonly.set(true);
       registTranscationStatus(handler.getConnection(), true, false, false);
    }

    
    /**
     * 
    * <p>函数功能说明:提交事务</p>
    * <p>Bieber  2014年5月28日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     * @throws SQLException 
     */
    public static void commit(DataSource ds) throws SQLException{
       ConnectionHandler handler =  currentThreadConnections.get().get(ds).peek();
       TranscationStatus status = currentThreadTranscationStatus.get().get(handler.getConnection()).pop();
       /**
        * 表示没有事务，则直接弹出连接栈
        */
       if(status==null){
           currentThreadConnections.get().get(ds).pop();
           return ;
       }
       
       
       if(status.isHasSavepoint()&&status.isCompleted()){
           Savepoint sp =currentThreadSavepoint.get().pop();
           handler.releaseConnnection();
           handler.releaseSavepoint(sp);
           return ;
       }
       
       if(status.isHasSavepoint()&&status.isRollBack()){
           Savepoint sp =currentThreadSavepoint.get().pop();
           if(logger.isDebugEnabled()){
       		logger.debug("roll back to savepoint "+sp.getSavepointName());
       	}
           handler.releaseConnnection();
           handler.getConnection().rollback(sp);
           return;
       }
       
       if(status.isNewTranscation()&&status.isCompleted()){
           handler.commit();
           currentThreadConnections.get().get(ds).pop();
           return;
       }
       if(status.isNewTranscation()&&status.isRollBack()){
           handler.rollBack();
           currentThreadConnections.get().get(ds).pop();
           return;
       }
       if(!status.isNewTranscation()&&status.isCompleted()){
           handler.releaseConnnection();
           return;
       }
       
       if(!status.isNewTranscation()&&status.isRollBack()){
           handler.rollBack();
           currentThreadConnections.get().get(ds).pop();
           return;
       }

    }
    /**
     * 
    * <p>函数功能说明:注册一个事务</p>
    * <p>Bieber  2014年5月28日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     */
    private static void registTranscationStatus(Connection connection, TranscationStatus transcationStatus) {
        Stack<TranscationStatus> status = currentThreadTranscationStatus.get().get(connection);
        if (status == null) {
            status = new Stack<TranscationStatus>();
            currentThreadTranscationStatus.get().put(connection, status);
        }
        status.push(transcationStatus);
    }

    /**
     * 
    * <p>函数功能说明:在当前线程注册一个savepoint</p>
    * <p>Bieber  2014年5月28日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     */
    private static void registSavepoint(Savepoint sp) {
        Stack<Savepoint> sps = currentThreadSavepoint.get();
        if (sps == null) {
            sps = new Stack<Savepoint>();
            currentThreadSavepoint.set(sps);
        }
        sps.push(sp);
    }

    /**
     * 
    * <p>函数功能说明:注册一个事务</p>
    * <p>Bieber  2014年5月28日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     */
    private static void registTranscationStatus(Connection connection, boolean isNewTranscation,
                                                boolean hasSavepoint, boolean completed) {
        TranscationStatus transcationStatus = new TranscationStatus();
        transcationStatus.setNewTranscation(isNewTranscation);
        transcationStatus.setHasSavepoint(hasSavepoint);
        transcationStatus.setCompleted(completed);
        registTranscationStatus(connection, transcationStatus);
        setTranscationStatus.set(true);
    }

    /**
     * 
    * <p>函数功能说明:检查当前数据源是否存在事务</p>
    * <p>Bieber  2014年5月28日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return boolean
     */
    private static boolean hasTranscation(Connection connection) {
        /**
         * 获取当前数据源的所有事务
         */
        Stack<TranscationStatus> statusStack = currentThreadTranscationStatus.get().get(connection);
        if (statusStack == null || statusStack.size() <= 0) {
            return false;
        }
        return true;
    }
    
    private static ConnectionHandler getCurrentConnectionHandler(DataSource ds){
    	 Map<DataSource, Stack<ConnectionHandler>> resources = currentThreadConnections.get();
    	 if(resources==null){
    		 throw new IllegalStateException("did not initialize transcation");
    	 }
    	 Stack<ConnectionHandler> handlerStack = resources.get(ds);
    	 if(handlerStack==null||handlerStack.size()<=0){
    		 return null;
    	 }
    	 return handlerStack.peek();
    }
    
    private static ConnectionHandler getConnectionHandler(DataSource ds) throws SQLException {
        Map<DataSource, Stack<ConnectionHandler>> resources = currentThreadConnections.get();
        
        if (resources == null) {
            throw new IllegalStateException("did not initialize transcation");
        }

        Stack<ConnectionHandler> handlerStack = resources.get(ds);
        if (handlerStack == null||handlerStack.size() <= 0) {
            createNewConnectionHandler(ds);
            handlerStack=resources.get(ds);
        } 
        ConnectionHandler handler = handlerStack.peek();
        if (!handler.hasConnection()) {
            Connection connection = ds.getConnection();
            handler.setConnection(connection);
        }
        return handler;
    }
    
    private static ConnectionHandler createNewConnectionHandler(DataSource ds) throws SQLException{
        Map<DataSource, Stack<ConnectionHandler>> resources = currentThreadConnections.get();
        if (resources == null) {
            throw new IllegalStateException("did not initialize transcation");
        }
        Stack<ConnectionHandler> handlerStack = resources.get(ds);
        if(handlerStack==null){
        	handlerStack=new Stack<ConnectionHandler>();
        	resources.put(ds, handlerStack);
        }
        Connection connection = ds.getConnection();
        ConnectionHandler handler = new ConnectionHandler(connection);
        handlerStack.push(handler);
        return handler;
    }

    public static Connection getConnection(DataSource ds) throws SQLException {
    	if(hasTranscation.get()==null||!hasTranscation.get()){
    		return ds.getConnection();
    	}
        ConnectionHandler handler = getConnectionHandler(ds);
        return handler.getConnection();
    }
}
