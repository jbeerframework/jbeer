/**   
* @Title: Parser.java
* @Package com.jbeer.framework.parser
* @author Bieber
* @date 2014年7月15日 下午4:57:14
* @version V1.0   
*/

package com.jbeer.framework.parser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import com.jbeer.framework.parser.exception.ParseException;

/**
 * <p>类功能说明:抽象的解析器</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: Parser.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月15日 下午4:57:14
 * @version V1.0
 */

public abstract class Parser {

	
	protected InputStream is;
	
	protected int c = -1;

	protected ByteArrayOutputStream baos;

	protected String encoding = "UTF-8";
	
	protected byte[] buffer = null;
	
	protected int bufferSize=2048;
	
	protected int realSize=0;
	
	protected int currentBufferIndex=0;
		
	protected abstract void bindContentHandler(ContentHandler handler) throws ParseException;
	
	protected Parser(InputStream is) {
		this.is = is;
		this.baos = new ByteArrayOutputStream();
		buffer=new byte[bufferSize];
	}

	protected Parser(InputStream is,String encoding) {
		this.is = is;
		this.baos = new ByteArrayOutputStream();
		this.encoding = encoding;
		buffer=new byte[bufferSize];
	}
	protected int readBuffer() throws IOException{
		if(realSize==0){
			read();
		}else if(realSize==currentBufferIndex){
			currentBufferIndex=0;
			read();
		}
		if(realSize>currentBufferIndex){
			int temp = buffer[currentBufferIndex];
			currentBufferIndex++;
			return temp;
		}else{
			return -1;
		}
	}
	protected int readBuffer(byte[] buffer) throws IOException{
		int i=0;
		for(;i<buffer.length;i++){
			int temp = readBuffer();
			if(temp>=0){
				buffer[i]=(byte) temp;
			}else{
				break;
			}
		}
		return i;
	}
	
	
	protected void read() throws IOException{
		realSize=is.read(buffer,0,bufferSize);
	}
	protected String getContent() throws UnsupportedEncodingException {
		String content = new String(baos.toByteArray(), encoding);
		baos.reset();
		return content;
	}
}
