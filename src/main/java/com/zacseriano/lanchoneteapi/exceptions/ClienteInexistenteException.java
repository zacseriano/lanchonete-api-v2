package com.zacseriano.lanchoneteapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Classe que implementa a ClienteInexistenteException na API
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Cliente inexistente.")  // 404
public class ClienteInexistenteException extends RuntimeException {
	private static final long serialVersionUID = 6648725043534411041L;

	public ClienteInexistenteException() {
		super("Cliente inexistente.");
	}

	public ClienteInexistenteException(String msg, Throwable t) {
		super(msg, t);
	}
}
