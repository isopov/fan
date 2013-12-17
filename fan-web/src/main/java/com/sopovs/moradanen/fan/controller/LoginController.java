package com.sopovs.moradanen.fan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController extends AbstractController {

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginForm() {
        return "loginForm";
    }

    @RequestMapping("register")
    public String registerForm() {
        return "registerForm";
    }

    @RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
    public String loginerror(ModelMap model) {
        model.addAttribute("error", "true");
        return "loginForm";
    }

}
