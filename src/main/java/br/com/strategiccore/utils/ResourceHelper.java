package br.com.strategiccore.utils;

import br.com.strategiccore.entities.AbstractEntity;
import br.com.strategiccore.repositories.AbstractRepository;
import io.vertx.core.http.HttpServerRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class ResourceHelper<T extends AbstractEntity> {

    final AbstractRepository<T> repository;
    final HttpServerRequest request;

    public ResourceHelper(AbstractRepository<T> repository,
                          HttpServerRequest request) {
        this.repository = repository;
        this.request = request;
    }

    public Response create(T entity) {
        entity = repository.add(entity, 1L);
        return Response.ok(entity).build();
    }

    public Response update(Long id, T entity) {
        entity = repository.update(id, entity, 2L);
        return Response.ok(entity).build();
    }

    public Response get(Long id) {
        T entity = repository.get(id);
        return Response.ok(entity).build();
    }

    public Response getAll(int page, int perPage) {
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
            builder.link(uriBuilder(1, perPage),
                    "first");
        }

        if (page - 1 > 1) {
            builder.link(uriBuilder(page - 1, perPage),
                    "previous");
        }

        if (page + 1 < pageCount) {
            builder.link(uriBuilder(page + 1, perPage),
                    "next");
        }

        if (page < pageCount) {
            builder.link(uriBuilder(pageCount, perPage),
                    "last");
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

    public Response delete(@PathParam("id") Long id) {
        T entity = repository.delete(id, 3L);
        return Response.ok(entity).build();
    }

    public Response syncCount(Long timestamp) {
        return Response.ok(repository.syncCount(timestamp)).build();
    }

    public Response sync(Long timestamp, Integer page, Integer perPage) {
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
                .ok(repository.sync(localDateTime, page, perPage))
                .build();
    }
}
