package de.arkadi.persistence.rest.resources;


import de.arkadi.persistence.model.Author;
import de.arkadi.persistence.rest.exception.ISBNNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.Response.Status.*;

/**
 * this bean not maintaining state
 * for every request a new bean is used
 */
//TIP you can return set  produce and accept media type for whole class. can be overridden on method lvl
@Stateless
@Path("/authors")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class AuthorEndpoint {

    // ======================================
    // =             Attributes             =
    // ======================================

    @PersistenceContext(unitName = "storePU")
    private EntityManager em;

    @Context
    UriInfo uriInfo;
    // ======================================
    // =           Public Methods           =
    // ======================================

    //TIP get author object created somewhere validate before proceed method
    @POST
    @Consumes("application/xml")
    public Response create(@Valid Author entity) {
        em.persist(entity);

        //TIP  [2 line example POST  not belong to code ] .path  can add variable part of the url of created resource
        URI uri = uriInfo.getAbsolutePathBuilder().path(entity.getId().toString()).build();
        Response response = Response.created(uri).status(CREATED).build();


        //TIP return an uri to created resources
        return Response.created(
                UriBuilder
                        //  path of this endpoint
                        .fromResource(AuthorEndpoint.class)
                        //append path to the existing path plus id , "redirecting"
                        .path(String.valueOf(entity.getId())).build())
                .status(CREATED)
                .build();
    }

    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    public Response deleteById(@PathParam("id") Long id) {
        Author entity = em.find(Author.class, id);
        if (entity == null) {
            return Response.status(NOT_FOUND).build();
        }
        em.remove(entity);
        return Response.noContent().build();
    }

    //TIP path parameter @PathParam. does work to "{id}/{department}". with : you  can define a pattern. java use injection behind the scene also we can set default values for parameters
    //TIP  a valid url pattern  @Path("user/{username}@{domain}.{company:^[a-zA-Z][a-zA-Z]$")}")
    //TIP it s up to if you want to use given pattern variables. if you want it java will inject them for us with the @PathParam annotation
    @GET
    @Path("{id:[0-9][0-9]*}")
    @Produces(APPLICATION_XML)
    public Response findById(@PathParam("id") @DefaultValue("0") Long id) throws ISBNNotFoundException {

        TypedQuery<Author> findByIdQuery = em.createQuery("SELECT DISTINCT a FROM Author a WHERE a.id = :entityId ORDER BY a.id", Author.class);
        findByIdQuery.setParameter("entityId", id);
        //TIP   short if is only for assignment,
        //TIP   if a null value occur throw exception which will be handled by our exception-manager, this will send an error response
        Author entity = Optional
                .of(findByIdQuery.getSingleResult())
                .orElseThrow(ISBNNotFoundException::new);

        return Response.ok(entity).build();
    }


    //TIP @QueryParam is more popular. the Parameters are defined as in this case /authors/?id=5&maxResult=20&start=20. With default value you can skipp null checks.

    /**
     * @see Request#evaluatePreconditions(EntityTag)
     */
    @GET
    @Produces(APPLICATION_XML)
    public Response listAll(
            @QueryParam("start") @DefaultValue("0") Integer startPosition,
            @QueryParam("max") @DefaultValue("10") Integer amount,
            @Context Request request) {

        // build and setup  query
        TypedQuery<Author> findAllQuery = em
                .createQuery("SELECT DISTINCT a FROM Author a ORDER BY a.id", Author.class)
                .setFirstResult(startPosition)
                .setMaxResults(amount);

        // use query
        final List<Author> results = findAllQuery.getResultList();

        //TIP Maintain list type, else <?>
        GenericEntity<List<Author>> authorWrapper = new GenericEntity<List<Author>>(results) {
        };


        Link self = Link.fromUri(uriInfo
                .getBaseUriBuilder()
                // add base path params
                .path(getClass())
                // add method of the class
                .path(getClass(), "listAll")
                // template parameter values
                .build("objekctGetSomeIDforEndPoint"))
                .rel("self")
                .type("GET")
                .build();


        //LinkResource selfLink = new LinkResource(self);

        //.status(Status.FOUND), ok = 200
        return Response.ok(authorWrapper).links(self).build();
    }


    //TIP caching example. save bandwidth if data not change sins last request
    @GET
    @Produces(APPLICATION_XML)
    public Response listAll2(
            @QueryParam("start") @DefaultValue("0") Integer startPosition,
            @QueryParam("max") @DefaultValue("10") Integer amount,
            @Context Request request) {

        // build and setup  query
        TypedQuery<Author> findAllQuery = em
                .createQuery("SELECT DISTINCT a FROM Author a ORDER BY a.id", Author.class)
                .setFirstResult(startPosition)
                .setMaxResults(amount);

        // use query
        final List<Author> results = findAllQuery.getResultList();

        // create current eTag for the result [not the best way to create the tag]
        EntityTag eTag = new EntityTag(Integer.toString(results.hashCode()));

        // create header response with Cache-Control entity
        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(1000);

        //  request contain old eTag for comparison
        Response.ResponseBuilder responseBuilder = request.evaluatePreconditions(eTag);

        // if the tag did not changed update expire date, else send request resource with new cacheControl
        responseBuilder = (responseBuilder != null)
                ? responseBuilder.cacheControl(cacheControl)
                : Response.ok(results).tag(eTag).cacheControl(cacheControl);

        return responseBuilder.build();
    }

    @PUT
    @Path("/{id:[0-9][0-9]*}")
    @Consumes("application/xml")
    public Response update(Author entity) {
        entity = em.merge(entity);
        return Response.noContent().build();
    }
}