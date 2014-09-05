/**   
* @Title: OrderByable.java
* @Package com.jbeer.framework.db.sqlrunner
* @author Bieber
* @date 2014年6月9日 上午10:23:15
* @version V1.0   
*/

package com.jbeer.framework.db.sqlrunner;

/**
* <p>类功能说明:TODO</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: OrderByable.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月9日 上午10:23:15
* @version V1.0
*/

public abstract class OrderByable extends SelectablePage{

    private OrderBy orderBy;
    
    public OrderBy orderBy(String columnName){
        if(orderBy==null){
            orderBy= new OrderBy(params, executor);
            sqlSubStatements.add(orderBy);
        }
        return orderBy.orderBy(columnName);
    }
    
    public OrderBy asc(String columnName){
        if(orderBy==null){
            orderBy= new OrderBy(params, executor);
            sqlSubStatements.add(orderBy);
        }
        return orderBy.asc(columnName);
    }
    
    public OrderBy desc(String columnName){
        if(orderBy==null){
            orderBy= new OrderBy(params, executor);
            sqlSubStatements.add(orderBy);
        }
        return orderBy.desc(columnName);
    }
}
