/**   
* @Title: DataSourceUtil.java
* @Package com.jbeer.framework.db
* @author Bieber
* @date 2014年5月26日 上午9:42:24
* @version V1.0   
*/

package com.jbeer.framework.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.jbeer.framework.db.tx.TranscationManager;

/**
* <p>类功能说明:数据源工具类</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: DataSourceUtil.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年5月26日 上午9:42:24
* @version V1.0
*/

public class DataSourceUtil {

    
    
    public static Connection getConnection(DataSource ds) throws SQLException{
        return TranscationManager.getConnection(ds);
    }
    
}
