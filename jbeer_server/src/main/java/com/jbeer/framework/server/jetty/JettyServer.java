/**   
 * @Title: JettyServer.java
 * @Package com.jbeer.framework.server.jetty
 * @author Bieber
 * @date 2014年6月19日 下午3:35:48
 * @version V1.0   
 */

package com.jbeer.framework.server.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import com.jbeer.framework.logging.Log;
import com.jbeer.framework.server.IServer;
import com.jbeer.framework.server.monitor.ChangeCallBack;
import com.jbeer.framework.server.monitor.ResourceMonitor;
import com.jbeer.framework.server.monitor.SimpleResourceMonitor;
import com.jbeer.framework.utils.LoggerUtil;

/**
 * <p>
 * 类功能说明:Jetty服务
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: JettyServer.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年6月19日 下午3:35:48
 * @version V1.0
 */

public class JettyServer implements IServer {

	//应用上下文
	private WebAppContext webAppContext;

	private Server server;

	private String webappDir = "src/main/webapp";

	private String context = "/";

	private int port = 8080;

	private boolean running = false;

	private long invertalTime;

	//项目资源变更监听器
	private ResourceMonitor monitor;
	
	private int maxFormContentSize = 1024*1024*2;//默认是2m
	
	private static final Log logger = LoggerUtil.generateLogger(JettyServer.class);
			

	/**
	 * 
	* <p>Title: 配置全部参数来设置整个服务的状态，并且设置自动扫描项目文件变更周期时间，以自动部署</p>
	* <p>Description: </p>
	* @param webAppDir
	* @param port
	* @param context
	* @param invertalTime
	 */
	public JettyServer(String webAppDir, int port, String context,int maxFormContentSize,
			long invertalTime) {
		this.webappDir = webAppDir;
		this.port = port;
		this.context = context;
		this.invertalTime = invertalTime;
		this.maxFormContentSize = maxFormContentSize;
		init();
	}
	/**
     * 
    * <p>Title: 启动服务，该方式不启用监听项目文件变更</p>
    * <p>Description: </p>
     */
    public JettyServer(int port) {
		this.port = port;
        init();
    }
	/**
     * 
    * <p>Title: 启动服务，该方式不启用监听项目文件变更</p>
    * <p>Description: </p>
     */
    public JettyServer(String webAppDir, int port, String context,int maxFormContentSize) {
    	this.webappDir = webAppDir;
		this.port = port;
		this.context = context;
		this.maxFormContentSize = maxFormContentSize;
        init();
    }
    /**
     * 
    * <p>Title: 启动服务，该方式不启用监听项目文件变更</p>
    * <p>Description: </p>
     */
    public JettyServer(String webAppDir, int port, String context) {
    	this.webappDir = webAppDir;
		this.port = port;
		this.context = context;
        init();
    }
    
    
	/**
	 * 
	* <p>Title: 启动服务，该方式不启用监听项目文件变更</p>
	* <p>Description: </p>
	 */
	public JettyServer() {
		init();
	}
	/**
	 * 
	* <p>Title: 启动服务，通过设置监听周期，定时监听项目文件变更，以达到重新部署项目</p>
	* <p>Description: </p>
	 */
	public JettyServer(int maxFormContentSize,long invertalTime) {
		this.invertalTime = invertalTime;
		this.maxFormContentSize = maxFormContentSize;
		init();
	}
	/**
	 * 
	* <p>Title: 启动服务，通过设置监听周期，定时监听项目文件变更，以达到重新部署项目</p>
	* <p>Description: </p>
	 */
	public JettyServer(long invertalTime) {
		this.invertalTime = invertalTime;
		init();
	}

	private void init() {
		webAppContext = new WebAppContext(webappDir, context);
		webAppContext.setMaxFormContentSize(maxFormContentSize);
		if (this.invertalTime > 0) {
			monitor = new SimpleResourceMonitor(this.invertalTime);
			monitor.callback(new ChangeCallBack() {

				public void hasChange() {
					try {
						webAppContext.stop();
						webAppContext.start();
					} catch (Exception e) {
						if(logger.isDebugEnabled()){
							logger.debug("failed to redeploy", e);
						}
					}
				}
			});
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jbeer.framework.server.IServer#start()
	 */
	public void start() throws Exception {
		if (!running) {
			server = new Server(this.port);
			server.setHandler(webAppContext);
			if(monitor!=null){
			monitor.start();
			}
			Runtime.getRuntime().addShutdownHook(new Thread(){

				@Override
				public void run() {
				 try {
					server.stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
				}
				
			});
			server.start();
			server.join();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jbeer.framework.server.IServer#stop()
	 */
	public void stop() throws Exception {
		if (running) {
			server.stop();
			if(monitor!=null){
			monitor.stop();
			}
		}
	}

}
