package com.zacseriano.lanchoneteapi.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zacseriano.lanchoneteapi.auth.AuthForm;
import com.zacseriano.lanchoneteapi.auth.AuthenticationResponse;
import com.zacseriano.lanchoneteapi.exceptions.ClienteExistenteException;
import com.zacseriano.lanchoneteapi.exceptions.GestorExistenteException;
import com.zacseriano.lanchoneteapi.models.cliente.Cliente;
import com.zacseriano.lanchoneteapi.models.gestor.Gestor;
import com.zacseriano.lanchoneteapi.repositories.ClienteRepository;
import com.zacseriano.lanchoneteapi.repositories.GestorRepository;
import com.zacseriano.lanchoneteapi.security.ImplementsUserDetailsService;
import com.zacseriano.lanchoneteapi.util.JwtUtil;

import io.swagger.annotations.ApiOperation;

/**
 * SpringBoot RestController que implementa os end-points de autenticação/autorização da API
 */  
@RestController
public class AuthenticateResource {
	
	@Autowired
	ImplementsUserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private GestorRepository gestorRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	/**
	 * Método que gera tokens válidos de JWT para autorização de Clientes/Gestor na API
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Tudo ocorreu como esperado
	 * 400 - Bad Request: A requisição não foi aceita, algum campo está faltando
	 * 401 - Unauthorized: Chave da API inválida
	 * 403 - Forbidden: A chave da API não tem permissão para fazer a requisição
	 * 404 - Not Found: O recurso requisitado não existe
	 * 500, 502, 503, 504 - Erros de server: problemas na Java API
	 */
	@ApiOperation(value="Recebe as credenciais de login e faz a autenticação/autorização.")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthForm authForm) throws Exception {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authForm.getEmail(), authForm.getSenha())
			);
		}
		catch (BadCredentialsException e) {
			throw new Exception("Usuario ou senha incorretos", e);
		}

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authForm.getEmail());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	/**
	 * Método que Recebe as credenciais, cadastra um gestor e permite apenas uma entrada,
	 * o método checa se existe um gestor e lança uma exceção caso exista
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Tudo ocorreu como esperado
	 * 400 - Bad Request: Já existe um gestor cadastrado
	 * 401 - Unauthorized: Chave da API inválida
	 * 403 - Forbidden: A chave da API não tem permissão para fazer a requisição
	 * 404 - Not Found: O recurso requisitado não existe
	 * 500, 502, 503, 504 - Erros de server: problemas na Java API
	 */
	@ApiOperation(value="Recebe as credenciais, cadastra um gestor e permite apenas uma entrada")
	@RequestMapping(value="/cadastrarGestor", method=RequestMethod.POST)
	public Gestor cadastrarGestor(@RequestBody @Valid Gestor gestor) {
		if(gestorRepository.findByEmail(gestor.getEmail()) != null) throw new GestorExistenteException();
		return gestorRepository.save(gestor);				
	}
	
	/**
	 * Método que recebe as credenciais e cadastra um cliente, caso o seu email não exista no banco de dados
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Tudo ocorreu como esperado
	 * 400 - Bad Request: Já existe um gestor cadastrado
	 * 401 - Unauthorized: Chave da API inválida
	 * 403 - Forbidden: A chave da API não tem permissão para fazer a requisição
	 * 404 - Not Found: O recurso requisitado não existe
	 * 500, 502, 503, 504 - Erros de server: problemas na Java API
	 */
	@ApiOperation(value="Recebe as credenciais e cadastra um cliente")
	@RequestMapping(value="/cadastrarCliente", method=RequestMethod.POST)
	public Cliente cadastrarCliente(@RequestBody @Valid Cliente cliente) {
		if(clienteRepository.findByEmail(cliente.getEmail()) != null) throw new ClienteExistenteException();
		return clienteRepository.save(cliente);				
	}

}