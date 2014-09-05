/**   
* @Title: TypeConverter.java
* @Package com.jbeer.framework.db.type
* @author Bieber
* @date 2014年6月6日 下午1:00:52
* @version V1.0   
*/

package com.jbeer.framework.db.type;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;

/**
* <p>类功能说明:类型转换器</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: TypeConverter.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月6日 下午1:00:52
* @version V1.0
*/

public class TypeConverter {

    public static Object convertToPojoType(ResultSet rs, int columnIndex, int type)
                                                                                   throws SQLException {
        switch (type) {
            case Types.SMALLINT:
                return Integer.parseInt(rs.getObject(columnIndex).toString());
            case Types.BIGINT:
                return Integer.parseInt(rs.getObject(columnIndex).toString());
            case Types.TINYINT:
                return Integer.parseInt(rs.getObject(columnIndex).toString());
            case Types.INTEGER:
                return Integer.parseInt(rs.getObject(columnIndex).toString());
            case Types.DOUBLE:
                return Double.parseDouble(rs.getObject(columnIndex).toString());
            case Types.FLOAT:
                return Float.parseFloat(rs.getObject(columnIndex).toString());
            case Types.CHAR:
                return rs.getObject(columnIndex).toString();
            case Types.VARCHAR:
                return rs.getObject(columnIndex).toString();
            case Types.DATE: {
                Date jdbcdate = (Date) rs.getObject(columnIndex);
                java.util.Date date = new java.util.Date();
                date.setTime(jdbcdate.getTime());
                return date;
            }
            case Types.TIME: {
                Time timer = (Time) rs.getObject(columnIndex);
                java.util.Date date = new java.util.Date();
                date.setTime(timer.getTime());
                return date;
            }
            case Types.TIMESTAMP:{
                Timestamp timestamp = (Timestamp) rs.getObject(columnIndex);
                java.util.Date date = new java.util.Date();
                date.setTime(timestamp.getTime());
                return date;
            }
            default:
                return rs.getObject(columnIndex);
        }
    }

    public static Object convertToJdbcType(Object object) {
        return object;
    }

}
