package com.example.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "videogame")
@Table(name="videogame")
public class Videogame {
	 @Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
	 private Long id;
	 
	 @Column
	 private String nombre;
	 
	 @Column
     private String tematica;
	 
	 @Column
     private String compania;
	 
	 @Column
     private String consola; 

	public Videogame() {
         super();
     }
     
     public Videogame(String nombre, String compania, String tematica, String consola) {
    	 super();
    	 this.nombre = nombre;
    	 this.compania = compania;
    	 this.tematica = tematica;
    	 this.consola = consola;
     }
     
     public Videogame(Long id, String nombre, String compania, String tematica, String consola) {
    	 super();
    	 this.id = id;
    	 this.nombre = nombre;
    	 this.compania = compania;
    	 this.tematica = tematica;
    	 this.consola = consola;
     }

     public Long getId() {
		return id;
	}

     public void setId(Long id) {
		this.id = id;
	}

     public String getNombre() {
		return nombre;
	}

     public void setNombre(String nombre) {
		this.nombre = nombre;
	}

     public String getTematica() {
		return tematica;
	}

     public void setTematica(String tematica) {
		this.tematica = tematica;
	}

     public String getCompania() {
		return compania;
	}

     public void setCompania(String compania) {
		this.compania = compania;
	}

	public String getConsola() {
		return consola;
	}

	public void setConsola(String consola) {
		this.consola = consola;
	}
     
}