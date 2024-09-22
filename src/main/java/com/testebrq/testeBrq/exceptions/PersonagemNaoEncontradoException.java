package com.testebrq.testeBrq.exceptions;

public class PersonagemNaoEncontradoException extends RuntimeException {
    public PersonagemNaoEncontradoException(String message) {
        super(message);
    }
}