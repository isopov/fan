package com.sopovs.moradanen.fan.domain.infra;


import org.hibernate.annotations.ForeignKey;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "USER_ROLE")
public class UserRole implements Serializable {

    @Id
    @ManyToOne
    @ForeignKey(name = "USER_ROLE_USER_FK")
    private User user;

    @Enumerated
    @Id
    private Role role;

    public UserRole() {
    }

    public UserRole(Role role, User user) {
        this.role = role;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole userRole = (UserRole) o;

        if (role != userRole.role) return false;
        if (user != null ? !user.equals(userRole.user) : userRole.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    public enum Role implements GrantedAuthority {
        EDITOR, USER;

        @Override
        public String getAuthority() {
            return toString();
        }
    }
}
