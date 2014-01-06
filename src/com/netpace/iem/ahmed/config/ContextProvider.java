package com.netpace.iem.ahmed.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class ContextProvider implements ApplicationContextAware {

	private static ApplicationContext context = null;
	
	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		ContextProvider.context = context;  
	}
	
	public static ApplicationContext getContext() {
		return context;
	}

}
