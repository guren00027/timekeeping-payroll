package com.glenn.timekeeping_payroll.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/hr/test")
    public String hrTest() {
        return "HR access OK";
    }

    @GetMapping("/employee/test")
    public String employeeTest() {
        return "Employee access OK";
    }
}

