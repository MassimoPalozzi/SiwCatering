package it.uniroma3.siw.catering.controller;

import it.uniroma3.siw.catering.model.Buffet;
import it.uniroma3.siw.catering.model.Chef;
import it.uniroma3.siw.catering.model.Ingrediente;
import it.uniroma3.siw.catering.model.Piatto;
import it.uniroma3.siw.catering.service.BuffetService;
import it.uniroma3.siw.catering.service.PiattoService;
import it.uniroma3.siw.catering.validator.IngredienteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.catering.service.IngredienteService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Controller
public class IngredienteController {
	
	@Autowired
	private IngredienteService ingredienteService;

	@Autowired
	private IngredienteValidator ingredienteValidator;
	@Autowired
	private PiattoService piattoService;

	@Autowired
	private BuffetService buffetService;

	@GetMapping("admin/addIngrediente")
	public String showFormAddIngrediente(Model model){
		model.addAttribute("ingrediente", ingredienteService.createIngrediente());
		return "admin/ingredienteAddForm";
	}

	@PostMapping("admin/ingrediente")
	public String aggiungiIngrediente(@ModelAttribute("ingrediente") @Valid Ingrediente ingrediente,
									  BindingResult binding, Model model) {
		ingredienteValidator.validate(ingrediente, binding);
		if (binding.hasErrors()) {
			return "admin/ingredienteAddForm";
		}
		ingredienteService.save(ingrediente);
		model.addAttribute("ingrediente",ingrediente);
		return "admin/home";
	}

	@GetMapping("admin/ingredienteRemove")
	public String viewIngredienteRemoveForm(Model model){
		model.addAttribute("ingredientiObjs", ingredienteService.findAll());
		return "admin/ingredienteDeleteForm";
	}

	@PostMapping("/admin/removeIngrediente")
	public String removeIngrediente(@RequestParam("ingredientiIds")List<Long> ingredientiIds, Model model) {
		Set<Piatto> piatti = new LinkedHashSet<Piatto>();
		Set<Buffet> buffets = new LinkedHashSet<>();

		for (Long Id : ingredientiIds)
			piatti.addAll(piattoService.findAllByIngrediente(Id));

		for (Piatto piatto : piatti) {
			buffetService.findAllByPiatto(piatto.getId())
					.forEach((Buffet buffet) -> {
						buffetService.delete(buffet);
					});
		}
		for (Piatto piatto : piatti){
			piattoService.delete(piatto);
		}

		for (Long Id : ingredientiIds)
			ingredienteService.deleteById(Id);

		//this.ingredienteService.reorderChefIds();
		return "admin/home";
	}

	@GetMapping("/admin/selectIngrediente")
	public String viewSelectIngredienteEdit(Model model){
		model.addAttribute("ingredientiObjs", ingredienteService.findAll());
		return "admin/ingredienteSelect";
	}

	@PostMapping("/admin/sendIngredienteId")
	public String sendIngredienteId(@RequestParam Long idIngrediente) {
		return "redirect:/admin/ingredienteEdit/" + idIngrediente;
	}


	@GetMapping("/admin/ingredienteEdit/{id}")
	public String viewIngredienteEditFormId(@PathVariable("id")Long id, Model model){
		model.addAttribute("ingrediente", ingredienteService.findById(id));
		model.addAttribute("id", id);
		return "admin/ingredienteEditForm";
	}

	@PostMapping("admin/editIngrediente")
	public String editIngrediente(@Valid @ModelAttribute("ingrediente") Ingrediente ingrediente, Model model, @RequestParam("nome") String nome,
								      @RequestParam("origine") String origine, @RequestParam("descrizione")String descrizione, @RequestParam("id")Long id) {
		ingredienteService.edit(id, nome, origine, descrizione);
		model.addAttribute("ingrediente",ingrediente);
		return "admin/home";
	}

	@GetMapping("/ingredienti/{id}")
	public String viewPiatti(@PathVariable("id")Long id, Model model){
		Set<Ingrediente> ingredienti = new LinkedHashSet<>();
		ingredienti.addAll(piattoService.findAllIngredientiIn(id));
		model.addAttribute("ingredientiObjs",ingredienti);
		return "ingredienti";
	}
}

