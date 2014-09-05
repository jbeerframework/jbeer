/**   
* @Title: PropertiesTest.java
* @Package com.jbeer.framework
* @author Bieber
* @date 2014年6月1日 上午9:18:37
* @version V1.0   
*/

package com.jbeer.framework;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

/**
* <p>类功能说明:TODO</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: PropertiesTest.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月1日 上午9:18:37
* @version V1.0
*/

public class PropertiesTest {

    public static void main(String[] args) throws IOException{
        Properties proper = new Properties();
        Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources("/com");
        System.out.println(urls);
        while(urls.hasMoreElements()){
            System.out.println("fdfadsf");
           URL url =  urls.nextElement();
           System.out.println(url.getPath());
        }
        /*proper.load(PropertiesTest.class.getResourceAsStream("/imageio/plugins/common/iio-plugin.properties"));
        System.out.println(proper);*/
    }
}
