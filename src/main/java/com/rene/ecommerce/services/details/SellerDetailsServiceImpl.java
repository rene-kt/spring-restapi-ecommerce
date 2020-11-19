package com.rene.ecommerce.services.details;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.rene.ecommerce.domain.users.Seller;
import com.rene.ecommerce.repositories.SellerRepository;
import com.rene.ecommerce.security.SellerSS;

public class SellerDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private SellerRepository repo;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Seller sel = repo.findByEmail(email);
		
		if(sel == null) {
			throw new UsernameNotFoundException(email);
		}
		
		SellerSS selSS = new SellerSS();
		
		selSS.setId(sel.getId());
		selSS.setEmail(sel.getEmail());
		selSS.setPassword(sel.getPassword());
		selSS.setAuthorities(Arrays.asList(sel.getType()));
		
		return selSS;
	}

}
