package it.uniroma3.siw.catering.controller;

import it.uniroma3.siw.catering.model.Buffet;
import it.uniroma3.siw.catering.model.Chef;
import it.uniroma3.siw.catering.service.BuffetService;
import it.uniroma3.siw.catering.service.ChefService;
import it.uniroma3.siw.catering.service.IngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.catering.service.PiattoService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PiattoController {
	
	@Autowired
	private PiattoService piattoService;

	@Autowired
	private IngredienteService ingredienteService;

	@Autowired
	private ChefService chefService;

	@Autowired
	private BuffetService buffetService;

	@GetMapping("/admin/piattoAddForm/{id}")
	public String showPiattoForm ( @PathVariable("id") long id, Model model){
		Buffet buffet = buffetService.findById(id);
		model.addAttribute("piattoObjs", piattoService.findAll());
		model.addAttribute("buffet", buffet);
		return "admin/piattoAddForm";
	}


}


