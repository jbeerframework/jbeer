/**   
* @Title: BlockingPool.java
* @Package com.jbeer.framework.pool
* @author Bieber
* @date 2014-5-24 下午7:50:25
* @version V1.0   
*/

package com.jbeer.framework.pool;

import java.util.concurrent.TimeUnit;

/**
 * <p>类功能说明:阻塞的对象池</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: BlockingPool.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-24 下午7:50:25
 * @version V1.0
 */

public interface BlockingPool<T> extends Pool<T> {

	 /**
	  * 
	 * <p>函数功能说明:设置阻塞等待时间的获取接口</p>
	 * <p>Bieber  2014-5-24</p>
	 * <p>修改者名字 修改日期</p>
	 * <p>修改内容</a>  
	 * @return T
	  */
	public T get(long time,TimeUnit unit);
}
