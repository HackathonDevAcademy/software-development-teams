package com.developer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendActivationEmail(String email, String activationLink) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

        helper.setTo(email);
        helper.setSubject("Активация аккаунта");
//        helper.setFrom("test@alanbinu.com");

        String text = "<html>" +
                "<body>" +
                "Благодарим Вас за регистрацию на нашем сайте" +
                "<br>" +
                "Для активации аккаунта нажмите на кнопку:" +
                "<br>" +
                "<h3><a href=\"" + activationLink + "\">" + "Активировать аккаунт" + "</a></h3>" +
                "</body>" +
                "</html>";

        helper.setText(text, true);

        mailSender.send(message);
    }

}

