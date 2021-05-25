package com.zacseriano.lanchoneteapi.models.pedido;

import javax.validation.constraints.NotNull;

import com.zacseriano.lanchoneteapi.exceptions.NovoEstadoInvalidoException;
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
		if(this.estado == Estado.CANCELADO)
			if(pedido.getEstado() == Estado.EM_ANDAMENTO || pedido.getEstado() == Estado.ENTREGUE)
				throw new NovoEstadoInvalidoException();
		
		if(this.estado == Estado.EM_ANÁLISE)
			throw new NovoEstadoInvalidoException();
		
		if(this.estado == Estado.EM_ANDAMENTO)
			if(pedido.getEstado() == Estado.CANCELADO || pedido.getEstado() == Estado.ENTREGUE)
				throw new NovoEstadoInvalidoException();
		
		if(this.estado == Estado.ENTREGUE)
			if(pedido.getEstado() == Estado.CANCELADO || pedido.getEstado() == Estado.EM_ANÁLISE)
				throw new NovoEstadoInvalidoException();
				
		pedido.setEstado(this.estado);
		
		return pedido;
	}

}
