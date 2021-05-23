package com.zacseriano.lanchoneteapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zacseriano.lanchoneteapi.models.cliente.Cliente;
import com.zacseriano.lanchoneteapi.models.pedido.Pedido;

/**
 * Interface que implementa o repositório de Item, com métodos JPA CRUD.
 */
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	
	/**
	 * Método para procurar um Pedido pelo seu Id.
	 */
	Pedido findById(long id);
	
	/**
	 * Método para procurar um Pedido pelo seu Estado.
	 */
	Pedido findByEstado(String estado);
	
	/**
	 * Método para procurar um Pedido pelo seu Cliente.
	 */
	Pedido findByCliente(Cliente cliente);
}

