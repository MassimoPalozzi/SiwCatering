package it.uniroma3.siw.catering.repository;

import it.uniroma3.siw.catering.model.Buffet;
import it.uniroma3.siw.catering.model.Piatto;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.catering.model.Chef;

import java.util.List;

public interface ChefRepository extends CrudRepository<Chef, Long> {

	public boolean existsByNomeAndCognomeAndNazionalita(String nome, String cognome, String nazionalita);
}
