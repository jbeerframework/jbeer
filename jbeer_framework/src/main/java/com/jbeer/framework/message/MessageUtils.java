/**   
* @Title: MessageUtils.java
* @Package com.jbeer.framework.message
* @author Bieber
* @date 2014年6月1日 上午10:47:17
* @version V1.0   
*/

package com.jbeer.framework.message;

import java.util.Locale;
import java.util.Map;

import com.jbeer.framework.exception.MessageException;

/**
* <p>类功能说明:获取配置国际化信息的工具类</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: MessageUtils.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月1日 上午10:47:17
* @version V1.0
*/

public class MessageUtils {

    /**
     * 
    * <p>函数功能说明:获取默认区域的消息，并且没有参数</p>
    * <p>Bieber  2014年6月1日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return String
     */
    public static String getMessage(String name) throws MessageException{
      return MessageContext.getMessage(name,null,null);
    }
    
    public static Map<String,String> getMessages(String locale){
    	return MessageContext.localMessages.get(locale);
    }
    
    /**
     * 
    * <p>函数功能说明:获取默认区域的消息，带有参数</p>
    * <p>Bieber  2014年6月1日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return String
     */
    public static String getMessage(String name,Object[] args) throws MessageException{
        return MessageContext.getMessage(name,null,args);
      }
    
    /**
     * 
    * <p>函数功能说明:获取指定区域的消息，并且没有参数</p>
    * <p>Bieber  2014年6月1日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return String
     */
    public static String getMessage(String name,Locale locale) throws MessageException{
        return MessageContext.getMessage(name, locale,null);
    }
    /**
     * 
    * <p>函数功能说明:获取指定区域的消息，带有参数</p>
    * <p>Bieber  2014年6月1日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return String
     */
    public static String getMessage(String name,Locale locale,Object[] args) throws MessageException{
        return MessageContext.getMessage(name, locale, args);
    }
}
