/**   
* @Title: Where.java
* @Package com.jbeer.framework.db.sqlrunner
* @author Bieber
* @date 2014年6月9日 下午12:46:26
* @version V1.0   
*/

package com.jbeer.framework.db.sqlrunner;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.jbeer.framework.db.sqlrunner.DefaultWhere.OperationType;

/**
* <p>类功能说明:where 子句接口</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: Where.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月9日 下午12:46:26
* @version V1.0
*/

public interface Where {
    
    /**
     * 
    * <p>函数功能说明:整型条件</p>
    * <p>Bieber  2014年6月9日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return Where
     */
    public Where where(String columnName,OperationType operationType,int columnValue);
    /**
     * 
    * <p>函数功能说明:短整型条件</p>
    * <p>Bieber  2014年6月9日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return Where
     */
    public Where where(String columnName,OperationType operationType,short columnValue);
    /**
     * 
    * <p>函数功能说明:长整型条件</p>
    * <p>Bieber  2014年6月9日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return Where
     */
    public Where where(String columnName,OperationType operationType,long columnValue);
    /**
     * 
    * <p>函数功能说明:字节整型条件</p>
    * <p>Bieber  2014年6月9日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return Where
     */
    public Where where(String columnName,OperationType operationType,byte columnValue);
    /**
     * 
    * <p>函数功能说明:字符条件</p>
    * <p>Bieber  2014年6月9日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return Where
     */
    public Where where(String columnName,OperationType operationType,char columnValue);
    /**
     * 
    * <p>函数功能说明:字符串条件</p>
    * <p>Bieber  2014年6月9日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return Where
     */
    public Where where(String columnName,OperationType operationType,String columnValue);
    /**
     * 
    * <p>函数功能说明:日期型条件</p>
    * <p>Bieber  2014年6月9日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return Where
     */
    public Where where(String columnName,OperationType operationType,Date columnValue);
    /**
     * 
    * <p>函数功能说明:大整型条件</p>
    * <p>Bieber  2014年6月9日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return Where
     */
    public Where where(String columnName,OperationType operationType,BigInteger columnValue);
    /**
     * 
    * <p>函数功能说明:大数条件</p>
    * <p>Bieber  2014年6月9日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return Where
     */
    public Where where(String columnName,OperationType operationType,BigDecimal columnValue);
}
