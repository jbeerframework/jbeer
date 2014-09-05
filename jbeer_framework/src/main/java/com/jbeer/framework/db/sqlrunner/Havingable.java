/**   
* @Title: Havingable.java
* @Package com.jbeer.framework.db.sqlrunner
* @author Bieber
* @date 2014年6月9日 上午10:47:15
* @version V1.0   
*/

package com.jbeer.framework.db.sqlrunner;

import com.jbeer.framework.db.sqlrunner.DefaultWhere.OperationType;


/**
* <p>类功能说明:TODO</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: Havingable.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月9日 上午10:47:15
* @version V1.0
*/

public abstract class Havingable extends OrderByable {

     
     public Having having(String functionName,String columnName,OperationType type,Object columnValue){
         Having having= new Having(params, executor, functionName, columnName, type, columnValue);
         sqlSubStatements.add(having);
         return having;
     }

}
