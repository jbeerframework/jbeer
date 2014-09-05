package com.jbeer.framework.web.viewrender;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jbeer.framework.JBeer;
import com.jbeer.framework.exception.RenderingViewException;
import com.jbeer.framework.parser.json.JSON;
import com.jbeer.framework.web.ModelAndView;
import com.jbeer.framework.web.ModelAndView.ViewType;

/**
 * 
 * @author bieber
 * @version $Id: JSONRender.java, v 0.1 2014年4月24日 下午5:10:38 bieber Exp $
 */
public class JSONRender implements Render{

    public void render(HttpServletRequest request,HttpServletResponse response,ModelAndView modelView) throws RenderingViewException{
        try{
            if(modelView.getData()==null&&(modelView.getDatas()==null||modelView.getDatas().size()<=00)){
                throw new RenderingViewException("未设置返回前端的数据，请设置相关数据....");
            }
            String retMsg = null;
            if(modelView.getData()!=null){
            	retMsg =JSON.writeToJson(modelView.getData()); 
            }else{
            	retMsg = JSON.writeToJson(modelView.getDatas()); 
            }
            response.setContentType("Content-Type:application/json;charset="+JBeer.getApplicationEncode());
            OutputStream writer = response.getOutputStream();
            writer.write(retMsg.getBytes());
            writer.flush();
            writer.close();
        }catch(Exception e){
            throw new RenderingViewException(e);
        }
        }

    /* (non-Javadoc)
     * @see com.jbeer.framework.web.viewrender.Render#isSupport(com.jbeer.framework.web.ModelAndView)
     */
    @Override
    public boolean isSupport(ModelAndView modelView) {
        if(ViewType.JSON==modelView.getViewType()){
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
