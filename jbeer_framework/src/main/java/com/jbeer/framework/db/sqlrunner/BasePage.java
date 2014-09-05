/**   
* @Title: Limit.java
* @Package com.jbeer.framework.db.sqlrunner
* @author Bieber
* @date 2014年6月9日 上午9:13:28
* @version V1.0   
*/

package com.jbeer.framework.db.sqlrunner;

import java.util.List;

import com.jbeer.framework.exception.DBException;

/**
* <p>类功能说明:分页SQL</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: Limit.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月9日 上午9:13:28
* @version V1.0
*/

public class BasePage extends SQLSubStatement {

    /**
     * 
    * <p>Title:进行分页，从1页开始 </p>
    * <p>Description: </p>
    * @param executor
    * @param params
    * @param pageSize
    * @param currentPage
    * @throws DBException
     */
    protected BasePage(SQL executor,List<Object> params,Integer pageSize,Integer currentPage) throws DBException{
        this.executor = executor;
        if(currentPage<=0){
            throw new DBException("currentPage must create than 0"+currentPage+"<1");
        }
        if(sqlStatement==null){
            sqlStatement = new StringBuffer();
        }
        sqlStatement.append("limit").append(" ?,?");
        this.params=params;
        this.params.add(currentPage*pageSize);
        this.params.add(pageSize);
    }
    
    /* (non-Javadoc)
     * @see com.jbeer.framework.db.sqlrunner.SQLSubStatement#isNeedResetLength()
     */
    @Override
    protected boolean isNeedResetLength() {
        return false;
    }

}
