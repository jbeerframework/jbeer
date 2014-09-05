/**   
 * @Title: XMLParser.java
 * @Package com.jbeer.framework.parser.xml
 * @author Bieber
 * @date 2014年7月12日 下午4:48:55
 * @version V1.0   
 */

package com.jbeer.framework.parser.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

import com.jbeer.framework.parser.ContentHandler;
import com.jbeer.framework.parser.Parser;
import com.jbeer.framework.parser.exception.ParseException;

/**
 * <p>
 * 类功能说明:XML解析器
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: XMLParser.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月12日 下午4:48:55
 * @version V1.0
 */

public class XMLParser extends Parser{
	private XMLContentHandler handler;
	private Stack<String> tagNameStack = new Stack<String>();
	
	
	
	protected XMLParser(InputStream is) {
		super(is);
	}

	protected XMLParser(InputStream is, String encoding) {
		super(is,encoding);
	}

	public static XMLParser newParser(InputStream is, XMLContentHandler handler) throws ParseException {
		XMLParser parser = new XMLParser(is);
		parser.bindContentHandler(handler);
		return parser;
	}

	public static XMLParser newParser(InputStream is, XMLContentHandler handler,
			String encoding) throws ParseException {
		XMLParser parser = new XMLParser(is,encoding);
		parser.bindContentHandler(handler);
		return parser;
	}

	public void parseXML() throws ParseException {
		try {
			handler.startDocument();
			parse();
			handler.endDocument();
		} catch (Exception  e) {
			 throw new ParseException(e.getMessage(),e);
		} finally {
			try{
			finished();
			}catch(Exception e){
				throw new ParseException(e);
			}
		}
	}

	private void parse() throws Exception, ParseException {
		while((c=readBuffer())!=-1){
			if (60 == c) {// 标签开始付<
				startParseTag();
			}
		}
	}
	
	
	private void startParseTag() throws ParseException, Exception {
		boolean isStartTag = true;
		while ((c = readBuffer()) != -1) {
			if (isSkip(c)) {
				/**
				 * 当遇到空格或者其他可以或忽略的符号，并且还没触发开始解析标签情况下，则表示开始标签解析结束，
				 * 该情况是正对开始标签中有属性字段
				 */
				if (baos.size() > 0 && isStartTag) {
					String tempTagName = getContent();
					tagNameStack.push(tempTagName);
					handler.tagStart(tempTagName);
				}
				continue;
			} else if (63 == c) {// xml头的解析?
				parseXMLHead();
				break;
			} else if (60 == c && isStartTag) {// 为了解析嵌套标签<,并且是开始标签，则是嵌套标签
				startParseTag();
				break;
			} else if (62 == c) {// >是标签头解析结束的标致，该情况下还有解析介绍标签的时候</tag>
				// 该种情况是对应开始标签没有属性情况<tag>
				if (isStartTag && baos.size() > 0) {// 避免解析完包含属性的标签会遇到>符号，并且会进入该段区域
					String tempTagName = getContent();
					tagNameStack.push(tempTagName);
					handler.tagStart(tempTagName);
				} else if (!isStartTag) {// 此时是进入结束标签解析，解析完毕之后则退出,表示一个标签解析结束,避免开始标签直接/>结束
					String tempTagName = getContent();
					String expect = tagNameStack.pop();
					if (!expect.equals(tempTagName) && tempTagName.length() > 0) {
						throw new ParseException("expect tag name is " + expect
								+ " actual is " + tempTagName);
					}
					handler.tagEnd(expect);
					break;
				}
				if (isStartTag) {
					parseText(false, null);// 触发完毕开始标签后，则进入解析文本
				}
			} else if (47 == c) {// 反斜杠表示标签解析结束
				isStartTag = false;// 进入结束标签
			} else if (33 == c) {// 遇到感叹号，则表示是一个注解开始或者是cdata的开始
				parseComments();
				break;
			} else if (61 == c) {// 当是等于号的时候表示是一个属性的开始，则进入属性解析模块
				parseAttribute(getContent());
			} else {
				baos.write(c);
			}
		}
	}
	
	
	

	private void parseComments() throws Exception, ParseException {
		while ((c = readBuffer()) != -1) {
			if (45 == c) {// 如果是-则表示是注释
				continue;
			} else if (62 == c) {// >表示注释结束
				handler.comments(getContent());
				break;
			} else if (91 == c) {// [表示是CDATA开始
				parseText(true, null);
				break;
			} else {
				baos.write(c);
			}
		}
	}

	private void parseText(boolean isCDATA, String cdataText) throws Exception, ParseException {
		boolean startParseText = false;
		if (isCDATA) {// [
			byte[] cdata = new byte[6];
			readBuffer(cdata);
		}
		int lastByte = -1;
		boolean cdataEnd = false;
		while ((c = readBuffer()) != -1) {
			if (!startParseText && isSkip(c)) {
				continue;
			} else if (isCDATA&&93 == c) {// ]
				if (lastByte == c) {
					cdataEnd = true;
				} else {
					lastByte = c;
				}
			} else if (isCDATA && cdataEnd) {
				c = readBuffer();
				if (60 == c) {// < 读取完毕了一个cdata
					handler.text(getContent());
					startParseTag();
					break;
				} else {
					parseText(false, getContent());
					break;
				}
			} else if (!isCDATA && 60 == c) {// <,非CDATA模式
				if (baos.size() <= 0) {// 遇到<退回
					startParseTag();
					break;
				} else {
					if (cdataText == null) {
						handler.text(getContent());
					} else {
						handler.text(cdataText + getContent());
					}
					break;
				}
			} else {
				startParseText = true;
				baos.write(c);
			}
		}
	}

	private void parseXMLHead() throws IOException {
		while ((c = readBuffer()) != -1) {
			if (isSkip(c)) {
				getContent();
				continue;
			} else if (61 == c) {// 当是等于号的时候表示是一个属性的开始，则进入属性解析模块
				parseAttribute(getContent());
			} else if (63 == c) {// 当解析消息头的时候再次遇到问号，则表示头解析结束
				break;
			} else {
				baos.write(c);
			}
		}
	}

	private void parseAttribute(String attrName) throws IOException {
		while ((c = readBuffer()) != -1) {
			if (34 == c) {// 双引号是一个属性value的开始
				if (baos.size() > 0) {
					handler.attribute(attrName, getContent());
					break;
				} else {
					continue;
				}
			} else {
				baos.write(c);
			}
		}
	}

	private boolean isSkip(int c) {
		return 32 == c || 13 == c || 10 == c || 9 == c;
	}



	private void finished() throws IOException {
		if (is != null) {
			is.close();
		}
	}

	@Override
	protected void bindContentHandler(ContentHandler handler) throws ParseException {
		if(handler instanceof XMLContentHandler){
			this.handler = (XMLContentHandler) handler;
			return ;
		}
		throw new ParseException("bind content handler "+handler.getClass().getName()+" not instance of "+XMLContentHandler.class.getName());
	}

}
