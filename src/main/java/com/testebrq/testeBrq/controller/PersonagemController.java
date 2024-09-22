package com.testebrq.testeBrq.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.testebrq.testeBrq.modelo.Personagem;
import com.testebrq.testeBrq.modelo.PersonagemResponse;
import com.testebrq.testeBrq.service.PersonagemService;
import com.testebrq.testeBrq.utils.UtilsValidation;

@RestController
public class PersonagemController {

	private final PersonagemService personagemService;
	
	public PersonagemController(PersonagemService personagemService) {
        this.personagemService = personagemService;
    }
    
	@GetMapping("/personagens")
    public PersonagemResponse getPersonagens(@RequestParam(defaultValue = "1") String page) {
		int maxPages = personagemService.getTotalPaginas();
	    UtilsValidation.validarPagina(page, maxPages);
	    return personagemService.buscarPersonagens(page);
	}
	
	@GetMapping("/personagens/{nome}")
	public Personagem getPersonagemPorNome(@PathVariable String nome) {
	    return personagemService.buscarPersonagemPorNome(nome);
	}
}