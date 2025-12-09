package com.example.sendemail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {


    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${mail.to}")
    private String to;

    public MailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("Daily Message");
        message.setText(content);

        mailSender.send(message);
        System.out.println("已发送邮件：" + content);
    }

}
