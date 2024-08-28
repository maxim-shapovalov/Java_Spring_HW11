package ru.gb.spring.aspect.logging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class LoggingAspect {

    private final LoggingProperties properties;
    @Pointcut("@annotation(ru.gb.spring.aspect.logging.Logging)") // method
    public void loggingMethodsPointcut() {}

    @Pointcut("@within(ru.gb.spring.aspect.logging.Logging)") // class
    public void loggingTypePointcut() {}

    @Around(value = "loggingMethodsPointcut() || loggingTypePointcut()")
    public Object loggingMethod(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        log.atLevel(properties.getLevel()).log("Before -> TimesheetService#{}", methodName);
        try {
            return pjp.proceed();
        } finally {
            log.atLevel(properties.getLevel()).log("After -> TimesheetService#{}", methodName);
        }
    }

}
