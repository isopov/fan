package com.sopovs.moradanen.fan.domain.infra;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.sopovs.moradanen.fan.domain.AbstractEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "USER", uniqueConstraints = @UniqueConstraint(columnNames = "USERNAME"))
public class User extends AbstractEntity implements UserDetails {


    private String username;
    private String email;
    //Spring stores salt concatenated with hashed password
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

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static final Function<UserRole,UserRole.Role> ROLE_FUNCTION = new Function<UserRole, UserRole.Role>() {
        @Override
        public UserRole.Role apply(UserRole input) {
            return input.getRole();
        }
    };
}
