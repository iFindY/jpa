package de.arkadi.persistence.producers;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;


public class ResourceProducer {

    // ======================================
    // =              Producers             =
    // ======================================

    @Produces
    @PersistenceContext(unitName = "storePU")
    private EntityManager em;

    @Produces
    @RequestScoped
    private FacesContext produceFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    @Produces
    @RequestScoped
    private HttpServletResponse produceHttpServletResponse() {
        return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    }

    /**
     * @param injectionPoint give access to meta data about the injection point.
     *                       In this case it will get the name if the case it is injected into.
     *                       can get bean type , its qualifier, object itself.
     *                       Just inject the logger and the logger will confider thyself.
     * @return a configured logger
     */
    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
}