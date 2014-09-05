/**   
* @Title: TxAdvice.java
* @Package com.jbeer.framework.db.tx
* @author Bieber
* @date 2014年5月27日 上午11:34:29
* @version V1.0   
*/

package com.jbeer.framework.db.tx;

import java.lang.reflect.Method;

import com.jbeer.framework.annotation.Tx;
import com.jbeer.framework.bean.proxy.InvokeHandler;
import com.jbeer.framework.db.JBeerDBContext;
import com.jbeer.framework.logging.Log;
import com.jbeer.framework.plugin.AspectablePlugin;
import com.jbeer.framework.utils.LoggerUtil;

/**
* <p>类功能说明:事务增强类</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: TxAdvice.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年5月27日 上午11:34:29
* @version V1.0
*/

public class TxAdvice extends AspectablePlugin {
    

	private static final Log logger = LoggerUtil.generateLogger(TxAdvice.class);

    /* (non-Javadoc)
     * @see com.jbeer.framework.aop.Advice#before(com.jbeer.framework.bean.proxy.InvokeHandler)
     */
    @Override
    public void before(InvokeHandler handler) throws Throwable {
        //开启事务
    	Method targetMethod = handler.getTargetClass().getDeclaredMethod(handler.getInvokeMethod().getName(), handler.getInvokeMethod().getParameterTypes());
        Tx tx = targetMethod.getAnnotation(Tx.class);
        if (tx != null) {
            TranscationManager.initializeTranscationManager();
            TranscationDefinition definition = new TranscationDefinition(handler.getInvokeMethod()
                .getName());
            definition.setIsolation(tx.isolation());
            definition.setPropagation(tx.propagation());
            definition.setReadonly(tx.readonly());
            definition.setRollbackFor(tx.rollbackFor());
            TranscationManager.registTranscation(definition, JBeerDBContext.getDataSource());
        }
    }

    /* (non-Javadoc)
     * @see com.jbeer.framework.aop.Advice#after(com.jbeer.framework.bean.proxy.InvokeHandler, java.lang.Object)
     */
    @Override
    public void after(InvokeHandler handler, Object ret) throws Throwable {
        //提交事务
    	Method targetMethod = handler.getTargetClass().getDeclaredMethod(handler.getInvokeMethod().getName(), handler.getInvokeMethod().getParameterTypes());
        Tx tx = targetMethod.getAnnotation(Tx.class);
        if (tx != null) {
            TranscationStatus status = TranscationManager
                .getCurrentTranscation(JBeerDBContext.getDataSource());
            if (status != null) {
                status.setCompleted(true);
            }
            TranscationManager.commit(JBeerDBContext.getDataSource());
        }
    }

    /* (non-Javadoc)
     * @see com.jbeer.framework.aop.Advice#exception(com.jbeer.framework.bean.proxy.InvokeHandler, java.lang.Throwable)
     */
    @Override
    public void exception(InvokeHandler handler, Throwable e) throws Throwable {
        Method targetMethod = handler.getTargetClass().getDeclaredMethod(handler.getInvokeMethod().getName(), handler.getInvokeMethod().getParameterTypes());
        Tx tx = targetMethod.getAnnotation(Tx.class);
        if (tx != null) {
        	 //回滚事务
            TranscationStatus status = TranscationManager
                .getCurrentTranscation(JBeerDBContext.getDataSource());
            if(status==null){
            	return ;
            }
            Class<? extends Throwable>[] ts = tx.rollbackFor();
            if (ts != null && ts.length > 0) {
                for (Class<? extends Throwable> t : ts) {
                    if (t.isAssignableFrom(e.getClass())) {
                        if (status != null) {
                            status.setRollBack(true);
                        }
                        break;
                    }
                }
            }
            TranscationManager.commit(JBeerDBContext.getDataSource());
        }
    }

    /* (non-Javadoc)
     * @see com.jbeer.framework.plugin.Plugin#initialize()
     */
    @Override
    public void initialize() {
        if(logger.isDebugEnabled()){
            logger.debug("initialize tx plugin.....");
        }
    }

    /* (non-Javadoc)
     * @see com.jbeer.framework.plugin.Plugin#destory()
     */
    @Override
    public void destory() {
        if(logger.isDebugEnabled()){
            logger.debug("destory tx plugin.....");
        }
    }

    /* (non-Javadoc)
     * @see com.jbeer.framework.aop.Advice#isMatchedClass(java.lang.String)
     */
    @Override
    public boolean isMatchedClass(String classFullName) {
        return classFullName.matches(JBeerDBContext.getTxPointCut());
    }
}
