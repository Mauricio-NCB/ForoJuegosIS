package com.example.demo.web;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.demo.model.Foros;
import com.example.demo.model.MensajeForo;
import com.example.demo.model.User;
import com.example.demo.service.ForoService;
import com.example.demo.service.MensajeForoService;
import com.example.demo.service.UserServiceImpl;

@Controller
public class ForosController {
	@Autowired
	private ForoService foroService;
	@Autowired
	private MensajeForoService mensajeForoService;
	
	@Autowired
	private UserServiceImpl userService;
	
	private Long Foro_id = (long) 0;
	
	
	
	@GetMapping("/addForo")
	public String verPaginaBusqueda(Model model) {
		model.addAttribute("foro", new Foros());
		return "addForo";
		
	}
	
	@PostMapping("/foro")
	public String addForo(@ModelAttribute("foro") Foros foro) {
		foroService.save(foro);
		return "redirect:/foro";
		
	}
	
	@RequestMapping("/foro")
	public String mostrarForos(Model model, @Param("palabraClave") String palabraClave) {
		List<Foros> foros = foroService.buscarForos();
		
		model.addAttribute("foroslist",foros);
		return "/foro";
		
	}
	

	@GetMapping("/addMensajeAForo")
    public String addMensajeAForo(Model model) {
        model.addAttribute("MensajeForo", new MensajeForo());
        return "addMensajeForo";
    }
    
    @PostMapping("/addMensajeAForo")
    public String addMensajeAForo( @ModelAttribute("MensajeForo") MensajeForo mensaje, BindingResult result, RedirectAttributes redirectAttrs, Model model) {       
    	redirectAttrs
        .addFlashAttribute("mensaje", "Mensaje añadido al foro")
        .addFlashAttribute("clase", "success");
    	Long user_id;
    	String email;
		Optional<User> userMensaje;
		email = userService.getUserActual();
		userMensaje= userService.busquedaEmail(email);
		user_id = userMensaje.get().getId();
    	mensaje.setForo_id(Foro_id);
		mensaje.setUser_id(user_id);
		
		Optional<User> escritor;
		escritor = userService.busquedaId(user_id);
		
		mensaje.setUser_name(escritor.get().getUsername());
    	mensajeForoService.save(mensaje);
    	
    	
        return "redirect:/addMensajeForo";
    }
    
    
    
    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    @RequestMapping("/addMensajeForo")
	public String verElForo(Model model, @RequestParam(value = "foros", required = false) Long foro_id) {
		
    	Foro_id = foro_id;
    	
		List<MensajeForo> mensajes = mensajeForoService.buscarMensajes();
    	
		if(!mensajes.isEmpty()) {
			for(int i = 0; i < mensajes.size(); i++) {
	    		if(mensajes.get(i).getForo_id() != foro_id) {
	    			mensajes.remove(i);
	    			i--;
	    		}	
	    	}
			
	    	model.addAttribute("mensajes",mensajes);
		}
		
    	
		
		return "addMensajeForo";
	}
   
    @RequestMapping("/deleteForo")
   	public String deleteForo(Model model, @RequestParam(value = "foros", required = false) Long foro_id, RedirectAttributes redirectAttrs) {
   		
    	redirectAttrs
        .addFlashAttribute("mensaje", "Borrado foro")
        .addFlashAttribute("clase", "success");
    	
    	//mensajeForoService.deleteMensajesForo(foro_id);
    	foroService.deleteForo(foro_id);
    	
        return "redirect:/foro";
   	}
}
