package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "opinion")
@Table(name = "opinion")
public class Opinion {
	@Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
	 private Long id;
	
	@Column
	private String comentario;
	
	@Column
	private int nota;
	
	@ManyToOne()
	@JoinColumn(name = "User_ID")
	private User user;
	
	@ManyToOne()
	@JoinColumn(name = "Vid_ID")
	private Videogame game;
	
	public Opinion() {
		
	}
	
	public Opinion(Long _id, String _coment, int _nota, User _user, Videogame _game) {
		id = _id;
		comentario = _coment;
		nota = _nota;
		user = _user;
		game = _game;
	}
	
	public boolean equals(Opinion o) {
		if(!o.getComentario().equals(comentario) || o.getNota() != nota || o.getGame().getId() != game.getId())
			return false;
		return true;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getComentario() {
		return comentario;
	}
	
	public int getNota() {
		return nota;
	}
	
	public User getUser() {
		return user;
	}
	
	public String getUserName() {
		return user.getUsername();
	}
	
	public Videogame getGame() {
		return game;
	}

	public void setId(Long _id) {
		id = _id;
	}
	
	public void setComentario(String _coment) {
		comentario = _coment;
	}
	
	public void setNota(int _nota) {
		nota = _nota;
	}
	
	public void setUser(User _user) {
		user = _user;
	}
	
	public void setGame(Videogame _game) {
		game = _game;
	}


}
