/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sopovs.moradanen.fan.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Preconditions;
import com.sopovs.moradanen.fan.domain.infra.User;
import com.sopovs.moradanen.fan.domain.infra.UserRole;
import com.sopovs.moradanen.fan.security.SignInUtils;
import com.sopovs.moradanen.fan.service.IFanUserDetailsService;

@Controller
public class SignupController {

    @Autowired
    private UsersConnectionRepository userConnectionRepository;

    @Autowired
    private IFanUserDetailsService userDetailService;

    public SignupController() {

    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signupForm(WebRequest request) {
        Connection<?> connection = ProviderSignInUtils.getConnection(request);
        if (connection != null) {
            List<String> userIds = userConnectionRepository.findUserIdsWithConnection(connection);
            Preconditions.checkState(userIds.size() < 2);
            if (userIds.size() > 0) {
                // UUID userId = UUID.fromString(userIds.get(0));
                // User user = userDetailService.loadUserById(userId);

                SignInUtils.signin(userIds.get(0));
                // TODO - we are already signed up
                // ProviderSignInUtils.handlePostSignUp(userIds.get(0),
                // request);
                // SecurityContextHolder.getContext().setAuthentication(
                // new UsernamePasswordAuthenticationToken(user, null, null));
                return new ModelAndView("redirect:/");
            }
            UserProfile userProfile = connection.fetchUserProfile();
            String username = userProfile.getUsername();
            if (!userDetailService.checkUsernameNotUsed(username)) {
                username = null;
            }

            String visibleName = userProfile.getFirstName() + userProfile.getLastName();
            if (!userDetailService.checkVisibleNameNotUsed(visibleName)) {
                visibleName = null;
            }
            if (userDetailService.checkUsernameNotUsed(username)) {
                return new ModelAndView("chooseUserName", "signupData", new SignupData(username, visibleName));
            } else {
                return new ModelAndView("chooseUserName");
            }
        } else {
            // TODO Exception?
            return new ModelAndView("redirect:/login");
        }
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Valid SignupData signupData, BindingResult bindingResult, ModelMap modelMap,
            WebRequest request) {
        if (!userDetailService.checkUsernameNotUsed(signupData.getUserName())) {
            bindingResult.addError(new FieldError("registrationData", "userName", signupData.getUserName(),
                    false, null, null, "username already in use"));
        }
        if (!userDetailService.checkVisibleNameNotUsed(signupData.getVisibleName())) {
            bindingResult.addError(new FieldError("registrationData", "visibleName", signupData.getVisibleName(),
                    false, null, null, "visible name already in use"));
        }

        if (bindingResult.hasErrors()) {
            modelMap.put("signupData", signupData);
            return "chooseUserName";
        }
        User newUser = signupData.createUser();
        userDetailService.saveUser(newUser);
        SignInUtils.signin(newUser.getId().toString());
        ProviderSignInUtils.handlePostSignUp(newUser.getId().toString(), request);

        return "redirect:/";

    }

    // internal helpers

    public static class SignupData {
        @NotEmpty
        @Size(max = 30, min = 3)
        @Pattern(regexp = "^[A-Z0-9-]+$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "can only contain alphanumeric characters")
        private String userName;

        @NotEmpty
        @Size(max = 30, min = 3)
        @Pattern(regexp = "^[A-Z0-9- ]+$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "can only contain alphanumeric characters and spaces")
        private String visibleName;

        public User createUser() {

            User user = new User();
            user.setUsername(userName);
            user.setVisibleName(visibleName);
            user.setRoles(Arrays.asList(new UserRole(UserRole.Role.USER, user)));

            return user;
        }

        public SignupData(String userName, String visibleName) {
            this.userName = userName;
            this.visibleName = visibleName;
        }

        public SignupData() {

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

    }

}
