package com.testebrq.testeBrq.modelo;

import java.util.List;

public class PersonagemResponse {
    private List<Personagem> results;
    private Info info;
    
	public List<Personagem> getResults() {
		return results;
	}
	public void setResults(List<Personagem> results) {
		this.results = results;
	}
	public Info getInfo() {
		return info;
	}
	public void setInfo(Info info) {
		this.info = info;
	} 
    
}
