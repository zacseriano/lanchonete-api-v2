package com.zacseriano.lanchoneteapi.models.item;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.zacseriano.lanchoneteapi.exceptions.cliente.ClienteInexistenteException;
import com.zacseriano.lanchoneteapi.exceptions.pedido.PedidoExistenteException;
import com.zacseriano.lanchoneteapi.models.cliente.Cliente;
import com.zacseriano.lanchoneteapi.models.pedido.Pedido;
import com.zacseriano.lanchoneteapi.models.produto.Produto;
import com.zacseriano.lanchoneteapi.repositories.ClienteRepository;
import com.zacseriano.lanchoneteapi.repositories.ItemRepository;
import com.zacseriano.lanchoneteapi.repositories.ProdutoRepository;

/**
 * Classe que é implementada com três campos, passando um valor que indica a quantidade do Produto, um Id de Produto
 * e um email de Cliente como informações no formulário utilizado no RequestBody dos seguintes 
 * métodos de endpoints:
 *   	- salvaPrimeiroItem() no /cliente/solicitarPedido
 *  	- salvaOutroItem() no /cliente/solicitarPedido
 */
public class PrimeiroItemForm {
	
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
	public Pedido converter(ProdutoRepository produtoRepository, ItemRepository itemRepository, ClienteRepository clienteRepository) {
		
		Cliente cliente = clienteRepository.findByEmail(clienteEmail);
		if(cliente == null) throw new ClienteInexistenteException();
		if(cliente.acharPedidoAberto() != null) throw new PedidoExistenteException();
		
		Produto produto = produtoRepository.findById(produtoId);
		produto.diminuiEstoque(quantidade);
		
		Item item = new Item(produto, quantidade);
		item.defineValorItem();
		produto.addItem(item);
		itemRepository.save(item);
		
		Pedido pedido = new Pedido(cliente, item);
		item.setPedido(pedido);
		pedido.setValorTotal();
		
		return pedido;
	}
}