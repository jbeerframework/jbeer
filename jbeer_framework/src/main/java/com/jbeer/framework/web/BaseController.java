/**   
 * @Title: BaseController.java
 * @Package com.jbeer.framework.web
 * @author Bieber
 * @date 2014年6月16日 下午8:48:44
 * @version V1.0   
 */

package com.jbeer.framework.web;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jbeer.framework.exception.JBeerException;

/**
 * <p>
 * 类功能说明:抽象基础controller类，封装一些controller类
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: BaseController.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014年6月16日 下午8:48:44
 * @version V1.0
 */

public abstract class BaseController {

	protected final <T> T getObject(String fieldName, Class<T> type)
			throws JBeerException {

		return RequestParameterUtil.getObject(fieldName, type);
	}

	protected final <T> List<T> getList(String fieldName, Class<T> type)
			throws JBeerException {
		return RequestParameterUtil.getList(fieldName, type);
	}

	protected final File getFile(String fieldName) throws JBeerException {
		return RequestParameterUtil.getFile(fieldName);
	}

	protected final List<File> getFiles(String fieldName) throws JBeerException {
		return RequestParameterUtil.getFiles(fieldName);
	}

	protected final String getString(String fieldName) {
		return RequestParameterUtil.getString(fieldName);
	}

	protected final Integer getInteger(String fieldName) {
		return RequestParameterUtil.getInteger(fieldName);
	}

	protected final Float getFloat(String fieldName) {
		return RequestParameterUtil.getFloat(fieldName);
	}

	protected final Double getDouble(String fieldName) {
		return RequestParameterUtil.getDouble(fieldName);
	}

	protected final <T> T[] getArray(Class<T> t, String fieldName) throws JBeerException{
		return RequestParameterUtil.getArray(t, fieldName);
	}
	
	protected final Short getShort(String fieldName) {
		return RequestParameterUtil.getShort(fieldName);
	}

	protected final HttpServletRequest getRequest() {
		return JBeerWebContext.getRequest();
	}

	protected final HttpServletResponse getResponse() {
		return JBeerWebContext.getResponse();
	}

	protected final ServletContext getContext() {
		return JBeerWebContext.getServletContext();
	}

}
