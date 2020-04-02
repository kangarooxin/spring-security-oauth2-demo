package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author kangarooxin
 */
@Controller
public class LoginController {

    @GetMapping("login")
    public String login() {
        return "login";
    }

}
