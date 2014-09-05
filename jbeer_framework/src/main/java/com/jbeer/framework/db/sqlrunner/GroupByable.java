/**   
* @Title: Groupable.java
* @Package com.jbeer.framework.db.sqlrunner
* @author Bieber
* @date 2014年6月9日 上午10:56:47
* @version V1.0   
*/

package com.jbeer.framework.db.sqlrunner;


/**
* <p>类功能说明:可以执行group by子句的抽象类</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: Groupable.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月9日 上午10:56:47
* @version V1.0
*/

public abstract class GroupByable extends Havingable{

    private GroupBy groupBy;
    
    public GroupBy groupBy(String columnName){
        if(groupBy==null){
            groupBy = new GroupBy(params, executor);
            sqlSubStatements.add(groupBy);
        }
        return groupBy.groupBy(columnName);
    }
    

}
