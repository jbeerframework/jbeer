/**   
* @Title: Where.java
* @Package com.jbeer.framework.db.sqlunit
* @author Bieber
* @date 2014年6月6日 下午4:29:04
* @version V1.0   
*/

package com.jbeer.framework.db.sqlrunner;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.jbeer.framework.db.type.TypeConverter;

/**
* <p>类功能说明:where sql子句</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: Where.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月6日 下午4:29:04
* @version V1.0
*/

public class DefaultWhere extends Pageable implements Where{

    
    public static enum OperationType{
      EQUAL,NOT_EQUAL,CREATE_THAN,LESS_THAN,LIKE,CREATE_EQUAL,LESS_EQUAL;
    }
    
    protected DefaultWhere(SQL other,List<Object> params){
        this.executor=other;
        this.params = params;
    }
    public DefaultWhere defaultWhere(String columnName,OperationType operationType,Object columnValue){
        if(sqlStatement==null){
            sqlStatement = new StringBuffer(" where");
        }
        params.add(TypeConverter.convertToJdbcType(columnValue));
        switch (operationType) {
            case EQUAL:
                sqlStatement.append(" ").append(columnName).append("=? ");
                break;
            case NOT_EQUAL:
                sqlStatement.append(" ").append(columnName).append("<>? ");
                break;
            case CREATE_THAN:
                sqlStatement.append(" ").append(columnName).append(">? ");
                break;
            case LESS_THAN:
                sqlStatement.append(" ").append(columnName).append("<? ");
                break;
            case LIKE:
                sqlStatement.append(" ").append(columnName).append(" like ? ");
                break;
            case CREATE_EQUAL:
                sqlStatement.append(" ").append(columnName).append(">=? ");
                break;
            case LESS_EQUAL:
                sqlStatement.append(" ").append(columnName).append("<=? ");
                break;
            default:
                break;
        }
        return this;
    }
    
    public DefaultWhere otherCondition(String condition){
        sqlStatement.append(" ").append(condition).append(" ");
        return this;
    }
    public DefaultWhere and(){
        sqlStatement.append(" and ");
        return this;
    }
    
    public DefaultWhere or(){
        sqlStatement.append(" or ");
        return this;
    }
    
    
    public DefaultWhere where(String columnName,OperationType operationType,int columnValue){
        return defaultWhere(columnName,operationType, columnValue);
    }
    public DefaultWhere where(String columnName,OperationType operationType,short columnValue){
        return defaultWhere(columnName,operationType, columnValue);
    }
    public DefaultWhere where(String columnName,OperationType operationType,long columnValue){
        return  defaultWhere(columnName,operationType, columnValue);
    }
    public DefaultWhere where(String columnName,OperationType operationType,byte columnValue){
        return  defaultWhere(columnName,operationType, columnValue);
    }
    public DefaultWhere where(String columnName,OperationType operationType,char columnValue){
        return  defaultWhere(columnName,operationType, columnValue);
    }
    public DefaultWhere where(String columnName,OperationType operationType,String columnValue){
        return  defaultWhere(columnName,operationType, columnValue);
    }
    public DefaultWhere where(String columnName,OperationType operationType,Date columnValue){
        return  defaultWhere(columnName,operationType, columnValue);
    }
    public DefaultWhere where(String columnName,OperationType operationType,BigInteger columnValue){
        return  defaultWhere(columnName,operationType, columnValue);
    }
    public DefaultWhere where(String columnName,OperationType operationType,BigDecimal columnValue){
        return  defaultWhere(columnName,operationType, columnValue);
    }


    /* (non-Javadoc)
     * @see com.jbeer.framework.db.sqlrunner.ExecuteSQL#isNeedResetLength()
     */
    @Override
    protected boolean isNeedResetLength() {
        return false;
    }
}
