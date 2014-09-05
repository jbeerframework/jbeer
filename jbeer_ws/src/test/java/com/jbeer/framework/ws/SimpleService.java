package com.jbeer.framework.ws;

import javax.jws.WebService;

@WebService
public interface SimpleService {
	public String sayHello(String name);
}
