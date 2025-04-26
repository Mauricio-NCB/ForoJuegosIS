package com.example.demo.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Videogame;
@Repository
public interface VideogameRepository extends JpaRepository <Videogame, Long>{
	
	@Query("SELECT v FROM videogame v WHERE "
			+ "CONCAT (v.id, v.nombre, v.tematica, v.compania, v.consola)"
			+ "LIKE %?1%")
	List<Videogame> findAll(String palabraClave);
	
	List<Videogame> findAll();
	
	@Query ("SELECT COUNT(*) FROM videogame")
	long findAllRegisters();

	List<Videogame> findByTematica(String tematica);
	List<Videogame> findByNombre(String nombre);
	List<Videogame> findByCompania(String compania);
	List<Videogame> findByConsola(String consola);
	
}