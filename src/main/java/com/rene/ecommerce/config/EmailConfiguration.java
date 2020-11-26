package com.rene.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rene.ecommerce.services.email.EmailService;
import com.rene.ecommerce.services.email.SmtpEmailService;

@Configuration
public class EmailConfiguration {

	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
}
