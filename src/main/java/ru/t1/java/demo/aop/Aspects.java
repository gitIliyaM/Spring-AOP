package ru.t1.java.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class Aspects {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(public * ru.t1.java.demo.service.DataProcessorService.*(..))")
    public void allPointCut(){}

    @Before(value = "accountPointCut()")
    public void beforeAllPointCut(JoinPoint joinPoint){
        String string = Arrays.stream(joinPoint.getArgs())
            .map(a -> a.toString())
            .collect(Collectors.joining(","));
        logger.info("before {}, args=[{}]", joinPoint, string);
    }
    @After(value = "accountPointCut()")
    public void afterAllPointCut(JoinPoint joinPoint){
        String string = Arrays.stream(joinPoint.getArgs())
            .map(a -> a.toString())
            .collect(Collectors.joining(","));
        logger.info("after {}, args=[{}]", joinPoint, string);
    }
}

