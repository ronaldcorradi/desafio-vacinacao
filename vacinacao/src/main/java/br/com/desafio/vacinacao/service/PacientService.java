package br.com.desafio.vacinacao.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.desafio.vacinacao.model.Pacient;
import br.com.desafio.vacinacao.repository.PacientRepository;
import br.com.desafio.vacinacao.service.dto.PacientDTO;
import br.com.desafio.vacinacao.service.form.PacientForm;

@Service
public class PacientService {

	@Autowired
	PacientRepository repository;

	@Transactional
	public Pacient save(PacientForm pacienteForm) {
		Pacient pacient = pacienteForm.convert();
		repository.save(pacient);
		return pacient;
	}

	public Optional<Pacient> findById(Long id) {
		Optional<Pacient> pacient = repository.findById(id);
		if (pacient.isPresent()) {
			return pacient;
		} else {
			return Optional.empty();
		}
	}

	public List<PacientDTO> findAll(String name) {
		if (name == null) {
			return PacientDTO.convert(repository.findAll());
		} else {
			return PacientDTO.convert(repository.findByName(name));
		}
	}

	@Transactional
	public Pacient update(PacientForm pacientForm, Long id) {
		Optional<Pacient> pacient = repository.findById(id);
		if (pacient.isPresent()) {
			pacient.get().setBirth(pacientForm.getBirth());
			pacient.get().setName(pacientForm.getName());
			pacient.get().setCpf(pacientForm.getCpf());
			pacient.get().setEmail(pacientForm.getEmail());

			repository.save(pacient.get());
			return pacient.get();

		} else {
			return null;
		}
	}

	@Transactional
	public boolean delete(@PathVariable Long id) {
		Optional<Pacient> pacient = repository.findById(id);
		if (pacient.isPresent()) {
			repository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}

}
