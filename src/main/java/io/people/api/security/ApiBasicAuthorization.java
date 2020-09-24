package io.people.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ApiBasicAuthorization extends WebSecurityConfigurerAdapter{

	//Overwrite the Authentication_config method and set a new user / password / role
	@Override 
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("admin1234")).roles("ADMIN");
	}
	
	//Overwrite the HttpSecurity_config method and let access ADMIN to the user logged
	@Override 
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable().authorizeRequests().antMatchers("/***").hasRole("ADMIN").
		anyRequest().authenticated().and().httpBasic();
	}
	
	// Enable Encrypt to the password
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
