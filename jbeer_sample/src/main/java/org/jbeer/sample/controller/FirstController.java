package org.jbeer.sample.controller;

import java.io.IOException;
import java.util.Map;

import org.jbeer.sample.bean.User;
import org.jbeer.sample.bean.service.SimpleWebservice;
import org.jbeer.sample.bean.service.UserService;

import com.jbeer.framework.annotation.Action;
import com.jbeer.framework.annotation.Controller;
import com.jbeer.framework.annotation.PathParam;
import com.jbeer.framework.annotation.RefBean;
import com.jbeer.framework.annotation.RequestParameter;
import com.jbeer.framework.enumeration.RequestType;
import com.jbeer.framework.exception.DBException;
import com.jbeer.framework.exception.JBeerException;
import com.jbeer.framework.exception.ScanClassException;
import com.jbeer.framework.web.BaseController;
import com.jbeer.framework.web.ModelAndView;
import com.jbeer.framework.web.ModelAndView.PageModelAndView;
import com.jbeer.framework.web.RequestParameterUtil;
import com.jbeer.framework.ws.client.WSClientFactory;

@Controller(urlPattern="/first")
public class FirstController extends BaseController{

	@RefBean
	private UserService userService;
	
	@RefBean(factoryBeanClass=WSClientFactory.class)
	private SimpleWebservice simpleWebservice;
	
	@Action(urlPatterns="post.htm",requestType=RequestType.POST)
	public ModelAndView post(@RequestParameter(value="user")User users) throws JBeerException{
		System.out.println(this);
		PageModelAndView mav = ModelAndView.createModelAndView();
		mav.setView("view");
		return mav;
	}
	
	@Action(urlPatterns="index.htm",requestType=RequestType.ANY)
	public String index() throws IOException, ScanClassException{
		String hello = simpleWebservice.sayHello("bieber");
		System.out.println(hello);
		return "index";
	}
	
	@Action(urlPatterns="invoke_${id}_${name}.htm")
	public String pathParam(@PathParam("id")Integer id,@PathParam("name")String name){
	    
	    return "view";
	}
}
