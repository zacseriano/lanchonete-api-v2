package com.zacseriano.lanchoneteapi.resources.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.zacseriano.lanchoneteapi.models.pedido.Estado;
import com.zacseriano.lanchoneteapi.models.pedido.Pedido;

public class PedidoDtoGestor {
	private long id;
	private String nomeCliente;
	private List<ItemDto> itens;
	private Estado estado;
	private BigDecimal total;
	
	public PedidoDtoGestor(Pedido pedido) {
		this.id = pedido.getId();
		this.nomeCliente = pedido.getCliente().getNome();
		this.estado = pedido.getEstado();
		this.itens = new ArrayList<>();
		this.itens.addAll(pedido.getItem().stream().map(ItemDto::new).collect(Collectors.toList()));
		this.total = pedido.getTotal();
	}

	public long getId() {
		return id;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public List<ItemDto> getItens() {
		return itens;
	}

	public Estado getEstado() {
		return estado;
	}

	public BigDecimal getTotal() {
		return total;
	}
	
	public static List<PedidoDtoGestor> converter(List<Pedido> pedidos) {
		return pedidos.stream().map(PedidoDtoGestor::new).collect(Collectors.toList());
	}
	
}
