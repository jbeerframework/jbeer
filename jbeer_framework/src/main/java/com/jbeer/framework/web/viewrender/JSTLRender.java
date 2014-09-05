 
package com.jbeer.framework.web.viewrender;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jbeer.framework.JBeerWeb;
import com.jbeer.framework.exception.RenderingViewException;
import com.jbeer.framework.web.ModelAndView;
import com.jbeer.framework.web.ModelAndView.ViewType;

/**
 * 
 * @author bieber
 * @version $Id: JSTLRender.java, v 0.1 2014年4月24日 下午1:07:23 bieber Exp $
 */
public class JSTLRender implements Render{

    public void render(HttpServletRequest request,HttpServletResponse response,ModelAndView modelView) throws RenderingViewException{
        try{
        Map<String,Object> datas= modelView.getDatas();
        for(Entry<String,Object> entry :datas.entrySet()){
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        StringBuffer sb = new StringBuffer(JBeerWeb.getViewPrefix());
        sb.append(modelView.getView());
        sb.append(JBeerWeb.getViewSuffix());
        request.getRequestDispatcher(sb.toString()).forward(request, response);
        }catch(Exception e){
            throw new RenderingViewException(e);
        }
        }

    /* (non-Javadoc)
     * @see com.jbeer.framework.web.viewrender.Render#isSupport(com.jbeer.framework.web.ModelAndView)
     */
    @Override
    public boolean isSupport(ModelAndView modelView) {
        if(ViewType.PAGE==modelView.getViewType()){
            return true;
        }
        return false;
    }

    /* (non-Javadoc)
     * @see com.jbeer.framework.web.viewrender.Render#order()
     */
    @Override
    public int order() {
        return 0;
    }

   

    
}
