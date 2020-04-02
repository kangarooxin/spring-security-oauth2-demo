package com.example.demo.controller.group1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kangarooxin
 */
@RestController
@RequestMapping("/api/group1")
public class Group1Controller {

    @RequestMapping
    public String hello() {
        return "hello group1";
    }
}
