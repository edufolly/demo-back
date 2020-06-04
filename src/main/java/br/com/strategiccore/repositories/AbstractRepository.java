package br.com.strategiccore.repositories;

import br.com.strategiccore.entities.AbstractEntity;
import br.com.strategiccore.utils.Config;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import org.hibernate.Session;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;

/**
 * @author Eduardo Folly
 */
@ApplicationScoped
public abstract class AbstractRepository<T extends AbstractEntity>
        implements PanacheRepository<T> {

    @Inject
    EntityManager em;

    public T add(T entity, Long userId) {
        entity.setCreatedAt(LocalDateTime.now());
        entity.setCreatedBy(userId);
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setUpdatedBy(userId);

        persist(entity);

        return entity;
    }

    public T update(Long id, T newEntity, Long userId) {
        newEntity.setId(id);
        newEntity.setUpdatedAt(LocalDateTime.now());
        newEntity.setUpdatedBy(userId);

        Session session = (Session) em.getDelegate();
        session.update(newEntity);

        return newEntity;
//
//        T entity = findById(id);
//
//        entity.merge(newEntity);
//
//        return entity;
    }

    public T get(Long id) {
        PanacheQuery<T> query = this.find("id = ?1 and deletedAt = ?2",
                id, Config.NOT_DELETED);

        T entity = query.firstResult();

        if (entity == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return entity;
    }

    public int pageCount(int perPage) {
        PanacheQuery<T> query = this
                .find("deletedAt = ?1", Config.NOT_DELETED);

        return query.page(Page.ofSize(perPage)).pageCount();
    }

    public List<T> getAll(int page, int perPage) {
        PanacheQuery<T> query = this
                .find("deletedAt = ?1", Config.NOT_DELETED);

        return query.page(Page.of(page - 1, perPage)).list();
    }

    public T delete(Long id, Long userId) {
        int deleted = this
                .update("deletedAt = ?1, deletedBy = ?2 where id = ?3",
                        LocalDateTime.now(), userId, id);

        if (deleted == 0) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        if (deleted > 1) {
            throw new WebApplicationException(Response.Status.NOT_MODIFIED);
        }

        return this.findById(id);
    }

    public long syncCount(Long timestamp) {
        if (timestamp == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        LocalDateTime localDateTime = LocalDateTime
                .ofInstant(Instant.ofEpochMilli(timestamp),
                        TimeZone.getDefault().toZoneId());

        PanacheQuery<T> query = this
                .find("deletedAt > ?1 or updatedAt > ?1", localDateTime);

        return query.count();
    }

    public List<T> sync(LocalDateTime timestamp, Integer page, Integer perPage) {

        PanacheQuery<T> query = this
                .find("deletedAt > ?1 or updatedAt > ?1", timestamp);

        return query.page(Page.of(page - 1, perPage)).list();
    }
}
