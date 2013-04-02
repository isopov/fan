package com.sopovs.moradanen.fan.domain.infra;

import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.sopovs.moradanen.fan.domain.AbstractEntity;

@Entity
@Table(name = "USER", uniqueConstraints = { @UniqueConstraint(name = "USER_USERNAME_UK", columnNames = "USERNAME"),
        @UniqueConstraint(name = "USER_EMAIL_UK", columnNames = "EMAIL"),
        @UniqueConstraint(name = "USER_VISIBLE_NAME_UK", columnNames = "VISIBLE_NAME") })
public class User extends AbstractEntity implements UserDetails {
    private static final long serialVersionUID = 1L;
    private String username;
    @Column(name = "VISIBLE_NAME")
    private String visibleName;
    private String email;
    // Spring stores salt concatenated with hashed password
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserRole> roles;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections2.transform(roles, ROLE_FUNCTION);

    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVisibleName() {
        return visibleName;
    }

    public void setVisibleName(String visibleName) {
        this.visibleName = visibleName;
    }

    public static final Function<UserRole, UserRole.Role> ROLE_FUNCTION = new Function<UserRole, UserRole.Role>() {
        @Override
        public UserRole.Role apply(UserRole input) {
            return input.getRole();
        }
    };
}
