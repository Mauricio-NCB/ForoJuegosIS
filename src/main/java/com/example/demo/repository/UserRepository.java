package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import  com.example.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	public Optional<User> findByEmail(String email);
	public Optional<User> findByPassword(String password);
	public Optional<User> findByUsername(String username);
	
	public void deleteByEmail(String email);
}
