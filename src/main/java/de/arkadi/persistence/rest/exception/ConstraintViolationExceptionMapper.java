package de.arkadi.persistence.rest.exception;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.stream.Collectors;


//TIP you catch a container exception
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {

        final String message = exception.getConstraintViolations().stream()
                .map(cv -> extractPropertyName(cv.getPropertyPath().toString())
                        + " : " + cv.getMessage())
                .collect(Collectors.joining(", "));


        return Response.status(Response.Status.BAD_REQUEST)
                .header("X-Validation-Failure", message)
                .build();
    }

    private String extractPropertyName(String path) {
        return path.substring(path.lastIndexOf(".") + 1);
    }
}
