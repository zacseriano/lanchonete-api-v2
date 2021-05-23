package com.zacseriano.lanchoneteapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zacseriano.lanchoneteapi.models.produto.Produto;

/**
 * Interface que implementa o repositório de Produto, com métodos JPA CRUD.
 */
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	/**
	 * Método para procurar um Produto pelo seu Id.
	 */
	Produto findById(long id);
	
	/**
	 * Método para procurar um Produto pelo seu Nome.
	 */
	Produto findByNome(String nome);
}