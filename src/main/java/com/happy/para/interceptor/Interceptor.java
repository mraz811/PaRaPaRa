package com.happy.para.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/*
 * servlet-context.xml의 interceptor를 처리해 주는 class
 * 
 */
public class Interceptor extends HandlerInterceptorAdapter {

	private Logger logger = LoggerFactory.getLogger(Interceptor.class);
	
	// 인터셉터가 시작 될 때
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.debug("인터셉터 시작 ----- {}", new Date());
		HttpSession session = request.getSession();
		if(session.getAttribute("loginDto") == null) {
			response.sendRedirect("./index.jsp");
			return false;
		}
		return super.preHandle(request, response, handler);
	}

	// 인터셉터가 지나가고 나서
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.debug("인터셉터 종료 ----- {}", new Date());
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

	
	
	
}
