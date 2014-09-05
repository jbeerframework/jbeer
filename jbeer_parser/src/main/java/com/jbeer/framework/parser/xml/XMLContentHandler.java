/**   
* @Title: EventHandler.java
* @Package com.jbeer.framework.parser.xml
* @author Bieber
* @date 2014年7月12日 下午4:49:24
* @version V1.0   
*/

package com.jbeer.framework.parser.xml;

import com.jbeer.framework.parser.ContentHandler;

/**
 * <p>类功能说明:xml解析器触发器</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: EventHandler.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月12日 下午4:49:24
 * @version V1.0
 */

public interface XMLContentHandler extends ContentHandler{
	/**
	 * 
	* <p>函数功能说明:开始解析文档</p>
	* <p>Bieber  2014年7月12日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	public void startDocument();

	/**
	 * 
	* <p>函数功能说明:开始解析标签触发的方法</p>
	* <p>Bieber  2014年7月12日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	public void tagStart(String name);
	
	/**
	 * 
	* <p>函数功能说明:解析完毕一个标签触发的方法</p>
	* <p>Bieber  2014年7月12日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	public void tagEnd(String name);
	
	/**
	 * 
	* <p>函数功能说明:解析完毕一个属性触发的方法</p>
	* <p>Bieber  2014年7月12日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	public void attribute(String name,String value);
	
	/**
	 * 
	* <p>函数功能说明:解析完毕一个注释触发的方法</p>
	* <p>Bieber  2014年7月12日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	public void comments(String content);
	
	/**
	 * 
	* <p>函数功能说明:解析文本触发的方法</p>
	* <p>Bieber  2014年7月12日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	public void text(String content);
	
	/**
	 * 
	* <p>函数功能说明:解析完毕该文档触发的方法</p>
	* <p>Bieber  2014年7月12日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	public void endDocument();
	
	
}
