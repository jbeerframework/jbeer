/**
 * 
 */
package com.jbeer.framework.timer;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;

import com.jbeer.framework.constant.JBeerConstant;
import com.jbeer.framework.enumeration.BeanScope;
import com.jbeer.framework.exception.JBeerException;
import com.jbeer.framework.ioc.JBeerIOCContainerContext;
import com.jbeer.framework.logging.Log;
import com.jbeer.framework.plugin.CallStart;
import com.jbeer.framework.plugin.Plugin;
import com.jbeer.framework.properties.PropertiesContext;
import com.jbeer.framework.timer.annotation.Job;
import com.jbeer.framework.timer.annotation.Timer;
import com.jbeer.framework.utils.ClassUtils;
import com.jbeer.framework.utils.LoggerUtil;

/**
 * @author bieber
 *
 */
public class TimerPlugin implements Plugin,CallStart {

	public static final String JOB_INFO_HANDLER = "TIMER_INFO_HANDLER";


	private SchedulerFactory factory;

	private Scheduler scheduler;

	private static final Log logger = LoggerUtil
			.generateLogger(TimerPlugin.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jbeer.framework.plugin.Plugin#initialize()
	 */
	public void initialize() throws JBeerException {
		try {
			if(logger.isDebugEnabled()){
				logger.debug("initialize timer plugin ");
			}
			factory = new StdSchedulerFactory();
			scheduler = factory.getScheduler();
			
			
		} catch (Exception e) {
			throw new JBeerException("fail to instialize timer plugin", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jbeer.framework.plugin.Plugin#destory()
	 */
	public void destory() throws JBeerException {
		if (scheduler != null) {
			try {
				if(logger.isDebugEnabled()){
					logger.debug("shutdown timer scheduler");
				}
				scheduler.shutdown(true);
			} catch (SchedulerException e) {
				logger.error("fail to shutdown timer scheduler", e);
			}
		}

	}

	private void generateJob(Class<?> jobClass) throws SchedulerException {
		Method[] methods = jobClass.getDeclaredMethods();
		for (Method method : methods) {
			Job job = method.getAnnotation(Job.class);
			if (job == null) {
				continue;
			}
			String cron = job.cronExpression();
 			JobDataMap dataMap = new JobDataMap();
			JobHandler handler = new JobHandler();
			handler.setJobMethod(method);
			handler.setTimerInstanceId(jobClass.getName());
			dataMap.put(JOB_INFO_HANDLER, handler);
			JobDetail jobDetail = JobBuilder
					.newJob(DefaultJobInvoker.class)
					.withIdentity(
							(job.jobName() == null || job.jobName()
									.length() <= 0) ? generateJobName(
									jobClass, method) : job.jobName(),
							job.groupId()).setJobData(dataMap).build();
			TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger().withIdentity(jobDetail.getKey().getName(),
					jobDetail.getKey().getGroup());
			if (cron == null || cron.length() <= 0) {
				String repeatCountStr = job.repeatTimes();
				String repeatCountProp = PropertiesContext
						.getProperties(repeatCountStr);
				int repeatCount = repeatCountProp == null ? (Integer
						.parseInt(repeatCountStr)) : Integer
						.parseInt(repeatCountProp);
				String repeatIntervalStr = job.repeatIntervalTime();
				String repeatIntervalProp = PropertiesContext
						.getProperties(repeatIntervalStr);
				long repeatInterval = repeatIntervalProp == null ? Long
						.parseLong(repeatIntervalStr) : Long
						.parseLong(repeatIntervalProp);
				SimpleTriggerImpl simpleTrigger = (SimpleTriggerImpl) triggerBuilder.build();
				simpleTrigger.setRepeatCount(repeatCount);
				simpleTrigger.setRepeatInterval(repeatInterval);
				Date date = scheduler.scheduleJob(jobDetail, simpleTrigger);
				scheduler.getTrigger(simpleTrigger.getKey());
				if (logger.isDebugEnabled()) {
					logger.debug("job " + jobDetail.getKey() + " will run at "
							+ date + " repeat count is "
							+ simpleTrigger.getRepeatCount() + " repeat interval is "
							+ simpleTrigger.getRepeatInterval());

				}
			} else {
				String cronExpressionStr = job.cronExpression();
				String cronExpreProp = PropertiesContext.getProperties(cronExpressionStr);
				String cronExpression = cronExpreProp==null?cronExpressionStr:cronExpreProp;
				CronTriggerImpl cronTrigger = (CronTriggerImpl) triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
				Date date = scheduler.scheduleJob(jobDetail, cronTrigger);
				if (logger.isDebugEnabled()) {
					logger.debug("job " + jobDetail.getKey() + " will run at "
							+ date + " and repeat based on expression: "+cronTrigger.getCronExpression());

				}
			}
		}
	}

	private String generateJobName(Class<?> timerClass, Method jobMethod) {
		StringBuffer jobName = new StringBuffer();
		jobName.append(timerClass.getName()).append(".")
				.append(jobMethod.getName()).append("(");
		Class<?>[] parameters = jobMethod.getParameterTypes();
		for (Class<?> param : parameters) {
			jobName.append(param.getName()).append(",");
		}
		if(parameters.length>0){
		jobName.setLength(jobName.length() - 1);
		}
		jobName.append(")");
		return jobName.toString();
	}

	public void start() throws JBeerException  {
		try {
		Set<Class<?>> classes = ClassUtils
				.scanClassesByAnnotation(Timer.class);
		for (Class<?> clazz : classes) {
			generateJob(clazz);
			JBeerIOCContainerContext.registBeanDefinition(clazz, JBeerConstant.IOC_DEFALUT_FACTORY_METHOD_NAME, BeanScope.SINGLETON, clazz.getName());
		}
		
		scheduler.start();
		if(logger.isDebugEnabled()){
			logger.debug("start timer scheduler");
		}
		} catch (Exception e) {
			throw new JBeerException("fail to start timer plugin", e);
		}
		
	}
}
