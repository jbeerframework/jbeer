/**   
* @Title: WebMonitor.java
* @Package com.jbeer.framework.server.monitor
* @author Bieber
* @date 2014年6月19日 下午4:16:18
* @version V1.0   
*/

package com.jbeer.framework.server.monitor;

/**
* <p>类功能说明:web项目监控，用于监控项目代码变更，以支持变更后自动部署</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: WebMonitor.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月19日 下午4:16:18
* @version V1.0
*/

public interface ResourceMonitor {

    /**
     * 
    * <p>函数功能说明:开始监控项目资源变更</p>
    * <p>Bieber  2014年6月19日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     */
    public void start();
    
    /**
     * 
    * <p>函数功能说明:设置回调方法</p>
    * <p>Bieber  2014年6月19日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     */
    public void callback(ChangeCallBack callBack);
    
    /**
     * 
    * <p>函数功能说明:停止监听</p>
    * <p>Bieber  2014年6月19日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     */
    public void stop();
    
}
