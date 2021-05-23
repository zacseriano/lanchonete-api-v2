package com.zacseriano.lanchoneteapi.resources.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.zacseriano.lanchoneteapi.models.item.Item;

public class ItemDto {
	private String produtoNome;
	private BigDecimal quantidade;
	private BigDecimal valorItem;
	
	public ItemDto(Item item) {		
		this.produtoNome = item.getProduto().getNome();
		this.quantidade = item.getQuantidade();
		this.valorItem = item.getValorItem();
	}
	
	public String getProdutoNome() {
		return produtoNome;
	}
	
	public BigDecimal getQuantidade() {
		return quantidade;
	}
	
	public BigDecimal getValorItem() {
		return valorItem;
	}
	
	public static List<ItemDto> converter(List<Item> itens) {
		return itens.stream().map(ItemDto::new).collect(Collectors.toList());
	}
	
}
