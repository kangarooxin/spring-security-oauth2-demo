package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author kangarooxin
 */
@Controller
@RequestMapping("/oauth")
public class Oauth2Controller {

    @GetMapping("confirm_access")
    public String authorizeGet() {
        return "oauth/confirm_access";
    }

    @GetMapping("error")
    public String error() {
        return "oauth/error";
    }

}
