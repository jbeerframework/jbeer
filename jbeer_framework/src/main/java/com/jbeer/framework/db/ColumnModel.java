/**   
* @Title: ColumnModel.java
* @Package com.jbeer.framework.db
* @author Bieber
* @date 2014年6月4日 上午9:13:52
* @version V1.0   
*/

package com.jbeer.framework.db;

import java.lang.reflect.Method;

/**
* <p>类功能说明:列的实体</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: ColumnModel.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月4日 上午9:13:52
* @version V1.0
*/

public class ColumnModel {

    private Class<?> type;
    
    private String columnName;
    
    private boolean isPrimarykey;
    
    private boolean isAutoIncreasement;
    
    private Method getMethod;
    
    private Method setMethod;

    

    /**
    * @return getMethod
    */
    
    public Method getGetMethod() {
        return getMethod;
    }

    /**
    * @param getMethod getMethod
    */
    
    public void setGetMethod(Method getMethod) {
        this.getMethod = getMethod;
    }

    /**
    * @return setMethod
    */
    
    public Method getSetMethod() {
        return setMethod;
    }

    /**
    * @param setMethod setMethod
    */
    
    public void setSetMethod(Method setMethod) {
        this.setMethod = setMethod;
    }

    /**
    * @return type
    */
    
    public Class<?> getType() {
        return type;
    }

    /**
    * @param type type
    */
    
    public void setType(Class<?> type) {
        this.type = type;
    }

    /**
    * @return columnName
    */
    
    public String getColumnName() {
        return columnName;
    }

    /**
    * @param columnName columnName
    */
    
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
    * @return isPrimarykey
    */
    
    public boolean isPrimarykey() {
        return isPrimarykey;
    }

    /**
    * @param isPrimarykey isPrimarykey
    */
    
    public void setPrimarykey(boolean isPrimarykey) {
        this.isPrimarykey = isPrimarykey;
    }

    /**
    * @return isAutoIncreasement
    */
    
    public boolean isAutoIncreasement() {
        return isAutoIncreasement;
    }

    /**
    * @param isAutoIncreasement isAutoIncreasement
    */
    
    public void setAutoIncreasement(boolean isAutoIncreasement) {
        this.isAutoIncreasement = isAutoIncreasement;
    }

    
    
}
