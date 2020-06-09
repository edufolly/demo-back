package br.com.strategiccore.entities;

import io.quarkus.security.jpa.*;
import org.hibernate.envers.Audited;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Eduardo Folly
 */
@SuppressWarnings("unused")
@UserDefinition
@Entity
@Audited
@Table(name = "sys_user")
public class User extends AbstractEntity {

    @Username
    private String login;

    private String name;

    @Password
    private String pass;

    @ManyToMany
    @Roles
    private List<Role> roles = new ArrayList<>();

    public User() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonbTransient
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
