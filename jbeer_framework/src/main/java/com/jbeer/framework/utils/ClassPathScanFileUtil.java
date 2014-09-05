/**   
* @Title: ClassPathScanUtil.java
* @Package com.jbeer.framework.utils
* @author Bieber
* @date 2014年6月1日 上午11:11:59
* @version V1.0   
*/

package com.jbeer.framework.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.jboss.vfs.VFS;
import org.jboss.vfs.VirtualFile;

import com.jbeer.framework.logging.Log;

/**
* <p>类功能说明:扫描classpath下的文件工具类</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: ClassPathScanUtil.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月1日 上午11:11:59
* @version V1.0
*/

public class ClassPathScanFileUtil {

    
    private static final Log logger = LoggerUtil.generateLogger(ClassPathScanFileUtil.class);
    
    private static final String CLASS_PATH=Thread.currentThread().getContextClassLoader().getResource("").getPath().startsWith("/")?Thread.currentThread().getContextClassLoader().getResource("").getPath().substring(1):Thread.currentThread().getContextClassLoader().getResource("").getPath();
    
    /**
     * 
    * <p>函数功能说明:扫描所有classpath文件方法</p>
    * <p>Bieber  2014年6月1日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return Set<String>
     */
    public static Set<String> scanClassPathFile(String matchedPath, String extendName, boolean isFullPath)
                                                                                                throws Exception {
        if(logger.isDebugEnabled()){
            logger.debug("starting scan dir "+(matchedPath==null||matchedPath.equals("")?"classpath root ":matchedPath)+" find "+(extendName==null||extendName.equals("")?"any":extendName)+" file");
        }
        Set<String> filePaths = new HashSet<String>();
        
        Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(matchedPath);
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            String protocol = url.getProtocol();
            if ("file".equals(protocol)) {
                String filePath = URLDecoder.decode(url.getPath(), "UTF-8");
                findAndAddClassesInPackageByFile(filePaths, filePath, matchedPath,
                    extendName, isFullPath);
            } else if ("jar".equals(protocol)) {
                JarFile jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
                scanJarFile(jarFile,isFullPath,filePaths,extendName,matchedPath);
            }else if("vfs".equals(protocol)){
            	VirtualFile virtualFile = VFS.getChild(url.toURI());
            	findAndAddClassesInVFS(filePaths, virtualFile, matchedPath,
                        extendName, isFullPath);
            	 
            }
        }
        return filePaths;
    }

    /**
     * 
    * <p>函数功能说明:扫描jar包下面的内容</p>
    * <p>Bieber  2014年6月1日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     */
    private static void scanJarFile(JarFile jarFile,boolean isFullPath,Set<String> filePaths,String extendName,String matchedPath){
        String jarName = jarFile.getName();
        jarName = jarName.replaceAll("\\"+File.separator, "");
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getName();
            if (name.endsWith(extendName)) {
                if(!isFullPath){
                    filePaths.add(name); 
                }else{
                filePaths.add(jarFile.getName()+name);
                }
               }
        }
    }
    private static void   findAndAddClassesInVFS(Set<String> filePaths,VirtualFile virtualFile,String matchedPath,String extendName,boolean isFullPath){
 	   if(virtualFile.isDirectory()){
 		  List<VirtualFile> children = virtualFile.getChildren();
 		  for(VirtualFile file:children){
 			 findAndAddClassesInVFS(filePaths,file,matchedPath,extendName,isFullPath);
 		  }
 	   }else{
 		   String filePath = virtualFile.getPathName();
          if (filePath.contains(matchedPath)) {
              if (filePath.endsWith(extendName)) {
                  if (!isFullPath) {
                      if(matchedPath==null||matchedPath.equals("")){
                          filePath = filePath.replace(CLASS_PATH, "");
                          int classPathIndex = filePath.indexOf("classes");
                          if(classPathIndex>0){
                              filePath=filePath.substring(classPathIndex+8);
                          }
                          filePaths.add(filePath.replace(CLASS_PATH, ""));
                      }else{
                      filePaths.add(filePath.substring(filePath.indexOf(matchedPath)));
                      }
                  } else {
                      filePaths.add(filePath);
                  }
              }
          }
 	   }
    }
    /**
     * 
    * <p>函数功能说明:扫描包下面的文件</p>
    * <p>Bieber  2014年6月1日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     */
    private static void findAndAddClassesInPackageByFile(Set<String> filePaths, String filePath,
                                                         String matchedPath, String extendName,
                                                         boolean isFullPath)
                                                                         throws UnsupportedEncodingException {
        File rootFile = new File(filePath);
        if (rootFile.exists()) {
            if (rootFile.isDirectory()) {
                File[] files = rootFile.listFiles();
                for (File file : files) {
                    findAndAddClassesInPackageByFile(filePaths,
                        URLDecoder.decode(file.getPath(), "UTF-8"), matchedPath, extendName, isFullPath);
                }
            } else {
                if(!File.separator.equals("/")){
                    filePath = filePath.replaceAll("\\\\", "/");
                }
                if (filePath.contains(matchedPath)) {
                    if (filePath.endsWith(extendName)) {
                        if (!isFullPath) {
                            if(matchedPath==null||matchedPath.equals("")){
                                filePath = filePath.replace(CLASS_PATH, "");
                                int classPathIndex = filePath.indexOf("classes");
                                if(classPathIndex>0){
                                    filePath=filePath.substring(classPathIndex+8);
                                }
                                filePaths.add(filePath.replace(CLASS_PATH, ""));
                                
                            }else{
                            filePaths.add(filePath.substring(filePath.indexOf(matchedPath)));
                            }
                        } else {
                            filePaths.add(filePath);
                        }
                    }
                }
            }
        }
    }
}
