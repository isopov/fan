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

    public enum Role implements GrantedAuthority {
        EDITOR, USER;

        @Override
        public String getAuthority() {
            return toString();
        }
    }
}
