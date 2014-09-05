/**   
* @Title: BoundBlocingPool.java
* @Package com.jbeer.framework.pool
* @author Bieber
* @date 2014-5-24 下午7:58:08
* @version V1.0   
*/

package com.jbeer.framework.pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.jbeer.framework.logging.Log;
import com.jbeer.framework.utils.LoggerUtil;

/**
 * <p>类功能说明:阻塞式对象池实现类</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: BoundBlocingPool.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-24 下午7:58:08
 * @version V1.0
 */

public final class BoundBlockingPool<T> extends AbstractPool<T> implements BlockingPool<T> {

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
	private BlockingQueue<T> queue;
	/**
	 * 回收对象的执行者
	 */
	private ExecutorService executor = Executors.newCachedThreadPool();
	/**
	 * 是否关闭池标识
	 */
	private volatile boolean isShutdown=false;
	
	private int defaultTimeout;
	
	private int initSize;
	private static final Log logger = LoggerUtil.generateLogger(BoundBlockingPool.class);
	
	public BoundBlockingPool(int size,int initSize,Validator<T> validator,ObjectFactory<T> factory,int timeout){
		if(logger.isDebugEnabled()){
			logger.debug("initialize the pool");
		}
		this.validator= validator;
		this.factory = factory;
		this.queue = new LinkedBlockingQueue<T>(size);
		this.defaultTimeout=timeout;
		this.initSize = initSize;
		initializObjects();
	}
	private boolean initializObjects(){
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
		return get(defaultTimeout, TimeUnit.SECONDS);
	}


	/* (non-Javadoc)
	 * @see com.jbeer.framework.pool.Pool#shutdown()
	 */
	@Override
	public boolean shutdown() {
		isShutdown=true;
		executor.shutdownNow();
		for(T t:queue){
			validator.invalidate(t);
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.pool.BlockingPool#get(long, java.util.concurrent.TimeUnit)
	 */
	@Override
	public T get(long time, TimeUnit unit) {
		if(!isShutdown){
			T t = null;
			try{
				t = queue.poll(time, unit);
				return t;
			}catch(InterruptedException  e){
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
			return t;
		}
		throw new IllegalStateException("Pool is already shutdown!");
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.pool.AbstractPool#returnToPool(java.lang.Object)
	 */
	@Override
	public boolean returnToPool(T t) {
		if(validator.isValid(t)){
			executor.submit(new ReturnObject<T>(queue, t));
		}
		return false;
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

	private static class ReturnObject<T> implements Callable<T>{
		
		private BlockingQueue<T> queue;
		
		private T t;
		private ReturnObject(BlockingQueue<T> queue,T t){
			this.queue = queue;
			this.t= t;
		}

		/* (non-Javadoc)
		 * @see java.util.concurrent.Callable#call()
		 */
		@Override
		public T call() throws Exception {
			while(true){
				try{
				queue.put(t);
				break;
				}catch(InterruptedException e){
					if(logger.isDebugEnabled()){
						logger.debug("failed to return object", e);
					}
					Thread.currentThread().interrupt();
				}
			}
			return null;
		}
	}
	
}
