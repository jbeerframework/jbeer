/**   
* @Title: AbstractPool.java
* @Package com.jbeer.framework.pool
* @author Bieber
* @date 2014-5-24 下午7:38:19
* @version V1.0   
*/

package com.jbeer.framework.pool;

import com.jbeer.framework.logging.Log;
import com.jbeer.framework.utils.LoggerUtil;

/**
 * <p>类功能说明:TODO</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: AbstractPool.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-24 下午7:38:19
 * @version V1.0
 */

public abstract class AbstractPool<T> implements Pool<T> {

	private static final Log logger = LoggerUtil.generateLogger(AbstractPool.class);
	/* 
	 * 回收对象，如果对象有效将执行
	 * 先检查该对象是否有效，如果有效将放回池中，如果无效将触发异常处理
	 */
	@Override
	public final boolean release(T t) {
		if(isValid(t)){
			if(logger.isDebugEnabled()){
				logger.debug("return object "+t+" back to the pool");
			}
			return returnToPool(t);
		}else {
			return handleInvalidReturn(t);
		}
	}
	
	public abstract boolean returnToPool(T t);
	
	public abstract boolean isValid(T t);
	
	public abstract boolean handleInvalidReturn(T t);
}
