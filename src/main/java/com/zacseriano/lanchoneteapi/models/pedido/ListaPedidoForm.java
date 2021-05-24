package com.zacseriano.lanchoneteapi.models.pedido;

import javax.validation.constraints.Email;

public class ListaPedidoForm {
	
	//@Email
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}		

}
