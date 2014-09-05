/**   
* @Title: Propagation.java
* @Package com.jbeer.framework.db.tx
* @author Bieber
* @date 2014年5月27日 上午11:11:31
* @version V1.0   
*/

package com.jbeer.framework.db.tx;

/**
* <p>类功能说明:TODO</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: Propagation.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年5月27日 上午11:11:31
* @version V1.0
*/

public enum Propagation {
    /**
     * 事务传播机制里面，当当前线程存在一个事务，那么将不会创建新的事务，如果没有事务将会创建一个新的事务
     */
    PROPAGATION_REQUIRED(0),
    /**
     * 支持当前事务执行，如果当前没有事务，也可以继续执行
     */
    PROPAGATION_SUPPORTS(1), 
    /**
     * 使用当前事务，如果当前没事务则抛出异常
     */
    PROPAGATION_MANDATORY(2), 
    /**
     * 当当前需要一个事务进行处理的时候，都需要创建一个新的事务
     */
    PROPAGATION_REQUIRED_NEW(3),
    /**
     * 以非事务执行，如果当前存在事务，则挂起当前事务。
     */
    PROPAGATION_NOT_SUPPORTED(4),
    /**
     * 当前执行不需要事务，如果存在事务则抛出异常
     */
    PROPAGATION_NEVER(5), 
    /**
     * 内嵌式传播机制，在外面事务基础上创建一个子事务，当子事务提交失败，则不影响外面事务提交，外面父事务提交失败，则子事务也会回滚。
     * 如果当前没事务，则和<code>PROPAGATION_REQUIRED</code>类似
     */
    PROPAGATION_NESTED(6);
    
    private int value;

    private Propagation(int value) {
        this.value = value;
    }

    /**
    * @return value
    */
    
    public int getValue() {
        return value;
    }

}
