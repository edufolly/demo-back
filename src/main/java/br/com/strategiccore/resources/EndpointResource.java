package br.com.strategiccore.resources;

import br.com.strategiccore.entities.Endpoint;
import br.com.strategiccore.repositories.EndpointRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * @author Eduardo Folly
 */
@Path("/endpoint")
public class EndpointResource {

    @Inject
    EndpointRepository endpointRepository;

    @POST
    @Transactional
    @Consumes("application/json")
    @Produces("application/json")
    public Response create(Endpoint endpoint) {
        endpoint = endpointRepository.add(endpoint, 1L);
        return Response.ok(endpoint).build();
    }

    @PUT
    @Transactional
    @Consumes("application/json")
    @Produces("application/json")
    @Path("{id}")
    public Response update(@PathParam("id") Long id, Endpoint endpoint) {
        endpoint = endpointRepository.update(id, endpoint, 2L);
        return Response.ok(endpoint).build();
    }

    @GET
    @Produces("application/json")
    @Path("{id}")
    public Response get(@PathParam("id") Long id) {
        Endpoint endpoint = endpointRepository.get(id);
        return Response.ok(endpoint).build();
    }

    @GET
    @Produces("application/json")
    public Response getAll() {
        return Response.ok(endpointRepository.getAll()).build();
    }

    @DELETE
    @Transactional
    @Produces("application/json")
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        Endpoint endpoint = endpointRepository.delete(id, 3L);
        return Response.ok(endpoint).build();
    }
}
