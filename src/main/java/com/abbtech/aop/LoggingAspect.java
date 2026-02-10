package com.abbtech.aop;

import com.abbtech.annotations.LogIgnore;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.abbtech.service.*.*(..))")
    public void serviceLayer() {
    }

    @Around("serviceLayer()")
    public Object logBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().getName();

        Object[] args = sanitize(joinPoint.getArgs());

        log.info("Before method execution: {} with args: {}", method, args);

        Object response = joinPoint.proceed();

        var sanitizedResponse = sanitize(response);
        log.info("After method execution: {} with response: {}", method, sanitizedResponse);

        return response;
    }


    @After("serviceLayer()")
    public void logAfter() {
        System.out.println("After method execution");
    }

    private Object[] sanitize(Object[] objects) {
        return Arrays.stream(objects)
                .map(this::sanitize)
                .toArray();
    }

    private Object sanitize(Object object) {
        if (object == null) {
            return null;
        }

        try {
            Object copy = object.getClass().getDeclaredConstructor().newInstance();

            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);

                if (field.isAnnotationPresent(LogIgnore.class)) {
                    continue;
                }

                field.set(copy, field.get(object));
            }

            return copy;
        } catch (Exception e) {
            return object.toString();
        }
    }
}
