/**   
* @Title: Render.java
* @Package com.jbeer.framework.web.viewrender
* @author Bieber
* @date 2014年6月13日 下午1:33:34
* @version V1.0   
*/

package com.jbeer.framework.web.viewrender;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jbeer.framework.exception.RenderingViewException;
import com.jbeer.framework.web.ModelAndView;

/**
* <p>类功能说明:渲染器接口</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: Render.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月13日 下午1:33:34
* @version V1.0
*/

public interface Render {
    

    /**
     * 
    * <p>函数功能说明:当一个容器中有多个render的时候，order的值高的，将会优先调用其来进行渲染</p>
    * <p>Bieber  2014年6月17日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return int
     */
    public int order();
    /**
     * 
    * <p>函数功能说明:是否支持当前数据模型和视图渲染</p>
    * <p>Bieber  2014年6月13日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return boolean
     */
    public boolean isSupport(ModelAndView modelView);
    
    /**
     * 
    * <p>函数功能说明:进行渲染操作</p>
    * <p>Bieber  2014年6月13日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     */
    public void render(HttpServletRequest request, HttpServletResponse response,
                              ModelAndView modelView) throws RenderingViewException;
}
