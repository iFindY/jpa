package de.arkadi.persistence.interceptors;

import de.arkadi.persistence.qualifier.DisplayException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Level;
import java.util.logging.Logger;


@DisplayException
@Interceptor
public class ExceptionInterceptor {

    @Inject
    private Logger logger;

    @Inject
    private FacesContext facesContext;

    @AroundInvoke
    private Object intercept(InvocationContext ic) throws Exception {
        try {
            return ic.proceed();
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            logger.log(Level.SEVERE, "!!!" + ic.getMethod() + "--" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}