package com.setec.su54_pos_api.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {
    @GetMapping("")
    public String home() {
        return "redirect:/swagger-ui/index.html";
    }

    @GetMapping("/{id}")
    public int findById(@PathVariable int id) {
        return id;
    }

    @DeleteMapping("/delete/{id}")
    public int deleteById(@PathVariable int id) {
        return id;
    }

    @PutMapping("/update/{id}")
    public int updateById(@PathVariable int id) {
        return id;
    }

    @PostMapping("/create")
    public String createItem(@RequestParam("name") String name) {
        return name;
    }
}
