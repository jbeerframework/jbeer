本文主要分享一下在JBeer中MVC模块的Controller解析，主要分享JBeer如何解析一个Controller，映射到各个action处理的路径以及Request参数注入。
###一、Controller解析###
下面列举出简单的Action实例<br/>

	<!-- lang: java -->
	@Controller(urlPattern="/first")
    public class FirstController extends BaseController{

	
	@Action(urlPatterns="invoke_${id}_${name}.htm")
	public String pathParam(@PathParam("id")Integer id,@PathParam("name")String name){
	    
	    return "view";
	}
    }
下面对框架解析一个Controller以及分析Action进行分享：<br/>
一） 应用启动，触发`JBeerWeb`的init()方法<br/>
二） 通过`ClassUtils`的scanClassesByAnnotation方法获取所有注解了`@Controller`的类，并解析类中方法，并判断是否被`@Action`注解过。<br/>
三） 对每个注解了`@Action`的方法，生成一个`ActionEntity`，该类中描述了一个Action接受的处理路径，需要调用哪个方法以及是哪个controller类。下面粘贴处`ActionEntity`的类定义：<br/>
	
    <!-- lang:java -->
	/**
	 * action需要调用的方法对象
	 */
	private Method actionInvokeMethod;

	/**
	 * action需要调用的className
	 */
	private String controllerClassName;
	/**
	 * 存储当前Action的方法参数信息
	 */
	private Collection<ActionMethodParam> methodParams;
	/**
	 * 当前Action处理的请求方式
	 */
	private RequestType requestType;


	/**
	 * 当前Action可以匹配的请求路径集合
	 */
	private Collection<PatternableUrl> patternedUrls;

其中定义了该Action方法触发的上下文。上面对一些属性进行了封装，比如方法入参和匹配的路径。
 
1、`ActionMethodParam`封装了Action的各个入参信息，如果Action没有入参，则`methodParams`为空，下面看看`ActionMethodParam`中定义了什么<br/>

	<!-- lang:java -->
	 /**
		 * 方法参数名
		 */
	 private String paramName;
		/**
		 * 方法参数位置
		 */
		private int paramIndex;

		private Class<?> parameterType;
		/**
		 * 关联的ID，可以是bean的ID，也可以是引用Properties或者IN18消息信息
		 */
		private String refId;
		
		private String[] in18Args;

`paramName`表示匹配url上面占位符的内容，`paramIndex`表示该参数是Action的第几个位置的入参，`parameterType`是参数的类型，用于进行类型转换，上面三个属性就可以完成在路径上进行占位符配置的参数注入。而`refId`则是通过配置`@RefBean`,`@Properties`或者`@Message`注解来关联框架内的资源，比如依赖一个bean，配置信息等。而`in18Args`是当引用的是IN18的message配置信息时候，需要传递的参数。通过上面几个参数，就可以实现Action的入参的实际来源。

2、`PatternableUrl`定义了匹配路径的信息，具体定义如下：

	<!-- lang:java -->
	/**
		 * 匹配路径的正则表达式
		 */
		private String urlMatcher;
		/**
		 * 配置的url路径占位符相关信息
		 */
		private Collection<URLParam> urlParams;

其中`urlMatcher`是待匹配的路径正则表达式，而`urlParams`则是路径上面的参数信息。关于`URLParam`定义信息如下:

	<!-- lang:java -->
	/**
			 * 占位符参数名
			 */
			private String paramName;
			/**
			 * 占位符参数所在的位置是第几个，方面在请求时，确定第几个占位符的值是当前参数的值
			 */
			private int urlParamIndex;

上面通过解析配置的url，获取占位符中的内容赋值到`paramName`中，以及记录当前占位符的位置（`urlParamIndex`）。这里的`paramName`是和`ActionMethodParam`中的`paramName`保持一直的，不然无法匹配对应的路径参数值。

到此，当一个请求路径，通过匹配`ActionEntity.patternedUrls`集合中的某一个来确定当前的`ActionEntity`是否能够处理当前一个请求，如果匹配通过，那么在解析请求的路径，从而实例化需要传入Action方法的入参。<br/>
四）当解析完毕Controller中的Action后，那么将会把Contrller放入到IOC容器中，后面需要引用Controller类的实体，将会从IOC中获取。
###二、Request参数注入###
Jbeer框架的参数注入提供了两种方式，一种是通过配置Controller非单例模式，框架自动注入到Controller类的属性，这点类似struts2的风格。第二种是Controller单例模式，开发人员通过框架提供的工具类来获取Request请求的参数。下面对这两种原理进行介绍。
首先来看看JBeer是如何处理用户一个请求的吧。
![处理请求时序图](http://static.oschina.net/uploads/space/2014/0701/144214_Z3a5_1384459.jpg)

上图阐述了框架在接收到一个请求，经过哪些过程来进行处理。那么`ControllerInitialization`负责对Controller的实例化，包括对Controller对象的实例化（从IOC容器中获取）和Action方法参数的实例化。而Request参数注入发生在`generateController`方法中，而`initActionMethodParams`方法则是初始化Action方法的参数。下面将主要分享这两个方法。
####一）、generateController####
1. 从IOC容器中获取Controller实体，IOC将会根据该实体是单例还是非单例进行初始化Controller实体，IOC将会注入Controller依赖的Bean。<br>
2. 获取Request中的所有参数（分包含上传文件和普通POST/GET请求），将所有参数封装到一个`Map<String,Object>`(其中文件上传的文件也在其中)。<br/>
3. 分析步骤2得到的`Map<String,Object>`对象,我们知道前端传递过来的参数是键值对的，比如name="bieber"，user.name="bieber"，names[0]="bieber0"或者users[0].name="bieber0"这种形式，这三种分别表示普通的键值对，对象的属性键值对，数组形式键值对，对象数组键值对。上面这种层级关系，可以通过对象引用树来进行分析，那么可以假设这些对象属性的公共父节点是Controller实体，那么name则是这个Controller的字符串属性，user则是Controller的对象属性，一次类推names,users则是对象的数组属性或者是集合。下面则给出下面树形结构图。
![对象引用树](http://static.oschina.net/uploads/space/2014/0702/094920_Al5O_1384459.jpg)
那么分析`Map<String,Object>`的目的，就是把无结构`Map`解析成树形结构。通过“.”来对Map的key进行分析，以及通过正则表达式来匹配key是否存在`\[[0-9]{1,}\]`结构，可以分析每个`.`则是一个树的分层，`.`之前的是父节点，之后的是子节点。通过该步骤对`Map<String,Object>`分析，会得到树的第一层子节点。`Map<String,Object>`如果其中key对不包含`.`，其value则是一个`Object[]`数组,如果包含`.`，则value则是一个`Map<String,Object>[]`数组。这两通过树的深度优先遍历，可完成整个Request参数注入。当然，这一步先只分析一个深度，并把得到的`Map<String,Object>`放到`JBeerWebContext`中。<br/>
4. 判断框架的Controller是否是单例模式，如果是单例模式，则不自动将步骤3分析得到的`Map<String,Object>`自动注入到Controller的对应属性中。如果是单例，则完成自动注入。
5. 当单例模式的时候，可以通过`RequestParameterUtil`来手动获取请求参数。下图展示了提供的接口。
![](http://static.oschina.net/uploads/space/2014/0702/101721_lr2m_1384459.jpg)
####二）、initActionMethodParams####
该方法则是完成url上面的参数（通过`@PathParam`来指定参数名），以及系统层面的参数（比如：`HttpServletRequest`,`HttpServletResponse`等等）。分析规则，则是按照解析Action方法时生成的对应信息。具体可见上面内容。<br/>

关于具体实现，还希望通过项目源码进行分析，此处只是进行简单的分享。如果有什么意见还希望大家能够提点意见。




