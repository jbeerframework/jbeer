/**   
* @Title: ProjectFileScaner.java
* @Package com.jbeer.framework.server.monitor
* @author Bieber
* @date 2014年6月19日 下午9:22:30
* @version V1.0   
*/

package com.jbeer.framework.server.monitor;

import java.util.Set;

/**
 * <p>类功能说明:扫描项目恩见抽象接口</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: ProjectFileScaner.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年6月19日 下午9:22:30
 * @version V1.0
 */

public interface ProjectFileScaner {

	
	/**
	 * 
	* <p>函数功能说明:扫描项目工程</p>
	* <p>Bieber  2014年6月19日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return Set<FileItem>
	 */
	public Set<FileItem> scanProject(String projectRoot);
}
