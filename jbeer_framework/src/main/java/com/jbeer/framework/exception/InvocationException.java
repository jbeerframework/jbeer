/**   
* @Title: InvocationException.java
* @Package com.jbeer.framework.exception
* @author Bieber
* @date 2014年5月19日 上午10:23:33
* @version V1.0   
*/

package com.jbeer.framework.exception;

/**
* <p>类功能说明:调用异常类</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: InvocationException.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年5月19日 上午10:23:33
* @version V1.0
*/

public class InvocationException extends JBeerException {

    /**
    * @Fields serialVersionUID : TODO
    */
    
    private static final long serialVersionUID = 2169075691066290379L;

    public InvocationException() {
        super();
    }

    public InvocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvocationException(String message) {
        super(message);
    }

    public InvocationException(Throwable cause) {
        super(cause);
    }

}
