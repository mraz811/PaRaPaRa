package com.happy.para.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// 8^^번
public class AccessLoggerFilter implements Filter{

	private Logger logger = LoggerFactory.getLogger(AccessLoggerFilter.class);
	
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		
		// 외부에서 요청된(외부포트로 접근한:8091) 주소
		String remoteArr = StringUtils.defaultString(request.getRemoteAddr(), "-");
		
		String uri = StringUtils.defaultIfEmpty(request.getRequestURI(), "");
		String url = (request.getRequestURL()==null)?"" : request.getRequestURL().toString();
		
		// 주소의 ?뒤에 있는 "?키=값&...."
		String queryString = StringUtils.defaultIfEmpty(request.getQueryString(), "");
		
		// Header 정보(Referer)
		String refer = StringUtils.defaultString(request.getHeader("Referer"),"-");
		
		// Header 정보(User-Agent)
		String agent = StringUtils.defaultString(request.getHeader("User-Agent"),"-");
		
		String fullUrl = url;
		fullUrl += StringUtils.isNotEmpty(queryString) ? "?"+queryString : queryString;
		
		StringBuffer result = new StringBuffer();
		result.append(":").append(remoteArr).append(":").append(fullUrl).append(":").append(refer)
		.append(":").append(agent);
		
		logger.warn("[LOG FILTER]+"+result.toString());
		
		chain.doFilter(req, resp);
		
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}
