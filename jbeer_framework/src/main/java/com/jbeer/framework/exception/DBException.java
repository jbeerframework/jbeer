/**   
* @Title: DBException.java
* @Package com.jbeer.framework.exception
* @author Bieber
* @date 2014年6月5日 下午4:39:47
* @version V1.0   
*/

package com.jbeer.framework.exception;

/**
* <p>类功能说明:数据库相关异常类</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: DBException.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月5日 下午4:39:47
* @version V1.0
*/

public class DBException extends JBeerException{

    /**
    * @Fields serialVersionUID : TODO
    */
    
    private static final long serialVersionUID = 5171560149177892369L;

    public DBException() {
        super();
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBException(String message) {
        super(message);
    }

    public DBException(Throwable cause) {
        super(cause);
    }

}
