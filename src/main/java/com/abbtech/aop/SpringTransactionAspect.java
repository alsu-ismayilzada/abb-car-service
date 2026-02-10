package com.abbtech.aop;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SpringTransactionAspect {

    @Pointcut("@annotation(com.abbtech.annotations.SpringTransactionAnnotation)")
    public void springTransactionPointcut() {

    }

    @Before("springTransactionPointcut()")
    public void before() {
        System.out.println("Get connection from pool and begin transaction");
    }

    @AfterReturning(returning = "result", pointcut = "springTransactionPointcut()")
    public void afterReturning(Object result) {
        System.out.println("Commit transaction");
    }

    @AfterThrowing(pointcut = "springTransactionPointcut()", throwing = "ex")
    public void afterThrowing(Exception ex) {
        if(ex instanceof RuntimeException){
            System.out.println("Rollback transaction");
        }
    }

}
