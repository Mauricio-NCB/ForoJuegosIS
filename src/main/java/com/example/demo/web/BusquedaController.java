package com.example.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Opinion;
import com.example.demo.model.Videogame;
import com.example.demo.service.OpinionService;
import com.example.demo.service.UserServiceImpl;
import com.example.demo.service.VideogameService;

@Controller
public class BusquedaController {
	@Autowired
	private VideogameService gamesService;
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private OpinionService opService;
	
	
	@RequestMapping("/elegirBusqueda")
	public String verPaginaBusqueda(Model model, @Param("palabraClave") String palabraClave) {

		List<Videogame> videogamelist= gamesService.buscar(palabraClave);
		
		model.addAttribute("videogamelist",videogamelist);
		model.addAttribute("palabraClave", palabraClave);
		return "/elegirBusqueda";
	}
	
	@RequestMapping("/game")
	public String verJuego(Model model, @RequestParam(value = "videogame", required = false) String videogame,
										@RequestParam(value = "coment", required = false) String coment,
										@RequestParam(value = "nota", required = false) Integer nota,
										@RequestParam(value = "fav", required = false,	defaultValue = "no") String fav) {
		Videogame v = null;
		List<Opinion> opinions = null;
		if(videogame != null) {
			v = gamesService.findByName(videogame).get(0);
			opinions = opService.mostrar(v);
		}
		if(coment != null) {
			Opinion o = new Opinion(opService.numCom() + 1, coment, nota, userService.getLogedUser(), v);
			List<Opinion> opUser = opService.findByUser(userService.getLogedUser());
			int i = 0;
			while(i < opUser.size() && !opUser.get(i).equals(o)) {
				++i;
			}
			if(i == opUser.size())
				opService.save(o);
		}
		
		if(fav.equals("yes")) {
			userService.addGame(v);
			fav = "no";
		}
		
		model.addAttribute("opinions", opinions);
		model.addAttribute("vid", v);
		return "/game";
	}
	
	@RequestMapping("/juegoTerminado")
	public String verJuegosTerminados(Model model) {
		List<Videogame> finishedgamelist = userService.getLogedUser().getVideogames();
		model.addAttribute("finishedgamelist",finishedgamelist);
		return "/juegoTerminado";
	}
	
}
