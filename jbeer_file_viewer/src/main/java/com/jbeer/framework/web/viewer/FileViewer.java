/**   
* @Title: FileViewer.java
* @Package com.jbeer.framework.web.viewer
* @author Bieber
* @date 2014年6月17日 下午12:55:27
* @version V1.0   
*/

package com.jbeer.framework.web.viewer;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import com.jbeer.framework.exception.JBeerException;
import com.jbeer.framework.plugin.Plugin;
import com.jbeer.framework.utils.ClassPathScanFileUtil;

/**
* <p>类功能说明:文件视图插件</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: FileViewer.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月17日 下午12:55:27
* @version V1.0
*/

public class FileViewer implements Plugin {

    private static Map<String,String> mineType = new HashMap<String,String>();
    
    public String getMineType(String extendName){
        return mineType.get(extendName);
    }
    /* (non-Javadoc)
     * @see com.jbeer.framework.plugin.Plugin#initialize()
     */
    public void initialize() throws JBeerException {
        try {
           Set<String> files =  ClassPathScanFileUtil.scanClassPathFile("minetype", ".properties", false);
           for(String file:files){
               Properties properties = new Properties();
               if(file.contains("mine_type.properties")){
                   InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
                   properties.load(is);
                   Set<Entry<Object,Object>> entrySet = properties.entrySet();
                   for(Entry<Object,Object> entry:entrySet){
                       mineType.put(entry.getKey().toString(), entry.getValue().toString());
                   }
                   properties.clear();
               }
           }
        } catch (Exception e) {
           throw new JBeerException("loading mine type file has a exception ", e);
        }
    }

    /* (non-Javadoc)
     * @see com.jbeer.framework.plugin.Plugin#destory()
     */
    public void destory() throws JBeerException {
        mineType.clear();
        mineType=null;
    }

}
