package br.com.strategiccore.entities;

import io.quarkus.security.jpa.RolesValue;
import org.hibernate.envers.Audited;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * @author Eduardo Folly
 */
@SuppressWarnings("unused")
@Entity
@Audited
@Table(name = "sys_role")
public class Role extends AbstractEntity {

    @ManyToMany(mappedBy = "roles")
    @JsonbTransient
    private List<User> users;

    @RolesValue
    private String role;

    private String description;

    public Role() {
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
