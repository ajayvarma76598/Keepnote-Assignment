package com.stackroute.keepnote.aspectj;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/* Annotate this class with @Aspect and @Component */
@Aspect 
@Component
public class LoggingAspect {
	private Log log=LogFactory.getLog(getClass());
	/*
	 * Write loggers for each of the methods of Note controller, any particular method
	 * will have all the four aspectJ annotation
	 * (@Before, @After, @AfterReturning, @AfterThrowing).
	 */
	@Before("execution(* com.stackroute.keepnote.controller.NoteController.*(..))")
	public void beforNoteAdvice(JoinPoint joinPoint) {
		log.info("before Note Advice called "+ joinPoint.getSignature().getName());
	}
	
	@After("execution(* com.stackroute.keepnote.controller.NoteController.*(..))")
	public void afterNoteLog(JoinPoint joinPoint) {
		log.info("calling after "+ joinPoint.getSignature().getName());
	}
	
	@AfterReturning("execution(* com.stackroute.keepnote.controller.NoteController.*(..))")
	public void afterReturningNote(JoinPoint joinPoint) {
		log.info("calling after returning "+ joinPoint.getSignature().getName());
	}
	
	@AfterThrowing("execution(* com.stackroute.keepnote.controller.NoteController.*(..))")
	public void AfterThrowingNote(JoinPoint joinPoint) {
		log.info("calling after throwing note "+ joinPoint.getSignature().getName());
	}
}
