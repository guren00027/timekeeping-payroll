package com.glenn.timekeeping_payroll.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/app")
    public String app() {
        return "app";
    }

    @GetMapping("/hr")
    public String hrPage() {
        return "hr";
    }
}

