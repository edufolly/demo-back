package br.com.strategiccore.repositories;

import br.com.strategiccore.entities.User;
import io.quarkus.elytron.security.common.BcryptUtil;

/**
 * @author Eduardo Folly
 */
public class UserRepository extends AbstractRepository<User> {
    @Override
    public User add(User entity, Long userId) {
        entity.setPass(BcryptUtil.bcryptHash(entity.getPass()));
        return super.add(entity, userId);
    }

    // TODO - Override Update.
}
