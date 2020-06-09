package br.com.strategiccore.utils;

import br.com.strategiccore.entities.Role;
import br.com.strategiccore.entities.User;
import br.com.strategiccore.repositories.RoleRepository;
import br.com.strategiccore.repositories.UserRepository;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.List;

@Singleton
public class Startup {
    @Transactional
    public void loadUsers(@Observes StartupEvent evt) {
        Role endpointRole = new Role();
        endpointRole.setRole("endpoint");
        endpointRole.setDescription("Endpoint");

        RoleRepository roleRepository = new RoleRepository();
        roleRepository.add(endpointRole, 1L);

        User user = new User();
        user.setLogin("edufolly@gmail.com");
        user.setName("Eduardo Folly");
        user.setPass("654321");
        user.setRoles(List.of(endpointRole));

        UserRepository userRepository = new UserRepository();

        userRepository.add(user, 1L);
    }
}
