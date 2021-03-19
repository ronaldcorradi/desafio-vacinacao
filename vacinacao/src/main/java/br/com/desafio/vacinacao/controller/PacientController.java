package br.com.desafio.vacinacao.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.desafio.vacinacao.model.Pacient;
import br.com.desafio.vacinacao.service.PacientService;
import br.com.desafio.vacinacao.service.dto.PacientDTO;
import br.com.desafio.vacinacao.service.form.PacientForm;

@RestController
@RequestMapping("pacients/v1/")
public class PacientController {
	
	@Autowired
	PacientService service;
	
	@GetMapping()
	public List<PacientDTO> findAll(String name){
		return service.findAll(name);
	}
	
	@GetMapping("/pacient/{id}")
	public ResponseEntity<PacientDTO> findById(@PathVariable Long id) {
		Optional<Pacient> pacient = service.findById(id);
		if(pacient.isPresent()) {
			return ResponseEntity.ok(new PacientDTO(pacient.get()));
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<PacientDTO> save(@RequestBody @Valid PacientForm pacientForm,
			                               UriComponentsBuilder componentsBuilder){		
		Pacient pacient = service.save(pacientForm);		
		URI uri = componentsBuilder.path("/pacient/{id}").buildAndExpand(pacient.getId()).toUri();
		return ResponseEntity.created(uri).body(new PacientDTO(pacient));
	}
	
	@PutMapping("/pacient/{id}")
	@Transactional
	public ResponseEntity<PacientDTO> update(@Valid @RequestBody PacientForm pacientForm,@PathVariable Long id){
		Pacient pacient = service.update(pacientForm,id);
		if(pacient!=null) {
			return ResponseEntity.ok(new PacientDTO(pacient));
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/pacient/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable Long id){
		if(service.delete(id)) {
			return ResponseEntity.ok().build();
		}else {
			return ResponseEntity.notFound().build();
		}
	}

}
