package com.zacseriano.lanchoneteapi.models.item;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.zacseriano.lanchoneteapi.exceptions.PedidoInexistenteException;
import com.zacseriano.lanchoneteapi.models.cliente.Cliente;
import com.zacseriano.lanchoneteapi.models.pedido.Pedido;
import com.zacseriano.lanchoneteapi.models.produto.Produto;
import com.zacseriano.lanchoneteapi.repositories.ClienteRepository;
import com.zacseriano.lanchoneteapi.repositories.ItemRepository;
import com.zacseriano.lanchoneteapi.repositories.PedidoRepository;
import com.zacseriano.lanchoneteapi.repositories.ProdutoRepository;

public class AdicionarItemForm {
	
	@NotNull
	private BigDecimal quantidade;
	
	@NotNull
	private long produtoId;
	
	@Email
	private String clienteEmail;
	
	public BigDecimal getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}
	public long getProdutoId() {
		return produtoId;
	}
	public void setProdutoId(long produtoId) {
		this.produtoId = produtoId;
	}
	public String getClienteEmail() {
		return clienteEmail;
	}
	public void setClienteEmail(String clienteEmail) {
		this.clienteEmail = clienteEmail;
	}
	public Pedido converter(ProdutoRepository produtoRepository, ItemRepository itemRepository, ClienteRepository clienteRepository,
							PedidoRepository pedidoRepository) {
		
		Cliente cliente = clienteRepository.findByEmail(clienteEmail);
		
		Pedido pedido = cliente.acharPedidoAberto();
		if(pedido == null) throw new PedidoInexistenteException();
		
		Produto produto = produtoRepository.findById(produtoId);	
		produto.diminuiEstoque(quantidade);
		
		Item item = new Item(produto, quantidade);
		item.defineValorItem();
		produto.addItem(item);
		itemRepository.save(item);
		
		pedido.addItem(item);
		item.setPedido(pedido);
		pedido.setValorTotal();
		
		return pedido;
	} 
}
