package com.example.testserverapp.controller;

import com.example.testserverapp.model.request.EmailRequest;
import com.example.testserverapp.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    // Mengirim email plain text
    @PostMapping("/simple")
    public EmailRequest sendSimpleMessage(
        @RequestBody EmailRequest emailRequest
    ) {
        return emailService.sendSimpleMessage(emailRequest);
    }

    // Mengirim email dengan lampiran
    @PostMapping("/attach")
    public EmailRequest sendMessageWithAttachment(
        @RequestBody EmailRequest emailRequest
    ) {
        return emailService.sendMessageWithAttachment(emailRequest);
    }

    // Mengirim email dengan template HTML (menggunakan Thymeleaf)
    @PostMapping("/template")
    public EmailRequest sendEmailWithTemplate(
        @RequestBody EmailRequest emailRequest
    ) {
        return emailService.sendEmailWithTemplate(emailRequest);
    }
}
