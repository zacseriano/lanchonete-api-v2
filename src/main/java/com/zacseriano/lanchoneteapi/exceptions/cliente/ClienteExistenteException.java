package com.zacseriano.lanchoneteapi.exceptions.cliente;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Classe que implementa a ClienteExistenteException na API
 */ 
@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Cliente já cadastrado.")  // 404
public class ClienteExistenteException extends RuntimeException {
	private static final long serialVersionUID = 6648725043534411041L;

	public ClienteExistenteException() {
		super("Cliente com estas credenciais já cadastrado, favor contactar o suporte para recuperar a senha.");
	}

	public ClienteExistenteException(String msg, Throwable t) {
		super(msg, t);
	}
}