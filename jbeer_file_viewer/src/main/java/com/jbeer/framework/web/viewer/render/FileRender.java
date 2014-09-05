/**   
* @Title: FileRender.java
* @Package com.jbeer.framework.web.viewer.render
* @author Bieber
* @date 2014年6月17日 上午11:20:28
* @version V1.0   
*/

package com.jbeer.framework.web.viewer.render;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jbeer.framework.JBeer;
import com.jbeer.framework.annotation.RefBean;
import com.jbeer.framework.exception.RenderingViewException;
import com.jbeer.framework.logging.Log;
import com.jbeer.framework.utils.LoggerUtil;
import com.jbeer.framework.web.ModelAndView;
import com.jbeer.framework.web.ModelAndView.ViewType;
import com.jbeer.framework.web.viewer.FileViewer;
import com.jbeer.framework.web.viewer.model.FileModelAndView;
import com.jbeer.framework.web.viewrender.Render;

/**
* <p>类功能说明:文件渲染器</p>
* <p>类修改者	    修改日期</p>
* <p>修改说明</p>
* <p>Title: FileRender.java</p>
* @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
* @date 2014年6月17日 上午11:20:28
* @version V1.0
*/

public class FileRender implements Render {
	
	private static final Log logger = LoggerUtil.generateLogger(FileRender.class);

    @RefBean
    private FileViewer fileViewer;

    /* (non-Javadoc)
     * @see com.jbeer.framework.web.viewrender.Render#order()
     */
    public int order() {
        return 0;
    }

    /* (non-Javadoc)
     * @see com.jbeer.framework.web.viewrender.Render#isSupport(com.jbeer.framework.web.ModelAndView)
     */
    public boolean isSupport(ModelAndView modelView) {
        if (modelView.getViewType() == ViewType.FILE
            && FileModelAndView.class == modelView.getClass()) {
            return true;
        }
        return false;
    }

    /* (non-Javadoc)
     * @see com.jbeer.framework.web.viewrender.Render#render(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.jbeer.framework.web.ModelAndView)
     */
    public void render(HttpServletRequest request, HttpServletResponse response,
                       ModelAndView modelView) throws RenderingViewException {
        FileModelAndView fmav = (FileModelAndView) modelView;
        String readMode = "attachment;";
        if (!fmav.isDownload()) {
            readMode = "inline;";
        }
        String range = request.getHeader("Range");
        /**
         * 读取其实加载文件位置，以支持断点续传
         */
        int skip = 0;
        if (range != null) {
            Pattern pattern = Pattern.compile("[0-9]{1,}");
            Matcher matcher = pattern.matcher(range);
            if (matcher.find()) {
                range = matcher.group();
                skip = Integer.parseInt(range);
            }
        }

       

        InputStream is = null;
        OutputStream os = null;
        try {
        	 /**
             * 设置文件类型，以及是直接打开还是下载保存
             */
            readMode += "filename=" + URLEncoder.encode(fmav.getFileName(), JBeer.getApplicationEncode());
            response.addHeader("Content-Disposition", readMode);
            if (fmav.getExtendName() != null) {
                response.addHeader("Content-Type",
                    fileViewer.getMineType(fmav.getExtendName().toLowerCase()));
            }
            is = fmav.getInputStream();

            /**
             * 设置http头信息，设置文件大小，以及接受最小单元值
             */
            response.addHeader("Content-Length", fmav.getSize() + "");
            response.addHeader("Accept-Ranges", "bytes");

            byte[] buffer = new byte[1024];
            int length = 0;
            os = response.getOutputStream();
            is.skip(skip);
            if(logger.isDebugEnabled()){
            	Collection<String> headers = response.getHeaderNames();
            	logger.debug("download file");
            	for(String header:headers){
        		logger.debug(header+":"+response.getHeader(header));
            	}
        	}
            while ((length = is.read(buffer, 0, 1024)) > 0) {
                /**
                 * 设置http head，告知浏览器读取的内容区间以及内容大小
                 */
                response.addHeader("Content-Range", "bytes " + skip + "-" + (skip + length) + "/"
                                                    + length);
                os.write(buffer, 0, length);
                skip = skip + length;
            }
        } catch (Exception e) {
            throw new RenderingViewException("fial to rending file " + fmav.getFileName(), e);
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                throw new RenderingViewException("fial to rending file " + fmav.getFileName(), e);
            }
        }

    }

}
