/**   
* @Title: LogUtil.java
* @Package com.jbeer.framework.utils
* @author Bieber
* @date 2014年6月2日 上午10:41:29
* @version V1.0   
*/

package com.jbeer.framework.utils;

import com.jbeer.framework.logging.Log;
import com.jbeer.framework.logging.LogFactory;
 

 
/**
* <p>类功能说明:统一logger实体生成工具</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: LogUtil.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月2日 上午10:41:29
* @version V1.0
*/

public class LoggerUtil {

    public static Log generateLogger(Class<?> clazz){
        return LogFactory.getLog(clazz);
    }
    
    public static Log generateLogger(String name){
        return LogFactory.getLog(name);
    }
    
}
