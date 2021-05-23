package com.zacseriano.lanchoneteapi.resources.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.zacseriano.lanchoneteapi.models.produto.Produto;

public class ProdutoDto {
	
	private long id;
	private String nome;
	private BigDecimal valor;
	
	public ProdutoDto(Produto produto) {	
		this.id = produto.getProdutoId();
		this.nome = produto.getNome();
		this.valor = produto.getValorUnitario();		
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

	public static List<ProdutoDto> converter(List<Produto> produtos) {
		return produtos.stream().map(ProdutoDto::new).collect(Collectors.toList());
	}

}