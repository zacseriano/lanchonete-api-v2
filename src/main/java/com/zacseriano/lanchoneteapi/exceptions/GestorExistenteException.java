package com.zacseriano.lanchoneteapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Classe que implementa a GestorExistenteException na API
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Gestor já cadastrado.")  // 404
public class GestorExistenteException extends RuntimeException {
	private static final long serialVersionUID = 6648725043534411041L;

	public GestorExistenteException() {
		super("Gestor já cadastrado.");
	}

	public GestorExistenteException(String msg, Throwable t) {
		super(msg, t);
	}
}