package com.example.unipet.login.config.auth;

import com.example.unipet.login.entity.LoginUser;
import com.example.unipet.login.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService{

	private final LoginRepository loginRepository;
	
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		System.out.println("MyUserDetailsService : 진입");
		LoginUser user = loginRepository.findByUserId(name);
		System.out.println(user.toString());

		return new MyUserDetails(user);
	}
}
