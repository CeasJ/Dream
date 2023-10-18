package com.backend.dream.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private MailSender mailSender;

    @Autowired
    TokenService tokenService;

    public void sendWelcomeEmail(String to, String fullname) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject("Welcome to Dream Coffee and Tea");
        msg.setText("Welcome " + fullname + " to TimeZone");
        mailSender.send(msg);
    }

    public void sendEmailTokenPass(String to, String token, String fullname) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject("Reset Password");
        msg.setText("Hi " + fullname + ",\n\n"
                + "We have received a request to reset the password for your account. \n\n"
                + "Your authentication code is: " + token + "\n\n"
                + "If you did not request a password reset, please ignore this email.\n\n"
                + "Regards,\n"
                + "TimeZone");
        mailSender.send(msg);

    }

}
