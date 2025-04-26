package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Foros;
import com.example.demo.model.MensajeForo;
import com.example.demo.model.Videogame;
import com.example.demo.repository.MensajeForoRepository;
import com.example.demo.repository.VideogameRepository;

@Service
public class MensajeForoService {
	
	@Autowired
	private MensajeForoRepository repository;
	
	
	public MensajeForo save(MensajeForo mensaje) {
		return repository.save(mensaje);
	}
	

	public List<MensajeForo> buscarMensajes(){
		return repository.findAll();
	}
	 @Transactional
	 public void deleteMensaje(Long id) {
		 repository.deleteById(id);
		}
	
	
}
