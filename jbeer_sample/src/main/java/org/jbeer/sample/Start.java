/**   
* @Title: Start.java
* @Package org.jbeer.sample
* @author Bieber
* @date 2014年6月19日 下午3:51:50
* @version V1.0   
*/

package org.jbeer.sample;

import com.jbeer.framework.server.IServer;
import com.jbeer.framework.server.jetty.JettyServer;

/**
* <p>类功能说明:TODO</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: Start.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月19日 下午3:51:50
* @version V1.0
*/

public class Start {

    /**
    * <p>函数功能说明:TODO</p>
    * <p>Bieber  2014年6月19日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void   
    */

    public static void main(String[] args) {
        IServer server = new JettyServer("src/main/webapp", 8081, "/");
        try { 
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
