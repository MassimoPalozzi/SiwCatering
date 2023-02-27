package it.uniroma3.siw.catering.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import it.uniroma3.siw.catering.model.Buffet;
import it.uniroma3.siw.catering.model.Piatto;
import it.uniroma3.siw.catering.repository.BuffetRepository;
import it.uniroma3.siw.catering.repository.PiattoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.catering.model.Ingrediente;
import it.uniroma3.siw.catering.repository.IngredienteRepository;

@Service
public class IngredienteService {
	
	@Autowired
	private IngredienteRepository ingredienteRepository;

	@Autowired
	private PiattoRepository piattoRepository;

	@Autowired
	private BuffetRepository buffetRepository;
	
	@Transactional
	public void save(Ingrediente ingrediente) {
		ingredienteRepository.save(ingrediente);
	}
	
	@Transactional
	public void saveAll(List<Ingrediente> ingredienti) {
		ingredienteRepository.saveAll(ingredienti);
	}

	@Transactional
	public void edit(Long id, String nome, String origine, String descrizione){
		Ingrediente ingrediente = ingredienteRepository.findById(id).get();
		ingrediente.setNome(nome);
		ingrediente.setOrigine(origine);
		ingrediente.setDescrizione(descrizione);
		ingredienteRepository.save(ingrediente);
	}

	@Transactional
	public void deleteById(Long id) {
		ingredienteRepository.deleteById(id);
	}
	
	@Transactional
	public void delete(Ingrediente ingrediente) {
		ingredienteRepository.delete(ingrediente);
	}


	public Ingrediente createIngrediente() {
		return new Ingrediente();
	}

	public Ingrediente findById(Long id) {
		return ingredienteRepository.findById(id).get();
	}
	
	public List<Ingrediente> findAll() {
		List<Ingrediente> ingredienti = new ArrayList<>();
		for(Ingrediente i: ingredienteRepository.findAll()) 
			ingredienti.add(i);
		return ingredienti;
	}
	
	public boolean alreadyExists(Ingrediente ingrediente) {
		return ingredienteRepository.existsByNomeAndOrigine(ingrediente.getNome(), ingrediente.getOrigine());
	}
}
