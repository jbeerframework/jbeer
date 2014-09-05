/**   
* @Title: JBeerDB.java
* @Package com.jbeer.framework
* @author Bieber
* @date 2014年5月26日 上午10:43:42
* @version V1.0   
*/

package com.jbeer.framework;

import com.jbeer.framework.db.JBeerDBContext;
import com.jbeer.framework.exception.InitializationException;
import com.jbeer.framework.exception.ScanClassException;

/**
* <p>类功能说明:TODO</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: JBeerDB.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年5月26日 上午10:43:42
* @version V1.0
*/

public class JBeerDB {
    
    
    public static void init()
            throws ScanClassException, InitializationException {
    	JBeerDBContext.initializeDB();
    }
}
