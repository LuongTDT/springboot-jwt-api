package com.cocarius.security.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LuongTDT
 */
@RestController
@RequestMapping("/api/v1/users")
public class HelloRestController {
    @GetMapping
    public String helloUser(){
        return "hello user";
    }
}
