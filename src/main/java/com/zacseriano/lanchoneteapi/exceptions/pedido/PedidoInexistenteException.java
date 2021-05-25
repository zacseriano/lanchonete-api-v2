package com.zacseriano.lanchoneteapi.exceptions.pedido;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Classe que implementa a PedidoInexistenteException na API
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Pedido inválido.")
public class PedidoInexistenteException extends RuntimeException {
	private static final long serialVersionUID = 6648725043534411041L;

	public PedidoInexistenteException() {
		super("Pedido inválido.");
	}

	public PedidoInexistenteException(String msg, Throwable t) {
		super(msg, t);
	}
}
