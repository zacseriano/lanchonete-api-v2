package com.zacseriano.lanchoneteapi.resources.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.zacseriano.lanchoneteapi.models.produto.Produto;

public class ProdutoDtoGestor {
	
	private long id;
	private String nome;
	private BigDecimal valor;
	private BigDecimal estoque;
	
	public ProdutoDtoGestor(Produto produto) {	
		this.id = produto.getProdutoId();
		this.nome = produto.getNome();
		this.valor = produto.getValorUnitario();
		this.estoque = produto.getEstoque();
	}
	
	public long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public BigDecimal getValor() {
		return valor;
	}
	
	public BigDecimal getEstoque() {
		return estoque;
	}

	public void setEstoque(BigDecimal estoqoue) {
		this.estoque = estoqoue;
	}

	public static List<ProdutoDtoGestor> converter(List<Produto> produtos) {
		return produtos.stream().map(ProdutoDtoGestor::new).collect(Collectors.toList());
	}

}

