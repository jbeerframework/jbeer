/**   
* @Title: TranscationDefinition.java
* @Package com.jbeer.framework.db
* @author Bieber
* @date 2014年5月26日 下午5:14:49
* @version V1.0   
*/

package com.jbeer.framework.db.tx;


/**
* <p>类功能说明:事务定义实体</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: TranscationDefinition.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年5月26日 下午5:14:49
* @version V1.0
*/

public class TranscationDefinition {



    private Propagation                  propagation;

    private boolean                      readonly;

    private Class<? extends Throwable>[] rollbackFor;

    private Isolation                    isolation;

    private String                       name;

    /**
    * @return rollbackFor
    */
    
    public Class<? extends Throwable>[] getRollbackFor() {
        return rollbackFor;
    }

    public TranscationDefinition(String name) {
        this.name = name;
    }

    /**
    * @param propagation propagation
    */

    public void setPropagation(Propagation propagation) {
        this.propagation = propagation;
    }

    /**
    * @param readonly readonly
    */

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    /**
    * @param rollbackFor rollbackFor
    */

    public void setRollbackFor(Class<? extends Throwable>[] rollbackFor) {
        this.rollbackFor = rollbackFor;
    }

    /**
    * @param isolation isolation
    */

    public void setIsolation(Isolation isolation) {
        this.isolation = isolation;
    }

    

    /**
    * @return propagation
    */
    
    public Propagation getPropagation() {
        return propagation;
    }

    /**
    * @return isolation
    */
    
    public Isolation getIsolation() {
        return isolation;
    }

 
    public int getTimeout() {
        return 0;
    }

 
    public boolean isReadonly() {
        return readonly;
    }

   
    public String getName() {
        return name;
    }

}
