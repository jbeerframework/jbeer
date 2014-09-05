/**   
* @Title: PropertiesConfig.java
* @Package com.jbeer.framework.config
* @author Bieber
* @date 2014年6月1日 下午3:09:12
* @version V1.0   
*/

package com.jbeer.framework.config;

import com.jbeer.framework.JBeerProperties;

/**
* <p>类功能说明:加载配置信息配置类</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: PropertiesConfig.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月1日 下午3:09:12
* @version V1.0
*/

public class PropertiesConfig {

    public void setPropertiesPath(String properties){
        JBeerProperties.addPropertiesFile(properties);
    }
}
