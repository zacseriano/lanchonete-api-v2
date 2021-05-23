package com.zacseriano.lanchoneteapi.models.pedido;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.zacseriano.lanchoneteapi.models.cliente.Cliente;
import com.zacseriano.lanchoneteapi.models.item.Item;

/**
 * Classe que implementa o model/entidade Pedido
 */
@Entity
public class Pedido implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;		
	
	@NotNull(message="Selecione um item.")
	@OneToMany(mappedBy = "pedido")
	private List<Item> item;
	
	@NotNull(message="Cliente não-vinculado")
	@ManyToOne
	private Cliente cliente;
	
	@NotNull(message="Total não-calculado.")
	private BigDecimal total;
	
	private Estado estado;

	public Pedido(){
		
	}	

	public Pedido(Cliente cliente, Item item) {
		this.cliente = cliente;
		this.item = new ArrayList<Item>();
		this.item.add(item);
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public long getId() {
		return id;
	}

	public List<Item> getItem() {
		return item;
	}

	public void setItem(List<Item> itens) {
		this.item = itens;
	}		
	
	/**
	 * Método que adiciona um novo item(produtoId, quantidade) na lista de itens do pedido
	 */ 
	public void addItem(Item item) {
		this.item.add(item);		
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	/**
	 * Método que soma os valores individiais de cada item, iterando pela lista de Itens do Pedido e coletando cada valor
	 * e definindo o campo valorTotal do objeto Pedido
 	 */ 
	public void setValorTotal() {
		this.total = new BigDecimal("0");
		
		for(int i=0; i<this.item.size(); i++) 
			this.total = total.add(this.item.get(i).getValorItem());				
	}	
	
	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", item=" + item + ", cliente=" + cliente + ", total=" + total + ", estado="
				+ estado + "]";
	}
    
	/**
	 * Método que restitui o valor do estoque do Produto após algum usuário da API cancelar um pedido
 	 */
	public void aumentaEstoque() {
		for(int i=0; i<this.item.size(); i++)
		this.item.get(i).aumentaEstoque(this.item.get(i).getQuantidade());
	}		
}