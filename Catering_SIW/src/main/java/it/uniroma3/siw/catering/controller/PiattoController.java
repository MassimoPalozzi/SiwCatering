package it.uniroma3.siw.catering.controller;

import it.uniroma3.siw.catering.model.Buffet;
import it.uniroma3.siw.catering.model.Chef;
import it.uniroma3.siw.catering.model.Ingrediente;
import it.uniroma3.siw.catering.model.Piatto;
import it.uniroma3.siw.catering.service.BuffetService;
import it.uniroma3.siw.catering.service.ChefService;
import it.uniroma3.siw.catering.service.IngredienteService;
import it.uniroma3.siw.catering.validator.PiattoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.catering.service.PiattoService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Controller
public class PiattoController {
	
	@Autowired
	private PiattoService piattoService;

	@Autowired
	private PiattoValidator piattoValidator;

	@Autowired
	private IngredienteService ingredienteService;

	@Autowired
	private ChefService chefService;

	@Autowired
	private BuffetService buffetService;

	@GetMapping("/admin/piattoAddForm")
	public String showPiattoForm (Model model){
		model.addAttribute("ingredienti", ingredienteService.findAll());
		model.addAttribute("piatto", piattoService.createPiatto());
		return "admin/piattoAddForm";
	}

	@PostMapping("/admin/piatto")
	public String addPiatto(@Valid @ModelAttribute("piatto") Piatto piatto, BindingResult bindingResult,
							Model model, @RequestParam("ingredientiIds")List<Long> ingredientiIds) {

		piattoValidator.validate(piatto, bindingResult);
		if (bindingResult.hasErrors()) {
			model.addAttribute("ingredienti", this.ingredienteService.findAll());
			return "admin/piattoAddForm";
		}
		piattoService.save(piatto);
		for(Long id: ingredientiIds)
			piattoService.addIngrediente(piatto, ingredienteService.findById(id));
		model.addAttribute("piatto", piatto);
		this.piattoService.save(piatto);
		return "admin/home";
	}

	@GetMapping("/admin/piattoRemoveForm")
	public String viewPiattoRemoveForm(Model model){
		model.addAttribute("piattiObjs", piattoService.findAll());
		return "admin/piattoRemoveForm";
	}

	@PostMapping("/admin/removePiatto")
	public String removePiatto(@RequestParam("piattiIds")List<Long> piattiIds, Model model) {
		Set<Piatto> piatti = new LinkedHashSet<>();
		Set<Buffet> buffets = new LinkedHashSet<>();

		piattiIds.forEach((Long id) -> {
			piatti.add(piattoService.findById(id));
			buffets.addAll(buffetService.findAllByPiatto(id));
		});
		for (Buffet buffet : buffets)
			buffetService.delete(buffet);
		for (Piatto piatto : piatti)
			piattoService.delete(piatto);
		return "admin/home";
	}

	@GetMapping("/admin/selectPiatto")
	public String viewSelectIngredienteEdit(Model model){
		model.addAttribute("piattiObjs", piattoService.findAll());
		return "admin/piattoSelect";
	}

	@PostMapping("/admin/sendPiattoId")
	public String sendPiattoId(@RequestParam Long idPiatto,@RequestParam("invio")String scelta) {
		if(scelta.equals("editTesto"))
			return "redirect:/admin/piattoEdit/" + idPiatto;
		else if(scelta.equals("add"))
			return "redirect:/admin/piattoEditAdd/" + idPiatto;
		else if(scelta.equals("remove"))
			return "redirect:/admin/piattoEditRemove/" + idPiatto;
		else
			return "admin/home";
	}

	@GetMapping("/admin/piattoEdit/{id}")
	public String viewPiattoEditFormId(@PathVariable("id")Long id, Model model){
		model.addAttribute("piatto", piattoService.findById(id));
		model.addAttribute("id", id);
		return "admin/piattoEditForm";
	}

	@PostMapping("/admin/editPiatto")
	public String viewPiattoEditFormId(@RequestParam("id")Long id, @RequestParam("nome") String nome,
									   @RequestParam("descrizione") String descrizione, Model model){
		piattoService.editTesto(id, nome, descrizione);
		return "admin/home";
	}


	@GetMapping("/admin/piattoEditAdd/{id}")
	public String viewPiattoEditAddFormId(@PathVariable("id")Long id, Model model){
		model.addAttribute("piatto", piattoService.findById(id));
		model.addAttribute("id", id);
		model.addAttribute("ingredientiObjs", piattoService.findAllIngredientiNonIn(id));
		return "admin/piattoEditAdd";
	}

	@PostMapping("/admin/piattoAddEdit")
	public String editPiattoAdd(@RequestParam("piattoId")Long id,
								Model model, @RequestParam("ingredientiIds")List<Long> ingredientiIds){
		piattoService.editAddIngredienti(id, ingredientiIds);
		return "admin/home";
	}

	@GetMapping("/admin/piattoEditRemove/{id}")
	public String viewIngredienteEditFormIdRemove(@PathVariable("id")Long id, Model model){
		model.addAttribute("piatto", piattoService.findById(id));
		model.addAttribute("id", id);
		model.addAttribute("ingredientiObjs", piattoService.findAllIngredientiIn(id));

		return "admin/piattoEditRemove";
	}

	@PostMapping("/admin/piattoRemoveEdit")
	public String editPiattoRemove(@RequestParam("piattoId")Long id,
								Model model, @RequestParam("ingredientiIds")List<Long> ingredientiIds){
		piattoService.editRemoveIngredienti(id, ingredientiIds);
		return "admin/home";
	}

	@GetMapping("/piatti/{id}")
	public String viewPiatti(@PathVariable("id")Long id, Model model){
		Set<Piatto> piatti = new LinkedHashSet<>();
		piatti.addAll(buffetService.piatti(id));
		model.addAttribute("piattiObjs",piatti);
		return "piatti";
	}

	@PostMapping("/sendPiattoId")
	public String sendIngredienteIdUser(@RequestParam("idPiatto")Long id, Model model){
		return "redirect:/ingredienti/"+id;
	}
}



