package com.project.smartschool.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.project.smartschool.security.JwtAuthenticationFilter;
import com.project.smartschool.services.impl.UserServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserServiceImpl userService;

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/api/v2/api-docs",
                "/api/configuration/ui",
                "/api/swagger-resources/**",
                "/api/configuration/security",
                "/api/swagger-ui.html",
                "/api/webjars/**",
                "/favicon.ico");
	}

	//
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable()
        .and().cors()
        .and().csrf().disable()
        .authorizeRequests()
        .antMatchers("/api/v2/api-docs",
                "/api/configuration/ui",
                "/api/swagger-resources/**",
                "/api/configuration/security",
                "/api/swagger-ui.html",
                "/api/webjars/**",
                "/favicon.ico").permitAll()
        .antMatchers("/api/auth/*").permitAll()
		.antMatchers("ws://**", "/app/**", "/comment/**").permitAll()
        .antMatchers(HttpMethod.GET, "/api/block").permitAll()
        .antMatchers(HttpMethod.GET, "/api/major").permitAll()
        .antMatchers(HttpMethod.GET, "/api/class").permitAll()
        .antMatchers(HttpMethod.GET, "/api/chapter").permitAll()
        .antMatchers(HttpMethod.GET, "/api/major").permitAll()
        .antMatchers(HttpMethod.GET, "/api/knowledge").permitAll()
        .antMatchers(HttpMethod.GET, "/api/mathdesign").permitAll()
        .antMatchers(HttpMethod.PATCH, "/api/mathdesign/ids").permitAll()
        .antMatchers(HttpMethod.GET, "/api/block/**").permitAll()
        .antMatchers(HttpMethod.GET, "/api/test-type").permitAll()
        
        .anyRequest().fullyAuthenticated();
		//.and().sessionManagement(cust -> cust.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

	}
	//
}
