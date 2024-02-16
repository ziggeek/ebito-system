package com.ebito.document_generator.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class RestControllerLoggerAspect {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restControllerPointcut(){}

    @Around("restControllerPointcut()")
    public Object logAroundRestController(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isInfoEnabled()) {
            log.info("REQUEST - Executing rest call: {}.{}() with argument[s] = {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    Arrays.toString(joinPoint.getArgs()));
        }

        Object result = joinPoint.proceed();
        if (log.isInfoEnabled()) {
            log.info("RESPONSE - Exit rest call: {}.{}() with result = {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    result);
        }

        return result;
    }
}
