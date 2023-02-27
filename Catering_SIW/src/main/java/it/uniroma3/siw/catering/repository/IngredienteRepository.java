package it.uniroma3.siw.catering.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.catering.model.Ingrediente;

import java.util.List;

public interface IngredienteRepository extends CrudRepository<Ingrediente, Long> {

	public boolean existsByNomeAndOrigine(String nome, String origine);


}
