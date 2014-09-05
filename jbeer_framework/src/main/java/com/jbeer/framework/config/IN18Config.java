/**   
* @Title: IN18Config.java
* @Package com.jbeer.framework.config
* @author Bieber
* @date 2014年6月1日 下午3:08:51
* @version V1.0   
*/

package com.jbeer.framework.config;

import com.jbeer.framework.JBeerIN18;

/**
* <p>类功能说明:国际化配置信息</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: IN18Config.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月1日 下午3:08:51
* @version V1.0
*/

public class IN18Config {

    
    public void setBaseName(String baseName){
        JBeerIN18.addMessageFile(baseName);
    }
}
