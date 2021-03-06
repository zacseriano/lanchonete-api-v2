package com.zacseriano.lanchoneteapi.models.gestor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.zacseriano.lanchoneteapi.models.cliente.Cliente;
import com.zacseriano.lanchoneteapi.models.pedido.Pedido;
/**
 * Classe que implementa o model/entidade Gestor
 */
@Entity
public class Gestor implements UserDetails, Serializable{	
	private static final long serialVersionUID = 1L;

	@NotBlank @Size(min = 4, max = 20)
	private String nomeEstabelecimento;
	
	@Id @Email
	private String email;
	
	@NotBlank @Size(min = 6)
	private String senha;
	
	@OneToMany(mappedBy = "gestor")
	private List<Cliente>clientes = new ArrayList<Cliente>();
	
	public Gestor() {
		
	}
	
	/**
	 * Método que lista dos Pedidos relacionados a cada Cliente presentae na lista de Clientes presente no objeto Gestor 
 	 */ 
	public List<Pedido> listarPedidos(){		
	
		List<Pedido> pedidos = new ArrayList<Pedido>();
		for(int i=0; i<this.clientes.size(); i++) 
			pedidos.addAll(this.clientes.get(i).getPedido());
		
		return pedidos;
		
	}

	public String getNomeEstabelecimento() {
		return nomeEstabelecimento;
	}

	public void setNomeEstabelecimento(String nomeEstabelecimento) {
		this.nomeEstabelecimento = nomeEstabelecimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}
	
	/**
	 * Método passa a senha modificada pelo BCryptPasswordEncoder
 	 */ 
	public void setSenha(String senha) {
		this.senha = new BCryptPasswordEncoder().encode(senha);
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
	/**
	 * Método que define a role de autorização do objeto Gestor para ROLE_GESTOR
 	 */ 
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		final List<SimpleGrantedAuthority> authorities = new LinkedList<>();
	            authorities.add(new SimpleGrantedAuthority("ROLE_GESTOR"));
	 
	        return authorities;
	}	
	
	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}