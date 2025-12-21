package com.setec.su54_pos_api.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("")
    public String home() {
        return "redirect:/swagger-ui.html";
    }
}
