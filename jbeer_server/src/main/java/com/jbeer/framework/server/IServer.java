/**   
* @Title: IServer.java
* @Package com.jbeer.framework.server
* @author Bieber
* @date 2014年6月19日 下午3:20:24
* @version V1.0   
*/

package com.jbeer.framework.server;

/**
* <p>类功能说明:服务的抽象接口</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: IServer.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月19日 下午3:20:24
* @version V1.0
*/

public interface IServer {

    /**
     * 
    * <p>函数功能说明:服务启动方法</p>
    * <p>Bieber  2014年6月19日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     * @throws Exception 
     */
    public void start() throws Exception;
    
    /**
     * 
    * <p>函数功能说明:服务停止方法</p>
    * <p>Bieber  2014年6月19日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     * @throws Exception 
     */
    public void stop() throws Exception;
}
