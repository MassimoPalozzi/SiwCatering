package it.uniroma3.siw.catering.controller;

import it.uniroma3.siw.catering.model.Chef;
import it.uniroma3.siw.catering.model.Ingrediente;
import it.uniroma3.siw.catering.model.Piatto;
import it.uniroma3.siw.catering.service.PiattoService;
import it.uniroma3.siw.catering.validator.IngredienteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.catering.service.IngredienteService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class IngredienteController {
	
	@Autowired
	private IngredienteService ingredienteService;

	@Autowired
	private IngredienteValidator ingredienteValidator;
	@Autowired
	private PiattoService piattoService;

	@GetMapping("admin/addIngrediente")
	public String showFormAddIngrediente(Model model){
		model.addAttribute("ingrediente", ingredienteService.createIngrediente());
		return "admin/ingredienteAddForm";
	}
	@PostMapping("/admin/addIngredienti")
	public String addIngredienti(@RequestParam("piatto")Piatto salva, @RequestParam("ingredienteIds")List<Long> ingredienteIds, Model model) {
		for (Long Id : ingredienteIds)
			salva.addIngrediente(ingredienteService.findById(Id));
		return "admin/piattoAddForm";
	}


}

