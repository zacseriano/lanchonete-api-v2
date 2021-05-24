package com.zacseriano.lanchoneteapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zacseriano.lanchoneteapi.models.cliente.Cliente;
import com.zacseriano.lanchoneteapi.models.pedido.Pedido;

/**
 * Interface que implementa o repositório de Cliente, com métodos JPA CRUD.
 */
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	 
	/**
	 * Método para procurar um Cliente pelo seu email.
	 */ 
	Cliente findByEmail(String email);
	
	Pedido findByPedido_Estado(long id);

}
