/**   
* @Title: Pageable.java
* @Package com.jbeer.framework.db.sqlrunner
* @author Bieber
* @date 2014年6月9日 上午9:37:59
* @version V1.0   
*/

package com.jbeer.framework.db.sqlrunner;

import com.jbeer.framework.exception.DBException;

/**
* <p>类功能说明:TODO</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: Pageable.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月9日 上午9:37:59
* @version V1.0
*/

public abstract class Pageable extends SQLSubStatement {
    
    
    public BasePage page(Integer pageSize,Integer currentPage) throws DBException{
        BasePage page  = new BasePage(this,params,pageSize,currentPage);
        sqlSubStatements.add(page);
        return page;
    }
}
