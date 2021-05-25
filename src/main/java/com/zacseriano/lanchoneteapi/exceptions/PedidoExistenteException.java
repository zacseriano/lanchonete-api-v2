package com.zacseriano.lanchoneteapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Pedido inexistente.")  // 404
public class PedidoExistenteException extends RuntimeException {
	private static final long serialVersionUID = 6648725043534411041L;

	public PedidoExistenteException() {
		super("Pedido inexistente.");
	}

	public PedidoExistenteException(String msg, Throwable t) {
		super(msg, t);
	}
}

