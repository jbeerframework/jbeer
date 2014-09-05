/**   
* @Title: Configurate.java
* @Package com.jbeer.framework.config
* @author Bieber
* @date 2014年5月29日 下午4:04:41
* @version V1.0   
*/

package com.jbeer.framework.config;

/**
* <p>类功能说明:抽象配置信息</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: Configurate.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年5月29日 下午4:04:41
* @version V1.0
*/

public interface Configurate {

    public  void configurateContext(JBeerConfig config);
    
    public  void configurateAop(AopConfig config);
    
    public  void configurateDB(DBConfig config);
    
    public  void configurateWeb(WebConfig config);
    
    public  void configureateIOC(IOCConfig config);
    
    public void configurateIN18(IN18Config config);
    
    public void configurateProperties(PropertiesConfig config);
    
    
    public void pluginConfig(PluginConfigHandler handler);
    
    
}
