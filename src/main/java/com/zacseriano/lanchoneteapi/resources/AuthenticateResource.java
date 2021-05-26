package com.zacseriano.lanchoneteapi.resources;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.zacseriano.lanchoneteapi.auth.AuthForm;
import com.zacseriano.lanchoneteapi.exceptions.cliente.ClienteExistenteException;
import com.zacseriano.lanchoneteapi.models.cliente.Cliente;
import com.zacseriano.lanchoneteapi.models.gestor.Gestor;
import com.zacseriano.lanchoneteapi.repositories.ClienteRepository;
import com.zacseriano.lanchoneteapi.repositories.GestorRepository;
import com.zacseriano.lanchoneteapi.security.ImplementsUserDetailsService;
import com.zacseriano.lanchoneteapi.security.TokenDto;
import com.zacseriano.lanchoneteapi.security.TokenService;
import io.swagger.annotations.ApiOperation;

/**
 * SpringBoot RestController que implementa os end-points de autenticação/autorização da API
 */  
@RestController
public class AuthenticateResource {
	
	@Autowired
	ImplementsUserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private GestorRepository gestorRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private TokenService tokenService;
	
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
	@PostMapping(value = "/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthForm form){
		UsernamePasswordAuthenticationToken dadosLogin = form.converter();
		
		try {
			Authentication authentication = authManager.authenticate(dadosLogin);
			String token;
			if(form.verificarGestor(gestorRepository))
				token = tokenService.gerarTokenGestor(authentication);
			token = tokenService.gerarTokenCliente(authentication);
			
			
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
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
	@RequestMapping(value="/cadastarGestor", method=RequestMethod.POST)
	@Transactional
	public ResponseEntity<Gestor> cadastrarGestor(@RequestBody @Valid Gestor gestor) {
		
		if(gestorRepository.findAll() != null) { 
			return ResponseEntity.notFound().build();
		
		} else {
			gestorRepository.save(gestor);
			return ResponseEntity.created(null).build();
	
		}
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
	public ResponseEntity<Cliente> cadastrarCliente(@RequestBody @Valid Cliente cliente, UriComponentsBuilder uriBuilder) {
		if(clienteRepository.findByEmail(cliente.getEmail()) != null) throw new ClienteExistenteException();		
		return ResponseEntity.created(null).build();				
	}

}