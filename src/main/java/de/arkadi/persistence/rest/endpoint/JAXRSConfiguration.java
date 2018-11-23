package de.arkadi.persistence.rest.endpoint;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


/**
 * base uri or application endpoint
 * it is a config class
 * all resource a re processed by default
 * you can define which classes to register by overriding the getClasses method  which returns your classes
 */
@ApplicationPath("/api")
public class JAXRSConfiguration extends Application {
}