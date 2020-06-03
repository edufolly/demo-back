package br.com.strategiccore.repositories;

import br.com.strategiccore.entities.AbstractEntity;
import br.com.strategiccore.utils.Config;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.hibernate.Session;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

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

        return query.firstResult();
    }
}
