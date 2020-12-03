package com.rene.ecommerce.services.email;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.rene.ecommerce.domain.Product;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendConfirmationEmail(Product obj) {
		// TODO Auto-generated method stub
		SimpleMailMessage smToClient = prepareSimpleMessageFromProductToClient(obj);
		SimpleMailMessage smToSeller = prepareSimpleMessageFromProductToSeller(obj);
		sendEmail(smToClient);
		sendEmail(smToSeller);
	}

	@Override
	public void sendConfirmationEmailHtml(Product obj) {
		// TODO Auto-generated method stub
		
		try {
			MimeMessage mmClient = prepareMimeMessageFromProductClient(obj);
			MimeMessage mmSeller= prepareMimeMessageFromProductSeller(obj);

			sendEmailHtml(mmClient);
			sendEmailHtml(mmSeller);

		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			sendConfirmationEmail(obj);
		}
	}

	protected MimeMessage prepareMimeMessageFromProductClient(Product obj) throws MessagingException {
		MimeMessage mm = javaMailSender.createMimeMessage();

		MimeMessageHelper mmh = new MimeMessageHelper(mm, true);
		mmh.setTo(obj.getBuyerOfTheProduct().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Your order has been completed");
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplateProductClient(obj), true);

		return mm;
	}
	
	protected MimeMessage prepareMimeMessageFromProductSeller(Product obj) throws MessagingException {
		MimeMessage mm = javaMailSender.createMimeMessage();

		MimeMessageHelper mmh = new MimeMessageHelper(mm, true);
		mmh.setTo(obj.getProductOwner().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Someone has bought your product");
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplateProductSeller(obj), true);

		return mm;
	}


	protected SimpleMailMessage prepareSimpleMessageFromProductToClient(Product obj) {
		SimpleMailMessage sm = new SimpleMailMessage();

		sm.setTo(obj.getBuyerOfTheProduct().getEmail());
		sm.setFrom(sender);
		sm.setSubject("You order has been completed");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}

	protected SimpleMailMessage prepareSimpleMessageFromProductToSeller(Product obj) {
		SimpleMailMessage sm = new SimpleMailMessage();

		sm.setTo(obj.getProductOwner().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Someone has bought your product");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.getBuyerOfTheProduct().getName() + " bought your product");
		return sm;
	}

	protected String htmlFromTemplateProductClient(Product obj) {
		Context context = new Context();
		context.setVariable("product", obj);
		return templateEngine.process("email/Client", context);

	}

	protected String htmlFromTemplateProductSeller(Product obj) {
		Context context = new Context();
		context.setVariable("product", obj);
		return templateEngine.process("email/Seller", context);

	}
	
	
	@Override
	public void sendNewPassword(String email, String newPassword) {
		// TODO Auto-generated method stub
		SimpleMailMessage sm = prepareNewPasswordEmail(email, newPassword);
		sendEmail(sm);
	}
	
	
	protected SimpleMailMessage prepareNewPasswordEmail(String email, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(email);
		sm.setFrom(sender);
		sm.setSubject("New password");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("New password: " + newPass);
		return sm;
	}

}
