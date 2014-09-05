/**   
* @Title: AJAXRender.java
* @Package com.jbeer.framework.web.viewrender
* @author Bieber
* @date 2014-5-11 下午12:16:28
* @version V1.0   
*/

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
 * <p>类功能说明:TODO</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: AJAXRender.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-11 下午12:16:28
 * @version V1.0
 */

public class AJAXRender implements Render {
	 public void render(HttpServletRequest request,HttpServletResponse response,ModelAndView modelView) throws RenderingViewException{
	        try{
	            if(modelView.getContent()==null&&(modelView.getDatas()==null||modelView.getDatas().size()<=00)){
	                throw new RenderingViewException("未设置返回前端的数据，请设置相关数据....");
	            }
	            String retMsg = null;
	            if(modelView.getContent()!=null){
	            	retMsg = modelView.getContent();
	            }else{
	            	retMsg = JSON.writeToJson(modelView.getData()); 
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
        if(ViewType.AJAX==modelView.getViewType()){
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
