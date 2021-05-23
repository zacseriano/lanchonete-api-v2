package com.zacseriano.lanchoneteapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Classe que implementa a ClienteExistenteException na API
 */ 
@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Cliente já cadastrado.")  // 404
public class ClienteExistenteException extends RuntimeException {
	private static final long serialVersionUID = 6648725043534411041L;

	public ClienteExistenteException() {
		super("Cliente já cadastrado.");
	}

	public ClienteExistenteException(String msg, Throwable t) {
		super(msg, t);
	}
}