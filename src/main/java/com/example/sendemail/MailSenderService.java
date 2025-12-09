package com.example.sendemail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailSenderService {


    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Value("#{'${mail.to}'.split(',')}")
    private List<String> toList;

    public MailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(toList.toArray(new String[0]));
        message.setSubject("Daily Message");
        message.setText(content);

        mailSender.send(message);
        System.out.println("已发送邮件给：" + toList);
    }

}
