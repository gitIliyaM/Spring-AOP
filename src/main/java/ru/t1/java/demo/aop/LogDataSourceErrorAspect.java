package ru.t1.java.demo.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.model.DataSourceErrorLog;
import ru.t1.java.demo.repository.DataSourceErrorLogRepository;

import java.io.IOException;
import java.util.Arrays;

@Aspect
@Component
public class LogDataSourceErrorAspect {

    private final DataSourceErrorLogRepository dataSourceErrorLogRepository;

    @Autowired
    LogDataSourceErrorAspect(DataSourceErrorLogRepository dataSourceErrorLogRepository){
        this.dataSourceErrorLogRepository = dataSourceErrorLogRepository;
    }

    @AfterThrowing(pointcut = "execution(public * ru.t1.java.demo.service.DataProcessorService.*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) throws IOException {
        DataSourceErrorLog errorLog = new DataSourceErrorLog();
        errorLog.setExceptionStackTrace(Arrays.toString(ex.getStackTrace()));
        errorLog.setMessage(ex.getMessage());
        errorLog.setSignatureMethod(joinPoint.getSignature().toShortString());

        ObjectMapper objectMapper = new ObjectMapper();
        String string = objectMapper.writeValueAsString(errorLog);

        DataSourceErrorLog logList = objectMapper.readValue(string, DataSourceErrorLog.class);
        dataSourceErrorLogRepository.saveAndFlush(logList);
    }
}
