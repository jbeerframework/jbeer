/**   
 * @Title: LoadProjectFiles.java
 * @Package com.jbeer.framework.server.monitor
 * @author Bieber
 * @date 2014年6月19日 下午6:27:34
 * @version V1.0   
 */

package com.jbeer.framework.server.monitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jbeer.framework.logging.Log;
import com.jbeer.framework.utils.LoggerUtil;

/**
 * <p>
 * 类功能说明:加载项目依赖的文件，扫描eclipse创建的项目，更具classpath文件来进行确定项目的依赖关
 * 以达到监听项目里面的每个文件变更
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: LoadProjectFiles.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年6月19日 下午6:27:34
 * @version V1.0
 */

public class EclipseProjectFilesScaner implements ProjectFileScaner {

	private static final String FILE_KIND = "kind";

	private static final String FILE_PATH = "path";

	private static final String SRC_KIND = "src";

	private static final String CLASS_PATH_ENTRY = "classpathentry";
	
	private static final String CLASS_PATH_SETTING=".classpath";
	
	private static final Log logger = LoggerUtil.generateLogger(EclipseProjectFilesScaner.class);

	/**
	 * 
	* <p>函数功能说明:分析classpath文件，解析项目和各个项目之间的关联关系，从而可以监听该项目所关联的项目文件变更状态</p>
	* <p>Bieber  2014年6月19日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	private  void analysisClassPathFile(Set<FileItem> files,
			String projectFile) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		File classPathSetting = new File(projectFile+File.separator+CLASS_PATH_SETTING);
		if (!classPathSetting.exists() || classPathSetting.isDirectory()) {
			return;
		}
		InputStream inputStream = new FileInputStream(classPathSetting);
		try {
			Document document = builder.parse(inputStream);
			Element root = document.getDocumentElement();
			NodeList list = root.getElementsByTagName(CLASS_PATH_ENTRY);
			for (int i = 0; i < list.getLength(); i++) {
				Element classpathentry = (Element) list.item(i);
				String kind = classpathentry.getAttribute(FILE_KIND);
				if (SRC_KIND.equals(kind)) {
					String path = classpathentry.getAttribute(FILE_PATH);
					if(path!=null&&(path.startsWith("/")||path.startsWith("\\"))){
						String[] paths = projectFile.split("[\\\\/]{1}");
						StringBuffer pathSB = new StringBuffer();
						for(int j = 0;j<paths.length-1;j++){
							pathSB.append(paths[j]).append(File.separator);
						}
						pathSB.setLength(pathSB.length()-1);
						projectFile=pathSB.toString()+path;
						analysisClassPathFile(files,projectFile);
					}else if(path!=null){
						scanSrc(projectFile+File.separator+path,files);
					}
				}

			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}
	
	/**
	 * 
	* <p>函数功能说明:扫描源码文件夹</p>
	* <p>Bieber  2014年6月19日</p>
	* <p>修改者名字 修改日期</p>
	* <p>修改内容</a>  
	* @return void
	 */
	private void scanSrc(String srcDir,Set<FileItem> fileItems) throws IOException{
		File srcFile = new File(srcDir);
		if(srcFile.exists()&&srcFile.isDirectory()){
			File[] files = srcFile.listFiles();
			for(File file :files){
				 if(file.isDirectory()){
					 scanSrc(file.getPath(),fileItems);
				 }else{
					 FileItem item = new FileItem();
					 item.setLastModify(file.lastModified());
					 item.setPath(file.getCanonicalPath());
					 item.setSize(file.length());
					 fileItems.add(item);
				 }
			}
		}
	}
	
 
	/**
	 * 对外提供的每个工程扫描入口
	 */
	public Set<FileItem> scanProject(String projectRoot) {
		Set<FileItem> fielItems = new HashSet<FileItem>();
		try {
			analysisClassPathFile(fielItems,projectRoot);
		} catch (Exception e) {
			if(logger.isDebugEnabled()){
				logger.debug("failed to scan project", e);
			}
		}
		return fielItems;
	}

}
