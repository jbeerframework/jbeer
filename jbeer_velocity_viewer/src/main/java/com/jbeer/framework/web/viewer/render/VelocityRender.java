/**   
* @Title: VelocityRender.java
* @Package com.jbeer.framework.web.viewer.render
* @author Bieber
* @date 2014年6月17日 上午10:33:41
* @version V1.0   
*/

package com.jbeer.framework.web.viewer.render;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.jbeer.framework.JBeer;
import com.jbeer.framework.JBeerWeb;
import com.jbeer.framework.exception.RenderingViewException;
import com.jbeer.framework.web.ModelAndView;
import com.jbeer.framework.web.ModelAndView.ViewType;
import com.jbeer.framework.web.viewrender.Render;

/**
* <p>类功能说明:TODO</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: VelocityRender.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月17日 上午10:33:41
* @version V1.0
*/

public class VelocityRender implements Render {

    /* (non-Javadoc)
     * @see com.jbeer.framework.web.viewrender.Render#isSupport(com.jbeer.framework.web.ModelAndView)
     */
    public boolean isSupport(ModelAndView modelView) {
        if(ViewType.PAGE==modelView.getViewType()){
            return true;
        }
        return false;
    }

    /* (non-Javadoc)
     * @see com.jbeer.framework.web.viewrender.Render#render(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.jbeer.framework.web.ModelAndView)
     */
    public void render(HttpServletRequest request, HttpServletResponse response,
                       ModelAndView modelView) throws RenderingViewException {
        try {
            VelocityContext context = new VelocityContext();
            Map<String,Object> datas = modelView.getDatas();
            for(Entry<String,Object> entry:datas.entrySet()){
                context.put(entry.getKey(), entry.getValue());
            }
            PrintWriter writer = response.getWriter();
            Template template = Velocity.getTemplate(JBeerWeb.getViewPrefix()+modelView.getView()+JBeerWeb.getViewSuffix(), JBeer.getApplicationEncode());
            template.merge(context, writer);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            throw new RenderingViewException(e);
        }    

    }

    public int order() {
        return 1;
    }

}
