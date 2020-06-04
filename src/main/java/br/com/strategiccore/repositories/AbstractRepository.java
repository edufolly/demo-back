package br.com.strategiccore.repositories;

import br.com.strategiccore.entities.AbstractEntity;
import br.com.strategiccore.utils.Config;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.hibernate.Session;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;

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

    public List<T> getAll() {
        PanacheQuery<T> query = this.find("deletedAt = ?1",
                Config.NOT_DELETED);

        return query.list();
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
}
