/**   
* @Title: JBeerIN18.java
* @Package com.jbeer.framework
* @author Bieber
* @date 2014年6月1日 上午10:54:15
* @version V1.0   
*/

package com.jbeer.framework;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jbeer.framework.constant.JBeerConstant;
import com.jbeer.framework.exception.JBeerException;
import com.jbeer.framework.logging.Log;
import com.jbeer.framework.message.MessageContext;
import com.jbeer.framework.utils.ClassPathScanFileUtil;
import com.jbeer.framework.utils.LoggerUtil;

/**
* <p>类功能说明:国际化模块</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: JBeerIN18.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月1日 上午10:54:15
* @version V1.0
*/

public class JBeerIN18 {

    private static final List<String> messageFiles = new ArrayList<String>();

    private static final Log logger = LoggerUtil.generateLogger(JBeerIN18.class);
    
    public synchronized static void addMessageFile(String messageFile) {
        if(JBeer.isStartup()){
            return;
        }
        messageFiles.add(messageFile);
    }

    public static void init() throws JBeerException {
        try {
            if(logger.isDebugEnabled()){
                logger.debug("initializing in18 module......");
            }
            String filePath = "";
            String fileName = null;
            int index = -1;
            String targetFileName = null;
            String localName = JBeerConstant.DEFAULT_LOCAL;
            Pattern pattern = Pattern.compile("(_[a-zA-Z]{0,}_[a-zA-Z]{0,}){1}\\.properties");
            String baseName = null;
            for (int i = 0; i < messageFiles.size(); i++) {
                String messageFile = messageFiles.get(i);
                index = messageFile.lastIndexOf("/");
                fileName = messageFile;
                if (index != -1) {
                    filePath = fileName.substring(0, index);
                    fileName = fileName.substring(index + 1);
                }
                fileName = fileName.replaceAll("\\*", "(\\\\w{0,}.{0,}){0,}");
                Set<String> messages = ClassPathScanFileUtil.scanClassPathFile(filePath,
                    ".properties", false);
                for (String message : messages) {
                    
                    filePath="";
                    targetFileName = message;
                    index = message.lastIndexOf("/");
                    if (index == -1) {
                        index = message.lastIndexOf("\\");
                    }
                    if (index != -1) {
                        targetFileName = message.substring(index + 1);
                        filePath = message.substring(0, index+1);
                    }
                    Matcher matcher = pattern.matcher(targetFileName);
                    if (matcher.find()) {
                        localName = matcher.group();
                        baseName = matcher.replaceAll("");
                        if (baseName.matches(fileName)) {
                            if(logger.isDebugEnabled()){
                                logger.debug("loading message file "+message);
                            }
                            localName = localName.replace(".properties", "");
                            localName = localName.substring(1);
                            Properties properties = new Properties();
                            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(message);
                            if(is!=null){
                            properties.load(is);
                            Set<Object> keys = properties.keySet();
                            Map<String, String> messageMap = new HashMap<String, String>();
                            for (Object key : keys) {
                                messageMap.put(key.toString(), properties.get(key).toString());
                            }
                            MessageContext.setMessage(localName, messageMap);
                            properties.clear();
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

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("(_[a-zA-Z]{0,}_[a-zA-Z]{0,}){1}\\.properties");
        String name = "fdfdfd_fdfdf_zh_CN.properties";
        Matcher matcher = pattern.matcher(name);
        if (matcher.find()) {
            System.out.println(matcher.group());
        }
    }

    public static void destory() {
        if(logger.isDebugEnabled()){
            logger.debug("destory in18 module ");
        }
        messageFiles.clear();
        
    }
}
