package com.example.demo.controller.group2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kangarooxin
 */
@RestController
@RequestMapping("/api/group2")
public class Group2Controller {

    @RequestMapping
    public String hello() {
        return "hello group2";
    }
}
