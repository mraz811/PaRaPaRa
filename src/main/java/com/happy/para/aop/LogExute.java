package com.happy.para.aop;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogExute {

	
	// 메소드가 실행되기 전에 Argument 정보를 출력
	public void before(JoinPoint j) {
		Logger logger = LoggerFactory.getLogger(j.getTarget()+"");
		logger.debug("시작");
		
		Object[] args = j.getArgs();
		if(args !=null) {
			logger.debug("method:\t"+j.getSignature().getName());
			for (int i = 0; i < args.length; i++) {
				logger.debug(i+"번째:\t"+args[i]);
			}
			logger.debug("method:\t"+j.getSignature().getName());
		}
		
	}
	
	// 메소드가 실행되고 난 후 리턴이 있을때 
	public void afterReturning(JoinPoint j) {
		Logger logger = LoggerFactory.getLogger(j.getTarget()+"");
		logger.debug("끝");
	}
	
	// 예외가 발생 했을때 
	public void daoError(JoinPoint j) {
		Logger logger = LoggerFactory.getLogger(j.getTarget()+"");
		logger.debug("에러:\t"+j.getArgs());
		logger.debug("에러:\t"+j.toString());
	}
	
}
