package com.sopovs.moradanen.fan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorsController extends AbstractController {

    @RequestMapping("/error")
    public String error500() {
        return "errors/500";
    }

    @RequestMapping("404")
    public String error404() {
        return "errors/404";
    }

    @RequestMapping("403")
    public String error403() {
        return "errors/403";
    }
}
