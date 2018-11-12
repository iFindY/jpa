package de.arkadi.persistence.interceptors;


import de.arkadi.persistence.qualifier.Loggable2;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

@Loggable2(debug = true)
@Interceptor
public class LoggingDebugInterceptor {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Inject
    private Logger logger;

    // ======================================
    // =          Business methods          =
    // ======================================

    /**
     * debug methods  use invocation context
     * @param ic
     * @return
     * @throws Exception
     */
    @AroundInvoke
    private Object intercept(InvocationContext ic) throws Exception {
        logger.info("> {}" + ic.getMethod());
        logger.info("> Parameters            : {}" + ic.getParameters());
        final Class<? extends Object> runtimeClass = ic.getTarget().getClass();
        logger.info("> Runtime class         : {}" + runtimeClass.getName());
        logger.info("> Extended classes      : {}" + new Object[]{runtimeClass.getClasses()});
        logger.info("> Implemented interfaces: {}" + new Object[]{runtimeClass.getInterfaces()});
        logger.info("> Annotations ({})       : {}" + runtimeClass.getAnnotations().length + runtimeClass.getAnnotations());
        final Class<?> declaringClass = ic.getMethod().getDeclaringClass();
        logger.info("> Declaring class       : {}" + declaringClass);
        logger.info("> Extended classes      : {}" + new Object[]{declaringClass.getClasses()});
        logger.info("> Annotations ({})       : {}" + declaringClass.getAnnotations().length + declaringClass.getAnnotations());
        try {
            return ic.proceed();
        } finally {
            logger.info("< {}" + ic.getMethod());
        }
    }
}