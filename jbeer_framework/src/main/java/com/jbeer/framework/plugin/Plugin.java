/**   
* @Title: Plugin.java
* @Package com.jbeer.framework.plugin
* @author Bieber
* @date 2014年6月13日 下午1:09:18
* @version V1.0   
*/

package com.jbeer.framework.plugin;

import com.jbeer.framework.bean.proxy.MustCGLIBProxy;
import com.jbeer.framework.exception.JBeerException;

/**
* <p>类功能说明:插件接口</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: Plugin.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月13日 下午1:09:18
* @version V1.0
*/

public interface Plugin extends MustCGLIBProxy{

    /**
     * 
    * <p>函数功能说明:插件初始化</p>
    * <p>Bieber  2014年6月13日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     */
    public void initialize() throws JBeerException;
    
    /**
     * 
    * <p>函数功能说明:插件初始化</p>
    * <p>Bieber  2014年6月13日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     */
    public void destory() throws JBeerException;
    
  
    
}
