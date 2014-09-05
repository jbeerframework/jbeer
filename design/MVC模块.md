 

> 本系列博文，将会一步一步介绍如何构建一个轻量级的web框架<br/>jbeer git地址：http://git.oschina.net/bieber/jbeer

JBEER的MVC模块采取完全注解的Restful风格。支持Controller的单例和非单例模式，开发人员可以通过配置来选择。

# 一、启动MVC #

关于MVC的配置在Configurate接口的public void configurateWeb(WebConfig config);方法内配置WebConfig类来实现。该类提供的几个设置方法如下：

1. `setViewPrefix(String viewPrefix)`设置视图的前缀，比如`/WEB-INF/pages`
2. `setWebTempFileDir(String webTempFileDir)`设置文件上次的临时目录，默认是/temp,即，应用根目录下面的temp目录。
4. `setViewSuffix(String viewSuffix)`视图的后缀，例如：.jsp后者.ftl
5. `isSingletonMode(boolean singletonMode)controller`是否单例模式，默认是非单例模式，如果是单例模式，框架将不会自动将request中的参数，自动注入到controler的字段中（如果是对象，将会自动封装到对象的字段中，例如user.name，在controller中有user这个`User`字段，那么将会初始化一个`User`对象并且复制name到`User`的name属性上面）

通过上面描述我如下配置了MVC模块
#######1）配置WEB.XML#######
	<!-- lang: xml -->
	<context-param>
		<param-name>basePackageName</param-name>
		<param-value>org.jbeer.sample</param-value>
	</context-param>
	<listener>
		<listener-class>com.jbeer.framework.startup.JBeerWebContextListener</listener-class>
	</listener>
	<servlet>
		<servlet-name>jbeerDispatcherServlet</servlet-name>
		<servlet-class>com.jbeer.framework.web.JBeerDispatcherServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>jbeerDispatcherServlet</servlet-name>
		<url-pattern>*.htm</url-pattern>
	</servlet-mapping>

1、配置`JBeerDispatcherServlet`用于拦截MVC的请求，如上是拦截素有.htm的请求<br/>
2、配置`JBeerWebContextListener`用于启动框架，加载应用相关信息<br/>
3、配置`basePackageName`是告知框架，需要扫描类的基础包，该包下面的所有类都会进行扫描
#######2）配置应用的MVC#######
    <!-- lang: java -->
    public class AppConfig implements Configurate {
	@Override
	public void configurateWeb(WebConfig config) {
		config.setViewPrefix("/WEB-INF/pages");
		config.setViewSuffix(".jsp");
		config.isSingletonMode(true);
	}
    }
由于上面配置了`basePackageName`，并且`AppConfig`是在配置的包（子包）下，那么框架将会扫描到`AppConfig`类，并执行configurateWeb方法，对MVC进行配置。这里的配置告知了框架视图模板是WEB-INF/pages下面的.jsp文件，同时告知框架MVC里面的Controller是单例模式，那么需要手动在Controller中获取请求参数。下面便是一个单例模式下面的Controller实现
#######3）实现FirstController#######
	<!-- lang: java -->
	@Controller(urlPattern="/first")
	public class FirstController extends BaseController{

	@RefBean
	private UserService userService;
	
	@Action(urlPatterns="post.htm",requestType=RequestType.POST)
	public ModelAndView post() throws JBeerException{
		System.out.println(this);
		PageModelAndView mav = ModelAndView.createModelAndView();
		List<User> user = getList("user", User.class);
		List<File> file = getFiles("file");
		Short test = getShort("test");
		User[] users = getArray(User.class, "user");
		int a = getInteger("a");
		mav.setDataMap("test", test);
		mav.setDataMap("user", users[0]);
		mav.setDataMap("file", file.get(0).getName());
		mav.setView("view");
		return mav;
	}
	
	@Action(urlPatterns="index.htm",requestType=RequestType.ANY)
	public String index() throws IOException, ScanClassException{
		return "index";
	}
	}
从上往下看，分析该类的实现。<br/>
1、通过注解`@Controller`告知框架`FirstController`是一个控制器，并且匹配的路径是以`${ctx}/first`开始<br>
2、`FirstController`继承了`BaseController`，在`BaseController`中，有获取`HttpServletRequest`中的参数方法，例如`getList`,`getFiles`,`getShort`,`getObject`等等，继承了该类，那么不用再去手动从`HttpServletRequest`获取请求参数。当然，也可以不用继承该类，通过`RequestParameterUtil`里面的静态方法一样可以达到同样的效果，具体可以自己选择。<br/>
3、方法post通过注解`@Action`进行了修饰，表示该方法将会响应来自路径`${ctx}/first/post.htm`，并且是POST方式的请求，通过注解`@Action`注解进行修饰的方法，框架都没当作是一个Action来进行解析。<br/>
4、action方法返回的是`ModelAndView`,该类是Action返回的一个抽象，该类不能直接实体化，需要通过`ModelAndView`下面的静态方法构造对应的视图和数据模型对象。如果是需要返回一个页面到前端，则是通过`createModelAndView()`来构造一个`PageModelAndView`对象，如果是需要返回一个JSON对象返回到前端，那么通过`createJsonModel`来构造一个`JSONModelAndView`等等....根据具体需求可以生成对应的视图和数据模型<br>

通过上面几个步骤，JBeer的MVC就可以正常执行了，可以处理前端发起的请求。下面对JBeer里面视图渲染的思想进行描述一下。
#二、JBeer里面的视图渲染#

渲染视图，包括三个核心组件：数据，渲染器，视图。
数据和视图，是具体的请求在Action的方法中告知框架的，而渲染器，则需要框架根据对应的数据模型来匹配对应的渲染器。所以JBEER中，为每个`ModelAndView`匹配了视图。
######1）数据模型和视图的抽象######

	<!-- lang: java -->
	public class ModelAndView {

    protected String view;
    
    protected String callback;
    
    protected Map<String,Object> datas = new HashMap<String,Object>();
    
    protected ViewType type = ViewType.PAGE;
    
    protected Object data = null;
    
    protected String content;
    
    
    protected ModelAndView(){
        
    }
    public static JSONModelAndView createJsonModel(){
        return new JSONModelAndView();
    }
    
    public static JSONPModelAndView createJsonpModel(){
        return new JSONPModelAndView();
    }
    
    public static PageModelAndView createModelAndView(){
        return new PageModelAndView();
    }
   
    public static AJAXModelAdnView createAJAXModelAdnView(){
    	return new AJAXModelAdnView();
    }
    
    protected ModelAndView setContent(String content){
    	this.content = content;
    	return this;
    }
    protected ModelAndView setView(String view){
        this.view = view.startsWith("/")?view:("/"+view);
        return this;
    }
    protected ModelAndView setCallback(String callback){
        this.callback = callback;
        return this;
    }
    protected ModelAndView setViewType(ViewType type){
       this.type = type;
       return this;
    }
    protected ModelAndView setDataMap(String key,Object data){
        datas.put(key, data);
        return this;
    }
    protected ModelAndView setData(Object data){
        this.data = data;
        return this;
    }
    public String getContent(){
    	return content;
    }
    public String getView(){
        return view;
    }
    
    public Map<String,Object> getDatas(){
        return datas;
    }
    
    public Object getData(){
        return data;
    }
    
    public String getCallback(){
        return callback;
    }
    public ViewType getViewType(){
        return type;
    }
    
    public static class JSONModelAndView extends ModelAndView{

        public JSONModelAndView(){
            this.type = ViewType.JSON;
        }
        @Override
        public JSONModelAndView setData(Object data) {
             this.data = data;
             return this;
        }
        
    }
    
    public static class JSONPModelAndView extends JSONModelAndView{
        public JSONPModelAndView(){
            this.type = ViewType.JSONP;
        }
        @Override
        public JSONPModelAndView setCallback(String callback) {
            this.callback = callback;
            return this;
        }

    }
    
    public static class AJAXModelAdnView extends ModelAndView{
    	public AJAXModelAdnView(){
    		this.type = ViewType.AJAX;
    	}
		@Override
		public ModelAndView setContent(String content) {
			this.content = content;
			 return this;
		}
    	
    	
    }
    
    public static class PageModelAndView extends ModelAndView{
        public PageModelAndView(){
            this.type = ViewType.PAGE;
        }
 
        @Override
        public PageModelAndView setView(String view) {
            this.view = view;
            return this;
        }
 
        @Override
        public PageModelAndView setDataMap(String key, Object data) {
            this.datas.put(key, data);
            return this;
        }
        
    }
    
    public static enum ViewType{
        
        JSON("json"),PAGE("page"),FILE("file"),JSONP("jsonp"),AJAX("ajax");
        
        private String name;
        private ViewType(String name){
            this.name = name;
        }
        
        public String getName(){
            return name;
        }
        
    }
    
	}
在类`ModelAndView`中抽象出了描述数据和视图之间关系公共属性，并且相关的set方法是protected，那么在外部直接通过`ModelAndView`是无法进行设置值的，而`ModelAndView`有几个子类，分别是：`JSONModelAndView`,`JSONPModelAndView`,`PageModelAndView`,`AJAXModelAdnView`,那么各个子类，则对应了一种类型的视图了，并且在各个子类中通过基础父类相关set方法，并调整为public，以达到`ModelAndView`中的各个属性的set方法按需分配到了各个子类中，并且各个子类根据具体需求，对外暴露相关的set方法。这样就达到了绑定数据和相应视图关系。
######2）视图渲染器######
通过抽象一个渲染器的公共接口，`Render`来规范各种渲染器。
	<!-- lang: java -->
public interface Render {
    

    /**
     * 
    * <p>函数功能说明:当一个容器中有多个render的时候，order的值高的，将会优先调用其来进行渲染</p>
    * <p>Bieber  2014年6月17日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return int
     */
    public int order();
    /**
     * 
    * <p>函数功能说明:是否支持当前数据模型和视图渲染</p>
    * <p>Bieber  2014年6月13日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return boolean
     */
    public boolean isSupport(ModelAndView modelView);
    
    /**
     * 
    * <p>函数功能说明:进行渲染操作</p>
    * <p>Bieber  2014年6月13日</p>
    * <p>修改者名字 修改日期</p>
    * <p>修改内容</a>  
    * @return void
     */
    public void render(HttpServletRequest request, HttpServletResponse response,
                              ModelAndView modelView) throws RenderingViewException;
	}

该接口中只定义了三个方法，`order`方法用于当支持某一类型的视图有多个渲染器，那么会调用该值最高的渲染器渲染视图。`isSupport`用于判断当前渲染器是否支持当前数据模型的渲染，`render`方法则是进入到渲染具体方法，该方法执行完毕后，则将渲染完毕的视图，返回到前端。基于这个接口，框架默认提供了：`AJAXRender`用于渲染`AJAXModelAdnView`的数据模型和视图,`JSONPRender`则对一个了渲染`JSONPModelAndView`等等，这里就不再赘述，具体可以查看JBEER框架具体实现。<br/>
基于`Render`的接口，以及JBeer插件接口，可以提供freemark(具体见 [http://git.oschina.net/bieber/jbeer/tree/dev/jbeer_freemarker_viewer](http://git.oschina.net/bieber/jbeer/tree/dev/jbeer_freemarker_viewer "jbeer_freemarker_viewer")模块)和velocity(具体见  [http://git.oschina.net/bieber/jbeer/tree/dev/jbeer_velocity_viewer](http://git.oschina.net/bieber/jbeer/tree/dev/jbeer_velocity_viewer "jbeer_velocity_viewer")模块)的支持，以及对文件下载的支持，文件下载也可以理解为是一种试图渲染器，只是返回前端的是字节流（具体见 [http://git.oschina.net/bieber/jbeer/tree/dev/jbeer_file_viewer](http://git.oschina.net/bieber/jbeer/tree/dev/jbeer_file_viewer "jbeer_file_viewer")模块）。通过这种方式可以更加灵活的扩展JBeer的视图类型以及渲染器。

后续会对MVC部分细节的实现，进行详细描述，有什么问题欢迎大家积极提问。