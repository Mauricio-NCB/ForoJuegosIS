package com.example.demo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Opinion;
import com.example.demo.model.User;
import com.example.demo.model.Videogame;
import com.example.demo.repository.OpinionRepository;

@Service
public class OpinionService {
	@Autowired
	private OpinionRepository or;
	
	public Long numCom(){
		return (long) or.findAll().size();
	}
	
	public List<Opinion> mostrar(Videogame game){
		return or.findByGame(game);
	}
	
	public Opinion save(Opinion op) {
		return or.save(op);
	}
	
	public List<Opinion> findByUser(User user){
		return or.findByUser(user);
	}
}
