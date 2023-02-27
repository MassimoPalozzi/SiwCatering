package it.uniroma3.siw.catering.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import it.uniroma3.siw.catering.repository.ChefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.catering.model.Buffet;
import it.uniroma3.siw.catering.model.Chef;
import it.uniroma3.siw.catering.model.Piatto;
import it.uniroma3.siw.catering.repository.BuffetRepository;

@Service
public class BuffetService {

	@Autowired
	private BuffetRepository buffetRepository;

	@Autowired
	private ChefRepository chefRepository;

	@Autowired
	private ChefService chefService;

	@Transactional
	public void save(Buffet buffet, Chef chef) {
		buffet.setChef(chef);
		chefRepository.save(chef);
		buffetRepository.save(buffet);
	}
	
	@Transactional
	public void saveAll(List<Buffet> buffet) {
		buffetRepository.saveAll(buffet);
	}
	
	@Transactional
	public void deleteById(Long id) {
		buffetRepository.deleteById(id);
	}
	
	@Transactional
	public void delete(Buffet buffet) {
		buffetRepository.delete(buffet);
	}

	public List<Buffet> findAllByPiatto(Long id){return buffetRepository.findAllByPiattiId(id);}
	
	public Buffet findById(Long id) {
		return buffetRepository.findById(id).get();
	}
	
	public List<Buffet> findAll() {
		List<Buffet> buffet = new ArrayList<>();
		for(Buffet b : buffetRepository.findAll())
			buffet.add(b);
		return buffet;
	}
	
	public boolean alreadyExists(Buffet buffet) {
		return buffetRepository.existsByNome(buffet.getNome());
	}

	public boolean alreadyExists(long id) {
		return buffetRepository.existsById(id);
	}

	
	@Transactional
	public void addPiatto(Buffet buffet, Piatto piatto) {
		buffet.addPiatto(piatto);
		buffetRepository.save(buffet);
	}

	public List<Piatto> piatti(Long id){
		Buffet buffet = buffetRepository.findById(id).get();
		return buffet.getPiatti();
	}
	public Buffet createBuffet() {
		return new Buffet();
	}

	}


