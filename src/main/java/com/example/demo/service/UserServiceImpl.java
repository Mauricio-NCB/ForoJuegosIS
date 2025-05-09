package com.example.demo.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.MensajeForo;
import  com.example.demo.model.Role;
import  com.example.demo.model.User;
import com.example.demo.model.Videogame;
import  com.example.demo.repository.UserRepository;
import  com.example.demo.web.dto.UserRegistrationDto;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public Optional<User> busquedaNombre(String username){
        return userRepository.findByUsername(username);
	}
	public Optional<User> busquedaEmail(String email){
        return userRepository.findByEmail(email);
	}
	
	public Optional<User> busquedaId(Long id){
        return userRepository.findById(id);
	}
 
	public List<User> buscarUsuarios(){
		return userRepository.findAll();
	}
	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public User save(UserRegistrationDto registrationDto) {
		User user = new User(registrationDto.getUsername(),  registrationDto.getEmail(),
				passwordEncoder.encode(registrationDto.getPassword()), Arrays.asList(new Role("ROLE_USER")));
		
		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		Optional<User> user = userRepository.findByUsername(username);
		if(!user.isPresent()) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), mapRolesToAuthorities(user.get().getRoles()));		
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
	
	
	 public boolean checkUsernameExists(String username){
		 Optional<User> userFound = userRepository.findByUsername(username);
		 if(userFound.isPresent()) {
			 System.out.print("Ese nombre de usuario ya esta registrado, elige otro\n");
			 return true;
		 }
		 return false;
	 }
	 
	 public boolean checkEmailExists(String email){
		 Optional<User> userFound = userRepository.findByEmail(email);
		 if(userFound.isPresent()) {
			 System.out.print("Ese email ya esta registrado, elige otro\n");
			 return true;
		 }
		 return false;
	 }
	 
	 
	 public String getUserActual() {
		  
		    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		    UserDetails userDetails = null;
		    if (principal instanceof UserDetails) {
		      userDetails = (UserDetails) principal;
		    }
		    String userName = userDetails.getUsername();
		    return userName;
		}
	 
	 public User getLogedUser() {
		 return userRepository.findByEmail(getUserActual()).get();
	 }
	 
	 public User addGame(Videogame game) {
		 User user = getLogedUser();
		 user.getVideogames().add(game);
		 return userRepository.save(user);
	 }

	 @Transactional
	 public void deleteStudent(String email ) {
		 userRepository.deleteByEmail(email);
		}
	 
	 
	
}
