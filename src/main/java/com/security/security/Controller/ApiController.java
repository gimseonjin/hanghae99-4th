package com.security.security.Controller;

import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/greeting")
    public String greeting(){
        return "Hello!";
    }

    @PostMapping("/greeting")
    public String postGreeting(@RequestBody String name){
        return "Hello " + name;
    }
}
