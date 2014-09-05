/**   
* @Title: FileController.java
* @Package org.jbeer.sample.controller
* @author Bieber
* @date 2014年7月18日 上午10:57:31
* @version V1.0   
*/

package org.jbeer.sample.controller;

import java.io.File;

import com.jbeer.framework.annotation.Action;
import com.jbeer.framework.annotation.Controller;
import com.jbeer.framework.enumeration.RequestType;
import com.jbeer.framework.exception.JBeerException;
import com.jbeer.framework.web.BaseController;

/**
 * <p>类功能说明:TODO</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: FileController.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月18日 上午10:57:31
 * @version V1.0
 */
@Controller
public class FileController  extends BaseController{

	@Action(urlPatterns="uploadFile.htm",requestType=RequestType.POST)
	public void uploadFile() throws JBeerException{
		File file = getFile("file");
		System.out.println(file);
	}
 
}
