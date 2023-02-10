package it.uniroma3.siw.catering.controller;

import it.uniroma3.siw.catering.model.Buffet;
import it.uniroma3.siw.catering.model.Chef;
import it.uniroma3.siw.catering.model.Piatto;
import it.uniroma3.siw.catering.service.BuffetService;
import it.uniroma3.siw.catering.service.ChefService;
import it.uniroma3.siw.catering.service.IngredienteService;
import it.uniroma3.siw.catering.service.PiattoService;
import it.uniroma3.siw.catering.validator.BuffetValidator;
import it.uniroma3.siw.catering.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BuffetController {

	@Autowired
	private ChefService chefService;
	@Autowired
	private BuffetService buffetService;
	@Autowired
	private BuffetValidator buffetValidator;

	@PostMapping("/admin/addBuffet")
	public String addBuffet(@RequestParam("idBuffet") Long idBuffet,
							@RequestParam("idChef") Long idChef, @RequestParam("nomeBuffet")String nome,
							@RequestParam("descrizioneBuffet")String descrizione, Model model) {
		Buffet b =buffetService.findById(idBuffet);
		b.setNome(nome);
		b.setDescrizione(descrizione);
		buffetService.save(b,chefService.findById(idChef));
		return "redirect:/admin/piattoAddForm/" + idBuffet;

	}
}








