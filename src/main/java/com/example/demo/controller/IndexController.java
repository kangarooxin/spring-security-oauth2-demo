package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kangarooxin
 */
@RestController
public class IndexController {

    @RequestMapping
    public String index() {
        return "ok";
    }
}
