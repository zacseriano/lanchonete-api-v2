package com.zacseriano.lanchoneteapi.models.cliente;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.zacseriano.lanchoneteapi.models.gestor.Gestor;
import com.zacseriano.lanchoneteapi.models.pedido.Pedido;

/**
 * Classe que implementa o model/entidade Cliente
 */
@Entity
public class Cliente implements UserDetails, Serializable{	
	private static final long serialVersionUID = 1L;

	@NotNull(message="O nome do Cliente não pode estar vazio.")
	private String nome;
	
	@NotNull(message="A Data de Nascimento do Cliente não pode estar vazia.")
	private String dataNascimento;
	
	@NotNull(message="O Telefone do Cliente não pode estar vazio.")
	private String telefone;
	
	@Id
	@NotNull(message="O email do Cliente não pode estar vazio.")
	private String email;
	
	@NotNull(message="A senha do Cliente não pode estar vazia.")
	private String senha;
	
	@ManyToOne
	@JoinColumn(name="GESTOR_ID")
	private Gestor gestor;
	
	@OneToMany
	@JoinColumn(name="CLIENTE_ID")
	private List<Pedido> pedido;
	
	public Cliente() {
		
	}
	
	public Cliente(@NotNull String nome, @NotNull String dataNascimento, @NotNull String telefone,
			@NotNull String email, @NotNull String senha, List<Pedido> pedido) {
		super();
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.telefone = telefone;
		this.email = email;
		this.senha = senha;
		this.pedido = pedido;

	}
	
	/**
	 * Método que procura um Pedido no estado ANDAMENTO na lista de Pedidos do Cliente
 	 */ 
	/*public Pedido procurarPedido() {		
	
		String estado = "ANDAMENTO";
		for(int i=0; i<this.pedido.size(); i++) {
			if(estado == this.pedido.get(i).getEstado())
				return this.pedido.get(i);						
		}
		return null;
		
	}*/
		
	
	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getDataNascimento() {
		return dataNascimento;
	}



	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}



	public String getTelefone() {
		return telefone;
	}



	public void setTelefone(String telefone) {
		this.telefone = telefone;
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



	public List<Pedido> getPedido() {
		return pedido;
	}



	public void setPedido(List<Pedido> pedido) {
		this.pedido = pedido;
	}

	/**
	 * Método que define a role de autorização do objeto Cliente para ROLE_CLIENTE
 	 */ 
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		final List<SimpleGrantedAuthority> authorities = new LinkedList<>();
	            authorities.add(new SimpleGrantedAuthority("ROLE_CLIENTE"));
	 
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
