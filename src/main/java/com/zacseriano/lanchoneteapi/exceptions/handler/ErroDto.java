package com.zacseriano.lanchoneteapi.exceptions.handler;

public class ErroDto {
	
	private String mensagem;

	public ErroDto(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
