/**   
 * @Title: ActionInvoker.java
 * @Package com.jbeer.framework.web.invoker
 * @author Bieber
 * @date 2014-4-22 上午12:50:17
 * @version V1.0   
 */

package com.jbeer.framework.web;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.jbeer.framework.exception.ActionInvokeException;
import com.jbeer.framework.utils.ClassUtils;

/**
 * <p>
 * 类功能说明:Action调用模块
 * </p>
 * <p>
 * 类修改者 修改日期
 * </p>
 * <p>
 * 修改说明
 * </p>
 * <p>
 * Title: ActionInvoker.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-4-22 上午12:50:17
 * @version V1.0
 */

public class ActionInvoker {

    public static ModelAndView invokeAction(Object controller, Method method, Object[] args)
                                                                                            throws ActionInvokeException {
        if (method.isAccessible()) {
            method.setAccessible(true);
        }
        try {
            Object ret = method.invoke(controller, args);
            if (ret == null) {
                return null;
            }
            ModelAndView mav = null;
            /**
             * 此处是处理直接返回一个视图名称
             */
            if (String.class.isAssignableFrom(ret.getClass())) {
                mav = ModelAndView.createModelAndView();
                mav.setView(ret.toString());
                fillModel(controller,mav);
                /**
                 * 直接返回一个视图实体
                 */
            }else  if (ModelAndView.class.isAssignableFrom(ret.getClass())) {
                mav = (ModelAndView) ret;
            }else{
            	mav=ModelAndView.createAJAXModelAdnView().setData(ret);
            }
            return mav;
        } catch (Exception e) {
            throw new ActionInvokeException(e);
        }
    }
    
    private static void fillModel(Object controller,ModelAndView mav) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
        Class<?> controllerClass = controller.getClass();
        Collection<Field> fields = ClassUtils.listClassAllFields(controllerClass,true);
        Map<String, Object> mapdatas = mav.getDatas();
        Map<String, Method> getMethods = JBeerWebContext
            .getClassGetMethodsFromCache(controllerClass);
        if (getMethods == null) {
            getMethods = new HashMap<String, Method>();
            for (Field field : fields) {
                Method getMethod = ClassUtils.searchGetMethod(controllerClass,
                    field.getName());
                if (getMethod == null) {
                    continue;
                }
                if (!mapdatas.containsKey(field.getName())) {
                    getMethods.put(field.getName(), getMethod);
                    mav.setDataMap(field.getName(), getMethod.invoke(controller, new Object[]{}));
                }
            }
            JBeerWebContext.cacheClassGetMethods(controllerClass, getMethods);
        } else {
            for (Entry<String, Method> entry : getMethods.entrySet()) {
                if(mapdatas.containsKey(entry.getKey())){
                    continue;
                }
                mapdatas.put(entry.getKey(), entry.getValue().invoke(controller, new Object[]{}));
            }
        }
    }

}
