/**   
* @Title: HAVING.java
* @Package com.jbeer.framework.db.sqlrunner
* @author Bieber
* @date 2014年6月9日 上午9:51:31
* @version V1.0   
*/

package com.jbeer.framework.db.sqlrunner;

import java.util.List;

import com.jbeer.framework.db.sqlrunner.DefaultWhere.OperationType;

/**
* <p>类功能说明:having 子句</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: HAVING.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月9日 上午9:51:31
* @version V1.0
*/

public class Having extends OrderByable {
 
    
    protected Having(List<Object> params, SQL executor,String functionName,String columnName,OperationType type,Object columnValue) {
        this.executor = executor;
        this.params = params;
        sqlStatement=new StringBuffer("having ");
        sqlStatement.append(functionName).append("(").append(columnName).append(")");
        this.params.add(columnValue);
        switch (type) {
            case EQUAL:
                sqlStatement.append(" ").append("=? ");
                break;
            case NOT_EQUAL:
                sqlStatement.append(" ").append("<>? ");
                break;
            case CREATE_THAN:
                sqlStatement.append(" ").append(">? ");
                break;
            case LESS_THAN:
                sqlStatement.append(" ").append("<? ");
                break;
            case LIKE:
                sqlStatement.append(" ").append(" like ? ");
                break;
            case CREATE_EQUAL:
                sqlStatement.append(" ").append(">=? ");
                break;
            case LESS_EQUAL:
                sqlStatement.append(" ").append("<=? ");
                break;
            default:
                break;
        }
    }

    /* (non-Javadoc)
     * @see com.jbeer.framework.db.sqlrunner.ExecuteSQL#isNeedResetLength()
     */
    @Override
    protected boolean isNeedResetLength() {
        return false;
    }

}
