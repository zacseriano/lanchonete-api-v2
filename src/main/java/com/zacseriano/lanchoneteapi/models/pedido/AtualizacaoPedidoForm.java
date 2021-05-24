package com.zacseriano.lanchoneteapi.models.pedido;

import javax.validation.constraints.NotNull;

import com.zacseriano.lanchoneteapi.repositories.PedidoRepository;

public class AtualizacaoPedidoForm {
	@NotNull
	private Estado estado;

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}	
	
	public Pedido atualizar(Long id, PedidoRepository pedidoRepository) {
		Pedido pedido = pedidoRepository.getOne(id);
		
		pedido.setEstado(this.estado);
		
		return pedido;
	}

}
