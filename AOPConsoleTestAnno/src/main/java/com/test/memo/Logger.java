package com.test.memo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/* 
  	클래스명에 @Aspect 을 붙임으로서 보조클래스인 Logger 클래스가
  	Aspect(관심) 객체로 등록된다.
  	기존의 <aop:aspect> 역할을 한다.
 */
@Aspect
public class Logger {
	
	// Pointcut 을 생성한다.
	
	/*
	 	기존의 xml에서 <aop:pointcut> 역할을 한다.
	 	void + 인자값(파라미터) 없음으로 선언한다.
	 	인자값(파라미터)을 선언해도 어차피 어노테이션[@]의 AspectJ 표현식이 우선이기 때문에
	 	메소드 자체의 형식은 의미없다.
	 	또한 구현부도 의미없다.
	 	대신에 메소드 이름이 나중에 각각의 Advice에서 식별자로 사용된다.
	 */
	
	@Pointcut("execution(public * com.test.memo.Memo.*(..))")
	public void m1() { }
	
	// 보조 업무
	// - 메모를 작성하는데 걸리는 시간을 로그에 남기는 보조업무
	
	
	// 보조업무 추가
	// -- 메모를 작성, 수정 하기전에 시간을 로그에 남기는 보조업무
	// 보조업무 생성과 동시에 Advice 설정도 같이 선언
	// - 아래의 @Around 어노테이션이 기존의 XML <aop:around> 역할을 한다.
	/*@Around("m1()")
	public void around(ProceedingJoinPoint joinPoint) throws Throwable {
		
		// 보조없무 시작
		System.out.println("====> 시간 기록 시작 <====");
		Long start = System.currentTimeMillis();
		
		
		try {
			// 주업무 시작
			
			 	joinPoint.proceed(); 메소드를 실행함으로
			 	Pointcut 에 설정된 주업무를 처리해주는 메소드가 호출되어 실행된다.
			 	지금은 public * com.test.memo.Memo.*(..) 이다.
			 
			
			joinPoint.proceed();
			
			
			 	그리고 joinPoint.proceed() 메소드의 리턴값은Object 이다.
			 	이를 통해 Aspect로 연결된 Original Method(지금은 Memo 클래스의 모든 메소드임)의 
			 	리턴값을 형변환을 통하여 받을수 있다.
			 	지금은 Original Method의 리턴값은 void 이다.
			 
			
			// 주업무 끝
		} finally {
			Long finish = System.currentTimeMillis();
			System.out.println("====> 시간 기록 종료 <====");
			
			System.out.println("== 주업무 실행시간 : " + (finish - start) + "ms");
			// 보조없무 끝
		}
		
	}*/
	
	// 보조업무 추가
	// -- 메모를 작성, 수정 하기전에 시간을 로그에 남기는 보조업무
	
	// 보조업무 생성과 동시에 Advice 설정도 같이 선언
	// - 아래의 @Before 어노테이션이 기존의 XML <aop:before> 역할을 한다.
	
	@Before("m1()")
	public void before() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(new Date());
		
		System.out.println("==> 메모 작성(수정) 날짜시간 : " + now);
	}
	
	// 보조업무 추가
	// -- 메모를 작성, 수정 하면 메모작성 횟수와 메모수정 횟수를 누적해서 로그에 남기는 보조 업무
	
	// 보조업무 생성과 동시에 Advice 설정도 같이 선언
	// - 아래의 @After 어노테이션이 기존의 XML <aop:after> 역할을 한다.
	int cnt = 0;
	
	@After("m1()")
	public void after() {
		cnt++;
		System.out.printf("메모 작성 또는 편집 횟수 : %d회\n", cnt);
	}
	
	// 보조업무 추가
	// -- 메모를 삭제 한 뒤 삭제한 메모의 번호를 로그에 남기는 보조업무
	// -- 보조 객체의 인자명(파라미터명)과 XML의 returning 의 인자명(파라미터명)은 동일해야 한다.
	
	// 보조업무 생성과 동시에 Advice 설정도 같이 선언
	// - 아래의 @AfterReturning 어노테이션이 기존의 XML <aop:after-returning> 역할을 한다.
	@AfterReturning(pointcut="m1()", returning="seq")
	public void afterReturning(Object seq) {
		System.out.println("삭제된 메모 번호 : " + seq);
	}
	
	
	// 보조업무 추가
	// -- 메모를 읽다가 예외가 발생하면 로그에 남기는 보조업무
	// -- 보조객체의 인자명(파라미터명)과 XML의 throwing의 인자명(파라미터명)은 동일해야 한다.
	
	// 보조업무 생성과 동시에 Advice 설정도 같이 선언
	// - 아래의 @AfterThrowing 어노테이션이 기존의 XML <aop:after-throwing> 역할을 한다.
	@AfterThrowing(pointcut="m1()", throwing="e")
	public void afterThrowing(Exception e) {
		System.out.println("예외 기록 : " + e.getMessage());
	}
	
}
