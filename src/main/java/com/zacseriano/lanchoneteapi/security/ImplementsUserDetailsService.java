package com.zacseriano.lanchoneteapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zacseriano.lanchoneteapi.models.cliente.Cliente;
import com.zacseriano.lanchoneteapi.models.gestor.Gestor;
import com.zacseriano.lanchoneteapi.repositories.ClienteRepository;
import com.zacseriano.lanchoneteapi.repositories.GestorRepository;

/*
 * Classe que implementa o UserDetailsService que é utilizado na classe WebSecurityConfig para autorizar e autenticar 
 * um usuário.
 */
@Service
public class ImplementsUserDetailsService implements UserDetailsService{

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private GestorRepository gestorRepository;
	
	/*
	 * Método que encontra um usuário pelo email informado e checa se o mesmo existe,
	 * retornando o Cliente ou Gestor, quando encontrado.
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cliente = clienteRepository.findByEmail(email);
		Gestor gestor = gestorRepository.findByEmail(email);
		
		if(cliente == null && gestor == null){
			throw new UsernameNotFoundException("Usuario não encontrado!");
		} else if(cliente!=null) {
			return cliente;
		} else {
			return gestor;
		}		
	}
}
