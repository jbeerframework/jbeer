/**   
 * @Title: DefaultJob.java
 * @Package com.jbeer.framework.timer
 * @author Bieber
 * @date 2014年7月27日 下午4:21:10
 * @version V1.0   
 */

package com.jbeer.framework.timer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jbeer.framework.annotation.Message;
import com.jbeer.framework.annotation.Properties;
import com.jbeer.framework.annotation.RefBean;
import com.jbeer.framework.constant.JBeerConstant;
import com.jbeer.framework.ioc.FactoryBean;
import com.jbeer.framework.ioc.JBeerIOCContainerContext;
import com.jbeer.framework.logging.Log;
import com.jbeer.framework.message.MessageUtils;
import com.jbeer.framework.properties.PropertiesContext;
import com.jbeer.framework.utils.CaseUtils;
import com.jbeer.framework.utils.LoggerUtil;

/**
 * <p>
 * 类功能说明:默认任务执行类
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: DefaultJob.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月27日 下午4:21:10
 * @version V1.0
 */

public class DefaultJobInvoker implements Job {

	private static final Log logger = LoggerUtil
			.generateLogger(DefaultJobInvoker.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		JobHandler handler = (JobHandler) context.getJobDetail()
				.getJobDataMap().get(TimerPlugin.JOB_INFO_HANDLER);
		try {
			Method jobMethod = handler.getJobMethod();
			Object timerInstance = JBeerIOCContainerContext.getBeanById(handler
					.getTimerInstanceId());
			Annotation[][] annotations = jobMethod.getParameterAnnotations();
			Class<?>[] parameterTypes = jobMethod.getParameterTypes();
			Object[] args=null;
			if (annotations != null && annotations.length > 0) {
				args=new Object[annotations.length];
				for (int i=0;i<annotations.length;i++) {
					Annotation[] anno  = annotations[i];
					for(Annotation annotation:anno){
						if(annotation instanceof Properties){
							Properties prop = (Properties)annotation;
							args[i]=CaseUtils.caseType(parameterTypes[i], PropertiesContext.getProperties(prop.name()));
						}else if(annotation instanceof Message){
							Message message = (Message) annotation;
							args[i]=MessageUtils.getMessage(message.name(), message.args());
						}else if(annotation instanceof RefBean){
							RefBean ref = (RefBean) annotation;
							if(ref.value().equals(JBeerConstant.DEFAULT_BEANNAME)){
							args[i]=JBeerIOCContainerContext.getBeanByType(parameterTypes[i]);
							}else if(ref.factoryBeanClass()!=FactoryBean.class){
								FactoryBean factory = JBeerIOCContainerContext.getBeanByType(ref.factoryBeanClass());
								args[i]=factory.get(parameterTypes[i]);
							}else {
								args[i]=JBeerIOCContainerContext.getBeanById(ref.value());
							}
						}
					}
				}
			}
			Object ret = jobMethod.invoke(timerInstance, args);
			if(logger.isDebugEnabled()){
				logger.debug("fire job "+context.getJobDetail().getKey().getName()+" and return "+ret);
			}
		} catch (Exception e) {
			logger.debug("fail to get timer instance", e);
			throw new JobExecutionException("fail to get timer instance for "
					+ handler.getTimerInstanceId(), e);

		}
	}

}
