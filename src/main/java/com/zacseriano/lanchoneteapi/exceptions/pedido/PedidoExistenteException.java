package com.zacseriano.lanchoneteapi.exceptions.pedido;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Pedido já existe.") 
public class PedidoExistenteException extends RuntimeException {
	private static final long serialVersionUID = 6648725043534411041L;

	public PedidoExistenteException() {
		super("Solicitação de pedido já existe! Por favor, adicione mais itens ao pedido em solicitação já existente.");
	}

	public PedidoExistenteException(String msg, Throwable t) {
		super(msg, t);
	}
}

