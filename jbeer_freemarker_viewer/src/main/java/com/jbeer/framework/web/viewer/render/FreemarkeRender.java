/**   
* @Title: FreemarkeRender.java
* @Package com.jbeer.framework.web.viewer.render
* @author Bieber
* @date 2014年6月17日 上午9:59:55
* @version V1.0   
*/

package com.jbeer.framework.web.viewer.render;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jbeer.framework.JBeer;
import com.jbeer.framework.JBeerWeb;
import com.jbeer.framework.annotation.RefBean;
import com.jbeer.framework.exception.RenderingViewException;
import com.jbeer.framework.web.ModelAndView;
import com.jbeer.framework.web.ModelAndView.ViewType;
import com.jbeer.framework.web.viewer.FreemarkeViewer;
import com.jbeer.framework.web.viewrender.Render;

import freemarker.template.Template;

/**
* <p>类功能说明:freemarke渲染器</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: FreemarkeRender.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月17日 上午9:59:55
* @version V1.0
*/

public class FreemarkeRender implements Render {

    @RefBean
    private FreemarkeViewer viewer;
    /* (non-Javadoc)
     * @see com.jbeer.framework.web.viewrender.Render#isSupport(com.jbeer.framework.web.ModelAndView)
     */
    public boolean isSupport(ModelAndView modelView) {
        if(ViewType.PAGE==modelView.getViewType()&&viewer.getFreemarkeConfig()!=null){
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
            Template template = viewer.getFreemarkeConfig().getTemplate(JBeerWeb.getViewPrefix()+modelView.getView()+JBeerWeb.getViewSuffix(), JBeer.getApplicationEncode());
            PrintWriter writer = response.getWriter();
            template.process(modelView.getDatas(), writer);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            throw new RenderingViewException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.jbeer.framework.web.viewrender.Render#order()
     */
    public int order() {
        return 1;
    }

}
