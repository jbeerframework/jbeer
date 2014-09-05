/**   
* @Title: JBeerProperties.java
* @Package com.jbeer.framework
* @author Bieber
* @date 2014年6月1日 上午10:54:25
* @version V1.0   
*/

package com.jbeer.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import com.jbeer.framework.exception.JBeerException;
import com.jbeer.framework.logging.Log;
import com.jbeer.framework.properties.PropertiesContext;
import com.jbeer.framework.utils.ClassPathScanFileUtil;
import com.jbeer.framework.utils.LoggerUtil;

/**
* <p>类功能说明:配置信息模块</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: JBeerProperties.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月1日 上午10:54:25
* @version V1.0
*/

public class JBeerProperties {

    private static final Log logger = LoggerUtil.generateLogger(JBeerProperties.class);
    private static final List<String> propertiesFiles = new ArrayList<String>();

    public synchronized static void addPropertiesFile(String filePath) {
        if(JBeer.isStartup()){
            return;
        }
        propertiesFiles.add(filePath);
    }

    public static void init() throws JBeerException {
        try {
            if(logger.isDebugEnabled()){
                logger.debug("initializing properties module.....");
            }
            String filePath = "";
            String fileName = null;
            int index = -1;
            String targetFileName = null;
            Properties propertiesFile = new Properties();
            for (int i=0;i<propertiesFiles.size();i++) {
                String properties = propertiesFiles.get(i);
                index = properties.lastIndexOf("/");
                if(index==-1){
                    index = properties.lastIndexOf("\\");
                }
                fileName = properties;
                if (index != -1) {
                    filePath = properties.substring(0, index);
                    fileName = properties.substring(index + 1);
                }
                fileName = fileName.replaceAll("\\*", "\\\\w{0,}");
                Set<String> files = ClassPathScanFileUtil.scanClassPathFile(filePath,
                    ".properties", false);
                for (String file : files) {
                    
                    targetFileName = file;
                    index = file.lastIndexOf("/");
                    if(index==-1){
                        index=file.lastIndexOf("\\");
                    }
                    if (index != -1) {
                        targetFileName = file.substring(index + 1);
                    }
                    if (targetFileName.matches(fileName)) {
                        if(logger.isDebugEnabled()){
                            logger.debug("loading properties file "+file);
                        }
                        if(file.startsWith(File.separator)){
                        	file = file.substring(1);
                        	}
                        InputStream is = Thread.currentThread().getContextClassLoader()
                            .getResourceAsStream(file);
                        if(is==null){
                        	if(is==null){
                            is =new FileInputStream(new File(file));
                        	}
                        }
                        try {
                            propertiesFile.clear();
                            propertiesFile.load(is);
                            for (Entry<Object, Object> entry : propertiesFile.entrySet()) {
                                PropertiesContext.addProperty(entry.getKey().toString(), entry
                                    .getValue().toString());
                            }
                        } finally {
                            if(is!=null){
                            is.close();
                            }
                        }
                    }

                }
            }
        } catch (Exception e) {
            throw new JBeerException(e);
        }
    }

    public static void destory() {
        if(logger.isDebugEnabled()){
            logger.debug("destory properties module.....");
        }
        propertiesFiles.clear();
    }
}
