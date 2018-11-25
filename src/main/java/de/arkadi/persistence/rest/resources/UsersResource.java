package de.arkadi.persistence.rest.resources;

import academy.learnprogramming.entities.ApplicationUser;
import academy.learnprogramming.entities.Employee;
import de.arkadi.persistence.model.ApplicationUser;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;


/**
 * See also {@link #createNewUser(ApplicationUser, String)}}.
 * See also {@link de.arkadi.persistence.model.ApplicationUser}}.
 * See also {@link MyOtherClass#myMethod(String)}.
 * See also {@link com.mypackage.YetAnotherClass#myMethod(String)}.
 * See also this {@linkplain #myMethod(String) implementation}.
 * See also {@link <a href="http://google.com">link lable </a>}
 * {@link #toJsonString(List)} (int, int) getComponentAt}
 */
@Path("users")
@Consumes("application/json")
//@Produces("application/json")
public class UsersResource {

    //TIP @HeaderParam get a Header value for a specific key
    @POST //api/v1/users/form
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("form")
    public Response createNewUser(
            @FormParam("email") String email,
            @FormParam("password") String password,
            @HeaderParam("Referer") String referer) {


        return null;
    }


    /**
     * @see HttpHeaders#getRequestHeaders()
     */
    //TIP another way to inject headers in your code if you want all key-values
    @POST
    @Path("map")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createNewUser(
            MultivaluedMap<String, String> formMap,
            @Context HttpHeaders httpHeaders) {

//        httpHeaders.getHeaderString("Referer");

        httpHeaders.getRequestHeader("Referer").get(0);

        //printing headers
        httpHeaders.getRequestHeaders().keySet().forEach(System.out::println);

        // get header value for  a key
        String email = formMap.getFirst("email");
        String password = formMap.getFirst("password");

        return null;
    }

    //TIP read me creation

    /**
     * application user ist enriched with  @FormParams annotation, see {@link ApplicationUser#email}.
     */
    //TIP @CookieParam   get cookies
    @POST
    @Path("bean")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createNewUser(
            @BeanParam ApplicationUser applicationUser,
            @CookieParam("user") String user) {

        return null;
    }


    //TIP a client can accept tow types so we can produce two types, and return the supported one. there is also ann waiting of types.
    // GET /api/v1/employees/employees HTTP/1.1
    // based on accept header  proper  return  type is produced
    //     @Produces({MediaType.APPLICATION_JSON;qs=0,9, MediaType.APPLICATION_XML})
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUserById(@PathParam("id") Long id) {


        return null;
    }

    //TIP  return type with headers
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUserById2(@PathParam("id") Long id, @Context HttpHeaders httpHeaders) {

        MediaType mediaType = httpHeaders.getAcceptableMediaTypes().get(0);
        return Response.ok("SomeValue", mediaType).status(Response.Status.OK).build();
    }


}




