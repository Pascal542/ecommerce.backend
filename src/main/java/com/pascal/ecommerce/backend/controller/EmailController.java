package com.pascal.ecommerce.backend.controller;

import com.pascal.ecommerce.backend.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/emails")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send")
    public String sendEmail() {
        emailService.sendEmail("pascalthedog542@gmail.com",
                "Test Subject",
                "Test Body");
        return "Email Sent";
    }
}
