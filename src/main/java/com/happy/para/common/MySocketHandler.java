package com.happy.para.common;

import javax.servlet.ServletConfig;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component(value = "wsChat.do")
public class MySocketHandler extends TextWebSocketHandler implements ServletConfigAware {

	@Override
	public void setServletConfig(ServletConfig servletConfig) {
		// TODO Auto-generated method stub
		
	}
	
	

}
