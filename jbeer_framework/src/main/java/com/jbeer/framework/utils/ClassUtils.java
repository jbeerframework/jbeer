/**   
 * @Title: ClassUtils.java
 * @Package com.jbeer.framework.utils
 * @author Bieber
 * @date 2014-2-15 下午03:39:31
 * @version V1.0   
 */

package com.jbeer.framework.utils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jbeer.framework.JBeer;
import com.jbeer.framework.exception.InitObjectException;
import com.jbeer.framework.exception.ScanClassException;
import com.jbeer.framework.logging.Log;

/**
 * 类功能说明 类修改者 修改日期 修改说明
 * <p>
 * Title: ClassUtils.java
 * </p>
 * 
 * @author Bieber <a
 *         mailto="bieber.jbeer@hotmail.com">bieber.jbeer@hotmail.com</a>
 * @date 2014-2-15 下午03:39:31
 * @Description: 对Class反射操作类，以及遍历classpath类等工具
 * @version V1.0
 */

public class ClassUtils {

    
    private static final Log logger = LoggerUtil.generateLogger(ClassUtils.class);
    
    

    
    public static String getProjectRootDir(){
        try {
            return new File("").toURI().toURL().getPath();
        } catch (MalformedURLException e) {
            return null;
        }
    }
    
    
    public static String getClassPath(){
        String classPath = null;
        try {
            classPath = ClassUtils.class.getClassLoader().getResource("").toURI().getPath();
        } catch (URISyntaxException e) {
            classPath=ClassUtils.class.getClassLoader().getResource("").getPath();
        }
        return classPath;
    }
    /**
     * 获取指定类的所有get方法
     * 
     * @param clazz
     * @return
     */
    public static Collection<Method> collectionAllGetMethodFromClass(Class<?> clazz) {
        Set<Method> methods = new HashSet<Method>();
        Collection<Field> fields = listClassAllFields(clazz, true);
        for (Field field : fields) {
            Method method = searchGetMethod(clazz, field.getName());
            if (method != null) {
                methods.add(method);
            }
        }

        return methods;
    }

    /**
     * 
    * <p>函数功能说明:查找某个属性的set方法</p>
    * <p>Bieber  2014-2-22</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return Method
     */
    public static Method searchSetMethod(Class<?> targetClass, String fieldName, Class<?> fieldType) {
        String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase()
                               + fieldName.substring(1);
        try {
            Method setMethod = targetClass.getMethod(setMethodName, fieldType);
            return setMethod;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 
    * <p>函数功能说明:获取某个字段的get方法</p>
    * <p>Bieber  2014-2-22</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return Method
     */
    public static Method searchGetMethod(Class<?> targetClass, String fieldName) {
        String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase()
                               + fieldName.substring(1);
        try {
            Method getMethod = targetClass.getMethod(getMethodName);
            return getMethod;
        } catch (Exception e) {
            return null;
        }
    }

    public static Object fillObjectField(Class<?> clazz, Map<String, Object> requestParams) {
        Collection<Field> fields = listClassAllFields(clazz, true);
        try {
            Object instance = clazz.newInstance();
            for (Field field : fields) {
                if (requestParams.containsKey(field.getName())) {
                    Method setMethod = searchSetMethod(clazz, field.getName(), field.getType());
                    setMethod.invoke(instance, CaseUtils.caseType(field.getType(), requestParams
                        .get(field.getName()).toString()));
                }
            }
            return instance;
        } catch (Exception e) {
            throw new InitObjectException("初始化类" + clazz.getName() + "失败", e);
        }
    }

    private static boolean checkAvalibaleField(Class<?> targetClass, Field field) {
        Method setMethod = null;
        try {
            setMethod = searchSetMethod(targetClass, field.getName(), field.getType());
        } catch (Exception e) {

        }
        Method getMethod = null;
        try {
            getMethod = searchGetMethod(targetClass, field.getName());
        } catch (Exception e) {

        }
        return setMethod != null || getMethod != null;
    }

    /**
     * 
    * <p>函数功能说明:获取某个类的所有Field，通过needCheck判断是否需要校验该field是否有get方法</p>
    * <p>Bieber  2014年6月5日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return Collection<Field>
     */
    public static Collection<Field> listClassAllFields(Class<?> clazz, boolean needCheck) {
        Set<Field> fieldList = new HashSet<Field>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!needCheck || checkAvalibaleField(clazz, field)) {
                fieldList.add(field);
            }
        }
        if (clazz.getSuperclass() != Object.class) {
            fieldList.addAll(listClassAllFields(clazz.getSuperclass(), needCheck));
        }
        return fieldList;
    }
    /**
     * 
     * <p>
     * 函数功能说明:根据注解查找所有匹配的类
     * </p>
     * <p>
     * Bieber 2014-2-15
     * </p>
     * <p>
     * 修改者名字 修改日期
     * </p>
     * <p>
     * 修改内容</a>
     * 
     * @return Set<Class<?>>
     */
    public static Set<Class<?>> scanClassesByAnnotation(String pacakage,Class<? extends Annotation> annotationClass)
                                                                                                    throws ScanClassException {
        try {
            return scanClasses(pacakage, annotationClass, null, null,false);
        } catch (Exception e) {
            throw new ScanClassException(e);
        }
    }
    /**
     * 
     * <p>
     * 函数功能说明:根据注解查找所有匹配的类
     * </p>
     * <p>
     * Bieber 2014-2-15
     * </p>
     * <p>
     * 修改者名字 修改日期
     * </p>
     * <p>
     * 修改内容</a>
     * 
     * @return Set<Class<?>>
     */
    public static Set<Class<?>> scanClassesByAnnotation(String pacakage,Class<? extends Annotation> annotationClass,boolean isIgnoreInterfaceOrAbstract)
                                                                                                    throws ScanClassException {
        try {
            return scanClasses(pacakage, annotationClass, null, null,isIgnoreInterfaceOrAbstract);
        } catch (Exception e) {
            throw new ScanClassException(e);
        }
    }
    /**
     * 
     * <p>
     * 函数功能说明:根据实现的接口查找匹配的类
     * </p>
     * <p>
     * Bieber 2014-2-15
     * </p>
     * <p>
     * 修改者名字 修改日期
     * </p>
     * <p>
     * 修改内容</a>
     * 
     * @return Set<Class<?>>
     * @throws ScanClassException
     */
    public static Set<Class<?>> scanClassesByInterfaceClass(String pacakage,Class<?> interfaceClass,boolean isIgnoreInterfaceOrAbstract)
                                                                                    throws ScanClassException {

        try {
            return scanClasses(pacakage, null, interfaceClass, null,isIgnoreInterfaceOrAbstract);
        } catch (Exception e) {
            throw new ScanClassException(e);
        }
    }
    /**
     * 
     * <p>
     * 函数功能说明:根据实现的接口查找匹配的类
     * </p>
     * <p>
     * Bieber 2014-2-15
     * </p>
     * <p>
     * 修改者名字 修改日期
     * </p>
     * <p>
     * 修改内容</a>
     * 
     * @return Set<Class<?>>
     * @throws ScanClassException
     */
    public static Set<Class<?>> scanClassesByInterfaceClass(String pacakage,Class<?> interfaceClass)
                                                                                    throws ScanClassException {

        try {
            return scanClasses(pacakage, null, interfaceClass, null,false);
        } catch (Exception e) {
            throw new ScanClassException(e);
        }
    }

    /**
     * 
     * <p>
     * 函数功能说明:根据实现的父类查找匹配的类
     * </p>
     * <p>
     * Bieber 2014-2-15
     * </p>
     * <p>
     * 修改者名字 修改日期
     * </p>
     * <p>
     * 修改内容</a>
     * 
     * @return Set<Class<?>>
     * @throws ScanClassException
     */
    public static Set<Class<?>> scanClassesBySuperClass(String pacakage,Class<?> superClass)
                                                                            throws ScanClassException {

        try {
            return scanClasses(pacakage, null, null, superClass,false);
        } catch (Exception e) {
            throw new ScanClassException(e);
        }
    }
    /**
     * 
     * <p>
     * 函数功能说明:根据实现的父类查找匹配的类
     * </p>
     * <p>
     * Bieber 2014-2-15
     * </p>
     * <p>
     * 修改者名字 修改日期
     * </p>
     * <p>
     * 修改内容</a>
     * 
     * @return Set<Class<?>>
     * @throws ScanClassException
     */
    public static Set<Class<?>> scanClassesBySuperClass(String pacakage,Class<?> superClass,boolean isIgnoreInterfaceOrAbstract)
                                                                            throws ScanClassException {

        try {
            return scanClasses(pacakage, null, null, superClass,isIgnoreInterfaceOrAbstract);
        } catch (Exception e) {
            throw new ScanClassException(e);
        }
    }
    /**
     * 
     * <p>
     * 函数功能说明:根据注解查找所有匹配的类
     * </p>
     * <p>
     * Bieber 2014-2-15
     * </p>
     * <p>
     * 修改者名字 修改日期
     * </p>
     * <p>
     * 修改内容</a>
     * 
     * @return Set<Class<?>>
     */
    public static Set<Class<?>> scanClassesByAnnotation(Class<? extends Annotation> annotationClass)
                                                                                                    throws ScanClassException {
        try {
            return scanClasses(JBeer.getApplicationBasePackageName(), annotationClass, null, null,false);
        } catch (Exception e) {
            throw new ScanClassException(e);
        }
    }
    /**
     * 
     * <p>
     * 函数功能说明:根据注解查找所有匹配的类
     * </p>
     * <p>
     * Bieber 2014-2-15
     * </p>
     * <p>
     * 修改者名字 修改日期
     * </p>
     * <p>
     * 修改内容</a>
     * 
     * @return Set<Class<?>>
     */
    public static Set<Class<?>> scanClassesByAnnotation(Class<? extends Annotation> annotationClass,boolean isIgnoreInterfaceOrAbstract)
                                                                                                    throws ScanClassException {
        try {
            return scanClasses(JBeer.getApplicationBasePackageName(), annotationClass, null, null,isIgnoreInterfaceOrAbstract);
        } catch (Exception e) {
            throw new ScanClassException(e);
        }
    }
    /**
     * 
     * <p>
     * 函数功能说明:根据实现的接口查找匹配的类
     * </p>
     * <p>
     * Bieber 2014-2-15
     * </p>
     * <p>
     * 修改者名字 修改日期
     * </p>
     * <p>
     * 修改内容</a>
     * 
     * @return Set<Class<?>>
     * @throws ScanClassException
     */
    public static Set<Class<?>> scanClassesByInterfaceClass(Class<?> interfaceClass,boolean isIgnoreInterfaceOrAbstract)
                                                                                    throws ScanClassException {

        try {
            return scanClasses(JBeer.getApplicationBasePackageName(), null, interfaceClass, null,isIgnoreInterfaceOrAbstract);
        } catch (Exception e) {
            throw new ScanClassException(e);
        }
    }
    /**
     * 
     * <p>
     * 函数功能说明:根据实现的接口查找匹配的类
     * </p>
     * <p>
     * Bieber 2014-2-15
     * </p>
     * <p>
     * 修改者名字 修改日期
     * </p>
     * <p>
     * 修改内容</a>
     * 
     * @return Set<Class<?>>
     * @throws ScanClassException
     */
    public static Set<Class<?>> scanClassesByInterfaceClass(Class<?> interfaceClass)
                                                                                    throws ScanClassException {

        try {
            return scanClasses(JBeer.getApplicationBasePackageName(), null, interfaceClass, null,false);
        } catch (Exception e) {
            throw new ScanClassException(e);
        }
    }

    /**
     * 
     * <p>
     * 函数功能说明:根据实现的父类查找匹配的类
     * </p>
     * <p>
     * Bieber 2014-2-15
     * </p>
     * <p>
     * 修改者名字 修改日期
     * </p>
     * <p>
     * 修改内容</a>
     * 
     * @return Set<Class<?>>
     * @throws ScanClassException
     */
    public static Set<Class<?>> scanClassesBySuperClass(Class<?> superClass,boolean isIgnoreInterfaceOrAbstract)
                                                                            throws ScanClassException {

        try {
            return scanClasses(JBeer.getApplicationBasePackageName(), null, null, superClass,isIgnoreInterfaceOrAbstract);
        } catch (Exception e) {
            throw new ScanClassException(e);
        }
    }
    /**
     * 
     * <p>
     * 函数功能说明:根据实现的父类查找匹配的类
     * </p>
     * <p>
     * Bieber 2014-2-15
     * </p>
     * <p>
     * 修改者名字 修改日期
     * </p>
     * <p>
     * 修改内容</a>
     * 
     * @return Set<Class<?>>
     * @throws ScanClassException
     */
    public static Set<Class<?>> scanClassesBySuperClass(Class<?> superClass)
                                                                            throws ScanClassException {

        try {
            return scanClasses(JBeer.getApplicationBasePackageName(), null, null, superClass,false);
        } catch (Exception e) {
            throw new ScanClassException(e);
        }
    }

    /**
     * 
     * <p>
     * 函数功能说明:搜索当前classpath所有匹配条件的类集合
     * </p>
     * <p>
     * Bieber 2014-2-15
     * </p>
     * <p>
     * 修改者名字 修改日期
     * </p>
     * <p>
     * 修改内容</a>
     * 
     * @return Set<Class<?>>
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static Set<Class<?>> scanClasses(String basePackage,
                                             Class<? extends Annotation> annotationClass,
                                             Class<?> interfaceClass, Class<?> superClass,boolean isIgnoreInterfaceOrAbstract)
                                                                                          throws Exception,
                                                                                          ClassNotFoundException {
        if (logger.isDebugEnabled()) {
            logger.debug("scaning package " + basePackage);
        }
        Set<Class<?>> classes = new HashSet<Class<?>>();
        String packageDirName = basePackage.replace(".","/");
        Collection<String> files = ClassPathScanFileUtil.scanClassPathFile(packageDirName,
            ".class", false);
        String fullClassName = null;
        Pattern pattern = Pattern.compile("[/\\\\]{1,}");
        
        for (String file : files) {
           
            Matcher mathcer = pattern.matcher(file);
            fullClassName = mathcer.replaceAll(".");
            fullClassName = fullClassName.replace(".class", "");
            Class<?> clazz = Class.forName(fullClassName);
            /**
             * 抽象类和接口进行过滤，以防止无法初始化
             */
            if (matchClassByCondition(clazz, annotationClass, interfaceClass, superClass,isIgnoreInterfaceOrAbstract)) {
                classes.add(clazz);
            }
        }
        return classes;
    }

    /**
     * 
     * <p>
     * 函数功能说明:对查找的类是否匹配条件
     * </p>
     * <p>
     * Bieber 2014-2-15
     * </p>
     * <p>
     * 修改者名字 修改日期
     * </p>
     * <p>
     * 修改内容</a>
     * 
     * @return boolean
     */
    private static boolean matchClassByCondition(Class<?> targetClass,
                                                 Class<? extends Annotation> annotationClass,
                                                 Class<?> interfaceClass, Class<?> superClass,boolean isIgnoreInterfaceOrAbstract) {
        boolean matched = true;
        if (annotationClass != null) {
            Annotation annotation = targetClass.getAnnotation(annotationClass);
            matched = matched && annotation != null;
        }
        if (interfaceClass != null) {
            matched = matched && interfaceClass.isAssignableFrom(targetClass);
        }

        if (superClass != null) {
            matched = matched && superClass.isAssignableFrom(targetClass);
        }
        if(!isIgnoreInterfaceOrAbstract){
        	matched=matched&&!targetClass.isInterface()&&!Modifier.isAbstract(targetClass.getModifiers());
        }

        return matched;
    }

}
