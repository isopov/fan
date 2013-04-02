package com.sopovs.moradanen.fan.service;

import com.sopovs.moradanen.fan.domain.infra.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IFanUserDetailsService extends UserDetailsService {
    public void saveUser(User user);

    public boolean checkEmailNotUsed(String email);
    public boolean checkUsernameNotUsed(String username);
    public boolean checkVisibleNameNotUsed(String visibleName);
}
