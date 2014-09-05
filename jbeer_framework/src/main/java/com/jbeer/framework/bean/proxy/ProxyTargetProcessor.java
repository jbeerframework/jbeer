/**   
* @Title: ProxyTargetProcessor.java
* @Package com.jbeer.framework.bean.proxy
* @author Bieber
* @date 2014年5月19日 上午11:34:10
* @version V1.0   
*/

package com.jbeer.framework.bean.proxy;


/**
* <p>类功能说明:代理类的执行者</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: ProxyTargetProcessor.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年5月19日 上午11:34:10
* @version V1.0
*/

public interface ProxyTargetProcessor {

    /**
     * 
    * <p>函数功能说明:代理对象的目标执行器，当时Bean的代理，那么{@link target}参数时代理类在IOC中的bean实体。
    * 当时一个普通的代理，则是proxy对象</p>
    * <p>Bieber  2014年7月30日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return Object
     */
    Object invokeTarget(Object target,InvokeHandler handler) throws Throwable;
}
