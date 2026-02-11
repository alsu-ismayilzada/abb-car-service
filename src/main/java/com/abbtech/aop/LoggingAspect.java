package com.abbtech.aop;

import com.abbtech.annotations.LogIgnore;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Aspect
@Slf4j
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.abbtech.controller..*(..))")
    public void serviceLayer() {
    }

    @Pointcut("execution(* com.abbtech.security.service.*.*(..))")
    public void securityLayer() {
    }

    @Around("serviceLayer() || securityLayer()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        String method = joinPoint.getSignature().getName();
        Object sanitizedArgs = sanitize(joinPoint.getArgs(), new IdentityHashMap<>());

        log.info("Before method execution: {} with args: {}", method, sanitizedArgs);

        Object response = joinPoint.proceed();
        Object sanitizedResponse = sanitize(response, new IdentityHashMap<>());

        log.info("After method execution: {} with response: {}", method, sanitizedResponse);

        return response;
    }


    private Object sanitize(Object object, Map<Object, Boolean> visited) {

        if (object == null) return null;

        if (visited.containsKey(object)) {
            return "[CIRCULAR_REFERENCE]";
        }

        visited.put(object, true);

        Class<?> type = object.getClass();

        if (isSimple(type)) {
            return maskIfJwt(object);
        }

        if (object instanceof UserDetails ||
                type.getPackageName().startsWith("org.springframework.security")) {
            return "[SECURITY_OBJECT]";
        }

        if (type.isArray()) {
            int length = Array.getLength(object);
            List<Object> list = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                list.add(sanitize(Array.get(object, i), visited));
            }
            return list;
        }

        if (object instanceof Collection<?> collection) {
            return collection.stream()
                    .map(item -> sanitize(item, visited))
                    .collect(Collectors.toList());
        }

        if (object instanceof Map<?, ?> map) {
            return map.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> sanitize(e.getValue(), visited)
                    ));
        }

        Map<String, Object> result = new LinkedHashMap<>();

        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);

            if (field.isAnnotationPresent(LogIgnore.class)) {
                continue;
            }

            try {
                Object value = field.get(object);
                result.put(field.getName(), sanitize(value, visited));
            } catch (Exception e) {
                result.put(field.getName(), "[ERROR]");
            }
        }

        return result;
    }

    private boolean isSimple(Class<?> type) {
        return type.isPrimitive()
                || type.equals(String.class)
                || Number.class.isAssignableFrom(type)
                || type.equals(Boolean.class)
                || type.equals(Character.class)
                || type.isEnum()
                || type.equals(UUID.class)
                || type.equals(Date.class);
    }

    private Object maskIfJwt(Object object) {
        if (object instanceof String str) {
            if (str.startsWith("eyJ") && str.length() > 20) {
                return "[JWT_TOKEN]";
            }
        }
        return object;
    }
}
