/**   
 * @Title: SimpleResourceMonitor.java
 * @Package com.jbeer.framework.server.monitor
 * @author Bieber
 * @date 2014年6月19日 下午10:15:56
 * @version V1.0   
 */

package com.jbeer.framework.server.monitor;

import java.util.HashSet;
import java.util.Set;

import com.jbeer.framework.logging.Log;
import com.jbeer.framework.server.monitor.timer.SimpleTimer;
import com.jbeer.framework.server.monitor.timer.TimerTask;
import com.jbeer.framework.utils.ClassUtils;
import com.jbeer.framework.utils.LoggerUtil;

/**
 * <p>
 * 类功能说明:简单的资源监控类
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: SimpleResourceMonitor.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年6月19日 下午10:15:56
 * @version V1.0
 */

public class SimpleResourceMonitor implements ResourceMonitor {
	
	private static final Log logger = LoggerUtil.generateLogger(SimpleResourceMonitor.class);

	private SimpleTimer timer;

	private Set<FileItem> preFiles = new HashSet<FileItem>();

	private Set<FileItem> currFiles;

	private ProjectFileScaner scaner;
	
	private ChangeCallBack callBack;

	public SimpleResourceMonitor(long intervalTime) {
		timer = new SimpleTimer(intervalTime);
		scaner = new EclipseProjectFilesScaner();
	}

	public void start() {
		timer.submitTask(new TimerTask() {

			public void run() {
				currFiles = scaner.scanProject(ClassUtils.getProjectRootDir());
				compare();
			}

		});
		timer.start();
	}

	private void compare() {
		if(preFiles.size()<=0&&currFiles!=null){
			preFiles.addAll(currFiles);
		}else if(!preFiles.equals(currFiles)){
			if(this.callBack!=null){
				this.callBack.hasChange();
			}
			preFiles.clear();
			preFiles.addAll(currFiles);
			currFiles.clear();
		}
	}

	public void callback(ChangeCallBack callBack) {
		this.callBack = callBack;
	}

	public void stop() {
		timer.stop();
	}

}
