package br.com.strategiccore.resources;

import br.com.strategiccore.entities.Endpoint;
import br.com.strategiccore.repositories.EndpointRepository;
import br.com.strategiccore.utils.ResourceHelper;
import io.vertx.core.http.HttpServerRequest;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * @author Eduardo Folly
 */
@Path("/endpoints")
@Tag(name = "Endpoints", description = "Endpoints Resource.")
public class EndpointResource {
    @Inject
    EndpointRepository repository;

    @Context
    HttpServerRequest request;

    @POST
    @Transactional
    @Consumes("application/json")
    @Produces("application/json")
    @Operation(summary = "Add a new endpoint.")
    public Response create(Endpoint endpoint) {
        return new ResourceHelper<>(repository, request).create(endpoint);
    }

    @PUT
    @Transactional
    @Consumes("application/json")
    @Produces("application/json")
    @Path("{id}")
    @Operation(summary = "Update the endpoint.")
    public Response update(@PathParam("id") Long id, Endpoint endpoint) {
        return new ResourceHelper<>(repository, request).update(id, endpoint);
    }

    @GET
    @Produces("application/json")
    @Path("{id}")
    @Operation(summary = "Returns the endpoint by the specified id.")
    public Response get(@PathParam("id") Long id) {
        return new ResourceHelper<>(repository, request).get(id);
    }

    @GET
    @Produces("application/json")
    @Operation(
            summary = "Returns a list of entities.",
            description = "Returns a list of entities."
    )
    public Response getAll(@QueryParam("page") int page,
                           @QueryParam("per_page") int perPage) {

        return new ResourceHelper<>(repository, request).getAll(page, perPage);
    }

    @DELETE
    @Transactional
    @Produces("application/json")
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        return new ResourceHelper<>(repository, request).delete(id);
    }

    @GET
    @Path("/sync/count")
    @Produces("text/plain")
    public Response syncCount(@QueryParam("t") Long timestamp) {
        return new ResourceHelper<>(repository, request).syncCount(timestamp);
    }

    @GET
    @Path("/sync")
    @Produces("application/json")
    public Response sync(@QueryParam("t") Long timestamp,
                         @QueryParam("page") Integer page,
                         @QueryParam("per_page") Integer perPage) {
        return new ResourceHelper<>(repository, request)
                .sync(timestamp, page, perPage);
    }
}
