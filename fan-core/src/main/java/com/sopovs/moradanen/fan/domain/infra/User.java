package com.sopovs.moradanen.fan.domain.infra;

import static com.google.common.collect.Collections2.transform;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.common.base.Function;
import com.sopovs.moradanen.fan.domain.AbstractEntity;

@Entity
@Table(name = "USER", uniqueConstraints = { @UniqueConstraint(name = "USER_USERNAME_UK", columnNames = "USERNAME"),
        @UniqueConstraint(name = "USER_EMAIL_UK", columnNames = "EMAIL"),
        @UniqueConstraint(name = "USER_VISIBLE_NAME_UK", columnNames = "VISIBLE_NAME") })
@Getter
@Setter
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserSocialConnection> socialConnections;

    public User() {
    }

    public User(String username) {
        this.username = username;
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
        return transform(roles, ROLE_FUNCTION);

    }

    public static final Function<UserRole, UserRole.Role> ROLE_FUNCTION = new Function<UserRole, UserRole.Role>() {
        @Override
        public UserRole.Role apply(UserRole input) {
            return input.getRole();
        }
    };
}
