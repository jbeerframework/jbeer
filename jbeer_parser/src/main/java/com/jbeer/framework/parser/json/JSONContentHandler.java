/**   
* @Title: JSONContentHandler.java
* @Package com.jbeer.framework.parser.json
* @author Bieber
* @date 2014年7月15日 下午5:25:24
* @version V1.0   
*/

package com.jbeer.framework.parser.json;

import com.jbeer.framework.parser.ContentHandler;

/**
 * <p>类功能说明:JSON内容拦截器</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: JSONContentHandler.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月15日 下午5:25:24
 * @version V1.0
 */

public interface JSONContentHandler extends ContentHandler{

	/**
	 * 
	* <p>函数功能说明:开始解析数组</p>
	* <p>Bieber  2014年7月15日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	public void startArray();
	/**
	 * 
	* <p>函数功能说明:解析完毕一个数组</p>
	* <p>Bieber  2014年7月16日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	public void endArray();
	/**
	 * 
	* <p>函数功能说明:开始解析对象</p>
	* <p>Bieber  2014年7月15日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	public void startObject();
	/**
	 * 
	* <p>函数功能说明:解析完毕一个对象</p>
	* <p>Bieber  2014年7月16日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	public void endObject();
 
	/**
	 * 
	* <p>函数功能说明:开始解析值</p>
	* <p>Bieber  2014年7月15日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	public void value(String value);
	
	/**
	 * 
	* <p>函数功能说明:解析key值的是触发</p>
	* <p>Bieber  2014年7月16日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	public void key(String key);
}
