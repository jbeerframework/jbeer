/**   
* @Title: JBeerTestRunwith.java
* @Package com.jbeer.framework.test
* @author Bieber
* @date 2014-5-24 下午9:19:36
* @version V1.0   
*/

package com.jbeer.framework.test;
import java.lang.reflect.Field;
import java.util.List;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkField;

import com.jbeer.framework.JBeer;
import com.jbeer.framework.annotation.Message;
import com.jbeer.framework.annotation.Properties;
import com.jbeer.framework.annotation.RefBean;
import com.jbeer.framework.constant.JBeerConstant;
import com.jbeer.framework.exception.InitializationException;
import com.jbeer.framework.exception.JBeerException;
import com.jbeer.framework.exception.MessageException;
import com.jbeer.framework.ioc.FactoryBean;
import com.jbeer.framework.ioc.JBeerIOCContainerContext;
import com.jbeer.framework.logging.Log;
import com.jbeer.framework.message.MessageUtils;
import com.jbeer.framework.properties.PropertiesContext;
import com.jbeer.framework.utils.LoggerUtil;
import com.jbeer.framework.utils.StringUtils;

/**
 * <p>类功能说明:TODO</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: JBeerTestRunwith.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-24 下午9:19:36
 * @version V1.0
 */

public class JBeer4JUintTestRunner extends BlockJUnit4ClassRunner{

    private static final Log logger = LoggerUtil.generateLogger(JBeer4JUintTestRunner.class);
    
    private static boolean isInit = false;
    public JBeer4JUintTestRunner(Class<?> klass) throws Exception, JBeerException {
        super(klass);
        initFramework(klass);
    }
    
    private synchronized void initFramework(Class<?> testClass) throws InstantiationException, IllegalAccessException, JBeerException{
        if(!isInit){
            if(JBeerTestHelper.class.isAssignableFrom(testClass)){
                JBeerTestHelper helper = (JBeerTestHelper) testClass.newInstance();
                JBeer.initFramework(helper.applicationBasePackage(), helper.configurate());
            }
            isInit=true;
        }
    }
    
    /* (non-Javadoc)
     * @see org.junit.runners.ParentRunner#run(org.junit.runner.notification.RunNotifier)
     */
    @Override
    public void run(RunNotifier notifier) {
        super.run(notifier);
    }

    /* (non-Javadoc)
     * @see org.junit.runners.BlockJUnit4ClassRunner#createTest()
     */
    @Override
    protected Object createTest() throws Exception {
        List<FrameworkField> fields =  getTestClass().getAnnotatedFields(RefBean.class);
        Object tester = super.createTest();
        for(FrameworkField field:fields){
            Field targetField =field.getField();
            RefBean refBean = targetField.getAnnotation(RefBean.class);
            targetField.setAccessible(true);
            try {
            	if(refBean.factoryBeanClass()!=FactoryBean.class){
            		FactoryBean factoryBean =JBeerIOCContainerContext.getBeanByType(refBean.factoryBeanClass());
            		targetField.set(tester, factoryBean.get(targetField.getType()));
            	}else if(StringUtils.equals(refBean.value(), JBeerConstant.DEFAULT_BEANNAME)){
                    targetField.set(tester, JBeerIOCContainerContext.getBeanByType(targetField.getType()));
                }else{
                    targetField.set(tester, JBeerIOCContainerContext.getBeanById(refBean.value()));
                }
                
            } catch (InitializationException e) {
                if(logger.isDebugEnabled()){
                    logger.debug("fial to initalizing test instance",e);
                }
                throw new Exception(e);
            }
        }
        fields = getTestClass().getAnnotatedFields(Properties.class);
        for(FrameworkField field:fields){
            Field targetField =field.getField();
            Properties ref = targetField.getAnnotation(Properties.class);
            targetField.setAccessible(true);
            targetField.set(tester, PropertiesContext.getProperties(ref.name()));
        }
        fields = getTestClass().getAnnotatedFields(Message.class);
        for(FrameworkField field:fields){
            Field targetField =field.getField();
            Message m = targetField.getAnnotation(Message.class);
            targetField.setAccessible(true);
            try {
                targetField.set(tester, MessageUtils.getMessage(m.name(), m.args()));
            } catch (MessageException e) {
                if(logger.isDebugEnabled()){
                    logger.debug("fial to initalizing test instance",e);
                }
                throw new Exception(e);
            }
        }
        return tester;
    }

    
     
}
