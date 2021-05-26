package com.zacseriano.lanchoneteapi.resources.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.zacseriano.lanchoneteapi.models.produto.Categoria;
import com.zacseriano.lanchoneteapi.models.produto.Produto;

public class ProdutoDto {
	
	private long id;
	private String nome;
	private BigDecimal valor;
	private Categoria categoria;
	
	public ProdutoDto(Produto produto) {	
		this.id = produto.getProdutoId();
		this.nome = produto.getNome();
		this.valor = produto.getValorUnitario();	
		this.categoria = produto.getCategoria();
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
	
	public Categoria getCategoria() {
		return categoria;
	}

	public static List<ProdutoDto> converter(List<Produto> produtos) {
		return produtos.stream().map(ProdutoDto::new).collect(Collectors.toList());
	}
	
	public static Page<ProdutoDto> converter(Page<Produto> produtos) {
		return produtos.map(ProdutoDto::new);
	}

}
