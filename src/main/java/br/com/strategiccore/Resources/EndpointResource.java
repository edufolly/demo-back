package br.com.strategiccore.Resources;

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
        if (endpoint == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(endpoint).build();
    }
}
