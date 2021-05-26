package com.zacseriano.lanchoneteapi.models.pedido;

import java.util.List;

import javax.validation.constraints.Email;

import com.zacseriano.lanchoneteapi.exceptions.cliente.ClienteInexistenteException;
import com.zacseriano.lanchoneteapi.exceptions.pedido.PedidoInexistenteException;
import com.zacseriano.lanchoneteapi.models.cliente.Cliente;
import com.zacseriano.lanchoneteapi.models.item.Item;
import com.zacseriano.lanchoneteapi.repositories.ClienteRepository;
import com.zacseriano.lanchoneteapi.repositories.PedidoRepository;

public class CancelarPedidoForm {
	
	@Email
	private String clienteEmail;
	
	public String getClienteEmail() {
		return clienteEmail;
	}
	public void setClienteEmail(String clienteEmail) {
		this.clienteEmail = clienteEmail;
	}
	public Pedido converter(ClienteRepository clienteRepository, PedidoRepository pedidoRepository) {
		
		Cliente cliente = clienteRepository.findByEmail(clienteEmail);
		if(cliente == null) throw new ClienteInexistenteException();
		
		Pedido pedido = cliente.acharPedidoAberto();
		if(pedido == null) throw new PedidoInexistenteException();		
		
		pedido.setEstado(Estado.CANCELADO);
		
		List <Item> itens = pedido.getItem();
		for(int i=0; i<itens.size(); i++) {
			itens.get(i).getProduto().aumentaEstoque(itens.get(i).getQuantidade());
		}	
		
		return pedido;
	} 
}