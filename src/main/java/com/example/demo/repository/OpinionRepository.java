package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Opinion;
import com.example.demo.model.User;
import com.example.demo.model.Videogame;

@Repository
public interface OpinionRepository extends JpaRepository <Opinion, Long>{
	List<Opinion> findAll();
	List<Opinion> findByGame(Videogame game);
	List<Opinion> findByUser(User user);
}
