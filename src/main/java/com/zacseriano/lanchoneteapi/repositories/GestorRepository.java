package com.zacseriano.lanchoneteapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zacseriano.lanchoneteapi.models.gestor.Gestor;

/**
 * Interface que implementa o repositório de Gestor, com métodos JPA CRUD.
 */
public interface GestorRepository extends JpaRepository<Gestor, Long> {

	/**
	 * Método para procurar um Gestor pelo seu email.
	 */
	Gestor findByEmail(String email);
}

