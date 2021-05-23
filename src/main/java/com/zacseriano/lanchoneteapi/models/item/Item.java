package com.zacseriano.lanchoneteapi.models.item;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.zacseriano.lanchoneteapi.models.pedido.Pedido;
import com.zacseriano.lanchoneteapi.models.produto.Produto;

/**
 * Classe que implementa o model/entidade Item, que será utilizado para armazenar o Produto, a Quantidade desejada, assim
 * como o valor total da quantidade requisitada x valor unitário do Produto, esta classe será armazenada em uma lista
 * específica de cada pedido que foi feito pela API
 */
@Entity

public class Item implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotNull(message="O nome não pode estar vazio.")
	private Produto produto;
	
	@NotNull(message="A quantidade não pode estar vazia.")
	private BigDecimal quantidade;
	
	private BigDecimal valorItem;
	
	@ManyToOne
	private Pedido pedido;
	
	public Item(){
		
	}
	
	public Item(Produto produto, BigDecimal quantidade) {
		this.produto = produto;
		this.quantidade = quantidade;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorItem() {
		return valorItem;
	}

	public void setValorItem(BigDecimal valorItem) {
		this.valorItem = valorItem;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	/**
	 * Método que calcula o valor de cada Item, multiplicando a quantidade requisitada com o valor unitário de cada Produto
 	 */
	public void defineValorItem() {//CALCULA O VALOR DO ITEM RECEBENDO UM PRODUTO E UMA QUANTIDADE
		this.valorItem = this.produto.getValorUnitario().multiply(this.quantidade);
	}
	
	/**
	 * Método que restitui o valor do estoque do Produto após algum usuário da API cancelar um pedido
 	 */
	public void aumentaEstoque(BigDecimal quantidade) {
		
		this.produto.aumentaEstoque(quantidade);
	}

}