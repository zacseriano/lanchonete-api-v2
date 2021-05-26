package com.zacseriano.lanchoneteapi.exceptions.produto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Classe que implementa a ProdutoInexistenteException na API
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Produto inexistente.")  // 404
public class ProdutoInexistenteException extends RuntimeException {
	private static final long serialVersionUID = 6648725043534411041L;

	public ProdutoInexistenteException() {
		super("Produto desejado não existe com a credencial informada.");
	}

	public ProdutoInexistenteException(String msg, Throwable t) {
		super(msg, t);
	}
}
