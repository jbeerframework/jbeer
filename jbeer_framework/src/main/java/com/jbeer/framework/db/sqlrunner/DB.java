/**   
* @Title: DB.java
* @Package com.jbeer.framework.db
* @author Bieber
* @date 2014年6月3日 下午2:46:19
* @version V1.0   
*/

package com.jbeer.framework.db.sqlrunner;

import java.sql.Connection;
import java.sql.SQLException;

import com.jbeer.framework.db.DataSourceUtil;
import com.jbeer.framework.db.JBeerDBContext;
import com.jbeer.framework.exception.DBException;

/**
* <p>类功能说明:数据库操作类</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: DB.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月3日 下午2:46:19
* @version V1.0
*/

public class DB {

    /**
     * 
    * <p>函数功能说明:直接插入一个实体对象，该方法可以直接执行execute方法，<br/>
    * 该方式，如果在实体中通过{@link Column}注解配置了自动递增字段，将会从数据库获取当前ID</p>
    * <p>Bieber  2014年6月6日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return T
     */
    public static Insert insert(Object t) throws DBException {
        try {
            return new Insert(t, getConnection());
        } catch (Exception e) {
            throw new DBException(e);
        }
    }

    /**
     * 
    * <p>函数功能说明:指定要插入的实体类型，从而获取对应的实体表名，<br/>进行插入，该方式需要依次自定义需要插入的字段以及值</p>
    * <p>Bieber  2014年6月6日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return T
     */
    public static Insert insert(Class<?> entityClass) throws DBException {
        try {
            return new Insert(entityClass, getConnection());
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    /**
     * 
    * <p>函数功能说明:直接指定表名进行插入操作，使用方法同insert(Class<?> entityClass)方法</p>
    * <p>Bieber  2014年6月6日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return T
     */
    public static Insert insert(String entityName) throws DBException {
        try {
            return new Insert(entityName, getConnection());
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    /**
     * 
    * <p>函数功能说明:通过指定实体类来指明需要更新的实体表</p>
    * <p>Bieber  2014年6月6日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @param entityClass 更新的实体类
    * @return int
     * @throws DBException 
     */
    public static Update update(Class<?> entityClass) throws DBException {
        try {
            return new Update(entityClass, getConnection());
        } catch (Exception e) {
            throw new DBException(e);
        }
    }

    /**
     * 
    * <p>函数功能说明:通过传入一个更新实体来实现更新整个实体属性，并且该实体对象有对应的实体表，否则会出现异常</p>
    * <p>Bieber  2014年6月6日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @param entityClass 更新的实体类
    * @return int
     * @throws DBException 
     */
    public static Update update(Object entityObject) throws DBException {
        try {
            return new Update(entityObject, getConnection());
        } catch (Exception e) {
            throw new DBException(e);
        }
    }
    /**
     * 
    * <p>函数功能说明:通过指定实体名来确定更新的实体，需要更新的字段信息，需要依次设置</p>
    * <p>Bieber  2014年6月6日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @param entityClass 更新的实体类
    * @return int
     * @throws DBException 
     */
    public static Update update(String entityName) throws DBException {
        try {
            return new Update(entityName, getConnection());
        } catch (Exception e) {
            throw new DBException(e);
        }
    }
    /**
     * 
    * <p>函数功能说明:通过指定实体类型来删除指定表记录</p>
    * <p>Bieber  2014年6月7日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return Delete
     * @throws DBException 
     */
    public static Delete delete(Class<?> entityClass) throws DBException {
        try {
            return new Delete(entityClass, getConnection());
        } catch (Exception e) {
            throw new DBException(e);
        }
    }

    /**
     * 
    * <p>函数功能说明:同指定实体名称（表名）来指定删除表</p>
    * <p>Bieber  2014年6月7日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return Delete
     * @throws DBException 
     */
    public static Delete delete(String entityName) throws DBException {
        try {
            return new Delete(entityName, getConnection());
        } catch (Exception e) {
            throw new DBException(e);
        }
    }

   

    /**
     * 
    * <p>函数功能说明:通过指定实体名称，来制定需要查询的表名</p>
    * <p>Bieber  2014年6月10日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return Select
     */
    public static Select select(String entityName) throws DBException {
        try {
            return new Select(entityName, getConnection());
        } catch (Exception e) {
            throw new DBException(e);
        }
    }

    /**
     * 
    * <p>函数功能说明:通过指定一个实体类来指定查询的表</p>
    * <p>Bieber  2014年6月10日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return Select
     */
    public static Select select(Class<?> entityClass) throws DBException {
        try {
            return new Select(entityClass, getConnection());
        } catch (Exception e) {
            throw new DBException(e);
        }
    }

    /**
     * 
    * <p>函数功能说明:构造一个没有指定查询表的查询语句，通过后面设置查询，或者通过传入一条完整的SQL语句进行查询操作</p>
    * <p>Bieber  2014年6月10日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return Select
     */
    public static Select select() throws DBException {
        try {
            return new Select(getConnection());
        } catch (Exception e) {
            throw new DBException(e);
        }
    }

    private static Connection getConnection() throws SQLException {
        return DataSourceUtil.getConnection(JBeerDBContext.getDataSource());
    }

}
