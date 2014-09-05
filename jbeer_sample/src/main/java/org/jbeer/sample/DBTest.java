/**   
* @Title: DBTest.java
* @Package com.jbeer.framework
* @author Bieber
* @date 2014年5月31日 下午2:20:04
* @version V1.0   
*/

package org.jbeer.sample;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import com.jbeer.framework.db.datasource.AbstractDataSource;
import com.jbeer.framework.db.datasource.JBeerDataSource;

/**
 * <p>类功能说明:TODO</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: DBTest.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年5月31日 下午2:20:04
 * @version V1.0
 */

public class DBTest {
	
	public static void main(String[] args){
		final AtomicInteger add = new AtomicInteger(0);
		final AbstractDataSource ds = new JBeerDataSource(30, 40, 10, 200, "root", "123456", "jdbc:mysql://localhost:3306/jbeer", "com.mysql.jdbc.Driver");
		
			for(int i=0;i<1000;i++){
				Thread t = new Thread(new Runnable(){

				@Override
				public void run() {
					try {
					Connection conn = ds.getConnection();
					conn.setAutoCommit(false);
					conn.prepareStatement("insert into user (name) values('bieber"+Thread.currentThread().getName()+"')").execute();
					conn.commit();
					conn.close();
					System.out.println(add.incrementAndGet()+", remainConnnect:"+ds.getCurrentActivitySize()+" total:"+ds.getCurrentTotalSize());
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
			},"Thread"+i);
				t.start();
			}
		
		
		
		
	}

}
