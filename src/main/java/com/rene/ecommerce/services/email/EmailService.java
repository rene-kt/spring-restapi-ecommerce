package com.rene.ecommerce.services.email;

import org.springframework.mail.SimpleMailMessage;

import com.rene.ecommerce.domain.Product;

public interface EmailService {

	void sendConfirmationEmail(Product obj);
	void sendEmail(SimpleMailMessage msg);
}
