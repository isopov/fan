package com.sopovs.moradanen.fan.domain.infra;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "USER_ROLE")
@Getter
@Setter
public class UserRole implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "USER_ROLE_USER_FK"))
    private User user;

    @Enumerated(EnumType.STRING)
    @Id
    private Role role;

    public UserRole() {
    }

    public UserRole(Role role, User user) {
        this.role = role;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        UserRole userRole = (UserRole) o;

        if (role != userRole.role)
            return false;
        if (user != null ? !user.equals(userRole.user) : userRole.user != null)
            return false;

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
