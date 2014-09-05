/**   
* @Title: TranscationException.java
* @Package com.jbeer.framework.exception
* @author Bieber
* @date 2014年5月27日 下午2:01:11
* @version V1.0   
*/

package com.jbeer.framework.exception;

import com.jbeer.framework.db.tx.Propagation;

/**
* <p>类功能说明:TODO</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: TranscationException.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年5月27日 下午2:01:11
* @version V1.0
*/

public class TranscationException extends Exception {

    private Propagation propagation;
    
    
    /**
    * @return propagation
    */
    
    public Propagation getPropagation() {
        return propagation;
    }

    /**
    * @Fields serialVersionUID : TODO
    */
    
    private static final long serialVersionUID = 7693308173402993918L;

    /**
    * <p>Title: </p>
    * <p>Description: </p>
    */

    public TranscationException(Propagation propagetion) {
        this.propagation =propagetion;
    }

    public TranscationException(){
        
    }
    /**
    * <p>Title: </p>
    * <p>Description: </p>
    * @param arg0
    */

    public TranscationException(String arg0) {
        super(arg0);
    }

    /**
    * <p>Title: </p>
    * <p>Description: </p>
    * @param arg0
    */

    public TranscationException(Throwable arg0) {
        super(arg0);
    }

    /**
    * <p>Title: </p>
    * <p>Description: </p>
    * @param arg0
    * @param arg1
    */

    public TranscationException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

}
