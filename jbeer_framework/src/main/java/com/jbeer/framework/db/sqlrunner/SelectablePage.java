/**   
* @Title: SelectablePage.java
* @Package com.jbeer.framework.db.sqlrunner
* @author Bieber
* @date 2014年6月10日 上午9:13:20
* @version V1.0   
*/

package com.jbeer.framework.db.sqlrunner;

import com.jbeer.framework.exception.DBException;

/**
* <p>类功能说明:TODO</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: SelectablePage.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月10日 上午9:13:20
* @version V1.0
*/

public abstract class SelectablePage extends SelectableStatement{

    public Page4Select page(Integer pageSize,Integer currentPage) throws DBException{
        Page4Select page  = new Page4Select(this,params,pageSize,currentPage);
        sqlSubStatements.add(page);
        return page;
    }
}
