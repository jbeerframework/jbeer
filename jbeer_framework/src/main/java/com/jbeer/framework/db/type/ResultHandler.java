/**   
* @Title: TypeHandler.java
* @Package com.jbeer.framework.db.type
* @author Bieber
* @date 2014年6月9日 下午1:02:21
* @version V1.0   
*/

package com.jbeer.framework.db.type;

import java.util.List;
import java.util.Map;

/**
* <p>类功能说明:提供第三方将ResultSet转换为对应的实体</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: TypeHandler.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月9日 下午1:02:21
* @version V1.0
*/

public interface ResultHandler<T extends Object> {

    
    public List<T> getResults(List<Map<String,Object>> results);
    
    public  T getResult(Map<String,Object> results);
}
