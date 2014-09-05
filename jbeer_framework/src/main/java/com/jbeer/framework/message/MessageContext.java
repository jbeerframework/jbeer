/**   
* @Title: MessageContext.java
* @Package com.jbeer.framework.message
* @author Bieber
* @date 2014年6月1日 上午10:17:36
* @version V1.0   
*/

package com.jbeer.framework.message;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.jbeer.framework.constant.JBeerConstant;
import com.jbeer.framework.exception.MessageException;
import com.jbeer.framework.web.JBeerWebContext;

/**
* <p>类功能说明:国际化消息上下文</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: MessageContext.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月1日 上午10:17:36
* @version V1.0
*/

public final class MessageContext {
    
    
    protected static final Map<String,Map<String,String>> localMessages = new HashMap<String,Map<String,String>>();
    
    
    public synchronized static void setMessage(String localName,Map<String,String> messages){
        if(localMessages.containsKey(localName)){
        	localMessages.get(localName).putAll(messages);
            return ;
        }else{
            localMessages.put(localName, messages);
        }
    }
    
    private static String getCurrentLocale(){
        HttpServletRequest request = JBeerWebContext.getRequest();
        String localeName = JBeerConstant.DEFAULT_LOCAL;
        if(request!=null){
            localeName = request.getLocale().toString();
        }
        return localeName;
    }
    protected static String getMessage(String name,Locale locale,Object[] args) throws MessageException{
        String localName = getCurrentLocale();
        if(locale!=null){
            localName = locale.toString();
        }
        Map<String,String> currentLocalMessage = localMessages.get(localName);
        if(currentLocalMessage==null){
        	currentLocalMessage=localMessages.get(JBeerConstant.DEFAULT_LOCAL);
        	if(currentLocalMessage==null){
            throw new MessageException("did not found message which name is "+name+" in _"+locale.toString()+" message properties file");
        	}
        }
        if(args!=null&&args.length>0){
            String message = currentLocalMessage.get(name);
            Pattern pattern = Pattern.compile("\\{[0-9]{1,}\\}");
            Matcher matcher = pattern.matcher(message);
            return String.format(matcher.replaceAll("%s"), args);
        }else{
            return currentLocalMessage.get(name);
        }
    }
}
