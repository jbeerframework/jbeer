/**   
* @Title: Pool.java
* @Package com.jbeer.framework.pool
* @author Bieber
* @date 2014-5-24 下午7:35:50
* @version V1.0   
*/

package com.jbeer.framework.pool;

/**
 * <p>类功能说明:连接池的抽象接口</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: Pool.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-24 下午7:35:50
 * @version V1.0
 */

public interface Pool<T> {
	
	
	/**
	 * 
	* <p>函数功能说明:从池中获取一个有效对象</p>
	* <p>Bieber  2014-5-24</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return T
	 */
	public T get();

	/**
	 * 
	* <p>函数功能说明:将对象回收到池中</p>
	* <p>Bieber  2014-5-24</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return boolean
	 */
	public boolean release(T t);
	
	/**
	 * 
	* <p>函数功能说明:关闭对象池</p>
	* <p>Bieber  2014-5-24</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return boolean
	 */
	public boolean shutdown();
	/**
	 * <p>类功能说明:类的校验抽象接口</p>
	 * <p>类修改者	    修改日期</p>
	 * <p>修改说明</p>
	 * <p>Title: Validator.java</p>
	 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
	 * @date 2014-5-24 下午7:44:43
	 * @version V1.0
	 */

	public static interface Validator<T> {

		/**
		 * 
		* <p>函数功能说明:判断对象是否有效</p>
		* <p>Bieber  2014-5-24</p>
		* <p>修改者名字 修改日期</p>
		* <p>修改内容</a>  
		* @return boolean
		 */
		public boolean isValid(T t);
		/**
		 * 
		* <p>函数功能说明:将某个对象置为无效</p>
		* <p>Bieber  2014-5-24</p>
		* <p>修改者名字 修改日期</p>
		* <p>修改内容</a>  
		* @return void
		 */
		public void invalidate(T t);
	}
}
