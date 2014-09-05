/**   
* @Title: Aspect.java
* @Package com.jbeer.framework.ioc.aop
* @author Bieber
* @date 2014-5-18 下午2:01:21
* @version V1.0   
*/

package com.jbeer.framework.aop;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jbeer.framework.exception.InitializationException;
import com.jbeer.framework.ioc.JBeerIOCContainerContext;

/**
 * <p>类功能说明:增强的抽象接口</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: Aspect.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-18 下午2:01:21
 * @version V1.0
 */

public abstract class AbstractAdvice implements Advice{
    
    
    /**
     * 需要拦截类的切面
     */
    protected String[] classRegex;
    /**
     * 需要拦截方法的切面
     */
    protected String[] methodRegex;
    /**
     * 方法参数类型
     */
    protected Class<?>[] argTypes;
    
    /**
     * 拦截的异常类型
     */
    protected Class<? extends Throwable>[] handleException;
    /**
     * 根据对应ID去调用增强类
     */
    protected String beanName;
    /**
     * 拦截器方法法之前执行的方法
     */
    protected Method before;
    /**
     * 拦截器方法之后执行的方法
     */
    protected Method after;
    /**
     * 拦截方法抛出异常时候执行的方法
     */
    protected Method throwable;
    /**
     * 增强类实体
     */
    protected Object adviceInstance;
    
    

    /**
     * 
    * <p>Title: 注入增强需要的参数</p>
    * <p>Description: </p>
    * @param classAspect
    * @param methodAspect
    * @param beanName
    * @param before
    * @param after
    * @param throwable
     */
    public AbstractAdvice(String[] classRegex, String[] methodRegex,Class<?>[] argTypes,Class<? extends Throwable>[] handleException, String beanName, Method before,
                  Method after, Method throwable) {
        super();
        this.classRegex=classRegex;
        this.methodRegex = methodRegex;
        this.argTypes = argTypes;
        this.beanName = beanName;
        this.before = before;
        this.after = after;
        this.throwable = throwable;
        this.handleException=handleException;
    }

    /**
     * 
    * <p>Title:不做任何操作的增强构造函数 </p>
    * <p>Description: </p>
     */
    public AbstractAdvice() {

    }

    protected final synchronized Object getAdviceObject() throws InitializationException {
        if (adviceInstance == null) {
            adviceInstance = JBeerIOCContainerContext.getBeanById(beanName);
        }
        return adviceInstance;
    }

    public final boolean isMatchedClass(String className) {
        if(classRegex==null||classRegex.length<1){
            return true;
        }
        for(String regex:classRegex){
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(className);
            if(matcher.matches()){
                return true;
            }
        }
        return false;
    }

    public final boolean isMatchedMethod(String methodName) {
        if(methodRegex==null||methodRegex.length<1){
            return true;
        }
        for(String regex:methodRegex){
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(methodName);
            if(matcher.matches()){
                return true;
            }
        }
        return false;
    }

    public final boolean isMatchedArgs(Class<?>[] matchTypes){
        if(argTypes==null||argTypes.length<1){
            return true;
        }
        if(argTypes.length==matchTypes.length){
            int lenght = argTypes.length;
            for(int i=0;i<lenght;i++){
                if(argTypes[i]!=matchTypes[i]){
                    return false;
                }
            }
            return true;
        }else{
            return false;
        }
    }
    
    
    public final boolean isMatchException(Class<? extends Throwable> exceptionType){
    	 if(handleException==null||handleException.length<1){
             return true;
         }
         for(Class<? extends Throwable> t:handleException){
        	 if(t.isAssignableFrom(exceptionType)){
        		 return true;
        	 }
         }
         return false;
    }
   
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((adviceInstance == null) ? 0 : adviceInstance.hashCode());
        result = prime * result + ((after == null) ? 0 : after.hashCode());
        result = prime * result + Arrays.hashCode(argTypes);
        result = prime * result + ((beanName == null) ? 0 : beanName.hashCode());
        result = prime * result + ((before == null) ? 0 : before.hashCode());
        result = prime * result + Arrays.hashCode(classRegex);
        result = prime * result + Arrays.hashCode(methodRegex);
        result = prime * result + ((throwable == null) ? 0 : throwable.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractAdvice other = (AbstractAdvice) obj;
        if (adviceInstance == null) {
            if (other.adviceInstance != null)
                return false;
        } else if (!adviceInstance.equals(other.adviceInstance))
            return false;
        if (after == null) {
            if (other.after != null)
                return false;
        } else if (!after.equals(other.after))
            return false;
        if (!Arrays.equals(argTypes, other.argTypes))
            return false;
        if (beanName == null) {
            if (other.beanName != null)
                return false;
        } else if (!beanName.equals(other.beanName))
            return false;
        if (before == null) {
            if (other.before != null)
                return false;
        } else if (!before.equals(other.before))
            return false;
        if (!Arrays.equals(classRegex, other.classRegex))
            return false;
        if (!Arrays.equals(methodRegex, other.methodRegex))
            return false;
        if (throwable == null) {
            if (other.throwable != null)
                return false;
        } else if (!throwable.equals(other.throwable))
            return false;
        return true;
    }
    
    
}
