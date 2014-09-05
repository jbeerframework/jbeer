/**   
* @Title: Isolation.java
* @Package com.jbeer.framework.db.tx
* @author Bieber
* @date 2014年5月27日 下午1:12:29
* @version V1.0   
*/

package com.jbeer.framework.db.tx;

import java.sql.Connection;

/**
* <p>类功能说明:TODO</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: Isolation.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年5月27日 下午1:12:29
* @version V1.0
*/

public enum Isolation {

    ISOLATION_READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED), ISOLATION_READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED), ISOLATION_REPEATABLE_READ(
                                                                                                                                                                    Connection.TRANSACTION_REPEATABLE_READ), ISOLATION_SERIALIZABLE(
                                                                                                                                                                                                                                    Connection.TRANSACTION_SERIALIZABLE);
    private int value;

    private Isolation(int value) {
        this.value = value;
    }

    /**
    * @return value
    */

    public int getValue() {
        return value;
    }

}
