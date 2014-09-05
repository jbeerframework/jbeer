/**   
* @Title: JBeerWebContextListener.java
* @Package com.jbeer.framework.web
* @author Bieber
* @date 2014-2-15 下午03:25:03
* @version V1.0   
*/

package com.jbeer.framework.startup;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.jbeer.framework.JBeer;
import com.jbeer.framework.exception.JBeerException;
import com.jbeer.framework.logging.Log;
import com.jbeer.framework.utils.LoggerUtil;

/**
 * 类功能说明
 * 类修改者	    修改日期
 * 修改说明
 * <p>Title: JBeerWebContextListener.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-2-15 下午03:25:03
 * @Description: JBeer上下文监听器，用于将JBeer嵌入到Web容器中
 * @version V1.0
 */
@WebListener("JBeer上下文监听器，用于将JBeer嵌入到Web容器中")
public class JBeerWebContextListener implements ServletContextListener {

    
    private static final Log logger = LoggerUtil.generateLogger(JBeerWebContextListener.class);
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {
		try {
			JBeer.destoryFramework();
		} catch (JBeerException e) {
		    logger.error("fail to destory framework ", e);
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		try {
		    ServletContext context = event.getServletContext();
		   Enumeration<String> parameterNames = context.getInitParameterNames();
		  if(parameterNames!=null){
		      while(parameterNames.hasMoreElements()){
		        String name =  parameterNames.nextElement();
		        JBeer.setContextConfig(name, context.getInitParameter(name));
		      }
		  }
		 JBeer.initFramework4Web();
		} catch (JBeerException e) {
		    logger.error("fail to initializing framework ", e);
		}
	}

}
