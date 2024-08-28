package ru.gb.spring.aspect.recover;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class RecoverAspect {

    private final RecoverProperties properties;

    @Pointcut("@annotation(ru.gb.spring.aspect.recover.Recover)")
    public void methodRecoverPointcut() {}

    @Pointcut("@within(ru.gb.spring.aspect.recover.Recover)")
    public void typeRecoverPointcut() {}

    @Around(value = "methodRecoverPointcut() || typeRecoverPointcut()")
    public Object methodRecover(ProceedingJoinPoint pjp){
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Object target = pjp.getTarget();
        Method method = methodSignature.getMethod();

        try {
            return pjp.proceed();
        } catch (Throwable ex) {
            if (method.isAnnotationPresent(Recover.class)) {
                log.atLevel(properties.getLevel()).log("Recovering {}#{} after Exception[{}, \"{}\"]",
                        target.getClass().getSimpleName(),
                        method.getName(),
                        ex.getClass().getSimpleName(),
                        ex.getMessage());
                return method.getDefaultValue();
            }
            throw new RuntimeException(ex);
        }

    }
}
