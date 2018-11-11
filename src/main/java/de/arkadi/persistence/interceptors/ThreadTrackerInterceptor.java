package de.arkadi.persistence.interceptors;

import de.arkadi.persistence.qualifier.ThreadTrackable;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import java.util.logging.Logger;

import static javax.interceptor.Interceptor.Priority.APPLICATION;

@ThreadTrackable
@Interceptor
@Priority(APPLICATION + 30)
public class ThreadTrackerInterceptor {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Inject
    private Logger logger;

    // ======================================
    // =          Business methods          =
    // ======================================

    @AroundInvoke
    public Object annotateThread(InvocationContext invocationContext) throws Exception {
        String originName = Thread.currentThread().getName();
        try {
            String tracingName = invocationContext.getMethod().getName() + "%" + originName;
            logger.info("Thread :" + tracingName);
            Thread.currentThread().setName(tracingName);
            return invocationContext.proceed();
        } finally {
            Thread.currentThread().setName(originName);
        }
    }
}