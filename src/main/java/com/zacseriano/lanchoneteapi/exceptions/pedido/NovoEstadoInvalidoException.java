package com.zacseriano.lanchoneteapi.exceptions.pedido;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Estado Inválido.")  // 404
public class NovoEstadoInvalidoException extends RuntimeException {
	private static final long serialVersionUID = 6648725043534411041L;

	public NovoEstadoInvalidoException() {
		super("Operação de mudança de estado inválida, favor atentar-se ao estado atual do seu pedido");
	}

	public NovoEstadoInvalidoException(String msg, Throwable t) {
		super(msg, t);
	}
}
