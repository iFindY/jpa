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
import java.util.List;
import java.util.Optional;

/**
 * this bean not maintaining state
 * for every request a new bean is used
 */
@Stateless
@Path("/authors")
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

    //TIP get author object created somewhere validate before  proceed method
    @POST
    @Consumes("application/xml")
    public Response create(@Valid Author entity) {
        em.persist(entity);
        return Response.created(
                UriBuilder
                        //  path of this endpoint
                        .fromResource(AuthorEndpoint.class)
                        //append path to the existing path plus id , "redirecting"
                        .path(String.valueOf(entity.getId())).build())
                .build();
    }

    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    public Response deleteById(@PathParam("id") Long id) {
        Author entity = em.find(Author.class, id);
        if (entity == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        em.remove(entity);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces("application/xml")
    public Response findById(@PathParam("id") Long id) throws ISBNNotFoundException {
        TypedQuery<Author> findByIdQuery = em.createQuery("SELECT DISTINCT a FROM Author a WHERE a.id = :entityId ORDER BY a.id", Author.class);
        findByIdQuery.setParameter("entityId", id);
        //TIP   short if is only for assignment,
        //TIP   if a null value occur throw exception which will be handled by our exception-manager, this will send an error response
        Author entity = Optional
                .of(findByIdQuery.getSingleResult())
                .orElseThrow(ISBNNotFoundException::new);

        return Response.ok(entity).build();
    }

    @GET
    @Produces("application/xml")
    public Response listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult) {
        TypedQuery<Author> findAllQuery = em.createQuery("SELECT DISTINCT a FROM Author a ORDER BY a.id", Author.class);
        if (startPosition != null) {
            findAllQuery.setFirstResult(startPosition);
        }
        if (maxResult != null) {
            findAllQuery.setMaxResults(maxResult);
        }
        final List<Author> results = findAllQuery.getResultList();
        //TIP Maintain list type, else <?>
        GenericEntity<List<Author>> authorWrapper = new GenericEntity<List<Author>>(results) {
        };


        Link self = Link.fromUri(uriInfo
                .getBaseUriBuilder()
                .path(getClass())
                .path(getClass(), "listAll")
                .build("objekctGetSomeIDforEndPoint"))
                .rel("self")
                .type("GET")
                .build();


        //LinkResource selfLink = new LinkResource(self);

        //.status(Status.FOUND), ok = 200
        return Response.ok(authorWrapper).links(self).build();
    }

    @PUT
    @Path("/{id:[0-9][0-9]*}")
    @Consumes("application/xml")
    public Response update(Author entity) {
        entity = em.merge(entity);
        return Response.noContent().build();
    }
}