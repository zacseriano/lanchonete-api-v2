package com.zacseriano.lanchoneteapi.models.produto;

import java.math.BigDecimal;

import com.zacseriano.lanchoneteapi.repositories.ProdutoRepository;

public class AtualizacaoProdutoForm {
	private String nome;
	private BigDecimal valor;
	private BigDecimal estoque;
	private Categoria categoria;
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public BigDecimal getValor() {
		return valor;
	}
	
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	public BigDecimal getEstoque() {
		return estoque;
	}
	
	public void setEstoque(BigDecimal estoque) {
		this.estoque = estoque;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}
	
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public Produto atualizar(Long id, ProdutoRepository produtoRepository) {
		Produto produto = produtoRepository.getOne(id);
		
		produto.setNome(this.nome);
		produto.setValorUnitario(this.valor);
		produto.setEstoque(this.estoque);
		produto.setCategoria(this.categoria);
		
		return produto;
	}
}
