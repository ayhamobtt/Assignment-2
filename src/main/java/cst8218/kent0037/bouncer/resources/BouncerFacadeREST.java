/*
 * Contains RESTful web service resources for the Bouncer entity
 * Provides http responses
 * Author: Jordan Kent
 * Date: June 23, 2023
 */
package cst8218.kent0037.bouncer.resources;

import cst8218.kent0037.bouncer.business.AbstractFacade;
import cst8218.kent0037.bouncer.entity.Bouncer;
import java.util.List;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("cst8218.kent0037.bouncer.entity.bouncer")
public class BouncerFacadeREST extends AbstractFacade<Bouncer> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public BouncerFacadeREST() {
        super(Bouncer.class);
    }

    /* Added supporting case where there is no id body*/
    @PUT
    @Path("/{id}")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response edit(@PathParam("id") Long id, Bouncer entity) {
        if (entity.getId() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (!id.equals(entity.getId())) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Bouncer existingBouncer = super.find(id);
        if (existingBouncer == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        existingBouncer.setY(entity.getY());
        existingBouncer.setX(entity.getX());
        existingBouncer.setySpeed(entity.getySpeed());
        super.edit(existingBouncer);
        return Response.status(Response.Status.OK).entity(existingBouncer).build();
    }

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Long id) {
        Bouncer bouncer = super.find(id);
        if (bouncer == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        super.remove(bouncer);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response find(@PathParam("id") Long id) {
        Bouncer bouncer = super.find(id);
        if (bouncer == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(bouncer).build();
    }


    @GET
    @Path("{from}/{to}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        List<Bouncer> bouncers = super.findRange(new int[] { from, to });
        return Response.status(Response.Status.OK).entity(bouncers).build();
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public Response countREST() {
        String count = String.valueOf(super.count());
        return Response.status(Response.Status.OK).entity(count).build();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    /* Updated so if ID is present, returns a bad request*/
    @POST
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response createOrUpdate(Bouncer entity) {
        if (entity.getId() == null) {
            super.create(entity);
            return Response.status(Response.Status.CREATED)
                .header("Location", "/bouncers/" + entity.getId())
                .entity(entity)
                .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("ID should not be provided in the request body for creating a new resource.")
                .build();
        }
    }

    /* Implemented POST on a specific ID*/
    @POST
    @Path("/{id}")
    @Consumes(value = {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Asynchronous
    public void create(@Suspended final AsyncResponse asyncResponse, final Bouncer entity, @PathParam("id") String id) {
        if (id == null) {
            asyncResponse.resume(doCreate(entity));
        } else {
            asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
        }
    }

    private Response doCreate(Bouncer entity) {
        super.create(entity);
        return Response.status(Response.Status.CREATED).entity(entity).build();
    }

    @GET
    @Produces(value = {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Asynchronous
    public void findAll(@Suspended final AsyncResponse asyncResponse) {
        asyncResponse.resume(doFindAll());
    }

    private Response doFindAll() {
        List<Bouncer> bouncers = super.findAll();
        return Response.status(Response.Status.OK).entity(bouncers).build();
    }
    
}