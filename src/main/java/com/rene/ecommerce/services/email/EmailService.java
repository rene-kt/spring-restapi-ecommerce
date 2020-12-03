package com.rene.ecommerce.services.email;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.rene.ecommerce.domain.Product;

public interface EmailService {

	void sendConfirmationEmail(Product obj);
	void sendEmail(SimpleMailMessage msg);
	
	void sendConfirmationEmailHtml(Product obj);
	void sendEmailHtml(MimeMessage msg);
	
	void sendNewPassword(String email, String newPassword);
}
