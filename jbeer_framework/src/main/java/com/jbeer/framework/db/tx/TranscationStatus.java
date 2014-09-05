/**   
* @Title: TranscationStatus.java
* @Package com.jbeer.framework.db.tx
* @author Bieber
* @date 2014年5月27日 上午11:19:24
* @version V1.0   
*/

package com.jbeer.framework.db.tx;


/**
* <p>类功能说明:事务状态</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: TranscationStatus.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年5月27日 上午11:19:24
* @version V1.0
*/

public class TranscationStatus {

    private boolean isRollBack;
    
    private boolean isRollBackOnly;
    
    private boolean isNewTranscation;
    
    private boolean isCompleted;
    
    private boolean hasSavepoint;
    

    /**
    * @return isRollBack
    */
    
    public boolean isRollBack() {
        return isRollBack;
    }

    /**
    * @param isRollBack isRollBack
    */
    
    public void setRollBack(boolean isRollBack) {
        this.isRollBack = isRollBack;
    }

    /**
    * @return isRollBackOnly
    */
    
    public boolean isRollBackOnly() {
        return isRollBackOnly;
    }

    /**
    * @param isRollBackOnly isRollBackOnly
    */
    
    public void setRollBackOnly(boolean isRollBackOnly) {
        this.isRollBackOnly = isRollBackOnly;
    }

    /**
    * @return isNewTranscation
    */
    
    public boolean isNewTranscation() {
        return isNewTranscation;
    }

    /**
    * @param isNewTranscation isNewTranscation
    */
    
    public void setNewTranscation(boolean isNewTranscation) {
        this.isNewTranscation = isNewTranscation;
    }

    /**
    * @return isCompleted
    */
    
    public boolean isCompleted() {
        return isCompleted;
    }

    /**
    * @param isCompleted isCompleted
    */
    
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    /**
    * @return hasSavepoint
    */
    
    public boolean isHasSavepoint() {
        return hasSavepoint;
    }

    /**
    * @param hasSavepoint hasSavepoint
    */
    
    public void setHasSavepoint(boolean hasSavepoint) {
        this.hasSavepoint = hasSavepoint;
    }

    
}
