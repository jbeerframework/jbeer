/**   
* @Title: User.java
* @Package org.jbeer.sample.bean
* @author Bieber
* @date 2014-5-11 下午1:45:05
* @version V1.0   
*/

package org.jbeer.sample.bean;

import java.io.File;
import java.util.Date;

import com.jbeer.framework.annotation.Column;
import com.jbeer.framework.annotation.Entity;

/**
 * <p>类功能说明:TODO</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: User.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-5-11 下午1:45:05
 * @version V1.0
 */
@Entity(name="user")
public class User {

	@Column(isAutoGenerate=true,isPrimarykey=true)
	private Integer id;
	
	private String userName;
	
	private Integer userAge;
	
	private Date createTime;
	
	private File file;
	

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return createTime
	 */
	
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime createTime
	 */
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return id
	 */
	
	public Integer getId() {
		return id;
	}

	/**
	 * @param id id
	 */
	
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return userName
	 */
	
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName userName
	 */
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return userAge
	 */
	
	public Integer getUserAge() {
		return userAge;
	}

	/**
	 * @param userAge userAge
	 */
	
	public void setUserAge(Integer userAge) {
		this.userAge = userAge;
	}
	
}
