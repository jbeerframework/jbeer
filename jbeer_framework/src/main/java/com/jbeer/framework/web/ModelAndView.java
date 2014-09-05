/**   
* @Title: ModelView.java
* @Package com.jbeer.framework.web
* @author Bieber
* @date 2014-4-23 下午09:55:31
* @version V1.0   
*/

package com.jbeer.framework.web;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>类功能说明:返回的视图和模型</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: ModelView.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-4-23 下午09:55:31
 * @version V1.0
 */

public abstract class ModelAndView {

    protected String view;
    
    protected String callback;
    
    protected Map<String,Object> datas = new HashMap<String,Object>();
    
    protected ViewType type = ViewType.PAGE;
    
    protected Object data = null;
    
    protected String content;
    
    
    public static JSONModelAndView createJsonModel(){
        return new JSONModelAndView();
    }
    
    public static JSONPModelAndView createJsonpModel(){
        return new JSONPModelAndView();
    }
    
    public static PageModelAndView createModelAndView(){
        return new PageModelAndView();
    }
   
    public static AJAXModelAdnView createAJAXModelAdnView(){
    	return new AJAXModelAdnView();
    }
    
    protected ModelAndView setContent(String content){
    	this.content = content;
    	return this;
    }
    protected ModelAndView setView(String view){
        this.view = view.startsWith("/")?view:("/"+view);
        return this;
    }
    
    protected ModelAndView setCallback(String callback){
        this.callback = callback;
        return this;
    }
    
    
    protected ModelAndView setViewType(ViewType type){
       this.type = type;
       return this;
    }
    
    protected ModelAndView setDataMap(String key,Object data){
        datas.put(key, data);
        return this;
    }
    
    protected ModelAndView setData(Object data){
        this.data = data;
        return this;
    }
    

    public String getContent(){
    	return content;
    }
    public String getView(){
        return view;
    }
    
    public Map<String,Object> getDatas(){
        return datas;
    }
    
    public Object getData(){
        return data;
    }
    
    public String getCallback(){
        return callback;
    }
    public ViewType getViewType(){
        return type;
    }
    
    public static class JSONModelAndView extends ModelAndView{

        /* (non-Javadoc)
		 * @see com.jbeer.framework.web.ModelAndView#setDataMap(java.lang.String, java.lang.Object)
		 */
		@Override
		public ModelAndView setDataMap(String key, Object data) {
			return super.setDataMap(key, data);
		}
		public JSONModelAndView(){
            this.type = ViewType.JSON;
        }
        /** 
         * @see com.jbeer.framework.web.ModelAndView#setData(java.lang.Object)
         */
        @Override
        public JSONModelAndView setData(Object data) {
             this.data = data;
             return this;
        }
        
    }
    
    public static class JSONPModelAndView extends JSONModelAndView{
        public JSONPModelAndView(){
            this.type = ViewType.JSONP;
        }
        @Override
		public ModelAndView setDataMap(String key, Object data) {
			return super.setDataMap(key, data);
		}
        /** 
         * @see com.jbeer.framework.web.ModelAndView#setCallback(java.lang.String)
         */
        @Override
        public JSONPModelAndView setCallback(String callback) {
            this.callback = callback;
            return this;
        }

    }
    
    public static class AJAXModelAdnView extends ModelAndView{
    	public AJAXModelAdnView(){
    		this.type = ViewType.AJAX;
    	}
    	@Override
		public ModelAndView setDataMap(String key, Object data) {
			return super.setDataMap(key, data);
		}
		/* (non-Javadoc)
		 * @see com.jbeer.framework.web.ModelAndView#setContent(java.lang.String)
		 */
		@Override
		public ModelAndView setContent(String content) {
			this.content = content;
			 return this;
		}
    	
    	
    }
    
    
    
    public static class PageModelAndView extends ModelAndView{
        public PageModelAndView(){
            this.type = ViewType.PAGE;
        }

       
 
        /** 
         * @see com.jbeer.framework.web.ModelAndView#setView(java.lang.String)
         */
        @Override
        public PageModelAndView setView(String view) {
            this.view = view;
            return this;
        }

        /** 
         * @see com.jbeer.framework.web.ModelAndView#setDataMap(java.lang.String, java.lang.Object)
         */
        @Override
        public PageModelAndView setDataMap(String key, Object data) {
            this.datas.put(key, data);
            return this;
        }
        
    }
    
    public static enum ViewType{
        
        JSON("json"),PAGE("page"),FILE("file"),JSONP("jsonp"),AJAX("ajax");
        
        private String name;
        private ViewType(String name){
            this.name = name;
        }
        
        public String getName(){
            return name;
        }
        
    }
    
}

