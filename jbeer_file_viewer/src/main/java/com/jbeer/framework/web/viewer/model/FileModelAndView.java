/**   
* @Title: FileModelAndView.java
* @Package com.jbeer.framework.web.viewer.model
* @author Bieber
* @date 2014年6月17日 上午11:21:10
* @version V1.0   
*/

package com.jbeer.framework.web.viewer.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.jbeer.framework.web.ModelAndView;

/**
* <p>类功能说明:文件下载视图</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: FileModelAndView.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月17日 上午11:21:10
* @version V1.0
*/

public class FileModelAndView extends ModelAndView {
    private File         file;

    private InputStream is;

    private boolean      isDownload = true;

    private String       extendName;
    
    private String fileName;
    
    private byte[] bytes;
    
    private long size;
    
    
    

    /**
	 * @return size
	 */
	
	public long getSize() {
		if(bytes!=null){
			size =bytes.length;
		}
		return size;
	}

	/**
	 * @param size size
	 */
	
	public void setSize(long size) {
		
		this.size = size;
	}

	/**
	 * @return bytes
	 */
	
	public byte[] getBytes() {
		
		return bytes;
	}

	/**
	 * @param bytes bytes
	 */
	
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public FileModelAndView(boolean isDownload) {
        this.type = ViewType.FILE;
        this.isDownload = isDownload;
    }

    /**
    * @return fileName
    */
    
    public String getFileName() {
        return fileName;
    }

    /**
    * @param fileName fileName
    */
    
    public FileModelAndView setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    /**
    * @return file
    */

    public File getFile() {
        return file;
    }

    /**
    * @param file file
    */

    public void setFile(File file) {
        this.file = file;
    }

    /**
    * @return os
     * @throws FileNotFoundException 
    */

    public InputStream getInputStream() throws FileNotFoundException {
        if(file!=null){
            is = new FileInputStream(getFile());
        }else if(bytes!=null){
        	is = new ByteArrayInputStream(bytes);
        }
        return is;
    }

    /**
    * @return isDownload
    */

    public boolean isDownload() {
        return isDownload;
    }

    /**
    * @param isDownload isDownload
    */

    public void setDownload(boolean isDownload) {
        this.isDownload = isDownload;
    }

    /**
    * @return extendName
    */

    public String getExtendName() {
        return extendName;
    }

    protected FileModelAndView() {
        this.type = ViewType.FILE;
    }
    
    public static FileModelAndView newFileModelAndView(){
    	return new FileModelAndView();
    }

    public FileModelAndView setData(File file) {
        this.file = file;
        return this;
    }

    public FileModelAndView setInputStream(InputStream is) {
        this.is = is;
        return this;
    }

    public FileModelAndView setExtendName(String extendName) {
        this.extendName = extendName;
        return this;
    }
}
