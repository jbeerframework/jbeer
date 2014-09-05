package org.jbeer.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.TestSuite;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.jbeer.framework.exception.JBeerException;
import com.jbeer.framework.parser.json.JSON;
import com.jbeer.framework.parser.json.JSONContentHandler;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
	
	public static void main(String[] args){
		 String temp="fdsfasdf\"fdsfasdf\"";
		 System.out.println((int)'\n');
	}
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
       try {
    	/*long start = System.currentTimeMillis();
		XMLParser parser = XMLParser.newParser(new FileInputStream(new File("E:\\test.xml")), (com.jbeer.framework.parser.xml.XMLContentHandler) new Handler());
		parser.parseXML();
		System.out.println(System.currentTimeMillis()-start);
		 start = System.currentTimeMillis();
		 SAXParserFactory factory = SAXParserFactory.newInstance();
		 SAXParser saxParser = factory.newSAXParser();
		 TestHandler saxHandler = new TestHandler();
		 saxParser.parse(new FileInputStream(new File("E:\\test.xml")), saxHandler);
		 System.out.println(System.currentTimeMillis()-start);*/
    	   //String json = "{ \"resultCode\": \"1000\", \"resultMsg\": \"操作成功\", \"sessionId\": \"2ecd2e21-ca9f-40c4-92c8-51924cd36c77174146596\", \"traceNo\": null, \"balance\": 0,  \"availableBalance\": 0, \"idCardType\": 1, \"idCardNo\": null, \"userInfo\": { \"userId\": \"1000010000031747\", \"account\": null, \"phoneNum\": \"13770000000\", \"realName\": null, \"accountType\": 2, \"accountStatus\": 1, \"headImageUrl\": null, \"imageType\": 0, \"authLevel\": \"D\", \"createTime\": \"1404285355000\", \"loginPasswdStrength\": null, \"payPasswdStrength\": null }, \"bankCardInfos\": null, \"verToken\": null, \"imTokenInfo\": { \"host\": \"183.63.51.122\", \"port\": 5222, \"token\": \"R/3S0rEjpM4fkIEMjmpWz/+zCVU=\", \"channelType\": \"10003\", \"tokenParams\": \"customid=1000010000031747&phone=13770000000&datetime=1405488157403&rn=39227\" }, \"yqbFarmData\": null }";
    	  long start = System.currentTimeMillis();
    	  String json="[{\"name\":\"user1\",\"owners\":[{\"name\":[1,2,3,4]}]},{\"name\":\"user2\",\"owners\":[{\"name\":[1,2,3,4]}]},{\"name\":\"user2\",\"owners\":[{\"name\":[1,2,3,4]}]}]";
    	  System.out.println(json);
    	  List<User> users = JSON.readList(json.getBytes(), User.class);
    	  System.out.println("parseTime:"+(System.currentTimeMillis()-start));
    	  System.out.println(users);
    	  start = System.currentTimeMillis();
    	  System.out.println(JSON.writeToJson(users));
    	  System.out.println("writeTime:"+(System.currentTimeMillis()-start));
    	  
    	  
    	  start = System.currentTimeMillis();
    	  ObjectMapper mapper = new ObjectMapper();
    	  users =mapper.readValue(json, new TypeReference<ArrayList<User>>() {
		});
    	  System.out.println("parseTime:"+(System.currentTimeMillis()-start));
    	  System.out.println(users);
    	  start = System.currentTimeMillis();
    	 System.out.println(mapper.writeValueAsString(users));
    	 System.out.println("writeTime:"+(System.currentTimeMillis()-start));
    	 
    	 
    	  start = System.currentTimeMillis();
    	  users = com.alibaba.fastjson.JSON.parseArray(json, User.class);
    	  System.out.println("parseTime:"+(System.currentTimeMillis()-start));
    	  System.out.println(users);
    	  start = System.currentTimeMillis();
    	  System.out.println(com.alibaba.fastjson.JSON.toJSONString(users));
    	  System.out.println("writeTime:"+(System.currentTimeMillis()-start));
    	  
    	  
	} catch (Exception e) {
		e.printStackTrace();
	} 
    }

    class Test{
    	private Map<String,Map> hello;
    }
    
    static class JSONHandler implements JSONContentHandler{

		public void startArray() {
			System.out.println("[");
		}

		public void startObject() {
			System.out.println("{");
			
		}

 

	 
		public void endArray() {
			System.out.println("]");
		}

		public void endObject() {
			System.out.println("}");
		}

		public void value(String value) {
			System.out.println(value);
		}

		public void key(String key) {
			System.out.print(key+":");
		}

	 
    	
    }
    /**
     * @return the suite of tests being tested
     */
    public static TestSuite suite()
    {
        return new TestSuite( AppTest.class );
    }

 
}
class TestHandler extends DefaultHandler{

	public void setDocumentLocator(Locator locator) {
		// TODO Auto-generated method stub
		
	}

	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void endPrefixMapping(String prefix) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void processingInstruction(String target, String data)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void skippedEntity(String name) throws SAXException {
		// TODO Auto-generated method stub
		
	}
	
}
class Handler implements com.jbeer.framework.parser.xml.XMLContentHandler{

	public void startDocument() {
		System.out.println("start parsing document");
	}

	public void tagStart(String name) {
		System.out.println("start parsing tag:"+name);
	}
	public void tagEnd(String name) {
		System.out.println("end parsing tag:"+name);
	}

	public void attribute(String name, String value) {
		System.out.println("start parsing attribute name:"+name+" value:"+value);
		
	}

	public void comments(String content) {
		System.out.println("end parsing comments:"+content);
	}

	public void text(String content) {
		System.out.println("parsing text:"+content);
	}

	public void endDocument() {
		System.out.println("end parsing document");
	}

	 
	
}
