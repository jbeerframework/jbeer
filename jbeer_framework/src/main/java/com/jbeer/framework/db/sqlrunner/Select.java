/**   
* @Title: Select.java
* @Package com.jbeer.framework.db.sqlunit
* @author Bieber
* @date 2014年6月6日 下午4:29:20
* @version V1.0   
*/

package com.jbeer.framework.db.sqlrunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jbeer.framework.db.EntityModel;
import com.jbeer.framework.db.JBeerDBContext;
import com.jbeer.framework.db.sqlrunner.DefaultWhere.OperationType;
import com.jbeer.framework.db.type.ResultHandler;
import com.jbeer.framework.exception.DBException;
import com.jbeer.framework.logging.Log;
import com.jbeer.framework.utils.LoggerUtil;
import com.jbeer.framework.utils.StringUtils;

/**
* <p>类功能说明:select sql语句</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: Select.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月6日 下午4:29:20
* @version V1.0
*/

public class Select extends GroupByable{
    
    private Connection conn;
    
    private StringBuffer select;
    
    private SelectWhere where;
    
    private StringBuffer from;
    
    private StringBuffer join;
    
    private EntityModel entity;
    
    private boolean isSelectOne=false;
    
    private int entitySize=0;
    
    private String customSQL;
    
    private static final Log logger = LoggerUtil.generateLogger(Select.class);
    
   
    
    protected Select(Class<?> entityClass,Connection conn) throws DBException{
        this.conn = conn;
        this.executor=this;
        if(from==null){
            from = new StringBuffer(" from ");
        }
        this.entity = JBeerDBContext.getEntity(entityClass);
        if(entity==null){
            throw new DBException("not found entity for class "+entityClass);
        }
        params=new ArrayList<Object>();
        from.append(entity.getEntityName()).append(",");
        entitySize++;
    } 
    
    protected Select(String entityName,Connection conn){
        this.conn = conn;
        this.executor=this;
        if(from==null){
            from = new StringBuffer(" from ");
        }
        params=new ArrayList<Object>();
        from.append(entityName).append(",");
        entitySize++;
    }
    
    protected Select(Connection conn){
        this.conn = conn;
        this.executor=this;
        params=new ArrayList<Object>();
    }

    
    public SelectWhere where(String columnName,OperationType operationType,Object columnValue){
        if(where==null){
            where = new SelectWhere(this,params);
        }
        where.defaultWhere(columnName, operationType, columnValue);
        return where;
    }
    
    public Select from(String entityName){
        from.append(entityName).append(",");
        entitySize++;
        return this;
    }
    
    public Select leftJoin(String currentEntityColumnName,String otherEntityName,String columnName){
        if(from==null||entitySize>1){
            throw new IllegalArgumentException("please specify entity first!");
        }
        if(join==null){
            join = new StringBuffer();
        }
        join.append(" left join ").append(otherEntityName).append(" on ").append(currentEntityColumnName).append("=").append(columnName);
        return this;
    }
    
    public Select rightJoin(String currentEntityColumnName,String otherEntityName,String columnName){
        if(from==null||entitySize>1){
            throw new IllegalArgumentException("please specify entity first!");
        }
        if(join==null){
            join = new StringBuffer();
        }
        join.append(" right join ").append(otherEntityName).append(" on ").append(currentEntityColumnName).append("=").append(columnName);
        return this;
    }
    
    public Select fetch(String columnName){
        if(select==null){
            select= new StringBuffer("select ");
        }
        select.append(columnName).append(",");
        return this;
    }
    
    
    @SuppressWarnings("unchecked")
	public <T> T selectByPrimaryKey(Object primaryKeyValue) throws DBException{
        if(entity==null){
        	throw new DBException("not set entity class,not support find by primary key");
        }else if(entity.getPrimaryKeyName()==null){
        	throw new DBException("didn't know primaryKey column name");
        }
        if(where==null){
        	where(entity.getPrimaryKeyName(), OperationType.EQUAL, primaryKeyValue);
        }else{
        	where.defaultWhere(entity.getPrimaryKeyName(), OperationType.EQUAL, primaryKeyValue);
        }
        isSelectOne=true;
        Object res =  execute();
        return (T) res;
    }
    
    @SuppressWarnings("unchecked")
	public <T> T selectOne(Class<T> entityClass) throws DBException{
        entity = JBeerDBContext.getEntity(entityClass);
        if(entity==null){
        	throw new DBException("not found entity for class "+entityClass);
        }
        isSelectOne=true;
        Object res =  execute();
        return (T) res;
    }
    
    @SuppressWarnings("unchecked")
	public <T> List<T> selectList(ResultHandler<T> resultHandler) throws DBException{
        if(resultHandler==null){
            throw new DBException("resultHandler must not null");
        }
    	List<Map<String,Object>> results  = (List<Map<String, Object>>) execute();
        return resultHandler.getResults(results);
    }
    
    @SuppressWarnings("unchecked")
	public <T> List<T> selectList() throws DBException{
        if(entity==null){
            throw new DBException("not specify entityClass,please specify entityClass first!");
        }
    	return  (List<T>) execute();
    }
    
    @SuppressWarnings("unchecked")
	public Map<String,Object> selectOne() throws DBException{
    	 isSelectOne=true;
    	 Map<String,Object> result  = (Map<String, Object>) execute();
    	return result;
    }
    
    @SuppressWarnings("unchecked")
	public <T> List<T> selectList(Class<T> entityClass) throws DBException{
    	entity = JBeerDBContext.getEntity(entityClass);
    	if(entity==null){
        	throw new DBException("not found entity for class "+entityClass);
        }
		return (List<T>) execute();
    }
    
    public <T> T selectOne(ResultHandler<T> resultHandler) throws DBException{
        if(resultHandler==null){
            throw new DBException("resultHandler must not null");
        }
        return resultHandler.getResult(selectOne());
    }
    
    /**
     * 
    * <p>函数功能说明:设置自定义的SQL语句，如果设置该属性，那么之前设置的entityName，以及fetch设置的信息，join信息将无效，如果设置的where,group by ,having,order by 等page子句，将会拼装在后面</p>
    * <p>Bieber  2014年6月10日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return Select
     */
    public Select setCustomSQL(String customSQL){
        this.customSQL=customSQL;
        return this;
    }

    /* (non-Javadoc)
     * @see com.jbeer.framework.db.sqlrunner.SQL#execute()
     */
    @Override
    public Object execute() throws DBException {
    	String sql = sqlString();
        PreparedStatement preparedStatement=null;
        ResultSet rs = null;
        if(logger.isDebugEnabled()){
        	logger.debug("execute sql "+sql);
        	logger.debug("parameters "+params);
        }
        try{
        	preparedStatement = conn.prepareStatement(sql);
        	setParaments(preparedStatement, params);
        	rs = preparedStatement.executeQuery();
        	if(isSelectOne){
        		if(entity!=null){
        		  Object obj = entity.getEntityInstance();
        		  getOneResult(rs, obj, entity);
        		  return obj;
        		}else{
        			 List<Map<String, Object>> results = getResults(rs);
        			 if(results.size()>0){
        				 return results.get(0);
        			 }
        			return null;
        		}
        	}else{
        		if(entity!=null){
        			return getResults(rs, entity);
        		}else{
        			return getResults(rs);
        		}
        	}
        }catch(Exception e){
        	throw new DBException(e);
        }finally{
        	finished(preparedStatement, rs);
        }
    }

    /* (non-Javadoc)
     * @see com.jbeer.framework.db.sqlrunner.SQL#sqlString()
     */
    @Override
    public String sqlString() throws DBException {
        if(StringUtils.isEmpty(customSQL)){
    	if(select==null){
    		fetch("*");
    	}
        select.setLength(select.length()-1);
        from.setLength(from.length()-1);
        select.append(from.toString()).append(" ");
        if(join!=null){
            select.append(join);
        }
        }else{
            select = new StringBuffer(customSQL);
        }
        if(where!=null){
        	select.append(where.sqlString());
        }
        return select.append(" ").append(super.sqlString()).toString();
    }

    /* (non-Javadoc)
     * @see com.jbeer.framework.db.sqlrunner.SubStatementSQL#isNeedResetLength()
     */
    @Override
    protected boolean isNeedResetLength() {
        return false;
    }

}
