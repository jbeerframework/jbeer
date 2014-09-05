/**   
* @Title: SelectableStatement.java
* @Package com.jbeer.framework.db.sqlrunner
* @author Bieber
* @date 2014年6月10日 上午8:54:02
* @version V1.0   
*/

package com.jbeer.framework.db.sqlrunner;

import java.util.List;
import java.util.Map;

import com.jbeer.framework.db.type.ResultHandler;
import com.jbeer.framework.exception.DBException;

/**
* <p>类功能说明:TODO</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: SelectableStatement.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月10日 上午8:54:02
* @version V1.0
*/

public abstract class SelectableStatement extends SQLSubStatement{
    
    /**
     * 
    * <p>函数功能说明:根据主键获取实体对象</p>
    * <p>Bieber  2014年6月10日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return T
     */
    public <T> T selectByPrimaryKey(Object primaryKeyValue) throws DBException{
        Select select = checkIsSelectable(executor);
        if(select!=null){
            return select.selectByPrimaryKey(primaryKeyValue);
        }
        return null;
    }
    
    /**
     * 
    * <p>函数功能说明:返回一个指定的实体类对象</p>
    * <p>Bieber  2014年6月10日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return T
     */
    public <T> T selectOne(Class<T> entityClass) throws DBException{
        Select select = checkIsSelectable(executor);
        if(select!=null){
            return select.selectOne(entityClass);
        }
        return null;
    }
    
    /**
     * 
    * <p>函数功能说明:根据自定义的{@link ResultHandler} 进行结果类型转换，返回list集合</p>
    * <p>Bieber  2014年6月10日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return List<T>
     */
    public <T> List<T> selectList(ResultHandler<T> resultHandler) throws DBException{
        Select select = checkIsSelectable(executor);
        if(select!=null){
            return select.selectList(resultHandler);
        }
        return null;
    }
    
    /**
     * 
    * <p>函数功能说明:如果是通过entityClass方式创建Select语句，那么就可以通过该方法返回对应实体类的对象集合</p>
    * <p>Bieber  2014年6月10日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return List<T>
     */
    public <T> List<T> selectList() throws DBException{
        Select select = checkIsSelectable(executor);
        if(select!=null){
            return select.selectList();
        }
        return null;
    }
    
    /**
     * 
    * <p>函数功能说明:TODO</p>
    * <p>Bieber  2014年6月10日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return Map<String,Object>
     */
    public Map<String,Object> selectOne() throws DBException{
        Select select = checkIsSelectable(executor);
        if(select!=null){
            return select.selectOne();
        }
        return null;
    }
    
    /**
     * 
    * <p>函数功能说明:通过制定实体类型，来返回查询的类对象集合</p>
    * <p>Bieber  2014年6月10日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return List<T>
     */
    public <T> List<T> selectList(Class<T> entityClass) throws DBException{
        Select select = checkIsSelectable(executor);
        if(select!=null){
            return select.selectList(entityClass);
        }
        return null;
    }
    
    /**
     * 
     * 
    * <p>函数功能说明:和 <pre>selectList(ResultHandler<T> resultHandler)</pre>方法类似,只是返回单个对象</p>
    * <p>Bieber  2014年6月10日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return T
     */
    public <T> T selectOne(ResultHandler<T> resultHandler) throws DBException{
        Select select = checkIsSelectable(executor);
        if(select!=null){
            return select.selectOne(resultHandler);
        }
        return null;
    }
}
