/**   
* @Title: WSServlet.java
* @Package com.jbeer.framework.ws
* @author Bieber
* @date 2014年7月29日 上午7:50:49
* @version V1.0   
*/

package com.jbeer.framework.ws;

import javax.servlet.ServletConfig;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;

import com.jbeer.framework.exception.JBeerException;
import com.jbeer.framework.ioc.JBeerIOCContainerContext;
import com.jbeer.framework.logging.Log;
import com.jbeer.framework.utils.LoggerUtil;

/**
 * <p>类功能说明:ws启动servlet</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: WSServlet.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月29日 上午7:50:49
 * @version V1.0
 */

public class WSServlet extends CXFNonSpringServlet {

	/**
	* @Fields serialVersionUID : TODO
	*/
	
	private static final long serialVersionUID = -8619727926894525751L;
	
	private static final Log logger = LoggerUtil.generateLogger(WSServlet.class);

	/* (non-Javadoc)
	 * @see org.apache.cxf.transport.servlet.CXFNonSpringServlet#loadBus(javax.servlet.ServletConfig)
	 */
	@Override
	protected void loadBus(ServletConfig sc) {
		super.loadBus(sc);
		Bus bus = getBus();
		BusFactory.setDefaultBus(bus);
		try {
			WSPlugin plugin = JBeerIOCContainerContext.getBeanByType(WSPlugin.class);
			plugin.publishWebservice();
		} catch (JBeerException e) {
			if(logger.isDebugEnabled()){
				logger.debug("fail to publish webservice", e);
			}
		}
	}

}
