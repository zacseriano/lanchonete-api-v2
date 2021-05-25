package com.zacseriano.lanchoneteapi.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.zacseriano.lanchoneteapi.exceptions.cliente.ClienteExistenteException;
import com.zacseriano.lanchoneteapi.exceptions.cliente.ClienteInexistenteException;
import com.zacseriano.lanchoneteapi.exceptions.pedido.NovoEstadoInvalidoException;
import com.zacseriano.lanchoneteapi.exceptions.pedido.PedidoExistenteException;
import com.zacseriano.lanchoneteapi.exceptions.pedido.PedidoInexistenteException;
import com.zacseriano.lanchoneteapi.exceptions.produto.ProdutoExistenteException;
import com.zacseriano.lanchoneteapi.exceptions.produto.ProdutoInexistenteException;
import com.zacseriano.lanchoneteapi.exceptions.produto.SemEstoqueException;

@RestControllerAdvice
public class ErroHandler {
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ClienteInexistenteException.class)
	public ErroDto handle(ClienteInexistenteException exception) {
		
		String mensagem = exception.getMessage();
		
		return new ErroDto(mensagem);
		
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ClienteExistenteException.class)
	public ErroDto handle(ClienteExistenteException exception) {
		
		String mensagem = exception.getMessage();
		
		return new ErroDto(mensagem);
		
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NovoEstadoInvalidoException.class)
	public ErroDto handle(NovoEstadoInvalidoException exception) {
		
		String mensagem = exception.getMessage();
		
		return new ErroDto(mensagem);
		
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(PedidoExistenteException.class)
	public ErroDto handle(PedidoExistenteException exception) {
		
		String mensagem = exception.getMessage();
		
		return new ErroDto(mensagem);
		
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(PedidoInexistenteException.class)
	public ErroDto handle(PedidoInexistenteException exception) {
		
		String mensagem = exception.getMessage();
		
		return new ErroDto(mensagem);
		
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ProdutoExistenteException.class)
	public ErroDto handle(ProdutoExistenteException exception) {
		
		String mensagem = exception.getMessage();
		
		return new ErroDto(mensagem);
		
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ProdutoInexistenteException.class)
	public ErroDto handle(ProdutoInexistenteException exception) {
		
		String mensagem = exception.getMessage();
		
		return new ErroDto(mensagem);
		
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(SemEstoqueException.class)
	public ErroDto handle(SemEstoqueException exception) {
		
		String mensagem = exception.getMessage();
		
		return new ErroDto(mensagem);
		
	}
	
}
