package com.sopovs.moradanen.fan.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sopovs.moradanen.fan.domain.infra.User;

public interface IFanUserDetailsService extends UserDetailsService {
    public void saveUser(User user);

    public User loadUserById(Long id);

    public boolean checkEmailNotUsed(String email);

    public boolean checkUsernameNotUsed(String username);

    public boolean checkVisibleNameNotUsed(String visibleName);
}
