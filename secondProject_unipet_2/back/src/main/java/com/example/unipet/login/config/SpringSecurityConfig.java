package com.example.unipet.login.config;

import com.example.unipet.login.config.auth.MyUserDetailsService;
import com.example.unipet.login.config.jwt.JwtAuthenticationFilter;
import com.example.unipet.login.config.jwt.JwtAuthorizationFilter;
import com.example.unipet.login.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // 시큐리티 활성화 -> 기본 스프링 필터체인에 등록
public class SpringSecurityConfig {
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private CorsConfig corsConfig;

	@Autowired
	private AuthenticationConfiguration authenticationConfiguration;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.addFilter(corsConfig.corsFilter())
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			    .and()
				.formLogin().disable()
				.httpBasic().disable()
				.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilter(jwtAuthorizationFilter())
				.authorizeHttpRequests() // 내가 바꾼 것.
				.requestMatchers("/home", "/join","/login", "/logout").permitAll()
				.requestMatchers("/manager/*").hasAnyAuthority("ROLE_MANAGER","ROLE_ADMIN")
				.requestMatchers("/user/*").hasAuthority("ROLE_USER")
				.anyRequest().permitAll();
		return http.build();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		System.out.println("등록");
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManagerBean());
		return jwtAuthenticationFilter;
	}

	@Bean
	public JwtAuthorizationFilter jwtAuthorizationFilter() throws Exception {
		return new JwtAuthorizationFilter(authenticationManagerBean(), loginRepository);
	}
}






