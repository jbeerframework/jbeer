/**   
* @Title: BoundPool.java
* @Package com.jbeer.framework.pool
* @author Bieber
* @date 2014-5-24 下午8:27:34
* @version V1.0   
*/

package com.jbeer.framework.pool;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

/**
 * <p>类功能说明:非阻塞对象池</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: BoundPool.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-24 下午8:27:34
 * @version V1.0
 */

public final class BoundPool<T> extends AbstractPool<T> {
	
	/**
	 * 对象校验者
	 */
	private Validator<T> validator;
	/**
	 * 对象构造工厂
	 */
	private ObjectFactory<T> factory;
	/**
	 * 阻塞队列
	 */
	private Queue<T> queue;
	
	private Semaphore semaphore;
 
	/**
	 * 是否关闭池标识
	 */
	private volatile boolean isShutdown=false;
	
	private int initSize;
	
	public BoundPool(int size,int initSize,Validator<T> validator,ObjectFactory<T> factory){
		this.validator= validator;
		this.factory = factory;
		this.queue = new LinkedList<T>();
		semaphore = new Semaphore(initSize,true);
		this.initSize=initSize;
		initializObjects();
	}
	/**
	* <p>函数功能说明:初始化池</p>
	* <p>Bieber  2014-5-24</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void   
	*/
	
	private boolean initializObjects() {
		for(int i=0;i<initSize;i++){
			queue.add(factory.createObject());
		}
		return true;
	}
	/* (non-Javadoc)
	 * @see com.jbeer.framework.pool.Pool#get()
	 */
	@Override
	public T get() {
		if(!isShutdown){
			if(semaphore.tryAcquire()){
				return queue.poll();
			}
		}
		throw new IllegalStateException("Pool is already shutdown!");
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.pool.Pool#shutdown()
	 */
	@Override
	public boolean shutdown() {
		isShutdown=true;
		for(T t:queue){
			validator.invalidate(t);
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.pool.AbstractPool#returnToPool(java.lang.Object)
	 */
	@Override
	public boolean returnToPool(T t) {
		boolean added = queue.add(t);
		if(added){
		semaphore.release();
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.pool.AbstractPool#isValid(java.lang.Object)
	 */
	@Override
	public boolean isValid(T t) {
		return validator.isValid(t);
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.pool.AbstractPool#handleInvalidReturn(java.lang.Object)
	 */
	@Override
	public boolean handleInvalidReturn(T t) {
		return true;
	}

}
