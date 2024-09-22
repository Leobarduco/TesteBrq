package com.testebrq.testeBrq.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.testebrq.testeBrq.exceptions.PersonagemNaoEncontradoException;
import com.testebrq.testeBrq.modelo.Info;
import com.testebrq.testeBrq.modelo.Personagem;
import com.testebrq.testeBrq.modelo.PersonagemResponse;

@Service
public class PersonagemService {

	private static final Logger logger = LoggerFactory.getLogger(PersonagemService.class);
	private final RestTemplate restTemplate;

    public PersonagemService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public int getTotalPaginas() {
        String url = "https://rickandmortyapi.com/api/character";
        String response = restTemplate.getForObject(url, String.class);
        JsonElement jsonElement = JsonParser.parseString(response);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        return jsonObject.getAsJsonObject("info").get("pages").getAsInt();
    }
    
    @Cacheable("personagens")
    public PersonagemResponse buscarPersonagens(String page) {
        logger.info("Testando o Cache, essa busca foi na API");
        String url = "https://rickandmortyapi.com/api/character?page=" + page;
        String response = restTemplate.getForObject(url, String.class);

        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        JsonArray personagens = jsonObject.getAsJsonArray("results");
        
        List<Personagem> filteredPersonagens = formatarPersonagens(personagens, false);
        
        Info info = new Gson().fromJson(jsonObject.get("info"), Info.class);

        String baseUrl = "http://localhost:8080/personagens";  

        if (info.getNext() != null && !info.getNext().isEmpty()) {
            info.setNext(info.getNext().replace("https://rickandmortyapi.com/api/character", baseUrl));
        }

        if (info.getPrev() != null && !info.getPrev().isEmpty()) {
            info.setPrev(info.getPrev().replace("https://rickandmortyapi.com/api/character", baseUrl));
        }

        PersonagemResponse resposta = new PersonagemResponse();
        resposta.setResults(filteredPersonagens);
        resposta.setInfo(info);

        return resposta;
    }
    
    @Cacheable("personagemPorNome")
    public Personagem buscarPersonagemPorNome(String nome) {
        try {
            logger.info("Testando o Cache, essa busca foi na API");
            String url = "https://rickandmortyapi.com/api/character/?name=" + nome;
            String response = restTemplate.getForObject(url, String.class);
            
            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
            JsonArray personagens = jsonObject.getAsJsonArray("results");
            
            if (personagens.size() > 0) {
                List<Personagem> filteredPersonagens = formatarPersonagens(personagens, true);
                return filteredPersonagens.get(0);
            } else {
                throw new PersonagemNaoEncontradoException("Erro ao buscar personagem: " + nome);
            }
        } catch (HttpClientErrorException e) {
            throw new PersonagemNaoEncontradoException("Erro ao buscar personagem: " + nome);
        }
    }
    
    private List<Personagem> formatarPersonagens(JsonArray personagensJson, boolean incluirEspecie) {
    	List<Personagem> lista = new ArrayList<>();
        
        for (JsonElement elemento : personagensJson) {
        	JsonObject personagemJson = elemento.getAsJsonObject();
            
            Personagem personagem = new Personagem();
            personagem.setId(personagemJson.get("id").getAsInt());
            personagem.setName(personagemJson.get("name").getAsString());
            
            if (incluirEspecie && personagemJson.has("species") && !personagemJson.get("species").isJsonNull()) {
                personagem.setSpecies(personagemJson.get("species").getAsString());
            }
            
            lista.add(personagem);
        }
        
        return lista;
    }
}
