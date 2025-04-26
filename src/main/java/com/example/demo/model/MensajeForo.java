package com.example.demo.model;

import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.example.demo.model.ChatMessage.MessageType;

@Entity(name = "comentarios")
@Table(name="comentarios")
public class MensajeForo {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column
	private String mensaje;
	
	@Column
	private Long foro_id;
	
	@Column
	private Long user_id;
	
	@Column
	private String user_name;
	
	
	 public MensajeForo() {
         super();
     }
	 
	 public MensajeForo(String mensaje) {
         super();
         this.mensaje = mensaje;
     }
	 
	 public MensajeForo(String mensaje, Long id_user, Optional<Foros> f) {
         super();
         this.mensaje = mensaje;
         this.user_id = id_user;
         if(f.isPresent())
        	 this.foro_id = f.get().getId();
     }


	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getForo_id() {
		return foro_id;
	}

	public void setForo_id(Long foro_id) {
		this.foro_id = foro_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	

}
