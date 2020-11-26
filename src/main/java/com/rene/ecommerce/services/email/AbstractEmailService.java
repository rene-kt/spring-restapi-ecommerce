package com.rene.ecommerce.services.email;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.rene.ecommerce.domain.Product;

public abstract class AbstractEmailService implements EmailService {

	
	@Value("${default.sender}")
	private String sender;
	@Override
	public void sendConfirmationEmail(Product obj) {
		// TODO Auto-generated method stub
		SimpleMailMessage smToClient = prepareSimpleMessageFromProductToClient(obj);
		SimpleMailMessage smToSeller = prepareSimpleMessageFromProductToSeller(obj);
		sendEmail(smToClient);
		sendEmail(smToSeller);
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
		sm.setText(obj.getBuyerOfTheProduct().getName() + "bought your product");
		return sm;
	}
}
