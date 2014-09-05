/**   
* @Title: Test.java
* @Package com.jbeer.framework
* @author Bieber
* @date 2014年5月26日 下午12:50:48
* @version V1.0   
*/

package com.jbeer.framework;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

/**
* <p>类功能说明:TODO</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: Test.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年5月26日 下午12:50:48
* @version V1.0
*/

public class Test {

    /**
    * <p>函数功能说明:TODO</p>
    * <p>Bieber  2014年5月26日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void   
    */

    public static void main(String[] args) {
    	try {
			Method method = Test.class.getMethod("test", String.class,List.class);
			Type[] gType = method.getGenericParameterTypes();
			Class<?> type = method.getParameterTypes()[0];
			type.getGenericSuperclass();
			System.out.println(type);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    
    public void test(String name,List<String> test){
    	
    }
}
 