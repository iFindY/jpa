package de.arkadi.persistence.interceptors;

import de.arkadi.persistence.qualifier.LifeCheck;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

@LifeCheck
@Interceptor
public class LifeCheckInterceptor {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Inject
    private Logger logger;

    // ======================================
    // =          Lifecycle methods         =
    // ======================================

    /**
     * methods monitor live cycle of a bean
     * @param ic
     * @throws Exception
     */
    @PostConstruct
    private void pingConstruct(InvocationContext ic) throws Exception {
        logger.info("Constructed " + ic.getTarget().toString());
    }

    @PreDestroy
    private void pingDestroy(InvocationContext ic) throws Exception {
        logger.info("Destroyed {}" + ic.getTarget().toString());
    }
}