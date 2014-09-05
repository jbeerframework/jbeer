/**   
* @Title: StreamUtils.java
* @Package com.jbeer.framework.utils
* @author Bieber
* @date 2014-2-22 下午01:59:03
* @version V1.0   
*/

package com.jbeer.framework.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

/**
 * <p>类功能说明:IO流的工具类</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: StreamUtils.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-2-22 下午01:59:03
 * @version V1.0
 */

public class FileUtils {

    /**
     * 
    * <p>函数功能说明:NIO的零拷贝</p>
    * <p>Bieber  2014-3-9</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     */
    public static void copyFile(File src, File dest) throws IOException {
        FileOutputStream fos = new FileOutputStream(dest);
        FileInputStream fis = new FileInputStream(src);
        FileChannel reader = fis.getChannel();
        FileChannel writer = fos.getChannel();
        writer.transferFrom(reader, 0, reader.size());
        reader.close();
        writer.close();
        fos.close();
        fis.close();
    }

    public static File createFile(byte[] bytes, String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        OutputStream os = new FileOutputStream(file);
        try {
            os.write(bytes, 0, bytes.length);
        } finally {
            os.flush();
            os.close();
        }
        return file;
    }

    public static byte[] readFile(String filePath) throws IOException {
        return readFile(new File(filePath));
    }

    public static byte[] readFile(File file) throws IOException{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        InputStream is = new FileInputStream(file);
        try{
        byte[] buffer = new byte[1024];
        int offset=0;
        while((offset=is.read(buffer, 0, 1024))>0){
            bos.write(buffer, 0, offset);
        }}finally{
            is.close();
            bos.flush();
        }
        return bos.toByteArray();
    }

}
