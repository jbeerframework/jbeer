/**   
* @Title: WebConfig.java
* @Package com.jbeer.framework.config
* @author Bieber
* @date 2014年5月29日 下午4:11:04
* @version V1.0   
*/

package com.jbeer.framework.config;

import com.jbeer.framework.JBeerWeb;

/**
* <p>类功能说明:mvc模块配置信息</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: WebConfig.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年5月29日 下午4:11:04
* @version V1.0
*/

public class WebConfig {
 
    public void setViewPrefix(String viewPrefix){
        JBeerWeb.setViewPrefix(viewPrefix);
    }
    
    public void setWebTempFileDir(String webTempFileDir){
        JBeerWeb.setWebTempFileDir(webTempFileDir);
    }
    
    
    public void setViewSuffix(String viewSuffix){
        JBeerWeb.setViewSuffix(viewSuffix);
    }
    
    public void isSingletonMode(boolean singletonMode){
    	JBeerWeb.setSingletonMode(singletonMode);
    }

}
