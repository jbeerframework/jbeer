/**   
* @Title: SelectWhere.java
* @Package com.jbeer.framework.db.sqlrunner
* @author Bieber
* @date 2014年6月9日 上午9:47:30
* @version V1.0   
*/

package com.jbeer.framework.db.sqlrunner;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.jbeer.framework.db.sqlrunner.DefaultWhere.OperationType;
import com.jbeer.framework.db.type.TypeConverter;

/**
* <p>类功能说明:仅仅在select 子句的where 子句</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: SelectWhere.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月9日 上午9:47:30
* @version V1.0
*/

public class SelectWhere extends GroupByable implements Where{
    
    protected SelectWhere(SQL other,List<Object> params){
        this.executor=other;
        this.params = params;
    }
    public SelectWhere defaultWhere(String columnName,OperationType operationType,Object columnValue){
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
    
    public SelectWhere otherCondition(String condition){
        sqlStatement.append(" ").append(condition).append(" ");
        return this;
    }
    public SelectWhere and(){
        sqlStatement.append(" and ");
        return this;
    }
    
    public SelectWhere or(){
        sqlStatement.append(" or ");
        return this;
    }
    
    /**
     * 
    * <p>函数功能说明:用于表之间字段关联的条件拼装</p>
    * <p>Bieber  2014年6月10日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return SelectWhere
     */
    public SelectWhere joinTable(String atableColumnName,String btableColumnName){
        sqlStatement.append(" ").append(atableColumnName).append("=").append(btableColumnName).append(" ");
        return this;
    }
    
    public SelectWhere where(String columnName,OperationType operationType,int columnValue){
        return defaultWhere(columnName,operationType, columnValue);
    }
    public SelectWhere where(String columnName,OperationType operationType,short columnValue){
        return defaultWhere(columnName,operationType, columnValue);
    }
    public SelectWhere where(String columnName,OperationType operationType,long columnValue){
        return  defaultWhere(columnName,operationType, columnValue);
    }
    public SelectWhere where(String columnName,OperationType operationType,byte columnValue){
        return  defaultWhere(columnName,operationType, columnValue);
    }
    public SelectWhere where(String columnName,OperationType operationType,char columnValue){
        return  defaultWhere(columnName,operationType, columnValue);
    }
    public SelectWhere where(String columnName,OperationType operationType,String columnValue){
        return  defaultWhere(columnName,operationType, columnValue);
    }
    public SelectWhere where(String columnName,OperationType operationType,Date columnValue){
        return  defaultWhere(columnName,operationType, columnValue);
    }
    public SelectWhere where(String columnName,OperationType operationType,BigInteger columnValue){
        return  defaultWhere(columnName,operationType, columnValue);
    }
    public SelectWhere where(String columnName,OperationType operationType,BigDecimal columnValue){
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
