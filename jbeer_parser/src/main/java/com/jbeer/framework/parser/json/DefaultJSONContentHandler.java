/**   
* @Title: DefaultJSONContentHandler.java
* @Package com.jbeer.framework.parser.json
* @author Bieber
* @date 2014年7月16日 下午5:30:14
* @version V1.0   
*/

package com.jbeer.framework.parser.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * <p>类功能说明:json内容拦截器默认实现</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: DefaultJSONContentHandler.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年7月16日 下午5:30:14
 * @version V1.0
 */

public class DefaultJSONContentHandler implements JSONContentHandler {

	
	private static final int ARRAY_TYPE = 1;
	
	private static final int OBJECT_TYPE = 2;
	
	private Object value = null;
	
	private int jsonType = -1;
	
	private  Stack<Integer> VALUE_TYPE_STACK=new Stack<Integer>();
	
	private  Stack<Object> VALUE_STACK = new Stack<Object>();
	
	private  Stack<String> KEY_STACK = new Stack<String>();
	
	/* (non-Javadoc)
	 * @see com.jbeer.framework.parser.json.JSONContentHandler#startArray()
	 */
	public void startArray() {
		VALUE_TYPE_STACK.push(ARRAY_TYPE);
		if(jsonType==-1){
			jsonType=ARRAY_TYPE;
		}
		VALUE_STACK.push(new ArrayList<Object>());
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.parser.json.JSONContentHandler#endArray()
	 */
	public void endArray() {
		assembleValue();
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.parser.json.JSONContentHandler#startObject()
	 */
	public void startObject() {
		VALUE_TYPE_STACK.push(OBJECT_TYPE);
		if(jsonType==-1){
			jsonType=OBJECT_TYPE;
		}
		VALUE_STACK.push(new HashMap<String,Object>());
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.parser.json.JSONContentHandler#endObject()
	 */
	public void endObject() {
		assembleValue();
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.parser.json.JSONContentHandler#value(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void value(String value) {
		int currentType = VALUE_TYPE_STACK.peek();
		if(currentType==ARRAY_TYPE){
			List<Object> currentValue = (List<Object>) VALUE_STACK.peek();
			currentValue.add(value);
		}else{
			Map<String,Object> currentValue =  (Map<String, Object>) VALUE_STACK.peek();
			currentValue.put(KEY_STACK.pop(), value);
		}
	}

	/* (non-Javadoc)
	 * @see com.jbeer.framework.parser.json.JSONContentHandler#key(java.lang.String)
	 */
	public void key(String key) {
		KEY_STACK.push(key);
	}
	
	@SuppressWarnings("unchecked")
	private void assembleValue(){
		VALUE_TYPE_STACK.pop();
		if(VALUE_TYPE_STACK.size()>0){
			int lastType = VALUE_TYPE_STACK.peek();
			Object currentValue = VALUE_STACK.pop();
			if(lastType==ARRAY_TYPE){
				List<Object> lastValue = (List<Object>) VALUE_STACK.peek();
				lastValue.add(currentValue);
			}else {
				Map<String,Object> lastValue = (HashMap<String, Object>) VALUE_STACK.peek();
				lastValue.put(KEY_STACK.pop(), currentValue);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> getList(){
		if(jsonType==OBJECT_TYPE){
			throw new IllegalAccessError("can not ");
		}
		return (List<Object>)getValue();
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> getMap(){
		return (Map<String, Object>) getValue();
	}
	
	private Object getValue(){
		if(value ==null&&VALUE_STACK.size()>0){
			value=VALUE_STACK.pop();
		}
		return value;
	}
	
	public boolean isArray(){
		return jsonType==ARRAY_TYPE;
	}
	
	public boolean isObject(){
		return jsonType==OBJECT_TYPE;
	}

}
