/**   
* @Title: JBeerConstant.java
* @Package com.jbeer.framework.constant
* @author Bieber
* @date 2014-2-15 下午05:12:52
* @version V1.0   
*/

package com.jbeer.framework.constant;

import java.util.Locale;

/**
 * <p>类功能说明:TODO</p>
 * <p>类修改者	    修改日期</p>
 * <p>修改说明</p>
 * <p>Title: JBeerConstant.java</p>
 * @author Bieber <a mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-2-15 下午05:12:52
 * @Description: TODO
 * @version V1.0
 */

public class JBeerConstant {

    //DB
    public static final String DEFAULT_ENTITY_NAME="CLASS_NAME";
    
    public static final String DEFAULT_COLUMN_NAME="FIELD_NAME";
    
    public static final String DEFALUT_TX_CLASS_REGEX="^([\\w\\$]{1,}[\\.]{0,1}){1,}[\\w\\$]{0,}$";
   

   //Message
    public static final String DEFAULT_LOCAL=Locale.SIMPLIFIED_CHINESE.toString();

    //Context
    public static final String ENCODE                          = "encode";
    public static final String DEFAULT_ENCODE                  = "UTF-8";
    public static final String DEFAULT_CONFIG_PATH             = "jbeer.properties";
    public static final String CONFIG_PARAM_NAME               = "configuratePath";
    public static final String APPLICATION_BASE_PACKAGENAME    = "applicationBasePackageName";

    //IOC
    public static final String DEFAULT_BEANNAME                = "SIMPLECLASSNAME";

    public static final String IOC_DEFALUT_FACTORY_METHOD_NAME = "NONE";
    
    public static final String DEFALUT_DESTROY_METHOD="NONE";
    
    
    
    //AOP
    public static final String DEFAULT_CLASS_REGEX="^([\\w\\$]{1,}[\\.]{0,1}){1,}[\\w\\$]{0,}$";
    
    public static final String DEFAULT_METHOD_REGEX="^[\\w\\$]{1,}$";
    
    public static final Class<?> DEFAULT_ARG_TYPE=Object.class;
    
    
    //WEB
    public static final String DEFAULT_URLPATTERN              = "/";

    public static final String VIEW_PREFIX                     = "view_prefix";

    public static final String VIEW_SUFFIX                     = "view_suffix";

    public static final String VIEW_RENDER                     = "view_render";

    public static final String GET                             = "GET";

    public static final String PUT                             = "PUT";

    public static final String POST                            = "POST";

    public static final String DELETE                          = "DELETE";

    public static final String UPLOAD_FILE_CONTENTTYPE         = "multipart/form-data";

    public static final String UPLOAD_FILE_DESPOSITION         = "Content-Disposition:";

    public static final String UPLOAD_FILE_CONTENTTYPE_HEAD    = "Content-Type:";

    public static final String UPLOAD_PARAMATER_NAME           = "name";

    public static final String UPLOAD_FILE_NAME                = "filename";

    public static final String HTTP_SPLIT_FLAG                 = ";";

    public static final String HTTP_BOUNDARY_NAME              = "boundary";

    public static final String WEB_TEMP_FILE_DIR               = "webTempFileDir";

    public static final String DEFAULT_TEMP_FILE_DIR           = "/temp";
    
    public static final String DEFAULT_PATH_PARAMETER_NAME     = "DEFAULT_PATH_PARAMETER_NAME";

    public static final String WEB_ACTION_PARAM_REF_BEAN       = "WEB_ACTION_PARAM_REF_BEAN";
    
    public static final String WEB_ACTION_PARAM_REF_PROPERTIES = "WEB_ACTION_PARAM_REF_PROPERTIES";
    
    public static final String WEB_ACTION_PARAM_REF_MESSAGE = "WEB_ACTION_PARAM_REF_MESSAGE";
    

   

}
