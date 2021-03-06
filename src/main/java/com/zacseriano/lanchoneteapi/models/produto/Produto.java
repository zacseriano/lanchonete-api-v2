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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.zacseriano.lanchoneteapi.models.item.Item;

/**
 * Classe que implementa o model/entidade Produto
 */ 
@Entity
public class Produto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id	@GeneratedValue(strategy=GenerationType.AUTO)
	private long produtoId;
	
	@NotBlank @Size(min = 4, max = 20)
	private String nome;
	
	@NotNull
	private Categoria categoria;
	
	@NotNull
	private BigDecimal estoque;
	
	@NotNull
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
	 * M??todo que verifica se a quantidade solicitada ?? suportada pelo estoque, retornando true, caso o estoque supra 
	 * a demanda, e retorna false caso o estoque n??o supra a demanda
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
	 * M??todo que aumenta a quantidade do estoque do Produto caso um pedido seja cancelado
	 */ 
	public BigDecimal aumentaEstoque(BigDecimal quantidade) {
		return this.estoque = this.estoque.add(quantidade);
	}
	
	/**
	 * M??todo que diminui a quantidade do estoque do Produto caso um pedido seja solicitado
	 */
	public BigDecimal diminuiEstoque(BigDecimal quantidade) {
		return this.estoque = this.estoque.subtract(quantidade);
	}
	
}
