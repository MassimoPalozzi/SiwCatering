package it.uniroma3.siw.catering.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@SequenceGenerator(name = "ING_SEQUENCE_GENERATOR", allocationSize = 1, sequenceName = "ING_SEQ")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"nome", "origine"}))
public class Ingrediente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ING_SEQUENCE_GENERATOR")
	private Long id;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String origine;
	
	@NotBlank
	private String descrizione;

	@ManyToMany(mappedBy = "ingredienti")
	private List<Piatto> piatti;

	public Ingrediente(Long id, String nome, String origine, String descrizione) {
		this.id = id;
		this.nome = nome;
		this.origine = origine;
		this.descrizione = descrizione;
	}

	public Ingrediente() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getOrigine() {
		return origine;
	}

	public void setOrigine(String origine) {
		this.origine = origine;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}



}
