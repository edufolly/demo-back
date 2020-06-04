package br.com.strategiccore.resources;

import br.com.strategiccore.entities.AbstractEntity;
import br.com.strategiccore.repositories.AbstractRepository;
import br.com.strategiccore.utils.Config;
import io.vertx.core.http.HttpServerRequest;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

/**
 * @author Eduardo Folly
 */
public abstract class AbstractResource<
        R extends AbstractRepository<T>,
        T extends AbstractEntity> {

    @Inject
    R repository;

    @Context
    HttpServerRequest request;

    @POST
    @Transactional
    @Consumes("application/json")
    @Produces("application/json")
    public Response create(T entity) {
        entity = repository.add(entity, 1L);
        return Response.ok(entity).build();
    }

    @PUT
    @Transactional
    @Consumes("application/json")
    @Produces("application/json")
    @Path("{id}")
    public Response update(@PathParam("id") Long id, T entity) {
        entity = repository.update(id, entity, 2L);
        return Response.ok(entity).build();
    }

    @GET
    @Produces("application/json")
    @Path("{id}")
    public Response get(@PathParam("id") Long id) {
        T entity = repository.get(id);
        return Response.ok(entity).build();
    }

    @GET
    @Produces("application/json")
    public Response getAll(@QueryParam("page") int page,
                           @QueryParam("per_page") int perPage) {

        if (page < 1) {
            page = 1;
        }

        if (perPage < 1 || perPage > Config.MAX_GET_PER_PAGE) {
            perPage = Config.MAX_GET_PER_PAGE;
        }

        int pageCount = repository.pageCount(perPage);

        if (page > pageCount) {
            page = pageCount;
        }

        Response.ResponseBuilder builder = Response.ok();

        builder.entity(repository.getAll(page, perPage));

        if (page > 1) {
            builder.link(uriBuilder(1, perPage), "first");
        }

        if (page - 1 > 1) {
            builder.link(uriBuilder(page - 1, perPage), "previous");
        }

        if (page + 1 < pageCount) {
            builder.link(uriBuilder(page + 1, perPage), "next");
        }

        if (page < pageCount) {
            builder.link(uriBuilder(pageCount, perPage), "last");
        }

        return builder.build();
    }

    private URI uriBuilder(int page, int perPage) {
        return UriBuilder
                .fromUri(request.absoluteURI())
                .replaceQueryParam("page", page)
                .replaceQueryParam("per_page", perPage)
                .build();
    }

    @DELETE
    @Transactional
    @Produces("application/json")
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        T entity = repository.delete(id, 3L);
        return Response.ok(entity).build();
    }

    @GET
    @Path("/sync/count")
    @Produces("text/plain")
    public Response syncCount(@QueryParam("t") Long timestamp) {
        return Response.ok(repository.syncCount(timestamp)).build();
    }

    @GET
    @Path("/sync")
    @Produces("application/json")
    public Response sync(@QueryParam("t") Long timestamp,
                         @QueryParam("page") Integer page,
                         @QueryParam("per_page") Integer perPage) {

        if (timestamp == null || page == null || perPage == null
                || timestamp < 0 || page < 1 || perPage < 1
                || perPage > Config.MAX_SYNC_PER_PAGE) {

            throw new WebApplicationException(Response
                    .Status.INTERNAL_SERVER_ERROR);
        }

        LocalDateTime localDateTime = LocalDateTime
                .ofInstant(Instant.ofEpochMilli(timestamp),
                        TimeZone.getDefault().toZoneId());

        return Response
                .ok(repository.sync(localDateTime, page, perPage)).build();
    }
}
