package br.com.desafio.vacinacao.service.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import br.com.desafio.vacinacao.model.Pacient;

public class PacientDTO {
	
	private String name;
	private String email;
	private LocalDate birth;
	
	public PacientDTO(Pacient pacient) {
		this.name = pacient.getName();
		this.email = pacient.getEmail();
		this.birth = pacient.getBirth();
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public LocalDate getBirth() {
		return birth;
	}

	public static List<PacientDTO> convert(List<Pacient> pacients) {		
		return pacients.stream().map(PacientDTO::new).collect(Collectors.toList());
	}
}
