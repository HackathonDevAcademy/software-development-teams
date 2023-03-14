package com.developer;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootApplication
public class SoftwareDevelopmentTeamsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoftwareDevelopmentTeamsApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

//	@Bean
//	public JavaMailSender javaMailSender() {
//		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//		mailSender.setHost("your.smtp.server");
//		mailSender.setPort(587);
//		mailSender.setUsername("your.username");
//		mailSender.setPassword("your.password");
//
//		return mailSender;
//	}

}
