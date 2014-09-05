/**   
* @Title: OrderBy.java
* @Package com.jbeer.framework.db.sqlrunner
* @author Bieber
* @date 2014年6月9日 上午9:44:47
* @version V1.0   
*/

package com.jbeer.framework.db.sqlrunner;

import java.util.List;

/**
* <p>类功能说明:order by子句</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: OrderBy.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月9日 上午9:44:47
* @version V1.0
*/

public class OrderBy extends Pageable {

    protected OrderBy(List<Object> params,SQL executor){
        this.executor = executor;
        this.params = params;
    }
    
    public OrderBy orderBy(String columnName){
        if(sqlStatement==null){
            sqlStatement = new StringBuffer("order by ");
        }
        sqlStatement.append(columnName).append(",");
        return this;
    }
    
    public OrderBy asc(String columnName){
        if(sqlStatement==null){
            sqlStatement = new StringBuffer("order by ");
        }
        sqlStatement.append(columnName).append(" asc,");
        return this;
    }
    public OrderBy desc(String columnName){
        if(sqlStatement==null){
            sqlStatement = new StringBuffer("order by ");
        }
        sqlStatement.append(columnName).append(" desc,");
        return this;
    }

    /* (non-Javadoc)
     * @see com.jbeer.framework.db.sqlrunner.SubStatementSQL#isNeedResetLength()
     */
    @Override
    protected boolean isNeedResetLength() {
        return true;
    }

}
