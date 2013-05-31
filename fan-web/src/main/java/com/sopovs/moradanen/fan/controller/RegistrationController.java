package com.sopovs.moradanen.fan.controller;

import java.util.Arrays;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Preconditions;
import com.sopovs.moradanen.fan.domain.infra.User;
import com.sopovs.moradanen.fan.domain.infra.UserRole;
import com.sopovs.moradanen.fan.service.IFanUserDetailsService;

@Controller
@RequestMapping("registration")
public class RegistrationController extends AbstractController {
    @Autowired
    private IFanUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ModelAttribute("registrationData")
    public RegistrationData getEmployeeObject() {
        return new RegistrationData();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String registrationForm() {
        return "registrationForm";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String registration(@Valid @ModelAttribute("registrationData") RegistrationData registrationData,
            BindingResult bindingResult, ModelMap modelMap) {

        if (!userDetailsService.checkUsernameNotUsed(registrationData.getUserName())) {
            bindingResult.addError(new FieldError("registrationData", "userName", registrationData.getUserName(),
                    false, null, null, "username already in use"));
        }
        if (!userDetailsService.checkEmailNotUsed(registrationData.getEmail())) {
            bindingResult.addError(new FieldError("registrationData", "email", registrationData.getEmail(), false,
                    null, null, "email already in use"));
        }
        if (!userDetailsService.checkVisibleNameNotUsed(registrationData.getVisibleName())) {
            bindingResult.addError(new FieldError("registrationData", "visibleName", registrationData.getVisibleName(),
                    false, null, null, "visible name already in use"));
        }
        if (bindingResult.hasErrors()) {
            modelMap.put("registrationData", registrationData);
            return "registrationForm";
        }
        userDetailsService.saveUser(registrationData.createUser(passwordEncoder));

        return "registrationSuccessful";
    }

    public static class RegistrationData {
        @NotEmpty
        @Size(max = 30, min = 3)
        @Pattern(regexp = "^[A-Z0-9-]+$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "can only contain alphanumeric characters")
        private String userName;

        @NotEmpty
        @Size(max = 30, min = 3)
        @Pattern(regexp = "^[A-Z0-9- ]+$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "can only contain alphanumeric characters and spaces")
        private String visibleName;

        @NotEmpty
        @Size(min = 3)
        private String password;
        @NotEmpty
        @Size(min = 3)
        private String password2;

        @NotEmpty
        // TODO email server validation
        // @Pattern(regexp = "", flags = Pattern.Flag.CASE_INSENSITIVE, message
        // = "must be a valid email")
        private String email;

        public User createUser(PasswordEncoder passwordEncoder) {
            Preconditions.checkState(Objects.equals(password, password2));

            User user = new User();
            user.setPassword(passwordEncoder.encode(password));
            user.setUsername(userName);
            user.setVisibleName(visibleName);
            user.setEmail(email);

            user.setRoles(Arrays.asList(new UserRole(UserRole.Role.USER, user)));

            return user;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getVisibleName() {
            return visibleName;
        }

        public void setVisibleName(String visibleName) {
            this.visibleName = visibleName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPassword2() {
            return password2;
        }

        public void setPassword2(String password2) {
            this.password2 = password2;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

}
