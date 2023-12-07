package com.example.tiary.users.service;import org.springframework.security.core.userdetails.UserDetailsService;import org.springframework.security.core.userdetails.UsernameNotFoundException;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import com.example.tiary.users.dto.RequestUserDto;import com.example.tiary.users.entity.Users;import com.example.tiary.users.repository.UsersRepository;@Servicepublic class UserService implements UserDetailsService {	private final UsersRepository usersRepository;	public UserService(UsersRepository usersRepository) {		this.usersRepository = usersRepository;	}	@Override	public Users loadUserByUsername(String email) throws UsernameNotFoundException {		return usersRepository.findByEmail(email)			.orElseThrow(() -> new UsernameNotFoundException("회원 정보가 없습니다."));	}	// 회원 가입	@Transactional	public void createUser(RequestUserDto requestUserDto) {		try {			usersRepository.save(requestUserDto.toSignUpEntity());		} catch (Exception e) {			e.printStackTrace();			throw new RuntimeException("회원 가입에 실패했습니다.");		}	}	// 이메일 존재 여부 체크	public boolean existsEmail(String email) {		return usersRepository.existsByEmail(email);	}	// 닉네임 존재 여부 체크	public boolean existsNickname(String nickname) {		return usersRepository.existsByNickname(nickname);	}	//유저 체크}