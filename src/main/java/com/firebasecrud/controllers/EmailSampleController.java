package com.firebasecrud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EmailSampleController {
	
	@Autowired 
	private JavaMailSender mailSender;

    @GetMapping("/email-send")
    public String sendMail() {
        SimpleMailMessage message = new SimpleMailMessage();    
        message.setText("Email do Spring Boot");
        message.setSubject("Teste de email Spring Boot");
        message.setTo("lemes_vilarinho@yahoo.com.br");
//        message.setFrom("wolmirgarbin@gmail.com");

        try {
            mailSender.send(message);
            return "Email enviado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao enviar email.";
        }
    }
}
