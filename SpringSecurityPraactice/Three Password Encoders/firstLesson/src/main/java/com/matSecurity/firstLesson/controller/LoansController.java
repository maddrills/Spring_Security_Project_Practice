package com.matSecurity.firstLesson.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoansController {

    @GetMapping("/myLoans")
    public String getAccountDetails(){
        return "Your in myLoans";
    }
}
