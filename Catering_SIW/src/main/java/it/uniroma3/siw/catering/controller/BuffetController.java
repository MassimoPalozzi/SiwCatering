package it.uniroma3.siw.catering.controller;

import it.uniroma3.siw.catering.model.Buffet;
import it.uniroma3.siw.catering.model.Chef;
import it.uniroma3.siw.catering.model.Ingrediente;
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Controller
public class BuffetController {

	@Autowired
	private ChefService chefService;
	@Autowired
	private BuffetService buffetService;
	@Autowired
	private BuffetValidator buffetValidator;
	@Autowired
	private PiattoService piattoService;


	@PostMapping("/admin/addBuffet")
	public String addBuffet(@Valid @ModelAttribute("buffet") Buffet buffet, BindingResult bindingResult,
							Model model, @RequestParam("piattiIds") List<Long> piattiIds, @RequestParam("chefId") Long chefId) {

		Chef chef = chefService.findById(chefId);
		buffetValidator.validate(buffet, bindingResult);
		if (bindingResult.hasErrors()) {
			model.addAttribute("chefObjs", chefService.findAll());
			return "admin/chefSelectForm";
		}
		buffetService.save(buffet, chef);
		for (Long id : piattiIds)
			buffetService.addPiatto(buffet, piattoService.findById(id));
		model.addAttribute("buffet", buffet);
		buffetService.save(buffet, chef);
		return "admin/home";
	}


	@GetMapping("/admin/buffetAdd/{id}")
	public String showAddBuffetForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("chef", chefService.findById(id));
		model.addAttribute("buffet", buffetService.createBuffet());
		model.addAttribute("piatti", piattoService.findAll());
		return "admin/buffetAddForm";
	}

	@GetMapping("/admin/buffetRemove/{id}")
	public String showRemoveBuffetForm(@PathVariable("id") long id, Model model) {
		Chef chef = chefService.findById(id);
		Set<Buffet> buffets = new LinkedHashSet<>();
		buffets.addAll(chefService.findAllBuffetByChef(chef));

		model.addAttribute("buffetObjs", buffets);
		return "admin/buffetRemoveForm";
	}

	@PostMapping("/admin/removeBuffet")
	public String removeBuffet(@RequestParam("buffetIds") List<Long> ids, Model model) {
		Set<Buffet> buffets = new LinkedHashSet<>();
		for (Long id : ids)
			buffets.add(buffetService.findById(id));
		buffets.forEach((Buffet buffet) -> {
			buffetService.delete(buffet);
		});
		return "admin/home";
	}

	@GetMapping("/buffets/{id}")
	public String viewBuffet(@PathVariable("id")Long id, Model model){
		Set<Buffet> buffets = new LinkedHashSet<>();
		buffets.addAll(chefService.findAllBuffetByChef(chefService.findById(id)));
		model.addAttribute("buffetObjs",buffets);
		return "buffets";
	}

	@PostMapping("/sendBuffetId")
	public String sendBuffetIdUser(@RequestParam("idBuffet")Long id, Model model){
		return "redirect:/piatti/"+id;
	}
}








