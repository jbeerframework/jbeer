/**   
* @Title: FreemarkeViewer.java
* @Package com.jbeer.framework.web.viewer
* @author Bieber
* @date 2014年6月17日 上午9:57:50
* @version V1.0   
*/

package com.jbeer.framework.web.viewer;

import java.io.File;
import java.io.IOException;

import com.jbeer.framework.JBeerWeb;
import com.jbeer.framework.exception.GenerateActionException;
import com.jbeer.framework.exception.JBeerException;
import com.jbeer.framework.logging.Log;
import com.jbeer.framework.plugin.Plugin;
import com.jbeer.framework.utils.LoggerUtil;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

/**
* <p>类功能说明:freemarke视图插件</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: FreemarkeViewer.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月17日 上午9:57:50
* @version V1.0
*/

public class FreemarkeViewer implements Plugin {

    private Configuration freemarkerConfig=null;
    
    private static final Log logger = LoggerUtil.generateLogger(FreemarkeViewer.class);
    
    public Configuration getFreemarkeConfig(){
        return freemarkerConfig;
    }
    /* (non-Javadoc)
     * @see com.jbeer.framework.plugin.Plugin#initialize()
     */
    public void initialize() throws GenerateActionException {
        freemarkerConfig = new Configuration();
        if(logger.isDebugEnabled()){
            logger.debug("initialize freemarke viewer....");
        }
        try {
            freemarkerConfig.setDirectoryForTemplateLoading(new File(JBeerWeb.getViewPrefix()));
        } catch (IOException e) {
            throw new GenerateActionException(e);
        }
        freemarkerConfig.setObjectWrapper(new DefaultObjectWrapper());
    }

    /* (non-Javadoc)
     * @see com.jbeer.framework.plugin.Plugin#destory()
     */
    public void destory() throws JBeerException {
        if(logger.isDebugEnabled()){
            logger.debug("destory freemarke viewer....");
        }
        if(freemarkerConfig!=null){
        freemarkerConfig.clearTemplateCache();
        freemarkerConfig=null;
        }
    }

}
