package com.zacseriano.lanchoneteapi.models.produto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.zacseriano.lanchoneteapi.models.item.Item;

/**
 * Classe que implementa o model/entidade Produto
 */ 
@Entity
public class Produto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long produtoId;
	
	@NotNull(message="O nome não pode estar vazio.")
	private String nome;
	
	@NotNull(message="A categoria não pode estar vazia.")
	private Categoria categoria;
	
	@NotNull(message="O estoque não pode estar vazio.")
	private BigDecimal estoque;
	
	@NotNull(message="O valor não pode estar vazio.")
	private BigDecimal valorUnitario;
	
	@OneToMany(mappedBy="produto")
	private List<Item> itens = new ArrayList<Item>();
	
	public Produto(){
		
	}

	public long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(long produtoId) {
		this.produtoId = produtoId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public BigDecimal getEstoque() {
		return estoque;
	}

	public void setEstoque(BigDecimal estoque) {
		this.estoque = estoque;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	
	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}
	
	public void addItem(Item item) {
		this.itens.add(item);
	}

	/**
	 * Método que verifica se a quantidade solicitada é suportada pelo estoque, retornando true, caso o estoque supra 
	 * a demanda, e retorna false caso o estoque não supra a demanda
	 */ 
	public boolean verificarEstoque(BigDecimal quantidade) {
		BigDecimal zero = new BigDecimal("0");
		int resultado = this.getEstoque().subtract(quantidade).compareTo(zero);
		if(resultado == -1)
			return true; 
		else
			return false;					
	}
	
	/**
	 * Método que aumenta a quantidade do estoque do Produto caso um pedido seja cancelado
	 */ 
	public BigDecimal aumentaEstoque(BigDecimal quantidade) {
		return this.estoque = this.estoque.add(quantidade);
	}
	
	/**
	 * Método que diminui a quantidade do estoque do Produto caso um pedido seja solicitado
	 */
	public BigDecimal diminuiEstoque(BigDecimal quantidade) {
		return this.estoque = this.estoque.subtract(quantidade);
	}
	
}
