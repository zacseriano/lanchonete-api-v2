package com.zacseriano.lanchoneteapi.models.gestor;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zacseriano.lanchoneteapi.models.cliente.Cliente;
import com.zacseriano.lanchoneteapi.models.pedido.Pedido;
/**
 * Classe que implementa o model/entidade Gestor
 */
@Entity
public class Gestor implements UserDetails, Serializable{	
	private static final long serialVersionUID = 1L;

	@NotNull(message="O Nome do Estabelecimento não pode estar vazio.")
	private String nomeEstabelecimento;
	
	@Id
	@NotNull(message="O email do Gestor não pode estar vazio.")
	private String email;
	
	@NotNull(message="A senha do Gestor não pode estar vazia.")
	@JsonIgnore
	private String senha;
	
	@OneToMany
	@JoinColumn(name="GESTOR_ID")
	private List<Cliente>clientes;
	
	public Gestor() {
		
	}

	public Gestor(@NotNull String nomeEstabelecimento, @NotNull String email, @NotNull String senha,
			List<Cliente> clientes) {
		super();
		this.nomeEstabelecimento = nomeEstabelecimento;
		this.email = email;
		this.senha = senha;
		this.clientes = clientes;
	}
	
	/**
	 * Método que lista dos Pedidos relacionados a cada Cliente presentae na lista de Clientes presente no objeto Gestor 
 	 */ 
	@SuppressWarnings("null")
	public List<Pedido> listarPedidos(){		
	
		List<Pedido> pedidos = null;
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
		// TODO Auto-generated method stub
		return this.senha;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}