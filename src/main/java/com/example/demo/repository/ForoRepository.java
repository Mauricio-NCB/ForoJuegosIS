package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Foros;
import com.example.demo.model.User;
import com.example.demo.model.Videogame;

@Repository
public interface ForoRepository extends JpaRepository <Foros, Long>{
	public Optional<Foros> findByTitulo(String email);
	public Optional<Foros> findByDescripcion(String password);
	public List<Foros> findAll();

	//@Query ("delete mi from foros f join comentarios  c on f.id=c.foro_id")
	public void deleteById(Long id);
}
