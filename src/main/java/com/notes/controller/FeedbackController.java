package com.notes.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class FeedbackController {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    String username;



    @PostMapping("/sendFeedback")
    public boolean sendFeedback(@RequestBody Map map){
        log.info(String.valueOf(map));
        String category = String.valueOf(map.get("category"));
        String content = String.valueOf(map.get("content"));
        String sender = String.valueOf(map.get("sender"));
        String email = String.valueOf(map.get("email"));
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);//自己给自己发邮件
        message.setTo(username);
        message.setSubject("错题管理系统————"+category+"【 sender="+sender+"】");
        message.setText(content+"\nemail="+email);
        try{
            mailSender.send(message);
            return true;
        }catch (MailException e){
            e.printStackTrace();
            return false;
        }
    }
}
