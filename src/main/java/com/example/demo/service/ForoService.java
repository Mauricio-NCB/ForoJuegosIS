package com.example.demo.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Foros;
import com.example.demo.model.User;
import com.example.demo.repository.ForoRepository;

@Service
public class ForoService {
	@Autowired
	private ForoRepository repository;
	
	public Foros save(Foros foro) {
		return repository.save(foro);
	}
	
	public List<Foros> buscarForos(){
		return repository.findAll();
	}
	
	public Optional<Foros> buscarPorTitulo(String titulo){
        return repository.findByTitulo(titulo);
	}
	
	public Optional<Foros> buscarPorId(Long id){
        return repository.findById(id);
	}
	
	 @Transactional
	 public void deleteForo(Long id) {
		 repository.deleteById(id);
		}
}
