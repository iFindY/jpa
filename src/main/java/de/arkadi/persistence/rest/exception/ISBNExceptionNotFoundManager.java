package de.arkadi.persistence.rest.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class ISBNExceptionNotFoundManager implements ExceptionMapper<ISBNNotFoundException> {
    @Override
    public Response toResponse(ISBNNotFoundException exception) {
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
