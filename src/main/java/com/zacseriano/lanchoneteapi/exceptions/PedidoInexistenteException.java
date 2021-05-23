package com.zacseriano.lanchoneteapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Classe que implementa a PedidoInexistenteException na API
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Pedido inexistente.")  // 404
public class PedidoInexistenteException extends RuntimeException {
	private static final long serialVersionUID = 6648725043534411041L;

	public PedidoInexistenteException() {
		super("Pedido inexistente.");
	}

	public PedidoInexistenteException(String msg, Throwable t) {
		super(msg, t);
	}
}
