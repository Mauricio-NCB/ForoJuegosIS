package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Foros;
import com.example.demo.model.MensajeForo;

@Repository
public interface MensajeForoRepository extends JpaRepository <MensajeForo, Long>{
	/*@Query ("SELECT * FROM comentarios WHERE foro_id = iddd")
	public List<MensajeForo> findByForo_id(Long iddd);*/
	//public Optional<MensajeForo> findByForo_id(Long id);
	public List<MensajeForo> findAll();
	public void deleteById(Long id);

}
