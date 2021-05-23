package com.zacseriano.lanchoneteapi.models.pedido;

import javax.validation.constraints.NotNull;

/**
 * Classe que é implementada com dois campos, passando um Estado e um Id de Pedido como informações no formulário 
 * utilizado no RequestBody dos seguintes métodos de endpoints:
 *   	- cancelarPedidoCliente() no /cliente/gerenciarPedido
 *   	- atualizaEstado() no /gestor/gerenciarPedido
 *  	- cancelarPedido() no /gestor/cancelarPedido
 */
public class PedidoForm {
	@NotNull(message="O estado não pode estar vazio.")
	private String estado;
	
	@NotNull(message="O Id do Pedido não pode estar vazio.")
	private long pedidoId;

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(long pedidoId) {
		this.pedidoId = pedidoId;
	}	
	
}
