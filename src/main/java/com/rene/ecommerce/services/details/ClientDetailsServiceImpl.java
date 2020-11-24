package com.rene.ecommerce.services.details;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rene.ecommerce.domain.users.Client;
import com.rene.ecommerce.repositories.ClientRepository;
import com.rene.ecommerce.security.ClientSS;
@Service
public class ClientDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ClientRepository repo;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Client cli = repo.findByEmail(email);
		
		if(cli == null) {
			throw new UsernameNotFoundException(email);
		}
		
		ClientSS cliSS = new ClientSS();
		
		cliSS.setId(cli.getId());
		cliSS.setEmail(cli.getEmail());
		cliSS.setPassword(cli.getPassword());
		cliSS.setAuthorities(Arrays.asList(cli.getType()));
		
		return cliSS;
	}

}
