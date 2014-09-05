/**   
* @Title: ExecutorSQL.java
* @Package com.jbeer.framework.db.sqlrunner
* @author Bieber
* @date 2014年6月9日 上午10:35:30
* @version V1.0   
*/

package com.jbeer.framework.db.sqlrunner;

import java.util.ArrayList;
import java.util.List;

import com.jbeer.framework.exception.DBException;

/**
* <p>类功能说明:可执行的SQL</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: ExecutorSQL.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月9日 上午10:35:30
* @version V1.0
*/

public abstract class SQLSubStatement extends SQL {

    protected SQL executor;
    
    protected StringBuffer sqlStatement;
    
    protected List<SQL> sqlSubStatements = new ArrayList<SQL>();
    
    /* (non-Javadoc)
     * @see com.jbeer.framework.db.sqlrunner.SQL#execute()
     */
    @Override
    public Object execute() throws DBException {
        return executor.execute();
    }
    
    protected abstract boolean isNeedResetLength();

    /* (non-Javadoc)
     * @see com.jbeer.framework.db.sqlrunner.SQL#sqlString()
     */
    @Override
    public String sqlString() throws DBException {
        if(sqlStatement==null){
            sqlStatement=new StringBuffer();
        }else if(isNeedResetLength()){
            sqlStatement.setLength(sqlStatement.length()-1);
        }
        for(int i=0;i<sqlSubStatements.size();i++){
            sqlStatement.append(" ").append(sqlSubStatements.get(i).sqlString());
        }
        return sqlStatement.toString();
    }
    
    
    protected Select checkIsSelectable(SQL executor){
        if(executor instanceof Select){
            return (Select)executor;
        }
        return null;
    }
    

    

}
