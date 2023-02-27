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
	@GetMapping("/admin/chefSelectDelete")
	public String viewSelectChefDelete(Model model) {
		List<Chef> chefObjs = chefService.findAll();
		model.addAttribute("chefObjs", chefObjs);
		return "admin/chefSelectFormDelete";
	}

	@GetMapping("/chefs")
	public String showChefs(Model model) {
		model.addAttribute("chefs", chefService.findAll());
		return "chefs";
	}

	@PostMapping("/admin/sendChefId")
	public String sendChefId(@RequestParam Long idChef) {
		return "redirect:/admin/buffetAdd/" + idChef;
	}

	@PostMapping("/admin/sendChefIdDelete")
	public String sendChefIdDelete(@RequestParam Long idChef) {
		return "redirect:/admin/buffetRemove/" + idChef;
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
			return "admin/home";
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
		return "admin/home";
	}

	@GetMapping("/buffets")
	public String viewChefSelectView(Model model){
		model.addAttribute("chefObjs", chefService.findAll());
		return "chefSelectView";
	}

	@PostMapping("/sendChefId")
	public String sendChefIdView(@RequestParam("idChef")Long id, Model model){
		return "redirect:/buffets/"+id;
	}


}