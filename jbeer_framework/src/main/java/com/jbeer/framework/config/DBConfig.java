/**   
 * @Title: DBConfig.java
 * @Package com.jbeer.framework.config
 * @author Bieber
 * @date 2014年5月29日 下午4:10:42
 * @version V1.0   
 */

package com.jbeer.framework.config;

import javax.sql.DataSource;

import com.jbeer.framework.db.JBeerDBContext;
import com.jbeer.framework.db.datasource.JBeerDataSource;

/**
 * <p>
 * 类功能说明:数据库模块的配置信息
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: DBConfig.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年5月29日 下午4:10:42
 * @version V1.0
 */

public class DBConfig {

    /**
     * 
    * <p>函数功能说明:使用框架自带的Datasource,并且配置连接池大小和获取连接超时时间，超时时间的单位是秒</p>
    * <p>Bieber  2014年6月24日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     */
    public void setDatasource(int initSize, int maxSize, int acquireIncrement, int timeout,
                                      String userName, String password, String jdbcUrl,
                                      String driveName) {
        JBeerDBContext.setDataSource(new JBeerDataSource(initSize, maxSize, acquireIncrement,
            timeout, userName, password, jdbcUrl, driveName));
    }

    /**
     * 
    * <p>函数功能说明:使用框架自带的Datasource,并且配置连接池大小和获取连接超时时间，超时时间的单位是秒，该方法可以使用占位符进行配置参数，${xxxxxx}将会引用properties文件中配置的属性</p>
    * <p>Bieber  2014年6月24日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     */
    public void setDatasource(String initSize, String maxSize, String acquireIncrement, String timeout,
                                      String userName, String password, String jdbcUrl,
                                      String driveName) {
        JBeerDBContext.setDataSource(new JBeerDataSource(initSize, maxSize, acquireIncrement,
            timeout, userName, password, jdbcUrl, driveName));
    }
    /**
     * 
    * <p>函数功能说明:使用框架自带的Datasource,连接池大小和超时时间采用默认值，框架默认连接池大小为20个，最大不超过40个，每次增加10个，超时时间是3秒</p>
    * <p>Bieber  2014年6月24日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     */
    public void setDatasource(String userName, String password, String jdbcUrl,
                                      String driveName) {
        JBeerDBContext.setDataSource(new JBeerDataSource(userName, password, jdbcUrl, driveName));
    }

    public void setDatasource(DataSource ds) {
        JBeerDBContext.setDataSource(ds);
    }
    
    public void setTxPointCutRegex(String pointCutRegex){
    	JBeerDBContext.setTxPointCut(pointCutRegex);
    }

}
