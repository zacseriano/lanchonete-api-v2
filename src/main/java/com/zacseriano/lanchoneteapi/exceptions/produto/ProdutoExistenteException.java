package com.zacseriano.lanchoneteapi.exceptions.produto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Classe que implementa a ProdutoExistenteException na API
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Cliente já cadastrado.")  // 404
public class ProdutoExistenteException extends RuntimeException {
	private static final long serialVersionUID = 6648725043534411041L;

	public ProdutoExistenteException() {
		super("Produto existente.");
	}

	public ProdutoExistenteException(String msg, Throwable t) {
		super(msg, t);
	}
}
