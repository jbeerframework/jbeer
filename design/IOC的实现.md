> 本系列博文，将会一步一步介绍如何构建一个轻量级的web框架jbeer git地址：http://git.oschina.net/bieber/jbeer

讲到IOC，想必大家都熟悉。自从我进入J2EE这个圈，就无比的崇拜Spring，谈到Spring，起初接触它的人，都是让它的IOC和AOP所吸引。说到IOC，至于其原理大家应该都知道，就是反射，调用`newInstance`方法来初始化一个类。当然，JBeer肯定也是这种方式，暂时还没找到其他的方式来实例化一个类（除了工厂方法）。本文需要分享的是JBeer在处理Bean之间的依赖关系如何实现。<br/>
###一、一般方法###
     <!-- lang:java -->
	public class Bean1 {

	private Bean2 bean;
	}

当初始化`Bean1`的时候，会分析`Bean1`字段，判断是否依赖其他Bean,此处会发现该Bean依赖`Bean2`，那么需要实例化一个`Bean2`赋值给`bean`属性。正常思维是这样的，加入在`Bean2`是下面的样子：

	<!-- lang:java -->
	public class Bean2 {

	private Bean1 bean;
	
	}

那么当框架来实例化`Bean2`的时候，需要`Bean1`，那么是不是就进入了一个比较麻烦的情况，因为`Bean1`和`Bean2`之间存在相互引用，这种情况在开发过程中是存在的，会让框架进入一个类似死锁的情况。要解决这个问题，也很好解决，但是通过一般的解决办法可能会比较繁琐。不是很清楚Spring是如何解决这种情况的，但是在Jbeer里面，则是通过代理的方式来完成Bean内部对其他Bean的依赖。
###二、JBeer解决方案###
Jbeer当实例化Bean1时候，发现Bean1中有对Bean2的依赖，此时Jbeer不会去实例化这个依赖的Bean2，而是通过这个Bean2的类型，创建一个该Bean2的代理，并且将Bean2的beanid放到该代理类中，当发现调用Bean2的方法时候，则通过beanid，去获取真是的Bean2实体，来进行处理。这样可以将bean的实例化和属性的赋值隔离开来，从而避免了在Bean初始化过程中，处理复杂的实体之间的引用。

上面这是JBeer在IOC实现的时候，一些细节的实现。而关于整个bean的实例化过程，这里就不再进行过多的描述，因为关于这方面的东西，实在太多了。而关于具体实现，欢迎查看Jbeer源码。

其他大家的留言和建议。