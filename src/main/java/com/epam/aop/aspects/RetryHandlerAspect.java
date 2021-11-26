package com.epam.aop.aspects;

import com.epam.annotations.RetryOnFailure;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.support.RetryTemplate;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


@Aspect
public class RetryHandlerAspect {

    private static Logger logger = LoggerFactory.getLogger(RetryHandlerAspect.class);


    @Around("callAt(retry)")
    public Object audit(ProceedingJoinPoint pjp, RetryOnFailure retry) throws Throwable {
        Object result = null;
        result = retryableExecute(pjp);
        return result;
    }

    @Pointcut("@annotation(retry)")
    public void callAt(RetryOnFailure retry) {
    }

    protected Object retryableExecute(final ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        logger.debug("-----Retry Aspect---------");
        logger.debug("Method: "+signature.toString());

        RetryOnFailure retry = method.getDeclaredAnnotation(RetryOnFailure.class);
        int retryAttempts = retry.retryAttempts();
        long sleepInterval = retry.sleepInterval();
        Class<? extends Throwable>[] ignoreExceptions = retry.ignoreExceptions();

        RetryTemplate retryTemplate = RetryTemplate.builder()
                .fixedBackoff(sleepInterval)
                .customPolicy(new CustomRetryPolicy(new ArrayList<>(List.of(ignoreExceptions)),
                        retryAttempts))
                .build();

        return   retryTemplate.execute(retryContext -> pjp.proceed());}
//        return   null;}
}