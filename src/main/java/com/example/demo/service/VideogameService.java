package com.example.demo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Videogame;
import com.example.demo.repository.VideogameRepository;

@Service
public class VideogameService {
	
	@Autowired
	private VideogameRepository repository;
	
	public List<Videogame> findAll(){
		return repository.findAll();
	}
	
	public List<Videogame> buscar(String palabraClave){
		if(palabraClave != null) {
			return repository.findAll(palabraClave);
		}
		return null;
	}
	
	public List<Videogame> findByName(String nombre){
		return repository.findByNombre(nombre);
	}
	
	public Videogame save(Videogame newGame) {
		long id = repository.findAllRegisters() + 1;

		Videogame videogame = new Videogame(id, newGame.getNombre(), newGame.getCompania(), newGame.getTematica(), newGame.getConsola());
		
		return repository.save(videogame);
	}

}