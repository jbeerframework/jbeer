/**   
* @Title: GroupBy.java
* @Package com.jbeer.framework.db.sqlrunner
* @author Bieber
* @date 2014年6月9日 上午9:34:00
* @version V1.0   
*/

package com.jbeer.framework.db.sqlrunner;

import java.util.List;

/**
* <p>类功能说明:group by 子句</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: GroupBy.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月9日 上午9:34:00
* @version V1.0
*/

public class GroupBy extends Havingable {

    protected GroupBy(List<Object> params, SQL executor) {
        this.params=params;
        this.executor=executor;
    }
    
    public GroupBy groupBy(String columnName){
        if(sqlStatement==null){
            sqlStatement = new StringBuffer("group by ");
        }
        sqlStatement.append(columnName).append(",");
        return this;
    }

    /* (non-Javadoc)
     * @see com.jbeer.framework.db.sqlrunner.ExecuteSQL#isNeedResetLength()
     */
    @Override
    protected boolean isNeedResetLength() {
        return true;
    }


}
