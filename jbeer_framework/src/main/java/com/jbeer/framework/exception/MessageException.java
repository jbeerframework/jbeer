/**   
* @Title: MessageException.java
* @Package com.jbeer.framework.exception
* @author Bieber
* @date 2014年6月1日 上午10:25:47
* @version V1.0   
*/

package com.jbeer.framework.exception;

/**
* <p>类功能说明:Message产生异常类</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: MessageException.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月1日 上午10:25:47
* @version V1.0
*/

public class MessageException extends JBeerException {

    /**
    * @Fields serialVersionUID : TODO
    */
    
    private static final long serialVersionUID = 1439573620323702534L;

    

    public MessageException() {
    }

    

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }

    

    public MessageException(String message) {
        super(message);
    }

    

    public MessageException(Throwable cause) {
        super(cause);
    }

}
