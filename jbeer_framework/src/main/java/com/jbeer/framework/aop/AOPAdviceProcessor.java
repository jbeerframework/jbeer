/**   
* @Title: AOPAdvice.java
* @Package com.jbeer.framework.aop
* @author Bieber
* @date 2014年5月19日 下午1:58:22
* @version V1.0   
*/

package com.jbeer.framework.aop;

import com.jbeer.framework.bean.proxy.InvokeHandler;
import com.jbeer.framework.bean.proxy.ProxyTargetProcessor;

/**
* <p>类功能说明:TODO</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: AOPAdvice.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年5月19日 下午1:58:22
* @version V1.0
*/

public class AOPAdviceProcessor implements ProxyTargetProcessor {
    
    private ProxyTargetProcessor processor;
    
    private Advice advice;
    
    

    public AOPAdviceProcessor(ProxyTargetProcessor processor, Advice advice) {
        this.processor = processor;
        this.advice = advice;
    }
    public AOPAdviceProcessor(ProxyTargetProcessor processor) {
        this.processor = processor;
    }



    /* (non-Javadoc)
     * @see com.jbeer.framework.bean.proxy.ProxyTargetProcessor#invokeTarget(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
     */
    @Override
    public Object invokeTarget(Object target,InvokeHandler handler) throws Throwable {
       Object ret = null;
       if(advice!=null){
        try{
            advice.before(handler);
            ret =processor.invokeTarget(target, handler);
            advice.after(handler, ret);
        }catch(Exception e){
            advice.exception(handler, e);
            throw e;
        }
        }else{
            ret =processor.invokeTarget(target, handler);
        }
        return ret;
    }

}
