package com.jbeer.framework.ws.impl;

import com.jbeer.framework.ws.SimpleService;

public class SimpleServiceImpl implements SimpleService {
 

	public String sayHello(String name) {
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "hello "+name;
	}

}
