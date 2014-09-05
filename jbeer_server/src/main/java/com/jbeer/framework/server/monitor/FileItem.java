/**   
* @Title: FileItem.java
* @Package com.jbeer.framework.server.monitor
* @author Bieber
* @date 2014年6月19日 下午9:21:14
* @version V1.0   
*/

package com.jbeer.framework.server.monitor;


/**
 * <p>类功能说明:文件实体</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: FileItem.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年6月19日 下午9:21:14
 * @version V1.0
 */

public class FileItem {
	 private String path;
     
     private long lastModify;
     
     private long size;

     /**
	 * @return lastModify
	 */
	
	public long getLastModify() {
		return lastModify;
	}

	/**
	 * @param lastModify lastModify
	 */
	
	public void setLastModify(long lastModify) {
		this.lastModify = lastModify;
	}

	/**
     * @return path
     */
     
     public String getPath() {
         return path;
     }

     /**
     * @param path path
     */
     
     public void setPath(String path) {
         this.path = path;
     }

   
     /**
     * @return size
     */
     
     public long getSize() {
         return size;
     }

     /**
     * @param size size
     */
     
     public void setSize(long size) {
         this.size = size;
     }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FileItem [path=" + path + ", lastModify=" + lastModify
				+ ", size=" + size + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (lastModify ^ (lastModify >>> 32));
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + (int) (size ^ (size >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileItem other = (FileItem) obj;
		if (lastModify != other.lastModify)
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (size != other.size)
			return false;
		return true;
	}

      
     
}
