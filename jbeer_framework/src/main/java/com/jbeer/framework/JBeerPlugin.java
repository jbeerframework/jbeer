/**   
* @Title: JBeerPlugin.java
* @Package com.jbeer.framework
* @author Bieber
* @date 2014年6月13日 下午2:06:32
* @version V1.0   
*/

package com.jbeer.framework;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.jbeer.framework.aop.JBeerAopContext;
import com.jbeer.framework.enumeration.BeanScope;
import com.jbeer.framework.exception.JBeerException;
import com.jbeer.framework.ioc.JBeerIOCContainerContext;
import com.jbeer.framework.plugin.AspectablePlugin;
import com.jbeer.framework.plugin.Plugin;
import com.jbeer.framework.utils.ClassUtils;

/**
* <p>类功能说明:TODO</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: JBeerPlugin.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月13日 下午2:06:32
* @version V1.0
*/

public class JBeerPlugin {

    public static void init() throws JBeerException{
    	try{
       Set<Class<?>> clazzSet = ClassUtils.scanClassesByInterfaceClass(Plugin.class);
       for(Class<?> clazz:clazzSet){
           JBeerIOCContainerContext.registBeanDefinition(clazz.newInstance(), BeanScope.SINGLETON,clazz.getName());
       }
        clazzSet = ClassUtils.scanClassesByInterfaceClass(JBeerPlugin.class.getPackage().getName(),Plugin.class);
       for(Class<?> clazz:clazzSet){
           JBeerIOCContainerContext.registBeanDefinition(clazz.newInstance(), BeanScope.SINGLETON,clazz.getName());
       }
      Map<String,Plugin> plugins = JBeerIOCContainerContext.getBeansByType(Plugin.class);
      Collection<Plugin> pluginsObject =  plugins.values();
      for(Plugin plugin:pluginsObject){
          plugin.initialize();
          if(AspectablePlugin.class.isAssignableFrom(plugin.getClass())){
              AspectablePlugin aspectable = (AspectablePlugin) plugin;
              JBeerAopContext.registAspectBean(aspectable);
          }
          
      }
      }catch(Exception e){
    	  throw new JBeerException("fail to initialize plugin module", e);
      }
    }
    
    public static void destory() throws JBeerException{
        Map<String,Plugin> plugins = JBeerIOCContainerContext.getBeansByType(Plugin.class);
        Collection<Plugin> pluginsObject =  plugins.values();
        for(Plugin plugin:pluginsObject){
            plugin.destory();
        }
    }
    
}
