package com.rene.ecommerce.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.rene.ecommerce.security.JWTUtil;
import com.rene.ecommerce.security.filters.JWTAuthenticationFilter;
import com.rene.ecommerce.services.details.ClientDetailsServiceImpl;
import com.rene.ecommerce.services.details.SellerDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	private ClientDetailsServiceImpl clientDetails;
	
	@Autowired
	private JWTUtil jwtUtil;
	

	 @Autowired
	 private SellerDetailsServiceImpl sellerDetails;
	
	private static final String[] PUBLIC_MATCHER = {

			"/ecommerce/create/**", "/ecommerce/products", "/ecommerce/product/**",
			
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();
		http.authorizeRequests().antMatchers(PUBLIC_MATCHER).permitAll().anyRequest().authenticated();
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	

	
	 @Override
	    public void configure(AuthenticationManagerBuilder auth) throws Exception {
	     
		 auth.userDetailsService(clientDetails).passwordEncoder(bCryptPasswordEncoder());
		 auth.userDetailsService(sellerDetails).passwordEncoder(bCryptPasswordEncoder());

	    }
	 

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}