package com.zacseriano.lanchoneteapi.models.item;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.zacseriano.lanchoneteapi.models.cliente.Cliente;
import com.zacseriano.lanchoneteapi.models.pedido.Pedido;
import com.zacseriano.lanchoneteapi.models.produto.Produto;
import com.zacseriano.lanchoneteapi.repositories.ClienteRepository;
import com.zacseriano.lanchoneteapi.repositories.ItemRepository;
import com.zacseriano.lanchoneteapi.repositories.PedidoRepository;
import com.zacseriano.lanchoneteapi.repositories.ProdutoRepository;

public class AdicionarItemForm {
	@NotNull(message="A quantidade não pode estar vazia.")
	private BigDecimal quantidade;
	
	@NotNull(message="O Id do Produto não pode estar vazio.")
	private long produtoId;
	
	@NotNull(message="O email do Cliente não pode estar vazio.")
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
		
		Produto produto = produtoRepository.findById(produtoId);	
		produto.diminuiEstoque(quantidade);
		
		Item item = new Item(produto, quantidade);
		item.defineValorItem();
		produto.addItem(item);
		itemRepository.save(item);
		
		Cliente cliente = clienteRepository.findByEmail(clienteEmail);
		
		Pedido pedido = cliente.acharPedidoAberto();
		pedido.addItem(item);
		item.setPedido(pedido);
		pedido.setValorTotal();
		
		return pedido;
	} 
}