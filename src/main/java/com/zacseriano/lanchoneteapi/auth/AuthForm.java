package com.zacseriano.lanchoneteapi.auth;

import javax.validation.constraints.NotNull;

/*
 * Classe que é implementada com dois campos, passando email e senha como informações para o formulário usado
 * nos seguintes métodos dos endpoints:
 * 		- createAuthenticationToken() no /login
 * 		- cadastrarGestor() no /login
 * 		- cadastrarCliente() no /login		
 */
public class AuthForm {	
	
	@NotNull(message="O email não pode estar vazio.")
	private String email;
	
	@NotNull(message="A senha não pode estar vazia.")
	private String senha;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
