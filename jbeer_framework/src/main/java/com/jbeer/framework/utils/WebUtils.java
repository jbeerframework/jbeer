/**   
 * @Title: WebUtils.java
 * @Package com.jbeer.framework.utils
 * @author Bieber
 * @date 2014-2-22 下午01:43:44
 * @version V1.0   
 */

package com.jbeer.framework.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.jbeer.framework.JBeer;
import com.jbeer.framework.constant.JBeerConstant;
import com.jbeer.framework.logging.Log;
import com.jbeer.framework.web.JBeerWebContext;

/**
 * <p>
 * 类功能说明:处理web请求的工具
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: WebUtils.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-2-22 下午01:43:44
 * @version V1.0
 */

public class WebUtils {
	
	public static final String NOTFOUND="404";
	
	public static final String NOT_REQUIRED_PARAMETER="400";
	
	public static final String NOT_SUPPORT_METHOD="401";
	
	public static final String SYSTEM_ERROR="500";
	
	private static final Log logger = LoggerUtil.generateLogger(WebUtils.class);
	/**
	 * 
	 * <p>
	 * 函数功能说明:对request中的参数进行分析，通过"."和"[number]"来判断对象层级关系以及数组等信息，
	 * 通过Map和List对象来封装这些信息，Map表示是一个对象，List表示是一个对象数组
	 * </p>
	 * <p>
	 * Bieber 2014-3-8
	 * </p>
	 * <p>
	 * 修改者名字 修改日期
	 * </p>
	 * <p>
	 * 修改内容</a>
	 * 
	 * @return Map<String,Object>
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> analyseRequestParameters(
			Map<String, Object> parameters) {
		Pattern pattern = Pattern.compile("\\[[0-9]*\\]");
		Map<String, Object> analyseResult = new HashMap<String, Object>();
		Set<String> keys = parameters.keySet();
		//分析每个键值
		for (String key : keys) {
			Object value = parameters.get(key);
			//判断是否是存在对象层级关系，当前只拆分两层
			String[] items = key.split("\\.", 2);
			//判断当前一层是否是数组[]
			Matcher matcher = pattern.matcher(items[0]);
			int index = 0;
			boolean found = matcher.find();
			if (found) {
				index = Integer.parseInt(matcher.group().replaceAll("[\\[\\]]",
						""));
			}
			//去除[]信息
			String newItem = matcher.replaceAll("");
			if (items.length > 1) {//如果当前分层大于1，则表示存在多层对象引用关系
				if(found){//表示当前层是一个集合数组
					List<Map<String,Object>> itemValues = (List<Map<String, Object>>) analyseResult.get(newItem);
					if (itemValues == null) {
						itemValues = generateList(index+1,null);
						Map<String, Object> itemValue = new HashMap<String, Object>();
						itemValue.put(items[1], value);
						itemValues.set(index, itemValue);
						analyseResult.put(newItem, itemValues);
					}else{
						if(itemValues.size()>index){
							Map<String,Object> itemValue = itemValues.get(index);
							if(itemValue==null){
								itemValue = new HashMap<String,Object>();
								itemValues.set(index, itemValue);
							}
							itemValue.put(items[1], value);
						}else{
							List<Map<String,Object>> tempValues=generateList(index+1,itemValues);
							itemValues=tempValues;
							Map<String, Object> itemValue = new HashMap<String, Object>();
							itemValue.put(items[1], value);
							itemValues.set(index, itemValue);
							analyseResult.put(newItem, itemValues);
						}
					}
				}else{
					Map<String, Object> itemValue = (Map<String, Object>) analyseResult.get(newItem);
					if(itemValue==null){
						itemValue = new HashMap<String,Object>();
						analyseResult.put(newItem, itemValue);
					}
					itemValue.put(items[1], value);
				}
			} else {
				if (found) {
					if(value.getClass().getComponentType()!=null){
					Object[] valArray = (Object[]) value;
					WebUtils.addParametervalue(analyseResult, newItem,
							valArray[0]);
					}else{
						WebUtils.addParametervalue(analyseResult, newItem,value);
					}
				} else {
					if(value.getClass().isArray()){
						Object[] valArray = (Object[]) value;
						if(valArray.length>1){
							analyseResult.put(newItem, valArray);
						}else{
							analyseResult.put(newItem, valArray[0]);
						}
					}else{
						analyseResult.put(newItem, value);
					}
				}
			}
		}
		for(Entry<String, Object> entry:analyseResult.entrySet()){
			Object value = entry.getValue();
			if(Map.class.isAssignableFrom(value.getClass())){
				entry.setValue(analyseRequestParameters((Map<String, Object>) value));
			}else if(Collection.class.isAssignableFrom(value.getClass())){
				List<Object> values = (List<Object>) value;
				for(int i=0;i<values.size();i++){
					Object object = values.get(i);
					if(Map.class.isAssignableFrom(object.getClass())){
						Map<String,Object> itemValue = (Map<String, Object>) object;
						values.set(i, analyseRequestParameters(itemValue));
					}
				}
			}
		}
		return analyseResult;
	}

	
	public static<T> List<T> generateList(int size,List<T> origin){
		List<T> list = new ArrayList<T>();
		for(int i=0;i<size;i++){
			if(origin!=null&&origin.size()>i){
			list.add(origin.get(i));
			}else{
				list.add(null);
			}
		}
		return list;
	}
	/**
	 * 
	 * <p>
	 * 函数功能说明: 从request获取上传的文件
	 * </p>
	 * <p>
	 * Bieber 2014-3-2
	 * </p>
	 * <p>
	 * 修改者名字 修改日期
	 * </p>
	 * <p>
	 * 修改内容</a>
	 * 
	 * @return Map<String,File>
	 */
	public static Map<String, Object> getParameterForUpload(
			HttpServletRequest request, String tempDir) throws IOException {
		Map<String, Object> parameterMaps = new HashMap<String, Object>();
		// 获取请求的输入流
		ServletInputStream inputStream = request.getInputStream();
		// 解析HTTP请求的头内容，从而判断是否是上传文件格式
		String contentType = request.getContentType();
		if (contentType == null
				|| !contentType.contains(JBeerConstant.UPLOAD_FILE_CONTENTTYPE)) {
			return parameterMaps;
		}
		File tempFile = null;
		String paramName = null;
		String fileName = null;
		String[] items = null;
		String boundary = null;
		byte[] lineBuffer = null;
		ByteArrayOutputStream textParameterOutputStream = null;
		contentType = contentType.replaceAll("[\\s\"]{1,}", "");
		String[] contentTypeItems = contentType.split(";");
		boolean startReadParam = false;
		try {
			/**
			 * 获取HTTP头内容中分隔符
			 */
			for (String contentTypeItem : contentTypeItems) {
				if (contentTypeItem
						.startsWith(JBeerConstant.HTTP_BOUNDARY_NAME)) {
					boundary = contentTypeItem
							.substring(JBeerConstant.HTTP_BOUNDARY_NAME
									.length() + 1);
				}
			}
			String lineStr = null;
			FileOutputStream fos = null;
			boolean newParameter = false;
			boolean isTextParameter = true;
			if (contentType.contains(JBeerConstant.UPLOAD_FILE_CONTENTTYPE)) {
				/**
				 * 通过次方法获取输入流的一行字节码
				 */
				lineBuffer = readLine(inputStream);
				/**
				 * 循环获取输入内容
				 */
				while (lineBuffer != null) {
					lineStr = new String(lineBuffer,
							JBeer.getApplicationEncode());
					/**
					 * 解析一行的内容从而判断当前读取的内容，进行相应的处理
					 */
					if (lineStr
							.startsWith(JBeerConstant.UPLOAD_FILE_DESPOSITION)) {
						lineStr = lineStr.replaceAll("[\\s\"]{1,}", "");
						items = lineStr.split(JBeerConstant.HTTP_SPLIT_FLAG);
						for (String item : items) {
							if (item.startsWith(JBeerConstant.UPLOAD_FILE_NAME)) {
								fileName = item
										.substring(JBeerConstant.UPLOAD_FILE_NAME
												.length() + 1);
								// fileName = new
								// String(fileName.getBytes("ISO8859-1"),JBeerConfiguration.applicationEncode);
							} else if (item
									.startsWith(JBeerConstant.UPLOAD_PARAMATER_NAME)) {
								paramName = item
										.substring(JBeerConstant.UPLOAD_PARAMATER_NAME
												.length() + 1);
							}
						}
						newParameter = true;
						lineBuffer = readLine(inputStream);
						continue;
					} else if (lineStr
							.startsWith(JBeerConstant.UPLOAD_FILE_CONTENTTYPE_HEAD)) {
						isTextParameter = false;
						lineBuffer = readLine(inputStream);
						continue;
					} else if (lineStr.contains(boundary)) {// 当读取到一个分隔符，表示是一个字段的开始或者结束
						if (fos != null) {
							fos.flush();
							fos.close();
							fos = null;
							startReadParam = false;
						}
						if (newParameter && isTextParameter
								&& textParameterOutputStream != null) {
							String value = textParameterOutputStream
									.toString(JBeer.getApplicationEncode());
							textParameterOutputStream.flush();
							textParameterOutputStream.reset();
							startReadParam = false;
							addParametervalue(parameterMaps, paramName,
									value.replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", ""));
						}
						isTextParameter = true;
						lineBuffer = readLine(inputStream);
						continue;
					} else if (!startReadParam
							&& (lineBuffer.length <= 0 || lineStr.equals("\n") || lineStr
									.equals("\r"))) {
						lineBuffer = readLine(inputStream);
						continue;
					} else {
						if (!isTextParameter) {
							if (newParameter && lineBuffer != null) {
								startReadParam = true;
								tempFile = new File(new File(tempDir), fileName);
								fos = new FileOutputStream(tempFile);
								newParameter = false;
								addParametervalue(parameterMaps, paramName,
										tempFile);
								JBeerWebContext.setTempFile(tempFile);
							}
							fos.write(lineBuffer);
						} else {
							if (textParameterOutputStream == null) {
								startReadParam = true;
								textParameterOutputStream = new ByteArrayOutputStream();
							}
							textParameterOutputStream.write(lineBuffer);
						}
						lineBuffer = readLine(inputStream);
					}
				}
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}

			if (textParameterOutputStream != null) {
				textParameterOutputStream.close();
			}
		}

		return parameterMaps;
	}

	private static byte[] readLine(InputStream is) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int c;
		while ((c = is.read()) > -1) {
			bos.write(c);
			if (c == '\n' || c == '\r') {
				break;
			}
		}
		if (bos.size() <= 0 && c < 0) {
			return null;
		}
		byte[] bytes = bos.toByteArray();
		return bytes;
	}

	/**
	 * 
	 * <p>
	 * 函数功能说明:将解析到HTTP请求的参数放到map对应参数的数组中
	 * </p>
	 * <p>
	 * Bieber 2014-3-8
	 * </p>
	 * <p>
	 * 修改者名字 修改日期
	 * </p>
	 * <p>
	 * 修改内容</a>
	 * 
	 * @return void
	 */
	public static void addParametervalue(Map<String, Object> parameterMap,
			String paraName, Object value) {
		if (parameterMap.get(paraName) != null) {
			Object[] paraValues = (Object[]) parameterMap.get(paraName);
			Object[] dest = new Object[paraValues.length + 1];
			System.arraycopy(paraValues, 0, dest, 0, paraValues.length);
			dest[paraValues.length] = value;
			parameterMap.put(paraName, dest);
		} else {
			Object[] values = new Object[1];
			values[0] = value;
			parameterMap.put(paraName, values);
		}
	}

	public static File getRequestPartToTempFile(String tempDir, Part part)
			throws IOException {
		File file = new File(tempDir + "/" + part.getName());
		if (!file.exists()) {
			file.createNewFile();
		}

		FileOutputStream fout = new FileOutputStream(file);
		FileChannel outChannel = fout.getChannel();
		ReadableByteChannel readableChannel = Channels.newChannel(part
				.getInputStream());
		FileLock fileLock = outChannel.lock();
		try {
			outChannel.transferFrom(readableChannel, 0, part.getSize());
		} finally {
			fileLock.release();
			outChannel.close();
			fout.close();
			readableChannel.close();
		}
		return file;
	}

	public static void sendError(int code, Throwable e) throws IOException {
		HttpServletResponse response = JBeerWebContext.getResponse();
		if (response != null) {
			response.sendError(code, e.getMessage());
		}
		throw new IOException(e);
	}

	public static void sendError(int code, String message) {
		HttpServletResponse response = JBeerWebContext.getResponse();
		response.setCharacterEncoding(JBeer.getApplicationEncode());
		if (response != null) {
			try {
				response.sendError(code, message);
			} catch (IOException e) {
				if(logger.isDebugEnabled()){
					logger.debug("fialed to send error", e);
				}
			}
		}
	}
	
	public static String generateExceptionMessage(Throwable e){
		if(e==null){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("<ul>");
		sb.append("<li>Case by: ").append(e.getMessage()).append("</li>");
		sb.append("<li><ul>");
		StackTraceElement[] elements = e.getStackTrace();
		for(StackTraceElement element:elements){
			sb.append("<li>").append(element.getClassName()).append(" ").append(element.getMethodName()).append(" ").append(element.getLineNumber()).append("</li>");
		}
		sb.append("</ul></li>");
		sb.append("<li>").append(generateExceptionMessage(e.getCause())).append("</li>");
		sb.append("</ul>");
		return sb.toString();
	}
}
