package com.example.testserverapp.service;

import com.example.testserverapp.model.request.EmailRequest;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@Slf4j
@AllArgsConstructor
public class EmailService {

    private JavaMailSender javaMailSender;
    private SpringTemplateEngine templateEngine;

    // Mengirim email plain text
    public EmailRequest sendSimpleMessage(EmailRequest emailRequest) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailRequest.getTo());
        message.setSubject(emailRequest.getSubject());
        message.setText(emailRequest.getBody());
        javaMailSender.send(message);

        return emailRequest;
    }

    // Mengirim email dengan lampiran
    public EmailRequest sendMessageWithAttachment(EmailRequest emailRequest) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(emailRequest.getTo());
            helper.setSubject(emailRequest.getSubject());
            helper.setText(emailRequest.getBody());

            FileSystemResource file = new FileSystemResource(
                new File(emailRequest.getAttachment())
            );

            helper.addAttachment(file.getFilename(), file);

            javaMailSender.send(message);
            log.info("Successfully sent email with attachment...");
        } catch (Exception e) {
            log.error("Failed to send email with attachment. Error: {}", e.getMessage());
        }
        return emailRequest;
    }

    // Mengirim email dengan template HTML menggunakan Thymeleaf
    public EmailRequest sendEmailWithTemplate(EmailRequest emailRequest) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");

            // Set email metadata
            helper.setTo(emailRequest.getTo());
            helper.setSubject(emailRequest.getSubject());

            // Load HTML template dengan data dinamis menggunakan Thymeleaf
            Context context = new Context();
            context.setVariable("recipientName", emailRequest.getRecipientName());
            context.setVariable("title", emailRequest.getTitle());
            context.setVariable("milestone1", emailRequest.getMilestone1());
            context.setVariable("milestone2", emailRequest.getMilestone2());
            context.setVariable("milestone3", emailRequest.getMilestone3());
            context.setVariable("phase1Date", emailRequest.getPhase1Date());
            context.setVariable("phase2Date", emailRequest.getPhase2Date());
            context.setVariable("phase3Date", emailRequest.getPhase3Date());
            context.setVariable("actionItem1", emailRequest.getActionItem1());
            context.setVariable("actionItem2", emailRequest.getActionItem2());
            context.setVariable("actionItem3", emailRequest.getActionItem3());
            context.setVariable("senderName", emailRequest.getSenderName());

            // Proses template dengan data dari EmailRequest
            String htmlContent = templateEngine.process("email-template-long-text", context);

            // Atur konten email ke HTML
            helper.setText(htmlContent, true);

            if (emailRequest.getAttachment() != null && !emailRequest.getAttachment().isEmpty()) {
              FileSystemResource file = new FileSystemResource(new File(emailRequest.getAttachment()));
              helper.addAttachment(file.getFilename(), file);
          }

            // Kirim email
            javaMailSender.send(message);
            log.info("Successfully sent email with HTML template...");
        } catch (Exception e) {
            log.error("Failed to send email with template. Error: {}", e.getMessage());
        }
        return emailRequest;
    }
}
