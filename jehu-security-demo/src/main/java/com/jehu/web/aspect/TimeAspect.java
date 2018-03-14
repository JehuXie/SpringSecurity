/**
 * 
 */
package com.jehu.web.aspect;

import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author Administrator
 *
 */
/*@Aspect
@Component*/
public class TimeAspect {

	//@Around("execution(* com.jehu.web.controller.UserController.*(..))")
	public Object HandlerControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
System.out.println("time aspect start");
		
		Object[] args = pjp.getArgs();
		for (Object arg : args) {
			System.out.println("arg is "+arg);
		}
		
		long start = new Date().getTime();
		
		Object object = pjp.proceed();
		
		System.out.println("time aspect 耗时:"+ (new Date().getTime() - start));
		
		System.out.println("time aspect end");
		
		return object; 
	}
	
}
