package it.uniroma3.siw.catering.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import it.uniroma3.siw.catering.model.Buffet;
import it.uniroma3.siw.catering.model.Ingrediente;
import it.uniroma3.siw.catering.model.Piatto;
import it.uniroma3.siw.catering.service.BuffetService;
import it.uniroma3.siw.catering.service.IngredienteService;
import it.uniroma3.siw.catering.service.PiattoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.catering.model.Chef;
import it.uniroma3.siw.catering.service.ChefService;
import it.uniroma3.siw.catering.validator.ChefValidator;

@Controller
public class ChefController {

	@Autowired
	private ChefService chefService;

	@Autowired
	private ChefValidator chefValidator;

	@Autowired
	private BuffetService buffetService;
	@Autowired
	private PiattoService piattoService;

	@GetMapping("/admin/chefSelect")
	public String viewSelectChef(Model model) {
		List<Chef> chefObjs = chefService.findAll();
		model.addAttribute("chefObjs", chefObjs);
		return "admin/chefSelectForm";
	}
	@GetMapping("/chefs")
	public String showChefs(Model model) {
		model.addAttribute("chefs", chefService.findAll());
		return "chefs";
	}

	@GetMapping()
	@PostMapping("/admin/sendChefId")
	public String sendChefId(@RequestParam Long idChef) {
		return "redirect:/admin/buffetAdd/" + idChef;
	}

	@GetMapping("/admin/buffetAdd/{id}")
	public String showBuffetForm(@PathVariable("id") long id, Model model) {
		Chef chef = chefService.findById(id);
		Buffet buffet = new Buffet();
		model.addAttribute("chef", chef);
		model.addAttribute("buffet", buffet);
		return "admin/buffetAddForm";
	}


	@GetMapping("/admin/chefAdd")
	public String chefAddForm(Model model) {
		model.addAttribute("chef", chefService.createChef());
		return "admin/chefAddForm";
	}

	@PostMapping("/admin/chef")
	public String addChef(@Valid @ModelAttribute(value="chef") Chef chef,
						  BindingResult bindingResult, Model model) {

		this.chefValidator.validate(chef, bindingResult);

		if (!bindingResult.hasErrors()) {
			this.chefService.save(chef);
			model.addAttribute("chef", chef);
			return "chef";
		}
		else {
			model.addAttribute("chef", chef);
			return "admin/chefAddForm";
		}
	}




	@GetMapping("/admin/chefRemove")
	public String chefDeleteForm(Model model){
		List<Chef> chefs = chefService.findAll();
		if(chefs.isEmpty())
			return "admin/home";
		model.addAttribute("chefObjs", chefs);
		return "admin/chefRemoveForm";
	}

	@PostMapping("/admin/removeChef")
	public String removeChef(@RequestParam("chefIds")List<Long> chefIds, Model model) {
		for (Long Id : chefIds)
			this.chefService.deleteById(Id);
		this.chefService.reorderChefIds();
		return "admin/home";
	}

}