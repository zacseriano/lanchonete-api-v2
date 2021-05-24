package com.zacseriano.lanchoneteapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zacseriano.lanchoneteapi.models.item.Item;

/**
 * Interface que implementa o repositório de Item, com métodos JPA CRUD.
 */
public interface ItemRepository extends JpaRepository<Item, Long> {
	
	/**
	 * Método para procurar um Item pelo seu Id.
	 */
	Item findById(long id);
	
}
