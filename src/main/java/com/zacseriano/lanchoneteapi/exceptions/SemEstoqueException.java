package com.zacseriano.lanchoneteapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Classe que implementa a SemEstoqueException na API
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Sem estoque.")  // 404
public class SemEstoqueException extends RuntimeException {
	private static final long serialVersionUID = 6648725043534411041L;

	public SemEstoqueException() {
		super("Sem estoque.");
	}

	public SemEstoqueException(String msg, Throwable t) {
			super("Sem estoque.", t);
	}
}
