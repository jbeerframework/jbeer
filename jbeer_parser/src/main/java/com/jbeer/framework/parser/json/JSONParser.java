/**   
 * @Title: JSONParser.java
 * @Package com.jbeer.framework.parser.json
 * @author Bieber
 * @date 2014年7月15日 下午4:56:48
 * @version V1.0   
 */

package com.jbeer.framework.parser.json;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import com.jbeer.framework.parser.ContentHandler;
import com.jbeer.framework.parser.Parser;
import com.jbeer.framework.parser.exception.ParseException;

/**
 * <p>
 * 类功能说明:JSON解析器
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: JSONParser.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月15日 下午4:56:48
 * @version V1.0
 */

public class JSONParser extends Parser {

	// 匹配数字正则表达式
	private static final Pattern NUM_PATTERN = Pattern
			.compile("^[-]{0,1}[0-9]{1,}(\\.[0-9]{1,}){0,1}([eE]{1}[+-]{0,1}[0-9]{1,}){0,1}$");

	private JSONContentHandler handler;

	protected JSONParser(InputStream is) {
		super(is);
	}

	protected JSONParser(InputStream is, String encoding) {
		super(is, encoding);
	}

	public static JSONParser newParser(InputStream is,
			JSONContentHandler handler) throws ParseException {
		JSONParser parser = new JSONParser(is);
		parser.bindContentHandler(handler);
		return parser;
	}

	public static JSONParser newParser(InputStream is,
			JSONContentHandler handler, String encoding) throws ParseException {
		JSONParser parser = new JSONParser(is, encoding);
		parser.bindContentHandler(handler);
		return parser;
	}

	public static JSONParser newParser(String json, JSONContentHandler handler,
			String encoding) throws ParseException {
		if (json == null) {
			throw new ParseException("parameter json must not be null");
		}
		return newParser(json.getBytes(), handler, encoding);
	}

	public static JSONParser newParser(String json, JSONContentHandler handler)
			throws ParseException {
		if (json == null) {
			throw new ParseException("parameter json must not be null");
		}
		return newParser(json.getBytes(), handler);
	}

	public static JSONParser newParser(byte[] jsonBytes,
			JSONContentHandler handler, String encoding) throws ParseException {
		if (jsonBytes == null) {
			throw new ParseException("parameter jsonBytes must not be null");
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(jsonBytes);
		return newParser(bais, handler, encoding);
	}

	public static JSONParser newParser(byte[] jsonBytes,
			JSONContentHandler handler) throws ParseException {
		if (jsonBytes == null) {
			throw new ParseException("parameter jsonBytes must not be null");
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(jsonBytes);
		return newParser(bais, handler);
	}

	public void parseJSON() throws IOException, ParseException {

		while ((c = readBuffer()) != -1) {
			if (c == 123) {// {
				handler.startObject();
				readObject();
			} else if (c == 91) {// [
				handler.startArray();
				readArray();
			}else if(isSkip(c)){
				continue;
			}else if(58 == c){//:
				handler.key(getContent());
				readValue();
			}
		}
	}

	private boolean checkSimpleContent(String content) {
		if (content.equalsIgnoreCase("true")
				|| content.equalsIgnoreCase("false")
				|| content.equalsIgnoreCase("null")
				|| NUM_PATTERN.matcher(content).matches()) {
			return true;
		}
		return false;
	}

	private boolean isSkip(int c) {
		return 32 == c || 13 == c || 10 == c || 9 == c;
	}

	private void readObject() throws IOException, ParseException {
		while ((c = readBuffer()) != -1) {
			if (125 == c) {// }
				handler.endObject();
				break;
			}else if(c == 123){
				handler.startObject();
			} else if (58 == c) {// :
				handler.key(getContent());
				readValue();
			}else if(isSkip(c)){
				continue;
			}else if(34==c){//"
				continue;
			} else {
				baos.write(c);
			}
		}
	}

	private void handlerSimpleValue() throws ParseException, UnsupportedEncodingException{
		if (baos.size() > 0) {
			String content = getContent();
			if (checkSimpleContent(content)) {
				handler.value(content);
			} else {
				throw new ParseException(content
						+ " is not a valid value");
			}
		}
	}
	
	private void readArray() throws IOException, ParseException {
		while ((c = readBuffer()) != -1) {
			if (c == 93) {// ]
				handlerSimpleValue();
				handler.endArray();
				break;
			} else if (c == 123) {// {
				handler.startObject();
				readObject();
			}else if(isSkip(c)){
				continue;
			} else if (44 == c) {// ,
				handlerSimpleValue();
			}else if(34==c){//"
				readString();
				handler.value(getContent());
			}else {
				baos.write(c);
			}
		}
	}

	private void readValue() throws IOException, ParseException {
		while ((c = readBuffer()) != -1) {
			if (c == 123) {// {
				handler.startObject();
				readObject();
			} else if (c == 91) {// [
				handler.startArray();
				readArray();
			} else if (34 == c) {// "
				readString();
				handler.value(getContent());
			}else if(isSkip(c)){
				continue;
			} else if (44 == c) {// ,
				handlerSimpleValue();
				break;
			}else if(125==c){
				handlerSimpleValue();
				handler.endObject();
			}else if(93==c){
				handlerSimpleValue();
				handler.endArray();
			} else {
				baos.write(c);
			}
		}
	}

	private void readString() throws IOException {
		int last = -1;
		while ((c = readBuffer()) != -1) {
			if (34 == c && last != 92) {// 遇到"并且上一个不是\
				break;
			} else {
				baos.write(c);
			}
			last = c;
		}
	}

	@Override
	protected void bindContentHandler(ContentHandler handler)
			throws ParseException {
		if (handler instanceof JSONContentHandler) {
			this.handler = (JSONContentHandler) handler;
			return;
		}
		throw new ParseException("bind content handler "
				+ handler.getClass().getName() + " not instance of "
				+ JSONContentHandler.class.getName());
	}

}
