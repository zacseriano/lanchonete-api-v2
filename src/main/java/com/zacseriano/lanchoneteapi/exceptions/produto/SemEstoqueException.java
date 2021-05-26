package com.zacseriano.lanchoneteapi.exceptions.produto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Classe que implementa a SemEstoqueException na API
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Estoque insuficiente.")  // 404
public class SemEstoqueException extends RuntimeException {
	private static final long serialVersionUID = 6648725043534411041L;

	public SemEstoqueException() {
		super("Estoque insuficiente para a operação desejada");
	}

	public SemEstoqueException(String msg, Throwable t) {
			super(msg, t);
	}
}
