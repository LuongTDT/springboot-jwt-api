package com.cocarius.security.controllers;

import org.springframework.web.bind.annotation.*;

/**
 * @author LuongTDT
 */
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    @GetMapping
    public String getInfo(){
        return "ADMIN:READ";
    }
    @PutMapping
    public String updateInfo(){
        return "ADMIN:UPDATE";
    }
    @DeleteMapping
    public String deleteInfo(){
        return "ADMIN:DELETE";
    }
    @PostMapping
    public String addInfo(){
        return "ADMIN:WRITE";
    }
}
