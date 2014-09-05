/**   
* @Title: VelocityViewer.java
* @Package com.jbeer.framework.web.viewer
* @author Bieber
* @date 2014年6月17日 上午10:14:30
* @version V1.0   
*/

package com.jbeer.framework.web.viewer;

import org.apache.velocity.app.Velocity;

import com.jbeer.framework.JBeer;
import com.jbeer.framework.exception.JBeerException;
import com.jbeer.framework.logging.Log;
import com.jbeer.framework.plugin.Plugin;
import com.jbeer.framework.utils.LoggerUtil;
import com.jbeer.framework.utils.StringUtils;

/**
* <p>类功能说明:velocityViewer</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: VelocityViewer.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月17日 上午10:14:30
* @version V1.0
*/

public class VelocityViewer implements Plugin{

    private final static Log logger = LoggerUtil.generateLogger(VelocityViewer.class);
    
    public void initialize() throws JBeerException {
        String velocityProperties = JBeer.getContextConfig("velocityProperties");
        if(logger.isDebugEnabled()){
            logger.debug("initialize velocity viewer....");
        }
        if (StringUtils.isEmpty(velocityProperties)) {
            Velocity.init();
        } else {
            Velocity.init(velocityProperties);
        }
    }

    public void destory() throws JBeerException {
        if(logger.isDebugEnabled()){
            logger.debug("destory velocity viewer....");
        }
    }

}
