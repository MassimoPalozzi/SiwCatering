package it.uniroma3.siw.catering.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import it.uniroma3.siw.catering.model.Chef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.catering.model.Ingrediente;
import it.uniroma3.siw.catering.model.Piatto;
import it.uniroma3.siw.catering.repository.PiattoRepository;

@Service
public class PiattoService {
	
	@Autowired
	private PiattoRepository piattoRepository;

	@Autowired
	private IngredienteService ingredienteService;

	@Transactional
	public void save(Piatto piatto) {
		piattoRepository.save(piatto);
	}
	
	@Transactional
	public void saveAll(List<Piatto> piatti) {
		piattoRepository.saveAll(piatti);
	}
	
	@Transactional
	public void deleteById(Long id) {
		piattoRepository.deleteById(id);
	}
	
	@Transactional
	public void delete(Piatto piatto) {
		piattoRepository.delete(piatto);
	}

	public List<Piatto> findAllByIngrediente(Long id){return piattoRepository.findAllByIngredientiId(id); }
	
	public Piatto findById(Long id) {
		return piattoRepository.findById(id).get();
	}


	public Piatto createPiatto() {
		return new Piatto();
	}

	public List<Ingrediente> findAllIngredientiNonIn(Long id){
		Piatto piatto = piattoRepository.findById(id).get();
		List<Ingrediente> piatti = piatto.getIngredienti();
		List<Ingrediente> allPiatti = ingredienteService.findAll();
		allPiatti.removeAll(piatti);
		return allPiatti;
	}

	public List<Ingrediente> findAllIngredientiIn(Long id){
		Piatto piatto = piattoRepository.findById(id).get();
		List<Ingrediente> piatti = piatto.getIngredienti();
		return piatti;
	}

	@Transactional
	public void editTesto(Long id, String nome, String descrizione){
		Piatto piatto = piattoRepository.findById(id).get();
		piatto.setNome(nome);
		piatto.setDescrizione(descrizione);
		piattoRepository.save(piatto);
	}

	@Transactional
	public void editAddIngredienti(Long piattoId, List<Long> ingredientiIds){
		Piatto piatto = piattoRepository.findById(piattoId).get();
		List<Ingrediente> ingredientiIn = piatto.getIngredienti();
		List<Ingrediente> ingredienti = new ArrayList<>();
		for(Long id : ingredientiIds)
			ingredienti.add(ingredienteService.findById(id));
		ingredienti.addAll(ingredientiIn);
		piatto.setIngredienti(ingredienti);
		piattoRepository.save(piatto);

	}


	@Transactional
	public void editRemoveIngredienti(Long piattoId, List<Long> ingredientiIds){
		Piatto piatto = piattoRepository.findById(piattoId).get();
		List<Ingrediente> ingredienti = piatto.getIngredienti();
		for(Long id : ingredientiIds)
			ingredienti.remove(ingredienteService.findById(id));
		piatto.setIngredienti(ingredienti);
		piattoRepository.save(piatto);

	}
	
	public boolean alreadyExists(Piatto piatto) {
		return piattoRepository.existsByNome(piatto.getNome());
	}
	
	@Transactional
	public void addIngrediente(Piatto piatto, Ingrediente ingrediente) {
		piatto.addIngrediente(ingrediente);
	}

	public List<Piatto> findAll() {
		List<Piatto> piatti = new ArrayList<>();
		for(Piatto p : piattoRepository.findAll())
			piatti.add(p);
		return piatti;
	}

}
