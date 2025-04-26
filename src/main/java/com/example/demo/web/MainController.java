package com.example.demo.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Busqueda;
import com.example.demo.model.User;
import com.example.demo.model.Videogame;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VideogameRepository;
import com.example.demo.service.UserServiceImpl;
import com.example.demo.service.VideogameService;


@Controller
@ComponentScan("com.example.demo.repository")
@ComponentScan("com.example.demo.service")

public class MainController {
	@Autowired
	private UserServiceImpl userService;
	@Autowired
	UserRepository repository;
	@Autowired
	VideogameRepository game_repository;
	@Autowired
	private VideogameService gameService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/cabecera")
	public String cabecera() {
		return "cabecera";
	}
	
	@GetMapping("/perfil")
	public String perfil() {
		return "perfil";
	}
	
	@GetMapping("/buscarUsuario")
    public String busquedaUsuario(Model model) {
        model.addAttribute("user", new Busqueda());
        return "buscarUsuario";
    }
	
	
	@GetMapping("/chatsPrivados")
	public String chatsPrivados() {
		return "chatsPrivados";
	}
	
	
    @PostMapping("/buscarUsuario")
    public String searchUser( @ModelAttribute("user") Busqueda busqueda, BindingResult result, RedirectAttributes redirectAttrs) {       
        Optional<User> name = userService.busquedaNombre(busqueda.getUsuario());
        Optional<User> email = userService.busquedaEmail(busqueda.getUsuario());


        if(name.isPresent()) {
            redirectAttrs
             .addFlashAttribute("mensaje", "Encontrado usuario con nombre: " + name.get().getUsername())
             .addFlashAttribute("clase", "success");
            
            return "redirect:/chatsPrivados";
        }
        else if(email.isPresent()) {
            redirectAttrs
             .addFlashAttribute("mensaje", "Encontrado usuario con email: " + email.get().getEmail())
             .addFlashAttribute("clase", "success");
            
            return "redirect:/chatsPrivados";
        }
        else {
            redirectAttrs
             .addFlashAttribute("mensaje", "No se ha encontrado ningun usuario registrado con esas caracteristicas")
             .addFlashAttribute("clase", "error");
            
            return "redirect:/buscarUsuario";
        }

    }
    
    
    @GetMapping("/addGameOver")
    public String addGameOver(Model model) {
        model.addAttribute("videogame", new Videogame());
        return "addGameOver";
    }
    
    @PostMapping("/addGameOver")
    public String addGameOver( @ModelAttribute("videogame") Videogame videogame, BindingResult result, RedirectAttributes redirectAttrs) {       
    	List<Videogame> nombre = game_repository.findByNombre(videogame.getNombre());

        if(nombre.size() == 0) {
        	gameService.save(videogame);
        }


        return "redirect:/addGameOver";
    }
    
    
    @GetMapping("/deleteUser")
	public String deleteUser(Model model) {
		try {
			String email;
			Optional<User> userToDelete;
			email = userService.getUserActual();
			userToDelete = userService.busquedaEmail(email);
			
			userService.deleteStudent(userToDelete.get().getEmail());
		
		} catch (Exception e) {
			model.addAttribute("deleteError","The user could not be deleted.");
		}
		
    	return "redirect:/login?logout";
	}
    @RequestMapping("/perfil")
  	public String user(Model model) {
  		try {
  			String email;
  			Optional<User> userToFind;
  			email = userService.getUserActual();
  			userToFind= userService.busquedaEmail(email);
  			
  			model.addAttribute("videogamelist",userToFind.get().getUsername());
  		
  		} catch (Exception e) {
  			model.addAttribute("deleteError","The user could not be deleted.");
  		}
  		
      	return "redirect:/login?logout";
  	}
	
}
